package uk.co.ramyun.herblore.task;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.potion.Tar;

public class MakeTar extends HerbloreTask {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file MakeTar.java
	 */

	private final Tar tar;

	public MakeTar(Tar toMake) {
		this.tar = toMake;
	}

	@Override
	public String getName() {
		return "Make " + tar.getName();
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
