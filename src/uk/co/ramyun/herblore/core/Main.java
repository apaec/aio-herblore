package uk.co.ramyun.herblore.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import uk.co.ramyun.herblore.movement.MovementManager;
import uk.co.ramyun.herblore.movement.RandomCameraEvent;
import uk.co.ramyun.herblore.movement.RandomMouseEvent;
import uk.co.ramyun.herblore.task.HerbloreTask;
import uk.co.ramyun.herblore.ui.Ui;
import uk.co.ramyun.herblore.util.GaussianRandom;
import uk.co.ramyun.herblore.util.TaskCollectionObserver;
import uk.co.ramyun.herblore.util.Timer;

@ScriptManifest(author = "Apaec", info = "AIO Herblore", name = "APA AIO Herblore", version = 1.00, logo = "")
public class Main extends Script implements TaskCollectionObserver {

	/**
	 * @author � Michael 17 Dec 2017
	 * @file Main.java
	 */

	private final GaussianRandom gRandom = new GaussianRandom();
	private final Timer runTime = new Timer(0L);
	private final MovementManager movementManager = new MovementManager();
	private HerbloreTaskManager herbloreTaskManager;

	@Override
	public void onStart() throws InterruptedException {
		Ui ui = new Ui();
		ui.setVisible(true);

		while (ui.isVisible())
			sleep(400);

		experienceTracker.start(Skill.HERBLORE);

		herbloreTaskManager = ui.getHerbloreTaskManager();
		herbloreTaskManager.registerObserver(this);

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

	private final Font paintFont = new Font("Dialog", Font.PLAIN, 12);
	private final Color cursorColour = new Color(255, 255, 255, 180), textColour = Color.WHITE;

	@Override
	public void onPaint(Graphics2D g) {

		g.setColor(textColour);
		g.setFont(paintFont);

		g.drawString(getName() + " v " + getVersion() + " by " + getAuthor(), 10, 240);
		g.drawString("Run time: " + runTime.toString(), 10, 260);
		herbloreTaskManager.getCurrent().ifPresent(t -> {
			g.drawString("Task: " + t, 10, 280);
			g.drawString("Progress: " + t.getTarget().getProgress(this), 10, 300);
		});

		/* Draw cross cursor */
		if (getMouse().isWithinCanvas()) {
			g.setColor(cursorColour);
			int mouseX = getMouse().getPosition().x, mouseY = getMouse().getPosition().y;
			g.drawLine(mouseX - 5, mouseY + 5, mouseX + 5, mouseY - 5);
			g.drawLine(mouseX + 5, mouseY + 5, mouseX - 5, mouseY - 5);
		}
	}

	@Override
	public void taskRegistered(HerbloreTask t) {
		this.log("Herblore task registered: " + t);
	}

	@Override
	public void taskDeregistered(HerbloreTask t) {
		this.log("Herblore task deregistered: " + t);
	}

	@Override
	public void cleared() {
		this.log("Herblore queue cleared");
	}
}
