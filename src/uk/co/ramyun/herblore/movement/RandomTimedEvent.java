package uk.co.ramyun.herblore.movement;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.util.GaussianRandom;
import uk.co.ramyun.herblore.util.Timer;


abstract class RandomTimedEvent implements MovementEvent {
	/**
	 * @author © Michael 7 Sep 2017
	 * @file RandomTimedEvent.java
	 */

	protected final GaussianRandom random = new GaussianRandom();
	private final Timer timer;
	private final long threshold, deviation;
	private long currentThreshold;

	public RandomTimedEvent(long threshold, long deviation) {
		timer = new Timer();
		this.threshold = threshold;
		this.deviation = deviation;
		this.currentThreshold = generateThreshold();
	}

	private long generateThreshold() {
		return random.gRandomInRange(threshold - deviation, threshold + deviation);
	}

	private void setThreshold(long value) {
		currentThreshold = value;
	}

	public long getThreshold() {
		return threshold;
	}

	public long getDeviation() {
		return deviation;
	}

	@Override
	public abstract boolean canExecute();

	@Override
	public boolean shouldExecute() {
		return timer.getElapsed() >= currentThreshold;
	}

	@Override
	public void execute(MethodProvider mp) {
		timer.reset();
		setThreshold(generateThreshold());
	}

}