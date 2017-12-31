package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Tar;

public class MakeTar extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeTar.java
	 */

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run(MethodProvider mp) {
		// TODO Auto-generated method stub
	}
}
