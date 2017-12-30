package uk.co.ramyun.herblore.potion;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

public enum Potion {

	/**
	 * @author © Michael 19 Feb 2017
	 * @file Potion.java
	 */

	ATTACK_POTION("Attack potion", Unfinished.GUAM_POTION, Secondary.EYE_OF_NEWT, 3),

	ANTIPOISON("Antipoison", Unfinished.MARRENTILL_POTION, Secondary.UNICORN_HORN_DUST, 5),

	STRENGTH_POTION("Strength potion", Unfinished.TARROMIN_POTION, Secondary.LIMPWURT_ROOT, 12),

	SERUM_207("Serum 207", Unfinished.TARROMIN_POTION, Secondary.ASHES, 15),

	RESTORE_POTION("Restore potion", Unfinished.HARRALANDER_POTION, Secondary.RED_SPIDERS_EGGS, 22),

	BALMISH_OIL("Balmish oil", Unfinished.HARRALANDER_POTION, Secondary.BALMISH_SNAIL_SLIME, 25),

	ENERGY_POTION("Energy potion", Unfinished.HARRALANDER_POTION, Secondary.CHOCOLATE_DUST, 26),

	DEFENCE_POTION("Defence potion", Unfinished.RANARR_POTION, Secondary.WHITE_BERRIES, 30),

	AGILITY_POTION("Agility potion", Unfinished.TOADFLAX_POTION, Secondary.TOADS_LEGS, 34),

	COMBAT_POTION("Combat potion", Unfinished.HARRALANDER_POTION, Secondary.GOAT_HORN_DUST, 36),

	PRAYER_POTION("Prayer potion", Unfinished.RANARR_POTION, Secondary.SNAPE_GRASS, 38),

	SUPER_ATTACK("Super attack", Unfinished.IRIT_POTION, Secondary.EYE_OF_NEWT, 45),

	SUPERANTIPOISON("Superantipoison", Unfinished.IRIT_POTION, Secondary.UNICORN_HORN_DUST, 48),

	FISHING_POTION("Fishing potion", Unfinished.AVANTOE_POTION, Secondary.SNAPE_GRASS, 50),

	SUPER_ENERGY_POTION("Super energy potion", Unfinished.AVANTOE_POTION, Secondary.MORT_MYRE_FUNGI, 52),

	HUNTER_POTION("Hunter potion", Unfinished.AVANTOE_POTION, Secondary.KEBBIT_TEETH_DUST, 53),

	SUPER_STRENGTH("Super strength", Unfinished.KWUARM_POTION, Secondary.LIMPWURT_ROOT, 55),

	MAGIC_ESSENCE_POTION("Magic essence potion", Unfinished.MAGIC_ESSENCE, Secondary.GORAK_CLAW_POWDER, 57),

	WEAPON_POISON("Weapon poison", Unfinished.KWUARM_POTION, Secondary.DRAGON_SCALE_DUST, 60),

	SUPER_RESTORE("Super restore", Unfinished.SNAPDRAGON_POTION, Secondary.RED_SPIDERS_EGGS, 63),

	SUPER_DEFENCE_POTION("Super defence potion", Unfinished.CADANTINE_POTION, Secondary.WHITE_BERRIES, 66),

	ANTIDOTE_1("Antidote+", Unfinished.ANTIDOTEPLUS, Secondary.YEW_ROOTS, 68),

	ANTIFIRE_POTION("Anti-fire potion", Unfinished.LANTADYME_POTION, Secondary.DRAGON_SCALE_DUST, 69),

	RANGING_POTION("Ranging potion", Unfinished.DWARF_WEED_POTION, Secondary.WINE_OF_ZAMORAK, 72),

	ZAMORAK_BREW("Zamorak brew", Unfinished.TORSTOL_POTION, Secondary.JANGERBERRIES, 78),

	ANTIDOTE_2("Antidote++", Unfinished.ANTIDOTEPLUSPLUS, Secondary.MAGIC_ROOTS, 79),

	SARADOMIN_BREW("Saradomin brew", Unfinished.TOADFLAX_POTION, Secondary.CRUSHED_NEST, 81);

	private final Unfinished unfPotion;
	private final Secondary secondary;
	private final String name;
	private final int level;

	Potion(String name, Unfinished unfPotion, Secondary secondary, int level) {
		this.name = name;
		this.unfPotion = unfPotion;
		this.secondary = secondary;
		this.level = level;
	}

	public Unfinished getUnfPotion() {
		return unfPotion;
	}

	public Secondary getSecondary() {
		return secondary;
	}

	public int getLevel() {
		return level;
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

	public boolean hasLevel(MethodProvider mp) {
		return mp.getSkills().getDynamic(Skill.HERBLORE) >= level;
	}

	public boolean hasUnf(MethodProvider mp) {
		return unfPotion.has(mp);
	}

	public boolean hasSecondary(MethodProvider mp) {
		return secondary.has(mp);
	}

	public boolean canMake(MethodProvider mp) {
		return hasLevel(mp) && hasUnf(mp) && hasSecondary(mp);
	}

	public boolean canMakeFinishedOrUnfinished(MethodProvider mp) {
		return canMake(mp) || unfPotion.canMake(mp);
	}

	public String getGenericName() {
		return name;
	}

	public String getName(int doses) {
		return name + "(" + doses + ")";
	}

	@Override
	public String toString() {
		return getGenericName();
	}
}
