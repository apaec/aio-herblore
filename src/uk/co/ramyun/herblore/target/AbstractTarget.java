package uk.co.ramyun.herblore.target;

import org.osbot.rs07.script.MethodProvider;

public abstract class AbstractTarget {

	/**
	 * @author � Michael 10 Aug 2017
	 * @file AbstractTarget.java
	 */

	protected boolean started = false, forceAccomplished = false;
	protected long threshold = 0;

	public void start(MethodProvider mp) {
		started = true;
	}

	public boolean started() {
		return started;
	}

	public boolean startIfNeeded(MethodProvider mp) {
		if (!started()) start(mp);
		return started();
	}

	public boolean accomplished(MethodProvider mp) {
		return forceAccomplished || targetReached(mp);
	}

	public void setThreshold(long newThreshold) {
		if (newThreshold <= maxThreshold() && newThreshold >= minThreshold()) this.threshold = newThreshold;
	}

	public long getThreshold() {
		return threshold;
	}

	public long maxThreshold() {
		return Long.MAX_VALUE;
	}

	public long minThreshold() {
		return 0;
	}

	public long defaultThreshold() {
		return minThreshold();
	}

	public void forceAccomplished() {
		forceAccomplished = true;
	}

	protected abstract boolean targetReached(MethodProvider mp);
}