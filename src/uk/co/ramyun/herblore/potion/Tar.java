package uk.co.ramyun.herblore.potion;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

public enum Tar {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file Tar.java
	 */

	GUAM_TAR(Herb.GUAM_LEAF, 19),

	MARRENTILL_TAR(Herb.MARRENTILL, 31),

	TARROMIN_TAR(Herb.TARROMIN, 39),

	HARRALANDER_TAR(Herb.HARRALANDER, 44);

	private final Herb herb;
	private final String name, buttonName;
	private final int levelRequired;

	Tar(Herb herb, int levelRequired) {
		this.herb = herb;
		this.name = herb.getBaseName() + " tar";
		this.buttonName = "15 " + name.toLowerCase();
		this.levelRequired = levelRequired;
	}

	public boolean has(MethodProvider mp) {
		return mp.getInventory().contains(name);
	}

	public long getAmount(MethodProvider mp) {
		if (has(mp)) return mp.inventory.getAmount(name);
		else return 0;
	}

	public Herb getHerb() {
		return herb;
	}

	public int getLevel() {
		return levelRequired;
	}

	public String getName() {
		return name;
	}

	public String getButtonName() {
		return buttonName;
	}

	public boolean hasHerb(MethodProvider mp) {
		return herb.has(mp);
	}

	public boolean hasPestleAndMortar(MethodProvider mp) {
		return mp.getInventory().contains("Pestle and Mortar");
	}

	public boolean hasLevel(MethodProvider mp) {
		return mp.getSkills().getDynamic(Skill.HERBLORE) >= levelRequired;
	}

	public boolean hasSufficientSwampTar(MethodProvider mp) {
		return mp.getInventory().contains("Swamp tar") && mp.getInventory().getAmount("Swamp tar") >= 15;
	}

	public boolean canMake(MethodProvider mp) {
		return hasLevel(mp) && hasPestleAndMortar(mp) && hasHerb(mp) && hasSufficientSwampTar(mp);
	}

	@Override
	public String toString() {
		return getName();
	}
}
