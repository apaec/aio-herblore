package uk.co.ramyun.herblore.movement;

import java.awt.Point;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class RandomMouseEvent extends RandomTimedEvent implements MovementEvent {

	/**
	 * @author © Michael 7 Sep 2017
	 * @file MouseEvent.java
	 */

	public RandomMouseEvent(long threshold, long deviation) {
		super(threshold, deviation);
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute(Script script) {
		switch (MethodProvider.random(5)) {
			case 0:
				script.getMouse().moveOutsideScreen();
				break;
			case 1:
				script.getMouse().move(random.gRandomInRange(0, script.getBot().getCanvas().getWidth()),
						random.gRandomInRange(0, script.getBot().getCanvas().getHeight()));
				break;
			case 2:
				Point destination20 = getNearbyPoint(script, 20);
				script.getMouse().move(destination20.x, destination20.y);
				break;
			case 3:
				Point destination50 = getNearbyPoint(script, 50);
				script.getMouse().move(destination50.x, destination50.y);
				break;
			case 4:
				Point destination200 = getNearbyPoint(script, 200);
				script.getMouse().move(destination200.x, destination200.y);
				break;
		}
		super.execute(script);
	}

	private Point getNearbyPoint(Script script, int squareRadius) {
		Point currentPosition = script.getMouse().getPosition();
		return new Point(random.gRandomInRange(currentPosition.x - squareRadius, currentPosition.x + squareRadius),
				random.gRandomInRange(currentPosition.y - squareRadius, currentPosition.y + squareRadius));
	}

	@Override
	public String toString() {
		return "Mouse event";
	}
}
