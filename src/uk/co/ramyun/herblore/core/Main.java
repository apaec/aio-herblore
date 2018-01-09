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
import uk.co.ramyun.herblore.paint.ProgressBar;
import uk.co.ramyun.herblore.task.HerbloreTask;
import uk.co.ramyun.herblore.ui.Ui;
import uk.co.ramyun.herblore.util.GaussianRandom;
import uk.co.ramyun.herblore.util.TaskCollectionObserver;
import uk.co.ramyun.herblore.util.Timer;

@ScriptManifest(author = "Apaec", info = "AIO Herblore", name = "APA AIO Herblore", version = 1.03, logo = "https://i.imgur.com/E1yWP31.png")
public class Main extends Script implements TaskCollectionObserver {

	/**
	 * @author © Michael 17 Dec 2017
	 * @file Main.java
	 */

	private final GaussianRandom gRandom = new GaussianRandom();
	private final MovementManager movementManager = new MovementManager();
	private Timer runTime;
	private HerbloreTaskManager herbloreTaskManager;

	/* Paint variables */
	private final Font paintFont = new Font("Dialog", Font.PLAIN, 12);
	private final Color cursorColour = new Color(255, 255, 255, 180), textColour = Color.WHITE;
	private final ProgressBar progressBar = new ProgressBar(this, Skill.HERBLORE, new Color(120, 160, 0, 200),
			new Color(200, 245, 60, 200), Color.BLACK, 5, 320, 220,
			16); /* width=509 to span game screen */
	private final int textX = 7, textY = 213;

	@Override
	public void onStart() throws InterruptedException {
		experienceTracker.start(Skill.HERBLORE);

		/* Create & show UI */
		Ui ui = new Ui();
		ui.setVisible(true);

		/* Wait for UI to close */
		while (ui.isVisible())
			sleep(400);

		/* Set up random movement manager */
		int frequency = 100000, deviation = 80000;
		movementManager.register(new RandomCameraEvent(frequency, deviation));
		movementManager.register(new RandomMouseEvent(frequency, deviation));
		log("Registered two gaussian-random input movement events.");
		log("Movement event frequency: " + frequency + "ms, deviation: " + deviation + "ms");

		/* Initialise counters */
		runTime = new Timer(0L);

		/* Grab data from UI */
		herbloreTaskManager = ui.getHerbloreTaskManager();
		herbloreTaskManager.registerObserver(this);
		if (herbloreTaskManager.totalTasks() <= 0) logStop("No tasks specified", false);
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
	public void onPaint(Graphics2D g) {
		/* Set colour and font */
		g.setColor(textColour);
		g.setFont(paintFont);

		/* Draw session data */
		int xpGained = experienceTracker.getGainedXP(Skill.HERBLORE);
		g.drawString(getName() + " v" + getVersion() + " by " + getAuthor(), textX, textY);
		if (runTime != null) g.drawString("Runtime: " + runTime.toString(), textX, textY + 20);
		if (xpGained > 0) g.drawString(
				"Exp gained: +" + runescapeFormat(xpGained) + " ("
						+ runescapeFormat(experienceTracker.getGainedXPPerHour(Skill.HERBLORE)) + "/h)",
				textX, textY + 40);
		herbloreTaskManager.getCurrent().ifPresent(t -> {
			g.drawString("Current Task: " + t.getName(), textX, textY + 60);
			g.drawString("Target: " + t.getTarget().toString(), textX, textY + 80);
			g.drawString("Progress: " + t.getTarget().getProgress(this), textX, textY + 100);
		});

		/* Draw progress bar */
		if (xpGained > 0) progressBar.draw(g);

		/* Draw cross cursor */
		if (getMouse().isWithinCanvas()) {
			g.setColor(cursorColour);
			int mouseX = getMouse().getPosition().x, mouseY = getMouse().getPosition().y;
			g.drawLine(mouseX - 5, mouseY + 5, mouseX + 5, mouseY - 5);
			g.drawLine(mouseX + 5, mouseY + 5, mouseX - 5, mouseY - 5);
		}
	}

	private String runescapeFormat(long l) {
		String[] suffix = new String[] { "K", "M", "B", "T" };
		int size = (l != 0) ? (int) Math.log10(l) : 0;
		if (size >= 3) while (size % 3 != 0)
			size--;
		return (size >= 3) ? +(Math.round((l / Math.pow(10, size)) * 10) / 10d) + suffix[(size / 3) - 1] : +l + "";
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
