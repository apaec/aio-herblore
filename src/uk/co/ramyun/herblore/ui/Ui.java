package uk.co.ramyun.herblore.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.osbot.rs07.api.ui.Skill;

import uk.co.ramyun.herblore.core.HerbloreTaskManager;
import uk.co.ramyun.herblore.potion.Herb;
import uk.co.ramyun.herblore.target.ExpTarget;
import uk.co.ramyun.herblore.target.LevelTarget;
import uk.co.ramyun.herblore.target.MinuteTarget;
import uk.co.ramyun.herblore.target.NoTarget;
import uk.co.ramyun.herblore.task.CleanHerbs;
import uk.co.ramyun.herblore.task.HerbloreTask;
import uk.co.ramyun.herblore.util.TaskCollectionObserver;

public class Ui extends JFrame implements TaskCollectionObserver {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file Ui.java
	 */

	private static final long serialVersionUID = 1L;

	private final JPanel contentPane = new JPanel();
	private final JButton testButton = new JButton("Test Button"), rmButton = new JButton("Remove");
	private final HerbloreTaskManager herbloreTaskManager = new HerbloreTaskManager();

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 880, 470);
		setContentPane(contentPane);

		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TaskConfigureUi toShow = new TaskConfigureUi(new CleanHerbs(Herb.GUAM_LEAF), new NoTarget(),
						new ExpTarget(Skill.HERBLORE), new LevelTarget(Skill.HERBLORE), new MinuteTarget());
				if (JOptionPane.showConfirmDialog(Ui.this, toShow, "Task Configuration",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					HerbloreTask newTask = toShow.getTask();
					newTask.setTarget(toShow.getTarget());
					herbloreTaskManager.registerTask(newTask);
				}
			}
		});

		rmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				taskList.getSelectedValuesList().stream().forEach(o -> herbloreTaskManager.deregisterTask(o));
			}
		});

		contentPane.add(testButton);
		contentPane.add(rmButton);
		contentPane.add(taskList);
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
