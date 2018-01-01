package uk.co.ramyun.herblore.util;

import java.util.Arrays;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class MakeWidget {

	/**
	 * @author © Michael 1 Jan 2018
	 * @file MakeWidget.java
	 */

	public MakeWidget() {}

	public boolean buttonAvailable(MethodProvider mp, String buttonName) {
		return getForSpellName(mp, 270, buttonName) != null;
	}

	public boolean allSelected(MethodProvider mp) {
		return getForActionExact(mp, 270, "All") == null;
	}

	public boolean selectAll(MethodProvider mp) {
		RS2Widget valueWidget = getForActionExact(mp, 270, "All");
		if (valueWidget != null) {
			if (valueWidget.interact("All")) return new ConditionalSleep(5000) {
				@Override
				public boolean condition() throws InterruptedException {
					return getForActionExact(mp, 270, "All") == null;
				}
			}.sleep();
		} else return true;
		return false;
	}

	public boolean pressButton(MethodProvider mp, String buttonName, String buttonInteraction,
			ConditionalSleep afterPress) {
		RS2Widget mainWidget = getForSpellName(mp, 270, buttonName);
		if (mainWidget != null) {
			if (mp.getInventory().isItemSelected()) mp.getInventory().deselectItem();
			else if (!allSelected(mp)) selectAll(mp);
			else if (mainWidget.interact(buttonInteraction)) return afterPress.sleep();
		}
		return false;
	}

	public void pressButtonAnimation(MethodProvider mp, String buttonName, String buttonInteraction) {
		pressButton(mp, buttonName, buttonInteraction, new ConditionalSleep(4000) {
			@Override
			public boolean condition() throws InterruptedException {
				return mp.myPlayer().isAnimating();
			}
		});
	}

	public boolean sleepUntilButtonAvailable(MethodProvider mp, String buttonName, int timeout) {
		return new ConditionalSleep(timeout) {
			@Override
			public boolean condition() throws InterruptedException {
				RS2Widget mainWidget = getForSpellName(mp, 270, buttonName);
				return mainWidget != null && mainWidget.isVisible();
			}
		}.sleep();
	}

	@SuppressWarnings("unchecked")
	private RS2Widget getForActionExact(MethodProvider mp, int root, String seq) {
		return mp.getWidgets().singleFilter(root, w -> w.getInteractActions() != null
				&& Arrays.stream(w.getInteractActions()).filter(s -> s.equals(seq)).count() > 0);
	}

	@SuppressWarnings("unchecked")
	private RS2Widget getForSpellName(MethodProvider mp, int root, String seq) {
		return mp.getWidgets().singleFilter(root, w -> w.getSpellName().contains(seq));
	}

}
