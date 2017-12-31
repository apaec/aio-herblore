package uk.co.ramyun.herblore.task;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Unfinished;

public class MakeUnfinished extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeUnfinished.java
	 */

	private final Unfinished unfinished;

	public MakeUnfinished(Unfinished toMake) {
		this.unfinished = toMake;
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
