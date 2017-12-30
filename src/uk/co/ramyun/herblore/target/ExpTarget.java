package uk.co.ramyun.herblore.target;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

public class ExpTarget extends AbstractTarget {

	/**
	 * @author © Michael 10 Aug 2017
	 * @file ExpTarget.java
	 */

	private final Skill skill;
	private long startingXp = 0;

	public ExpTarget(long xpThreshold, Skill skill) {
		setThreshold(xpThreshold);
		this.skill = skill;
	}

	@Override
	public long maxThreshold() {
		return 1000000;
	}

	@Override
	public long minThreshold() {
		return 1;
	}

	@Override
	public void start(MethodProvider mp) {
		startingXp = mp.getExperienceTracker().getGainedXP(skill);
		super.start(mp);
	}

	@Override
	protected boolean targetReached(MethodProvider mp) {
		return mp.getExperienceTracker().getGainedXP(skill) > getLimitXp();
	}

	private long getLimitXp() {
		return startingXp + getThreshold();
	}

	@Override
	public String toString() {
		return skill.toString() + " exp gained";
	}

}
