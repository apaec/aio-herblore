package uk.co.ramyun.herblore.task;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import uk.co.ramyun.herblore.potion.Herb;
import uk.co.ramyun.herblore.util.BankSleep;
import uk.co.ramyun.herblore.util.InventoryNavigator;
import uk.co.ramyun.herblore.util.ItemSleep;

public class CleanHerbs extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file CleanHerbs.java
	 */

	private final InventoryNavigator invNav = new InventoryNavigator(7, 4);
	private Mode mode = Mode.SNAKE_HORIZONTAL_SUPERFAST;
	private Herb herb = Herb.GUAM_LEAF;
	private ItemSleep itemSleep;

	public CleanHerbs(Herb toClean) {
		this.herb = toClean;

		JComboBox<Herb> herbCombo = new JComboBox<Herb>(Herb.cleanableValues());
		herbCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				herb = (Herb) herbCombo.getSelectedItem();
			}
		});

		JComboBox<Mode> modeCombo = new JComboBox<Mode>(Mode.values());
		modeCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = (Mode) modeCombo.getSelectedItem();
			}
		});

		panel.setLayout(new GridLayout(2, 2, 5, 5));
		panel.add(new JLabel("Herb to clean:"));
		panel.add(herbCombo);
		panel.add(new JLabel("Cleaning mode:"));
		panel.add(modeCombo);
	}

	@Override
	public String getName() {
		return "Clean herb: " + herb.getBaseName();
	}

	@Override
	public boolean canRun(MethodProvider mp) {
		return herb.hasLevelToClean(mp);
	}

	private enum Mode {
		CONDITIONAL, NORMAL, SNAKE_HORIZONTAL, SNAKE_VERTICAL, NORMAL_SUPERFAST, SNAKE_HORIZONTAL_SUPERFAST, SNAKE_VERTICAL_SUPERFAST;
		@Override
		public String toString() {
			String mode = super.toString().toLowerCase().replace("_", " ");
			return Character.toUpperCase(mode.charAt(0)) + mode.substring(1, mode.length());
		}
	}

	private boolean slotInteract(MethodProvider mp, int slot, String item, String interaction) {
		if (slot >= 0 && slot < 28) {
			if (mp.getInventory().getItemInSlot(slot).getName().equals(item))
				return mp.getInventory().interact(slot, interaction);
		}
		return false;
	}

	@Override
	public void run(MethodProvider mp) throws InterruptedException {
		if (mp.getInventory().contains(herb.getGrimyName())) {
			if (mp.getBank().isOpen()) {
				if (mp.getBank().close()) new BankSleep(mp, false, 4000).sleep();
			} else switch (mode) {
				case CONDITIONAL:
					itemSleep = new ItemSleep(mp, herb.getFullName(), 2000);
					if (mp.getInventory().interact("Clean", herb.getGrimyName())) itemSleep.sleep();
					break;
				case NORMAL:
					slotInteract(mp, invNav.nextNormal(), herb.getGrimyName(), "Clean");
					break;
				case SNAKE_HORIZONTAL:
					slotInteract(mp, invNav.nextSnakeHorizontal(), herb.getGrimyName(), "Clean");
					break;
				case SNAKE_VERTICAL:
					slotInteract(mp, invNav.nextSnakeVertical(), herb.getGrimyName(), "Clean");
					break;
				case SNAKE_HORIZONTAL_SUPERFAST:
					for (int i = 0; i < 28; i++)
						slotInteract(mp, invNav.nextSnakeHorizontal(), herb.getGrimyName(), "Clean");
					break;
				case NORMAL_SUPERFAST:
					for (int i = 0; i < 28; i++)
						slotInteract(mp, invNav.nextNormal(), herb.getGrimyName(), "Clean");
					break;
				case SNAKE_VERTICAL_SUPERFAST:
					for (int i = 0; i < 28; i++)
						slotInteract(mp, invNav.nextSnakeVertical(), herb.getGrimyName(), "Clean");
					break;
			}
		} else {
			invNav.reset();
			if (mp.getBank().isOpen()) {
				if (!mp.getInventory().isEmpty()) {
					if (mp.getBank().depositAll()) new ConditionalSleep(5000) {
						@Override
						public boolean condition() throws InterruptedException {
							return mp.getInventory().isEmpty();
						}
					}.sleep();
				} else {
					if (mp.getBank().contains(herb.getGrimyName())) {
						itemSleep = new ItemSleep(mp, herb.getGrimyName(), 2000);
						if (mp.getBank().withdrawAll(herb.getGrimyName())) itemSleep.sleep();
					} else target.forceAccomplished();
				}
			} else if (mp.getBank().open()) new BankSleep(mp, true, 4000).sleep();
		}
	}

}
