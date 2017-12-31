package uk.co.ramyun.herblore.task;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Potion;

public class MakeFinished extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeFinished.java
	 */

	private final Potion potion;

	public MakeFinished(Potion toMake) {
		this.potion = toMake;
	}

	@Override
	public String getName() {
		return "Make " + potion.getGenericName() + "s";
	}

	@Override
	public boolean canRun(MethodProvider mp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run(MethodProvider mp) {
		// TODO Auto-generated method stub
	}
}
