package uk.co.ramyun.herblore.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import org.osbot.rs07.script.MethodProvider;

import uk.co.ramyun.herblore.task.HerbloreTask;
import uk.co.ramyun.herblore.util.Observable;
import uk.co.ramyun.herblore.util.TaskCollectionObserver;

public class HerbloreTaskManager implements Observable {

	/**
	 * @author © Michael 30 Dec 2017
	 * @file HerbloreTaskManager.java
	 */

	private final Queue<HerbloreTask> tasks = new LinkedList<HerbloreTask>();
	private final List<TaskCollectionObserver> observers = new ArrayList<TaskCollectionObserver>();

	public HerbloreTaskManager(HerbloreTask... ts) {
		Arrays.stream(ts).forEachOrdered(t -> registerTask(t));
	}

	public void registerTask(HerbloreTask task) {
		tasks.add(task);
		observers.forEach(o -> o.taskRegistered(task));
	}

	public void deregisterTask(HerbloreTask task) {
		tasks.remove(task);
		observers.forEach(o -> o.taskDeregistered(task));
	}

	public void deregisterAll() {
		tasks.clear();
		observers.forEach(o -> o.cleared());
	}

	public int totalTasks() {
		return tasks.size();
	}

	public Optional<HerbloreTask> getCurrent() {
		HerbloreTask current = tasks.peek();
		return current == null ? Optional.empty() : Optional.of(current);
	}

	public boolean hasTasks() {
		return !tasks.isEmpty();
	}

	public boolean loop(MethodProvider mp) {
		getCurrent().ifPresent(t -> {
			if (!t.isComplete(mp) && t.canRun(mp)) {
				try {
					t.execute(mp);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				t.stop(mp);
				HerbloreTask polled = tasks.poll();
				observers.forEach(o -> o.taskDeregistered(polled));
			}
		});
		return tasks.isEmpty();
	}

	@Override
	public void registerObserver(TaskCollectionObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(TaskCollectionObserver o) {
		observers.remove(o);
	}

}
