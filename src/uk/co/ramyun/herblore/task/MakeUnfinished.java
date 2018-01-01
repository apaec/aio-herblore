package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import uk.co.ramyun.herblore.potion.Unfinished;
import uk.co.ramyun.herblore.util.AnimationTracker;
import uk.co.ramyun.herblore.util.BankSleep;
import uk.co.ramyun.herblore.util.Banker;
import uk.co.ramyun.herblore.util.MakeWidget;

public class MakeUnfinished extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeUnfinished.java
	 */

	private final Banker banker = new Banker();
	private final MakeWidget makeWidget = new MakeWidget();
	private Unfinished unfinished = Unfinished.GUAM_POTION;
	private AnimationTracker animationTracker;

	public MakeUnfinished(Unfinished toMake) {
		this.unfinished = toMake;
		panel.add(new JLabel("Potion to make:"));
		JComboBox<Unfinished> potionCombo = new JComboBox<Unfinished>(Unfinished.values());
		potionCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unfinished = (Unfinished) potionCombo.getSelectedItem();
			}
		});
		panel.add(potionCombo);
	}

	@Override
	public String getName() {
		return "Make potion: " + unfinished.getName();
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
		return unfinished.hasLevel(mp);
	}

	@Override
	public void run(MethodProvider mp) throws InterruptedException {
		if (unfinished.canMake(mp)) {
			if (animationTracker.isBusy(2000)) {
				// ...Then we're making glorious potions!
				MethodProvider.sleep(50);
			} else if (mp.getBank().isOpen()) {
				if (mp.getBank().close()) new BankSleep(mp, false, 4000).sleep();
			} else {
				if (makeWidget.buttonAvailable(mp, unfinished.getName())) {
					makeWidget.pressButtonAnimation(mp, unfinished.getName(), "Make");
				} else {
					if (mp.getInventory().isItemSelected()) {
						if (mp.getInventory().getSelectedItemName().equals(unfinished.getVessel().getName())) {
							if (mp.getInventory().interact("Use", unfinished.getHerb().getFullName()))
								makeWidget.sleepUntilButtonAvailable(mp, unfinished.getName(), 2000);
						} else if (mp.getInventory().getSelectedItemName().equals(unfinished.getHerb().getFullName())) {
							if (mp.getInventory().interact("Use", unfinished.getVessel().getName()))
								makeWidget.sleepUntilButtonAvailable(mp, unfinished.getName(), 2000);
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
								if (mp.getInventory().interact("Use", unfinished.getHerb().getFullName()))
									waitForSelection.sleep();
								break;
							case 1:
								if (mp.getInventory().interact("Use", unfinished.getVessel().getName()))
									waitForSelection.sleep();
								break;
						}
					}
				}
			}
		} else {
			if (mp.getBank().isOpen()) {
				if (!mp.getInventory().isEmptyExcept(unfinished.getHerb().getFullName(),
						unfinished.getVessel().getName())) {
					if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
				} else {
					switch (MethodProvider.random(0, 2)) {
						case 0:
							switch (banker.withdrawItem(mp, unfinished.getHerb().getFullName(), 14, false, false)) {
								case ACTION_FAIL:
									break;
								case INSUFFICIENT_AMOUNT:
									target.forceAccomplished();
									break;
								case INSUFFICIENT_SPACE:
									if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
									break;
								case SUCCESS:
									break;
							}
							break;
						case 1:
							switch (banker.withdrawItem(mp, unfinished.getVessel().getName(), 14, false, false)) {
								case ACTION_FAIL:
									break;
								case INSUFFICIENT_AMOUNT:
									target.forceAccomplished();
									break;
								case INSUFFICIENT_SPACE:
									if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
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
