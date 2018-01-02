package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import uk.co.ramyun.herblore.potion.Tar;
import uk.co.ramyun.herblore.util.AnimationTracker;
import uk.co.ramyun.herblore.util.BankSleep;
import uk.co.ramyun.herblore.util.Banker;
import uk.co.ramyun.herblore.util.MakeWidget;

public class MakeTar extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeTar.java
	 */

	private final Banker banker = new Banker();
	private final MakeWidget makeWidget = new MakeWidget();
	private AnimationTracker animationTracker;
	private Tar tar = Tar.GUAM_TAR;

	public MakeTar(Tar toMake) {
		this.tar = toMake;
		panel.add(new JLabel("Tar to make:"));
		JComboBox<Tar> tarCombo = new JComboBox<Tar>(Tar.values());
		tarCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tar = (Tar) tarCombo.getSelectedItem();
			}
		});
		panel.add(tarCombo);
	}

	@Override
	public String getName() {
		return "Make " + tar.getName();
	}

	@Override
	public boolean canRun(MethodProvider mp) {
		return tar.hasLevel(mp);
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
	public void run(MethodProvider mp) throws InterruptedException {
		if (tar.canMake(mp)) {
			if (animationTracker.isBusy(2000)) {
				// ...Then we're making (not-so-glorious) tars!
				mp.getMouse().moveOutsideScreen();
				MethodProvider.sleep(50);
			} else if (mp.getBank().isOpen()) {
				if (mp.getBank().close()) new BankSleep(mp, false, 4000).sleep();
			} else {
				if (makeWidget.buttonAvailable(mp, tar.getButtonName())) {
					makeWidget.pressButtonAnimation(mp, tar.getButtonName(), "Make sets:");
				} else {
					if (mp.getInventory().isItemSelected()) {
						if (mp.getInventory().getSelectedItemName().equals("Swamp tar")) {
							if (mp.getInventory().interact("Use", tar.getHerb().getFullName()))
								makeWidget.sleepUntilButtonAvailable(mp, tar.getButtonName(), 2000);
						} else if (mp.getInventory().getSelectedItemName().equals(tar.getHerb().getFullName())) {
							if (mp.getInventory().interact("Use", "Swamp tar"))
								makeWidget.sleepUntilButtonAvailable(mp, tar.getButtonName(), 2000);
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
								if (mp.getInventory().interact("Use", tar.getHerb().getFullName()))
									waitForSelection.sleep();
								break;
							case 1:
								if (mp.getInventory().interact("Use", "Swamp tar")) waitForSelection.sleep();
								break;
						}
					}
				}
			}
		} else {
			if (mp.getBank().isOpen()) {
				if (!mp.getInventory().isEmptyExcept("Swamp tar", "Pestle and mortar", tar.getName(),
						tar.getHerb().getFullName())) {
					if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
				} else {
					switch (banker.withdrawItem(mp, "Pestle and mortar", 1, false, false, false)) {
						case INSUFFICIENT_AMOUNT:
							target.forceAccomplished();
							break;
						case INSUFFICIENT_SPACE:
							if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
							break;
						case SUCCESS:
							if (tar.hasSufficientSwampTar(mp)) {
								switch (banker.withdrawItem(mp, tar.getHerb().getFullName(),
										mp.getInventory().getEmptySlots(), false, false, false)) {
									case INSUFFICIENT_AMOUNT:
										target.forceAccomplished();
										break;
									case INSUFFICIENT_SPACE:
										if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
										break;
									default:
										break;
								}
							} else {
								switch (banker.withdrawAllButOne(mp, 15, "Swamp tar")) {
									case INSUFFICIENT_AMOUNT:
										target.forceAccomplished();
										break;
									case INSUFFICIENT_SPACE:
										if (mp.getBank().depositAll()) banker.sleepUntilInventoryEmpty(mp, 4000);
										break;
									default:
										break;
								}
							}
							break;
						default:
							break;
					}
				}
			} else if (mp.getBank().open()) new BankSleep(mp, true, 4000).sleep();
		}
	}
}
