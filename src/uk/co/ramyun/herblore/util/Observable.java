package uk.co.ramyun.herblore.util;

public interface Observable {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file Observable.java
	 */

	public void registerObserver(TaskCollectionObserver o);

	public void removeObserver(TaskCollectionObserver o);

}
