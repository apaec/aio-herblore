package uk.co.ramyun.herblore.util;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class ItemSleep extends ConditionalSleep {
	/**
	 * @author © Michael 15 Jun 2017
	 * @file ItemSleep.java
	 */

	private final MethodProvider mp;
	private final long initialAmount;
	private final String itemName;

	public ItemSleep(MethodProvider mp, String itemName, int timeout) {
		super(timeout);
		this.mp = mp;
		this.itemName = itemName;
		this.initialAmount = mp.getInventory().contains(itemName) ? mp.getInventory().getAmount(itemName) : 0;
	}

	@Override
	public boolean condition() throws InterruptedException {
		return mp.getInventory().contains(itemName) && mp.getInventory().getAmount(itemName) > initialAmount;
	}
}
