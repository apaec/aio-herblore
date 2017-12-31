package uk.co.ramyun.herblore.util;

import uk.co.ramyun.herblore.task.HerbloreTask;

public interface TaskCollectionObserver {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file Observer.java
	 */

	public abstract void taskRegistered(HerbloreTask t);

	public abstract void taskDeregistered(HerbloreTask t);

	public abstract void cleared();

}
