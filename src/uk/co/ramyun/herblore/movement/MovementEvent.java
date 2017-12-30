package uk.co.ramyun.herblore.movement;

import org.osbot.rs07.script.Script;

public interface MovementEvent {

	public boolean canExecute();

	public boolean shouldExecute();

	public void execute(Script script);

}
