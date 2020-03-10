package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OperationlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GridPanel gp;
	public JButton b[];

	OperationlPanel(int controlPanel_Width, int controlPanel_Height, GridPanel gridpanel) {

		this.gp = gridpanel;
		setSize(controlPanel_Width, controlPanel_Height);
		setPreferredSize(new Dimension(controlPanel_Width, controlPanel_Height));
		this.b = new JButton[6];
		String buttonArray[] = { "Step", "Auto", "Stop/Resume", "Speed Up", "Speed Down", "Restart" };
		Font f = new Font("San Francisco", Font.PLAIN, 14);
		for (int i = 0; i < 6; i++) {
			b[i] = new JButton(buttonArray[i]);
			add(b[i]);
			b[i].setFont(f);
		}
		setLayout(new GridLayout(2, 3));

		b[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gp.draw_step();
			}
		});

		b[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gp.draw_auto();
			}
		});

		b[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gp.pause_flag_setting();
			}
		});

		b[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gp.speed_setting("1");
			}
		});

		b[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gp.speed_setting("2");
			}
		});

		b[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pattern_StatuString = gp.lp.l[3].getText();
				if (pattern_StatuString.hashCode() == "Pattern: New Pattern".hashCode()) {
					gp.restart_customized_pattern();
				} else if (pattern_StatuString.hashCode() == "Pattern: Random".hashCode()) {
					gp.restart_random();
				} else {
					gp.restart();
				}
			}
		});
	}
}
