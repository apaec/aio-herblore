package uk.co.ramyun.herblore.movement;

import java.awt.Point;

import org.osbot.rs07.script.MethodProvider;

public class RandomMouseEvent extends RandomTimedEvent implements MovementEvent {

	/**
	 * @author © Michael 7 Sep 2017
	 * @file RandomMouseEvent.java
	 */

	public RandomMouseEvent(long threshold, long deviation) {
		super(threshold, deviation);
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute(MethodProvider mp) {
		switch (MethodProvider.random(5)) {
			case 0:
				mp.getMouse().moveOutsideScreen();
				break;
			case 1:
				mp.getMouse().move(random.gRandomInRange(0, mp.getBot().getCanvas().getWidth()),
						random.gRandomInRange(0, mp.getBot().getCanvas().getHeight()));
				break;
			case 2:
				Point destination20 = getNearbyPoint(mp, 20);
				mp.getMouse().move(destination20.x, destination20.y);
				break;
			case 3:
				Point destination50 = getNearbyPoint(mp, 50);
				mp.getMouse().move(destination50.x, destination50.y);
				break;
			case 4:
				Point destination200 = getNearbyPoint(mp, 200);
				mp.getMouse().move(destination200.x, destination200.y);
				break;
		}
		super.execute(mp);
	}

	private Point getNearbyPoint(MethodProvider mp, int squareRadius) {
		Point currentPosition = mp.getMouse().getPosition();
		return new Point(random.gRandomInRange(currentPosition.x - squareRadius, currentPosition.x + squareRadius),
				random.gRandomInRange(currentPosition.y - squareRadius, currentPosition.y + squareRadius));
	}

	@Override
	public String toString() {
		return "Mouse event";
	}
}
