package uk.co.ramyun.herblore.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.ramyun.herblore.target.AbstractTarget;

public class TargetPanel extends JPanel {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file TargetPanel.java
	 */

	private static final long serialVersionUID = 1L;
	private final JComboBox<AbstractTarget> targetCombo;
	private final JSpinner spinner = new JSpinner();
	private final JPanel contentPane = new JPanel();

	private AbstractTarget chosen;

	public TargetPanel(AbstractTarget... targets) {
		if (targets.length < 1) throw new IllegalArgumentException("Insufficient target options");
		this.targetCombo = new JComboBox<AbstractTarget>(targets);
		this.chosen = targets[0];
		spinner.setModel(getSpinnerModelForTarget(chosen));
		spinner.setEnabled(chosen.getThreshold() > 0);

		spinner.setPreferredSize(new Dimension(String.valueOf(Arrays.stream(targets).map(AbstractTarget::maxThreshold)
				.reduce((a, b) -> Math.max(a, b)).orElse(100000L)).length() * 10, spinner.getHeight()));

		targetCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chosen = (AbstractTarget) targetCombo.getSelectedItem();
				spinner.setModel(getSpinnerModelForTarget(chosen));
				spinner.setEnabled(chosen.getThreshold() > 0);
			}
		});

		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				chosen.setThreshold((Long) spinner.getValue());
			}
		});

		setBorder(new TitledBorder("Target Settings"));
		setLayout(new GridLayout());
		contentPane.setBorder(new EmptyBorder(4, 4, 4, 4));
		contentPane.setLayout(new GridLayout(1, 2, 4, 4));
		contentPane.add(targetCombo);
		contentPane.add(spinner);
		add(contentPane);
	}

	private SpinnerNumberModel getSpinnerModelForTarget(AbstractTarget t) {
		Long val = t.getThreshold(), min = t.minThreshold(), max = t.maxThreshold(), step = t.getStep();
		return new SpinnerNumberModel(val, min, max, step);
	}

	public AbstractTarget getTarget() {
		return chosen;
	}

}
