package uk.co.ramyun.herblore.task;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.target.AbstractTarget;
import uk.co.ramyun.herblore.target.NoTarget;

public abstract class HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file HerbloreTask.java
	 */

	protected AbstractTarget target = new NoTarget();

	public abstract boolean canRun(MethodProvider mp);

	protected abstract void run(MethodProvider mp);

	public void execute(MethodProvider mp) {
		if (!target.started()) target.start(mp);
		else run(mp);
	}

	public AbstractTarget getTarget() {
		return target;
	}

	public void setTarget(AbstractTarget newTarget) {
		this.target = newTarget;
	}

	public boolean isComplete(MethodProvider mp) {
		return target.accomplished(mp);
	}

}
