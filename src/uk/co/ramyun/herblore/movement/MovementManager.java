package uk.co.ramyun.herblore.movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.script.Script;

public class MovementManager {

	private final List<MovementEvent> eventsList = new ArrayList<>();

	public MovementManager(MovementEvent... events) {
		Arrays.stream(events).forEach(e -> register(e));
	}

	public void roll(Script script) {
		eventsList.stream().filter(e -> e.canExecute() && e.shouldExecute()).forEach(e -> loggedExecute(e, script));
	}

	public void loggedExecute(MovementEvent e, Script script) {
		script.log("Executing movement event: " + e.toString());
		e.execute(script);
	}

	public void register(MovementEvent e) {
		eventsList.add(e);
	}

	public void unregister(MovementEvent e) {
		eventsList.remove(e);
	}

	public List<MovementEvent> getEvents() {
		return eventsList;
	}

}
