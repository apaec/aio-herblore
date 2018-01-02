package uk.co.ramyun.herblore.paint;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

public class ProgressBar {

	/**
	 * @author © Michael 12 Jun 2017
	 * @file ProgressBar.java
	 */

	private final MethodProvider mp;
	private final Skill skill;
	private String name;
	private int x, y, width, height;
	private Color red = Color.RED, green = Color.GREEN, text = Color.BLACK;

	public ProgressBar(MethodProvider mp, Skill skill, Color red, Color green, Color textAndBorder, int x, int y,
			int width, int height) {
		this.mp = mp;
		this.name = skill.toString();
		this.skill = skill;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.red = red;
		this.green = green;
		this.text = textAndBorder;
	}

	public ProgressBar(MethodProvider mp, Skill skill, int x, int y, int width, int height) {
		this.mp = mp;
		this.name = skill.toString();
		this.skill = skill;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Skill getSkill() {
		return skill;
	}

	public int getTargetExperience() {
		return mp.getSkills().getExperienceForLevel(mp.getSkills().getStatic(skill) + 1)
				- mp.getSkills().getExperienceForLevel(mp.getSkills().getStatic(skill));
	}

	public int getRemainingExperience() {
		return mp.getSkills().experienceToLevel(skill);
	}

	public int getCurrentExperience() {
		return mp.getSkills().getExperience(skill)
				- mp.getSkills().getExperienceForLevel(mp.getSkills().getStatic(skill));
	}

	public int getPercentage(int current, int total) {
		return current * 100 / total;
	}

	public int getWidth(int percentage, int totalWidth) {
		return percentage * totalWidth / 100;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getRed() {
		return red;
	}

	public void setRed(Color red) {
		this.red = red;
	}

	public Color getGreen() {
		return green;
	}

	public void setGreen(Color green) {
		this.green = green;
	}

	public void draw(Graphics2D g) {
		Color initialColour = g.getColor();
		int percentage = getPercentage(getCurrentExperience(), getTargetExperience()),
				greenWidth = getWidth(percentage, width);

		/* Progress made */
		g.setColor(green);
		g.fillRect(x, y, greenWidth, height);

		/* Progress to go */
		g.setColor(red);
		g.fillRect(x + greenWidth, y, width - greenWidth, height);

		/* Border */
		g.setColor(text);
		g.drawRect(x, y, width, height);

		/* Info text */
		String text = name + " (" + mp.getSkills().getStatic(skill) + "): " + percentage + "%";
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		Rectangle2D rect = metrics.getStringBounds(text, g);
		int xText = (width - (int) (rect.getWidth())) / 2,
				yText = (height - (int) (rect.getHeight())) / 2 + metrics.getAscent();
		g.drawString(text, x + xText, y + yText);

		g.setColor(initialColour);
	}
}
