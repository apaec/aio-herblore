package uk.co.ramyun.herblore.task;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.target.AbstractTarget;
import uk.co.ramyun.herblore.target.NoTarget;

public abstract class HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file HerbloreTask.java
	 */

	protected AbstractTarget target = new NoTarget();
	protected JPanel panel = new JPanel(), containerPanel = new JPanel();

	public HerbloreTask() {
		containerPanel.setBorder(new TitledBorder("Task Settings"));
		containerPanel.setLayout(new GridLayout());
		panel.setLayout(new GridLayout(1, 2, 5, 5));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		containerPanel.add(panel);
	}

	public abstract String getName();

	public abstract boolean canRun(MethodProvider mp);

	/**
	 * Runs the task
	 * 
	 * @param mp the script MethodProvider instance
	 */
	protected abstract void run(MethodProvider mp);

	/**
	 * Access point to the task. Starts the task if necessary, otherwise runs it
	 * 
	 * @param mp the script MethodProvider instance
	 */
	public void execute(MethodProvider mp) {
		if (!target.started()) target.start(mp);
		else run(mp);
	}

	/**
	 * Gets the ui panel to configure the task
	 * 
	 * @return the ui panel
	 */
	public JPanel getPanel() {
		return containerPanel;
	}

	/**
	 * Gets the target associated with this task
	 * 
	 * @return the task target
	 */
	public AbstractTarget getTarget() {
		return target;
	}

	/**
	 * Sets the target associated with this task
	 * 
	 * @param newTarget the new task target
	 */
	public void setTarget(AbstractTarget newTarget) {
		this.target = newTarget;
	}

	/**
	 * Determines whether the task is complete, based on the target status
	 * 
	 * @param mp the script MethodProvider instance
	 * @return whether this task is complete
	 */
	public boolean isComplete(MethodProvider mp) {
		return target.accomplished(mp);
	}

	@Override
	public String toString() {
		return getName() + ", Stop when: " + getTarget() + " (" + getTarget().getThreshold() + ")";
	}

}
