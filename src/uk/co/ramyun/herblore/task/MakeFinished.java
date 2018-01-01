package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import uk.co.ramyun.herblore.potion.Potion;
import uk.co.ramyun.herblore.util.AnimationTracker;
import uk.co.ramyun.herblore.util.BankSleep;
import uk.co.ramyun.herblore.util.Banker;
import uk.co.ramyun.herblore.util.MakeWidget;

public class MakeFinished extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeFinished.java
	 */

	private final Banker banker = new Banker();
	private final MakeWidget makeWidget = new MakeWidget();
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

	@Override
	public void run(MethodProvider mp) throws InterruptedException {
		if (potion.canMake(mp)) {
			if (animationTracker.isBusy(2000)) {
				// ...Then we're making glorious potions!
				mp.getMouse().moveOutsideScreen();
				MethodProvider.sleep(50);
			} else if (mp.getBank().isOpen()) {
				if (mp.getBank().close()) new BankSleep(mp, false, 4000).sleep();
			} else {
				if (makeWidget.buttonAvailable(mp, potion.getName(3))) {
					makeWidget.pressButtonAnimation(mp, potion.getName(3), "Make");
				} else {
					if (mp.getInventory().isItemSelected()) {
						if (mp.getInventory().getSelectedItemName().equals(potion.getUnfPotion().getName())) {
							if (mp.getInventory().interact("Use", potion.getSecondary().getName()))
								makeWidget.sleepUntilButtonAvailable(mp, potion.getName(3), 2000);
						} else if (mp.getInventory().getSelectedItemName().equals(potion.getSecondary().getName())) {
							if (mp.getInventory().interact("Use", potion.getUnfPotion().getName()))
								makeWidget.sleepUntilButtonAvailable(mp, potion.getName(3), 2000);
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
					if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
				} else {
					switch (MethodProvider.random(0, 2)) {
						case 0:
							switch (banker.withdrawItem(mp, potion.getSecondary().getName(), 14, false, false)) {
								case INSUFFICIENT_AMOUNT:
									target.forceAccomplished();
									break;
								case INSUFFICIENT_SPACE:
									if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
									break;
								default:
									break;
							}
							break;
						case 1:
							switch (banker.withdrawItem(mp, potion.getUnfPotion().getName(), 14, false, false)) {
								case INSUFFICIENT_AMOUNT:
									target.forceAccomplished();
									break;
								case INSUFFICIENT_SPACE:
									if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
									break;
								default:
									break;
							}
							break;
					}
				}
			} else if (mp.getBank().open()) new BankSleep(mp, true, 4000).sleep();
		}
	}
}
