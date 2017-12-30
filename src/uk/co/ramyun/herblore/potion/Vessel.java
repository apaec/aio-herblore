package uk.co.ramyun.herblore.potion;

import org.osbot.rs07.script.MethodProvider;

public enum Vessel {

	/**
	 * @author © Michael 20 Feb 2017
	 * @file Vessel.java
	 */

	VIAL_OF_WATER(), COCONUT_MILK();

	private final String name;

	Vessel() {
		String temp = super.toString().toLowerCase().replace("_", " ");
		this.name = Character.toUpperCase(temp.charAt(0)) + temp.substring(1, temp.length());
	}

	public boolean hasNoted(MethodProvider mp) {
		return mp.getInventory().contains(name) && mp.getInventory().getItem(name).isNote();
	}

	public boolean has(MethodProvider mp) {
		return mp.getInventory().contains(name) && !mp.getInventory().getItem(name).isNote();
	}

	public long getAmount(MethodProvider mp) {
		if (has(mp)) return mp.inventory.getAmount(name);
		else return 0;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
