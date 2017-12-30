package uk.co.ramyun.herblore.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.task.HerbloreTask;

public class HerbloreTaskManager {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file HerbloreTaskManager.java
	 */

	private final Queue<HerbloreTask> tasks = new LinkedList<HerbloreTask>();

	public HerbloreTaskManager(HerbloreTask... ts) {
		Arrays.stream(ts).forEachOrdered(t -> registerTask(t));
	}

	public void registerTask(HerbloreTask task) {
		tasks.add(task);
	}

	public void deregisterTask(HerbloreTask task) {
		tasks.remove(task);
	}

	public void deregisterAll() {
		tasks.clear();
	}

	public int totalTasks() {
		return tasks.size();
	}

	public Optional<HerbloreTask> getCurrent() {
		HerbloreTask current = tasks.peek();
		return current == null ? Optional.empty() : Optional.of(current);
	}

	public boolean hasTasks() {
		return tasks.isEmpty();
	}

	public boolean loop(MethodProvider mp) {
		Optional<HerbloreTask> currentTask = getCurrent();
		currentTask.ifPresent(t -> {
			if (!t.isComplete(mp)) t.execute(mp);
			else tasks.poll();
		});
		return tasks.isEmpty();
	}

}
