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
	protected boolean started = false;

	public HerbloreTask() {
		containerPanel.setBorder(new TitledBorder("Task Settings"));
		containerPanel.setLayout(new GridLayout());
		panel.setLayout(new GridLayout(1, 2, 5, 5));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		containerPanel.add(panel);
	}

	/**
	 * Returns the name of the task
	 * 
	 * @return the formatted name of the task
	 */
	public abstract String getName();

	/**
	 * Determines whether the user meets the requirements for this task to run
	 * 
	 * @param mp the script MethodProvider instance
	 * @return whether the task can run
	 */
	public abstract boolean canRun(MethodProvider mp);

	/**
	 * Runs the task
	 * 
	 * @param mp the script MethodProvider instance
	 * @throws InterruptedException
	 */
	protected abstract void run(MethodProvider mp) throws InterruptedException;

	/**
	 * Task access point, will start script if needed
	 * 
	 * @param mp the script MethodProvider instance
	 * @throws InterruptedException
	 */
	public final void execute(MethodProvider mp) throws InterruptedException {
		if (started(mp)) run(mp);
		else start(mp);
	}

	/**
	 * Starts the task
	 * 
	 * @param mp the script MethodProvider instance
	 */
	protected void start(MethodProvider mp) {
		started = true;
		target.start(mp);
	}

	/**
	 * Whether the task has been initialised
	 * 
	 * @param mp the script MethodProvider instance
	 */
	private final boolean started(MethodProvider mp) {
		return started && target.started();
	}

	/**
	 * Stops the task
	 * 
	 * @param mp the script MethodProvider instance
	 */
	public void stop(MethodProvider mp) {
		if (!started(mp)) start(mp);
	}

	/**
	 * Gets the ui panel to configure the task
	 * 
	 * @return the ui panel
	 */
	public final JPanel getPanel() {
		return containerPanel;
	}

	/**
	 * Gets the target associated with this task
	 * 
	 * @return the task target
	 */
	public final AbstractTarget getTarget() {
		return target;
	}

	/**
	 * Sets the target associated with this task
	 * 
	 * @param newTarget the new task target
	 */
	public final void setTarget(AbstractTarget newTarget) {
		this.target = newTarget;
	}

	/**
	 * Determines whether the task is complete, based on the target status
	 * 
	 * @param mp the script MethodProvider instance
	 * @return whether this task is complete
	 */
	public final boolean isComplete(MethodProvider mp) {
		return target.accomplished(mp);
	}

	@Override
	public String toString() {
		return getName() + ", Stop when: " + getTarget()
				+ (getTarget().getThreshold() > 0 ? " (" + getTarget().getThreshold() + ")" : "");
	}

}
