package uk.co.ramyun.herblore.task;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Herb;

public class CleanHerbs extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file CleanHerbs.java
	 */

	private final Herb herb;

	public CleanHerbs(Herb toClean) {
		this.herb = toClean;
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
