package uk.co.ramyun.herblore.target;

import org.osbot.rs07.script.MethodProvider;

public abstract class AbstractTarget {

	/**
	 * @author © Michael 10 Aug 2017
	 * @file AbstractTarget.java
	 */

	protected boolean started = false, forceAccomplished = false;
	protected long threshold = defaultThreshold();

	/**
	 * Starts this target. Allows subclasses to initialise variables
	 * 
	 * @param mp the script MethodProvider instance
	 */
	public void start(MethodProvider mp) {
		started = true;
	}

	/**
	 * Determins if the target is running
	 * 
	 * @return whether this target has been started
	 */
	public boolean started() {
		return started;
	}

	/**
	 * Starts the target if needed
	 * 
	 * @param mp the script MethodProvider instance
	 * @return whether this target has been started
	 */
	public boolean startIfNeeded(MethodProvider mp) {
		if (!started()) start(mp);
		return started();
	}

	/**
	 * Determines whether the target is complete, either naturally or forced
	 * 
	 * @param mp the script MethodProvider instance
	 * @return whether this target is complete
	 */
	public boolean accomplished(MethodProvider mp) {
		return forceAccomplished || targetReached(mp);
	}

	/**
	 * Sets a new threshold for the target based on the input. Will only allow
	 * application of valid threshold changes
	 * 
	 * @param newThreshold the new threshold for the target
	 */
	public void setThreshold(long newThreshold) {
		if (newThreshold <= maxThreshold() && newThreshold >= minThreshold()) this.threshold = newThreshold;
	}

	/**
	 * Returns the current threshold
	 * 
	 * @return the current target threshold
	 */
	public long getThreshold() {
		return threshold;
	}

	/**
	 * Returns the maximum threshold that can be assigned to this target
	 * 
	 * @return the maximum threshold
	 */
	public long maxThreshold() {
		return Long.MAX_VALUE;
	}

	/**
	 * Returns the minimum threshold that can be assigned to this target
	 * 
	 * @return the minimum threshold
	 */
	public long minThreshold() {
		return 0;
	}

	/**
	 * Returns the default threshold associated with this target
	 * 
	 * @return the default threshold
	 */
	public long defaultThreshold() {
		return minThreshold();
	}

	/**
	 * Returns the step size for adjusting the threshold. Designed for use with
	 * a JSpinner
	 * 
	 * @return the step size
	 */
	public long getStep() {
		return 1L;
	}

	/**
	 * Forces the target to be accomplished. Can be used to skip targets if
	 * requirements or resources are insufficient
	 */
	public void forceAccomplished() {
		forceAccomplished = true;
	}

	/**
	 * Determines whether this target has been naturally reached
	 * 
	 * @param mp the script MethodProvider instance
	 * @return whether this target is (naturally) complete
	 */
	protected abstract boolean targetReached(MethodProvider mp);

	/**
	 * Returns the name of this target
	 * 
	 * @return the target name
	 */
	public abstract String getName();

	/**
	 * Returns the progress of this target
	 * 
	 * @return the current progress
	 */
	public abstract String getProgress(MethodProvider mp);

	@Override
	public String toString() {
		return getName();
	}
}
