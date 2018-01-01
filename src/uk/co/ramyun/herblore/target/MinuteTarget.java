package uk.co.ramyun.herblore.target;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.util.Timer;

public class MinuteTarget extends AbstractTarget {
	/**
	 * @author © Michael 10 Aug 2017
	 * @file MinuteTarget.java
	 */

	private final Timer timer;

	public MinuteTarget() {
		this.timer = new Timer(0L);
	}

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
		return 10080L; // (1 week)
	}

	@Override
	public long minThreshold() {
		return 1L;
	}

	@Override
	public long defaultThreshold() {
		return 30L;
	}

	@Override
	protected boolean targetReached(MethodProvider mp) {
		return timer.getElapsed() > (getThreshold() * 60000);
	}

	@Override
	public String getProgress(MethodProvider mp) {
		return timer.getElapsed() / 60000 + "/" + getThreshold() + " min(s)";
	}

	@Override
	public String getName() {
		return "Minute(s) passed";
	}

}
