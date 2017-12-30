package uk.co.ramyun.herblore.potion;

import org.osbot.rs07.script.MethodProvider;

public enum Herb {

	/**
	 * @author © Michael 19 Feb 2017
	 * @file Herb.java
	 */

	GUAM_LEAF(), MARRENTILL(), TARROMIN(), HARRALANDER(), RANARR_WEED(),

	TOADFLAX(), IRIT_LEAF(), AVANTOE(), KWUARM(), SNAPDRAGON(),

	CADANTINE(), LANTADYME(), DWARF_WEED(), TORSTOL(), STARFLOWER();

	private final String name;

	Herb() {
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

	public String getFullName() {
		return name;
	}

	@Override
	public String toString() {
		return getFullName();
	}
}
