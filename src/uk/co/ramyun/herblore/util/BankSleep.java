package uk.co.ramyun.herblore.util;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class BankSleep extends ConditionalSleep {
	/**
	 * @author © Michael 1 Jan 2018
	 * @file BankSleep.java
	 */

	private final MethodProvider mp;
	private final boolean opening;

	public BankSleep(MethodProvider mp, boolean opening, int timeout) {
		super(timeout);
		this.mp = mp;
		this.opening = opening;
	}

	@Override
	public boolean condition() throws InterruptedException {
		return mp.getBank().isOpen() == opening;
	}
}
