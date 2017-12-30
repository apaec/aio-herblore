package uk.co.ramyun.herblore.movement;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

public class RandomCameraEvent extends RandomTimedEvent implements MovementEvent {

	/**
	 * @author © Michael 7 Sep 2017
	 * @file CameraEvent.java
	 */

	private final int MAX_CAMERA_PITCH = 67;

	public RandomCameraEvent() {
		super(0, 0);
	}

	public RandomCameraEvent(long threshold, long deviation) {
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
				script.getCamera().toTop();
				break;
			case 1:
				script.getCamera().moveYaw(random.gRandomInRange(0, 180));
				break;
			case 3:
				script.getCamera().moveYaw(random.gRandomInRange(0, 90));
				break;
			case 2:
				script.getCamera()
						.movePitch(random.gRandomInRange(script.getCamera().getLowestPitchAngle(), MAX_CAMERA_PITCH));
				break;
			case 4:
				script.getCamera().toBottom();
				break;
		}
		super.execute(script);
	}

	@Override
	public String toString() {
		return "Camera event";
	}

}
