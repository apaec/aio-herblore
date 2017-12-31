package uk.co.ramyun.herblore.potion;

import java.util.Arrays;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

public enum Herb {

	/**
	 * @author © Michael 19 Feb 2017
	 * @file Herb.java
	 */

	GUAM_LEAF(3), MARRENTILL(5), TARROMIN(11), HARRALANDER(20), RANARR_WEED(25),

	TOADFLAX(30), IRIT_LEAF(40), AVANTOE(48), KWUARM(54), SNAPDRAGON(59),

	CADANTINE(65), LANTADYME(67), DWARF_WEED(70), TORSTOL(75), STARFLOWER(0);

	private final String name;
	private final boolean cleanable;
	private final int cleanLevel;

	Herb(int cleanLevel) {
		this.cleanLevel = cleanLevel;
		this.cleanable = cleanLevel > 0;
		String temp = super.toString().toLowerCase().replace("_", " ");
		this.name = Character.toUpperCase(temp.charAt(0)) + temp.substring(1, temp.length());
	}

	public static Herb[] cleanableValues() {
		return Arrays.stream(values()).filter(Herb::isCleanable).toArray(Herb[]::new);
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

	public boolean canClean(MethodProvider mp) {
		return isCleanable() && hasLevelToClean(mp);
	}

	public boolean hasLevelToClean(MethodProvider mp) {
		return mp.getSkills().getDynamic(Skill.HERBLORE) >= cleanLevel;
	}

	public int getCleanLevel() {
		return cleanLevel;
	}

	public boolean isCleanable() {
		return cleanable;
	}

	public String getFullName() {
		return name;
	}

	public String getBaseName() {
		return name.split(" ")[0];
	}

	@Override
	public String toString() {
		return getFullName();
	}
}
