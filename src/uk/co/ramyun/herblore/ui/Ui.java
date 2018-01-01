package uk.co.ramyun.herblore.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.osbot.rs07.api.ui.Skill;

import uk.co.ramyun.herblore.core.HerbloreTaskManager;
import uk.co.ramyun.herblore.potion.Herb;
import uk.co.ramyun.herblore.potion.Potion;
import uk.co.ramyun.herblore.potion.Tar;
import uk.co.ramyun.herblore.potion.Unfinished;
import uk.co.ramyun.herblore.target.ExpTarget;
import uk.co.ramyun.herblore.target.LevelTarget;
import uk.co.ramyun.herblore.target.MinuteTarget;
import uk.co.ramyun.herblore.target.NoTarget;
import uk.co.ramyun.herblore.task.CleanHerbs;
import uk.co.ramyun.herblore.task.HerbloreTask;
import uk.co.ramyun.herblore.task.MakeFinished;
import uk.co.ramyun.herblore.task.MakeTar;
import uk.co.ramyun.herblore.task.MakeUnfinished;
import uk.co.ramyun.herblore.util.TaskCollectionObserver;

public class Ui extends JFrame implements TaskCollectionObserver {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file Ui.java
	 */

	private static final long serialVersionUID = 1L;

	private final JPanel contentPane = new JPanel(), controlButtonPanel = new JPanel(), addButtonPanel = new JPanel();
	private final JButton rmButton = new JButton("Remove"), clrButton = new JButton("Clear"),
			cleanButton = new JButton("Add herb cleaning task"),
			unfinishedButton = new JButton("Add unfinished potion task"),
			finishedButton = new JButton("Add finished potion task"), tarButton = new JButton("Add tar making task"),
			startButton = new JButton("Start");
	private final HerbloreTaskManager herbloreTaskManager = new HerbloreTaskManager();
	private final JScrollPane scrollPane = new JScrollPane();
	private final DefaultListModel<HerbloreTask> listModel = new DefaultListModel<HerbloreTask>();
	private final JList<HerbloreTask> taskList = new JList<HerbloreTask>(listModel);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new Ui().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Ui() {
		herbloreTaskManager.registerObserver(this);
		setTitle("All-In-One Herblore by Apaec");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1000, 400);
		setContentPane(contentPane);
		setLayout(new BorderLayout(5, 5));

		cleanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showTaskConfigureMenu(new TaskConfigureUi(new CleanHerbs(Herb.GUAM_LEAF), new NoTarget(),
						new ExpTarget(Skill.HERBLORE), new LevelTarget(Skill.HERBLORE), new MinuteTarget()));
			}
		});

		unfinishedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showTaskConfigureMenu(new TaskConfigureUi(new MakeUnfinished(Unfinished.GUAM_POTION), new NoTarget(),
						new ExpTarget(Skill.HERBLORE), new LevelTarget(Skill.HERBLORE), new MinuteTarget()));
			}
		});

		finishedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showTaskConfigureMenu(new TaskConfigureUi(new MakeFinished(Potion.ATTACK_POTION), new NoTarget(),
						new ExpTarget(Skill.HERBLORE), new LevelTarget(Skill.HERBLORE), new MinuteTarget()));
			}
		});

		tarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showTaskConfigureMenu(new TaskConfigureUi(new MakeTar(Tar.GUAM_TAR), new NoTarget(),
						new ExpTarget(Skill.HERBLORE), new LevelTarget(Skill.HERBLORE), new MinuteTarget()));
			}
		});

		rmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				taskList.getSelectedValuesList().stream().forEach(o -> herbloreTaskManager.deregisterTask(o));
			}
		});

		clrButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				herbloreTaskManager.deregisterAll();
			}
		});

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		scrollPane.setColumnHeaderView(new JLabel("Registered Task Queue", SwingConstants.CENTER));
		scrollPane.setViewportView(taskList);

		controlButtonPanel.setLayout(new GridLayout(1, 2, 5, 5));
		addButtonPanel.setLayout(new GridLayout(5, 1, 5, 5));

		controlButtonPanel.add(rmButton);
		controlButtonPanel.add(clrButton);

		addButtonPanel.add(cleanButton);
		addButtonPanel.add(unfinishedButton);
		addButtonPanel.add(finishedButton);
		addButtonPanel.add(tarButton);
		addButtonPanel.add(controlButtonPanel);

		contentPane.add(addButtonPanel, BorderLayout.LINE_END);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(startButton, BorderLayout.PAGE_END);
	}

	private boolean showTaskConfigureMenu(TaskConfigureUi toShow) {
		if (JOptionPane.showConfirmDialog(Ui.this, toShow, "Task Configuration",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			HerbloreTask newTask = toShow.getTask();
			newTask.setTarget(toShow.getTarget());
			herbloreTaskManager.registerTask(newTask);
			return true;
		} else return false;
	}

	public HerbloreTaskManager getHerbloreTaskManager() {
		return herbloreTaskManager;
	}

	@Override
	public void taskRegistered(HerbloreTask t) {
		listModel.addElement(t);
	}

	@Override
	public void taskDeregistered(HerbloreTask t) {
		listModel.removeElement(t);
	}

	@Override
	public void cleared() {
		listModel.removeAllElements();
	}
}
