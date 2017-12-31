package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Unfinished;

public class MakeUnfinished extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeUnfinished.java
	 */

	private Unfinished unfinished = Unfinished.GUAM_POTION;

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
	public boolean canRun(MethodProvider mp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run(MethodProvider mp) {
		// TODO Auto-generated method stub
	}
}
