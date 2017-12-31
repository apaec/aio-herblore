package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Potion;

public class MakeFinished extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeFinished.java
	 */

	private Potion potion = Potion.ATTACK_POTION;

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
	public boolean canRun(MethodProvider mp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run(MethodProvider mp) {
		// TODO Auto-generated method stub
	}
}
