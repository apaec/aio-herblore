package uk.co.ramyun.herblore.util;

import org.osbot.rs07.script.MethodProvider;

public class AnimationTracker implements Runnable {

	/**
	 * @author © Michael 1 Jan 2018
	 * @file AnimationTracker.java
	 */

	private MethodProvider mp;
	private final Timer animationTimer = new Timer(5000L, 5000L);
	private boolean exit = false;

	public AnimationTracker(MethodProvider mp) {
		this.mp = mp;
	}

	public boolean isBusy(long threshold) {
		return animationTimer.getElapsed() < threshold || mp.myPlayer().isAnimating();
	}

	@Override
	public void run() {
		while (!exit) {
			if (mp.myPlayer().isAnimating()) animationTimer.reset();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}

	public void exit() {
		exit = true;
	}

}
