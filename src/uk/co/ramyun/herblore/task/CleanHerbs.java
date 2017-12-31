package uk.co.ramyun.herblore.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Herb;

public class CleanHerbs extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file CleanHerbs.java
	 */

	private Herb herb = Herb.GUAM_LEAF;

	public CleanHerbs(Herb toClean) {
		this.herb = toClean;
		panel.add(new JLabel("Herb to clean:"));
		JComboBox<Herb> herbCombo = new JComboBox<Herb>(Herb.values());
		herbCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				herb = (Herb) herbCombo.getSelectedItem();
			}
		});
		panel.add(herbCombo);
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
