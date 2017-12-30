package uk.co.ramyun.herblore.movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.script.MethodProvider;

public class MovementManager {
	
	/**
	 * @author © Michael 7 Sep 2017
	 * @file MovementManager.java
	 */

	private final List<MovementEvent> eventsList = new ArrayList<>();

	public MovementManager(MovementEvent... events) {
		Arrays.stream(events).forEach(e -> register(e));
	}

	public void roll(MethodProvider mp) {
		eventsList.stream().filter(e -> e.canExecute() && e.shouldExecute()).forEach(e -> loggedExecute(e, mp));
	}

	public void loggedExecute(MovementEvent e, MethodProvider mp) {
		mp.log("Executing movement event: " + e.toString());
		e.execute(mp);
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
