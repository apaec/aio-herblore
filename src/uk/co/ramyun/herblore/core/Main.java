package uk.co.ramyun.herblore.core;

import java.awt.Graphics2D;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import uk.co.ramyun.herblore.movement.MovementManager;
import uk.co.ramyun.herblore.movement.RandomCameraEvent;
import uk.co.ramyun.herblore.movement.RandomMouseEvent;
import uk.co.ramyun.herblore.util.GaussianRandom;
import uk.co.ramyun.herblore.util.Timer;

@ScriptManifest(author = "Apaec", info = "AIO Herblore", name = "APA AIO Herblore", version = 1.00, logo = "")
public class Main extends Script {

	/**
	 * @author © Michael 17 Dec 2017
	 * @file Main.java
	 */

	private final GaussianRandom gRandom = new GaussianRandom();
	private final Timer runTime = new Timer(0L);
	private final MovementManager movementManager = new MovementManager();
	private final HerbloreTaskManager herbloreTaskManager = new HerbloreTaskManager();

	@Override
	public void onStart() throws InterruptedException {
		int frequency = 100000, deviation = 80000;
		movementManager.register(new RandomCameraEvent(frequency, deviation));
		movementManager.register(new RandomMouseEvent(frequency, deviation));
		log("Registered two gaussian-random input movement events.");
		log("Movement event frequency: " + frequency + "ms, deviation: " + deviation + "ms");
	}

	@Override
	public void log(String message) {
		super.log(message);
	}

	@Override
	public void onExit() {
		log("Time running: " + runTime.toString());
		log("Script stopped: " + this.getName() + " v" + getVersion() + " by " + this.getAuthor() + ".");
	}

	@Override
	public int onLoop() throws InterruptedException {
		movementManager.roll(this);
		if (herbloreTaskManager.loop(this)) logStop("All registered herblore tasks have been completed.", true);
		return gRandom.gRandomInRange(100, 300);
	}

	private void logStop(String message, boolean log) {
		this.log(message);
		stop(log);
	}

	@Override
	public void onPaint(Graphics2D g) {}
}
