package uk.co.ramyun.herblore.target;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.util.Timer;

public class MinuteTarget extends AbstractTarget {
	/**
	 * @author � Michael 10 Aug 2017
	 * @file MinuteTarget.java
	 */

	private final Timer timer;

	public MinuteTarget(long minutes) {
		setThreshold(minutes);
		this.timer = new Timer(0L);
	}

	@Override
	public void start(MethodProvider mp) {
		timer.reset();
		super.start(mp);
	}

	@Override
	public long maxThreshold() {
		return 10080; // (1 week)
	}

	@Override
	public long minThreshold() {
		return 1;
	}

	@Override
	public long defaultThreshold() {
		return 30;
	}

	@Override
	protected boolean targetReached(MethodProvider mp) {
		return timer.getElapsed() > (getThreshold() * 60000);
	}

	@Override
	public String toString() {
		return "Minute(s) passed";
	}

}