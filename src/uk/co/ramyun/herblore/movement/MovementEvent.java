package uk.co.ramyun.herblore.movement;

import org.osbot.rs07.script.MethodProvider;

public interface MovementEvent {
	
	/**
	 * @author © Michael 7 Sep 2017
	 * @file MovementEvent.java
	 */

	public boolean canExecute();

	public boolean shouldExecute();

	public void execute(MethodProvider mp);

}
