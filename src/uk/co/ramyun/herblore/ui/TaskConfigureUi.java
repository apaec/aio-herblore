package uk.co.ramyun.herblore.ui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.osbot.rs07.api.ui.Skill;

import uk.co.ramyun.herblore.potion.Herb;
import uk.co.ramyun.herblore.target.AbstractTarget;
import uk.co.ramyun.herblore.target.ExpTarget;
import uk.co.ramyun.herblore.target.LevelTarget;
import uk.co.ramyun.herblore.target.MinuteTarget;
import uk.co.ramyun.herblore.target.NoTarget;
import uk.co.ramyun.herblore.task.CleanHerbs;
import uk.co.ramyun.herblore.task.HerbloreTask;

public class TaskConfigureUi extends JFrame {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file TaskConfigureUi.java
	 */

	private static final long serialVersionUID = 1L;

	private final JPanel contentPane = new JPanel();
	private final HerbloreTask task;
	private final AbstractTarget[] targets;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TaskConfigureUi(new CleanHerbs(Herb.GUAM_LEAF), new NoTarget(), new ExpTarget(Skill.HERBLORE),
							new LevelTarget(Skill.HERBLORE), new MinuteTarget()).setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TaskConfigureUi(HerbloreTask toConfigure, AbstractTarget... targets) {
		this.task = toConfigure;
		this.targets = targets;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 450, 200);
		setContentPane(contentPane);
		setLayout(new GridBagLayout());
		Insets two = new Insets(2, 2, 2, 2);
		contentPane.add(task.getPanel(), createConstraints(0, 0, 1, GridBagConstraints.HORIZONTAL, two));
		contentPane.add(new TargetPanel(this.targets), createConstraints(0, 1, 1, GridBagConstraints.HORIZONTAL, two));
	}

	private GridBagConstraints createConstraints(int x, int y, int width, int fill, Insets insets) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.fill = fill;
		constraints.insets = insets;
		return constraints;
	}

}
