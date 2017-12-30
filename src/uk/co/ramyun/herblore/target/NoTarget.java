package uk.co.ramyun.herblore.target;

import org.osbot.rs07.script.MethodProvider;

public class NoTarget extends AbstractTarget {

	/**
	 * @author © Michael 23 Oct 2017
	 * @file NoTarget.java
	 */

	@Override
	protected boolean targetReached(MethodProvider mp) {
		return false;
	}

	@Override
	public String toString() {
		return "No target";
	}

}
