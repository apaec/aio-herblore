package uk.co.ramyun.herblore.target;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

public class LevelTarget extends AbstractTarget {

	/**
	 * @author © Michael 10 Aug 2017
	 * @file LevelTarget.java
	 */

	private final Skill skill;

	public LevelTarget(Skill skill) {
		this.skill = skill;
	}

	public LevelTarget(int level, Skill skill) {
		setThreshold(level);
		this.skill = skill;
	}

	@Override
	protected boolean targetReached(MethodProvider mp) {
		return mp.getSkills().getStatic(skill) >= getThreshold();
	}

	@Override
	public long maxThreshold() {
		return 99L;
	}

	@Override
	public long minThreshold() {
		return 2L;
	}

	@Override
	public long defaultThreshold() {
		return maxThreshold();
	}

	@Override
	public String getProgress(MethodProvider mp) {
		return mp.getSkills().getStatic(skill) + "/" + getThreshold();
	}

	@Override
	public String getName() {
		return skill.toString() + " level reached";
	}

}
