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
	public long maxThreshold() {
		return 0L;
	}

	@Override
	public long minThreshold() {
		return 0L;
	}

	@Override
	public long defaultThreshold() {
		return 0L;
	}

	@Override
	public long getStep() {
		return 0L;
	}

	@Override
	public String getProgress(MethodProvider mp) {
		return "-";
	}

	@Override
	public String getName() {
		return "Out of supplies";
	}

}
