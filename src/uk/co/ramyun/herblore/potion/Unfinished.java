package uk.co.ramyun.herblore.potion;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

public enum Unfinished {

	/**
	 * @author © Michael 19 Feb 2017
	 * @file Unfinished.java
	 */

	AVANTOE_POTION("Avantoe potion", Herb.GUAM_LEAF, Vessel.VIAL_OF_WATER, 50),

	CADANTINE_POTION("Cadantine potion", Herb.CADANTINE, Vessel.VIAL_OF_WATER, 66),

	DWARF_WEED_POTION("Dwarf weed potion", Herb.DWARF_WEED, Vessel.VIAL_OF_WATER, 72),

	GUAM_POTION("Guam potion", Herb.GUAM_LEAF, Vessel.VIAL_OF_WATER, 3),

	HARRALANDER_POTION("Harralander potion", Herb.HARRALANDER, Vessel.VIAL_OF_WATER, 22),

	IRIT_POTION("Irit potion", Herb.IRIT_LEAF, Vessel.VIAL_OF_WATER, 45),

	KWUARM_POTION("Kwuarm potion", Herb.KWUARM, Vessel.VIAL_OF_WATER, 55),

	LANTADYME_POTION("Lantadyme potion", Herb.LANTADYME, Vessel.VIAL_OF_WATER, 69),

	MARRENTILL_POTION("Marrentill potion", Herb.MARRENTILL, Vessel.VIAL_OF_WATER, 5),

	RANARR_POTION("Ranarr potion", Herb.RANARR_WEED, Vessel.VIAL_OF_WATER, 30),

	SNAPDRAGON_POTION("Snapdragon potion", Herb.SNAPDRAGON, Vessel.VIAL_OF_WATER, 63),

	TARROMIN_POTION("Tarromin potion", Herb.TARROMIN, Vessel.VIAL_OF_WATER, 12),

	TOADFLAX_POTION("Toadflax potion", Herb.TOADFLAX, Vessel.VIAL_OF_WATER, 34),

	TORSTOL_POTION("Torstol potion", Herb.TORSTOL, Vessel.VIAL_OF_WATER, 78),

	MAGIC_ESSENCE("Magic essence", Herb.STARFLOWER, Vessel.VIAL_OF_WATER, 57),

	ANTIDOTEPLUS("Antidote+", Herb.TOADFLAX, Vessel.COCONUT_MILK, 68),

	ANTIDOTEPLUSPLUS("Antidote++", Herb.IRIT_LEAF, Vessel.COCONUT_MILK, 79);

	private final Herb herb;
	private final int level;
	private final String name;
	private final Vessel vessel;

	Unfinished(String stem, Herb herb, Vessel vessel, int level) {
		this.herb = herb;
		this.name = stem + " (unf)";
		this.vessel = vessel;
		this.level = level;
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

	public Herb getHerb() {
		return herb;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public Vessel getVessel() {
		return vessel;
	}

	public boolean hasLevel(MethodProvider mp) {
		return mp.getSkills().getDynamic(Skill.HERBLORE) >= level;
	}

	public boolean hasHerb(MethodProvider mp) {
		return herb.has(mp);
	}

	public boolean hasVessel(MethodProvider mp) {
		return vessel.has(mp);
	}

	public boolean canMake(MethodProvider mp) {
		return hasLevel(mp) && hasHerb(mp) && hasVessel(mp);
	}

	@Override
	public String toString() {
		return getName();
	}
}
