package uk.co.ramyun.herblore.movement;

import org.osbot.rs07.script.MethodProvider;

public class RandomCameraEvent extends RandomTimedEvent implements MovementEvent {

	/**
	 * @author © Michael 7 Sep 2017
	 * @file RandomCameraEvent.java
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
	public void execute(MethodProvider mp) {
		switch (MethodProvider.random(5)) {
			case 0:
				mp.getCamera().toTop();
				break;
			case 1:
				mp.getCamera().moveYaw(random.gRandomInRange(0, 180));
				break;
			case 3:
				mp.getCamera().moveYaw(random.gRandomInRange(0, 90));
				break;
			case 2:
				mp.getCamera().movePitch(random.gRandomInRange(mp.getCamera().getLowestPitchAngle(), MAX_CAMERA_PITCH));
				break;
			case 4:
				mp.getCamera().toBottom();
				break;
		}
		super.execute(mp);
	}

	@Override
	public String toString() {
		return "Camera event";
	}

}
