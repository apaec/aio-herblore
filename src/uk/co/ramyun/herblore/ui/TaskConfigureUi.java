package uk.co.ramyun.herblore.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import uk.co.ramyun.herblore.target.AbstractTarget;
import uk.co.ramyun.herblore.task.HerbloreTask;

public class TaskConfigureUi extends JPanel {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file TaskConfigureUi.java
	 */

	private static final long serialVersionUID = 1L;

	private final HerbloreTask task;
	private final AbstractTarget[] targets;
	private final TargetPanel targetPanel;

	public TaskConfigureUi(HerbloreTask toConfigure, AbstractTarget... targets) {
		this.task = toConfigure;
		this.targets = targets;
		this.targetPanel = new TargetPanel(this.targets);
		setLayout(new GridBagLayout());
		Insets two = new Insets(5, 5, 5, 5);
		add(task.getPanel(), createConstraints(0, 0, 1, GridBagConstraints.HORIZONTAL, two));
		add(targetPanel, createConstraints(0, 1, 1, GridBagConstraints.HORIZONTAL, two));
	}

	public HerbloreTask getTask() {
		return task;
	}

	public AbstractTarget getTarget() {
		return targetPanel.getTarget();
	}

	private GridBagConstraints createConstraints(int x, int y, int width, int fill, Insets insets) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.fill = fill;
		constraints.insets = insets;
		return constraints;
	}

}
