package uk.co.ramyun.herblore.potion;

import org.osbot.rs07.script.MethodProvider;

public enum Secondary {

	/**
	 * @author © Michael 19 Feb 2017
	 * @file Secondary.java
	 */

	EYE_OF_NEWT("Eye of newt"), UNICORN_HORN_DUST("Unicorn horn dust"), SNAKE_WEED("Snake weed"),

	LIMPWURT_ROOT("Limpwurt root"), ASHES("Ashes"), RED_SPIDERS_EGGS("Red spiders' eggs"),

	GARLIC("Garlic"), SILVER_DUST("Silver dust"), BALMISH_SNAIL_SLIME("Balmish snail slime"),

	CHOCOLATE_DUST("Chocolate dust"), WHITE_BERRIES("White berries"), TOADS_LEGS("Toad's legs"),

	GOAT_HORN_DUST("Goat horn dust"), SNAPE_GRASS("Snape grass"), MORT_MYRE_FUNGI("Mort myre fungus"),

	SHRUNK_OGLEROOT("Shrunk ogleroot"), KEBBIT_TEETH_DUST("Kebbit teeth dust"), GORAK_CLAW_POWDER("Gorak claw powder"),

	DRAGON_SCALE_DUST("Dragon scale dust"), NAIL_BEAST_NAILS("Nail beast nails"), YEW_ROOTS("Yew roots"),

	WINE_OF_ZAMORAK("Wine of Zamorak"), CACTUS_SPINE("Cactus spine"), POTATO_CACTUS("Potato cactus"),

	AMYLASE_CRYSTAL("Amylase crystal"), JANGERBERRIES("Jangerberries"), MAGIC_ROOTS("Magic roots"),

	CRUSHED_NEST("Crushed nest"), NIGHTSHADE("Nightshade"), POISON_IVY_BERRIES("Poison ivy berries"),

	LAVA_SCALE_SHARD("Lava scale shard"), ZULRAH_SCALES("Zulrah's scales");

	private final String name;

	Secondary(String name) {
		this.name = name;
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
		return getName();
	}
}
