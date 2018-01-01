package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import uk.co.ramyun.herblore.potion.Potion;
import uk.co.ramyun.herblore.util.AnimationTracker;
import uk.co.ramyun.herblore.util.BankSleep;
import uk.co.ramyun.herblore.util.ItemSleep;

public class MakeFinished extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeFinished.java
	 */

	private Potion potion = Potion.ATTACK_POTION;
	private AnimationTracker animationTracker;

	public MakeFinished(Potion toMake) {
		this.potion = toMake;
		panel.add(new JLabel("Potion to make:"));
		JComboBox<Potion> potionCombo = new JComboBox<Potion>(Potion.values());
		potionCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				potion = (Potion) potionCombo.getSelectedItem();
			}
		});
		panel.add(potionCombo);
	}

	@Override
	public String getName() {
		return "Make potion: " + potion.getGenericName();
	}

	@Override
	public void start(MethodProvider mp) {
		super.start(mp);
		animationTracker = new AnimationTracker(mp);
		new Thread(animationTracker).start();
	}

	@Override
	public void stop(MethodProvider mp) {
		super.stop(mp);
		animationTracker.exit();
	}

	@Override
	public boolean canRun(MethodProvider mp) {
		return potion.hasLevel(mp);
	}

	/**
	 * Describes the status of a bank withdraw attempt.
	 */
	private enum WithdrawStatus {
		SUCCESS(false), INSUFFICIENT_AMOUNT(true), INSUFFICIENT_SPACE(false), ACTION_FAIL(false);
		private final boolean fatal;

		WithdrawStatus(boolean fatal) {
			this.fatal = fatal;
		}

		@SuppressWarnings("unused")
		public boolean isFatal() {
			return fatal;
		}
	}

	public WithdrawStatus withdrawItem(MethodProvider mp, String itemName, int total, boolean stackable, boolean noted)
			throws InterruptedException {
		if (mp.getBank().isOpen()) {
			int current = 0, emptySlots = mp.getInventory().getEmptySlots(), needed = 0;
			ItemSleep itemSleep = new ItemSleep(mp, itemName, 4000),
					itemSleepGreater = new ItemSleep(mp, itemName, false, 4000);
			BankMode requiredMode = noted ? BankMode.WITHDRAW_NOTE : BankMode.WITHDRAW_ITEM;
			if (mp.getInventory().contains(itemName)) current = (int) mp.getInventory().getAmount(itemName);
			if (current == total) return WithdrawStatus.SUCCESS;
			needed = total - current;
			if (!mp.getBank().getWithdrawMode().equals(requiredMode)) mp.getBank().enableMode(requiredMode);
			if (mp.getBank().getAmount(itemName) < needed) return WithdrawStatus.INSUFFICIENT_AMOUNT;
			if (stackable) {
				if (emptySlots >= 1 || current > 0) {
					if (mp.getBank().withdraw(itemName, needed) && itemSleep.sleep()) return WithdrawStatus.SUCCESS;
				} else if (emptySlots == 0 && current == 0) return WithdrawStatus.INSUFFICIENT_SPACE;
			} else {
				if (emptySlots == 0) {
					return WithdrawStatus.INSUFFICIENT_SPACE;
				} else if (emptySlots == needed) {
					if (mp.getBank().withdrawAll(itemName) && itemSleep.sleep()) return WithdrawStatus.SUCCESS;
				} else if (emptySlots < needed) {
					return WithdrawStatus.INSUFFICIENT_SPACE;
				} else if (current > total) {
					if (mp.getBank().deposit(itemName, current - total) && itemSleepGreater.sleep())
						return WithdrawStatus.SUCCESS;
				} else if (current < total) {
					if (mp.getBank().withdraw(itemName, needed) && itemSleep.sleep()) return WithdrawStatus.SUCCESS;
				}
			}
		} else if (mp.getBank().open()) new BankSleep(mp, true, 4000).sleep();
		return WithdrawStatus.ACTION_FAIL;
	}

	@SuppressWarnings("unchecked")
	private RS2Widget getForActionExact(MethodProvider mp, int root, String seq) {
		return mp.getWidgets().singleFilter(root, w -> w.getInteractActions() != null
				&& Arrays.stream(w.getInteractActions()).filter(s -> s.equals(seq)).count() > 0);
	}

	@SuppressWarnings("unchecked")
	private RS2Widget getForSpellName(MethodProvider mp, int root, String seq) {
		return mp.getWidgets().singleFilter(root, w -> w.getSpellName().contains(seq));
	}

	@Override
	public void run(MethodProvider mp) throws InterruptedException {
		if (potion.canMake(mp)) {
			if (animationTracker.isBusy(2000)) {
				// ...Then we're making glorious potions!
				MethodProvider.sleep(50);
			} else if (mp.getBank().isOpen()) {
				if (mp.getBank().close()) new BankSleep(mp, false, 4000).sleep();
			} else {
				RS2Widget mainWidget = getForSpellName(mp, 270, potion.getName(3));
				if (!mp.getInventory().isItemSelected() && mainWidget != null) {
					RS2Widget valueWidget = getForActionExact(mp, 270, "All");
					if (valueWidget != null && valueWidget.interact("All")) {
						new ConditionalSleep(5000) {
							@Override
							public boolean condition() throws InterruptedException {
								return getForActionExact(mp, 270, "All") == null;
							}
						}.sleep();
					} else if (mainWidget.interact("Make")) {
						new ConditionalSleep(4000) {
							@Override
							public boolean condition() throws InterruptedException {
								return mp.myPlayer().isAnimating();
							}
						}.sleep();
					}
				} else {
					if (mp.getInventory().isItemSelected()) {
						ConditionalSleep waitForWidget = new ConditionalSleep(2000) {
							@Override
							public boolean condition() throws InterruptedException {
								RS2Widget mainWidget = getForSpellName(mp, 270, potion.getName(3));
								return mainWidget != null && mainWidget.isVisible();
							}
						};
						if (mp.getInventory().getSelectedItemName().equals(potion.getUnfPotion().getName())) {
							if (mp.getInventory().interact("Use", potion.getSecondary().getName()))
								waitForWidget.sleep();
						} else if (mp.getInventory().getSelectedItemName().equals(potion.getSecondary().getName())) {
							if (mp.getInventory().interact("Use", potion.getUnfPotion().getName()))
								waitForWidget.sleep();
						} else mp.getInventory().deselectItem();
					} else {
						ConditionalSleep waitForSelection = new ConditionalSleep(2000) {
							@Override
							public boolean condition() throws InterruptedException {
								return mp.getInventory().isItemSelected();
							}
						};
						switch (MethodProvider.random(0, 2)) {
							case 0:
								if (mp.getInventory().interact("Use", potion.getSecondary().getName()))
									waitForSelection.sleep();
								break;
							case 1:
								if (mp.getInventory().interact("Use", potion.getUnfPotion().getName()))
									waitForSelection.sleep();
								break;
						}
					}
				}
			}
		} else {
			if (mp.getBank().isOpen()) {
				if (!mp.getInventory().isEmptyExcept(potion.getSecondary().getName(),
						potion.getUnfPotion().getName())) {
					if (mp.getBank().depositAll()) new ConditionalSleep(5000) {
						@Override
						public boolean condition() throws InterruptedException {
							return mp.getInventory().isEmpty();
						}
					}.sleep();
				} else {
					switch (MethodProvider.random(0, 2)) {
						case 0:
							switch (withdrawItem(mp, potion.getSecondary().getName(), 14, false, false)) {
								case ACTION_FAIL:
									break;
								case INSUFFICIENT_AMOUNT:
									target.forceAccomplished();
									break;
								case INSUFFICIENT_SPACE:
									if (mp.getBank().depositAll()) new ConditionalSleep(5000) {
										@Override
										public boolean condition() throws InterruptedException {
											return mp.getInventory().isEmpty();
										}
									}.sleep();
									break;
								case SUCCESS:
									break;
							}
							break;
						case 1:
							switch (withdrawItem(mp, potion.getUnfPotion().getName(), 14, false, false)) {
								case ACTION_FAIL:
									break;
								case INSUFFICIENT_AMOUNT:
									target.forceAccomplished();
									break;
								case INSUFFICIENT_SPACE:
									if (mp.getBank().depositAll()) new ConditionalSleep(5000) {
										@Override
										public boolean condition() throws InterruptedException {
											return mp.getInventory().isEmpty();
										}
									}.sleep();
									break;
								case SUCCESS:
									break;
							}
							break;
					}
				}
			} else if (mp.getBank().open()) new BankSleep(mp, true, 4000).sleep();
		}
	}
}
