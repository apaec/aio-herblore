package uk.co.ramyun.herblore.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ui extends JFrame {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file Ui.java
	 */

	private static final long serialVersionUID = 1L;

	private final JPanel contentPane = new JPanel();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Ui().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Ui() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 880, 470);
		setContentPane(contentPane);
	}
}
