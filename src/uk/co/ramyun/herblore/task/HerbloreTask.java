package uk.co.ramyun.herblore.task;

import org.osbot.rs07.script.MethodProvider;

public abstract class HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file HerbloreTask.java
	 */

	protected boolean started = false;

	public abstract boolean canRun(MethodProvider mp);

	public abstract void run(MethodProvider mp);

	public boolean isComplete() {
		return true;
	}

	public boolean hasStarted() {
		return started;
	}

	public void start(MethodProvider mp) {
		started = true;
	}

}
