package ui;

import javax.swing.*;

import life.base.Point;
import life.library.Library;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

class RadioButtonpPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JRadioButton[] rbs;
	JButton b;
	JLabel l;
	JTextField t;

	public RadioButtonpPanel(int radioButtonpPanel_Width, int radioButtonpPanel_Height, GridPanel gp, LabelPanel lp) {
		setSize(new Dimension(radioButtonpPanel_Width, radioButtonpPanel_Height));
		setPreferredSize(new Dimension(radioButtonpPanel_Width, radioButtonpPanel_Height));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		String rbText[] = { "Blip", "Blip2", "Block", "Beehive", "Loaf", "Blinker", "Glider1", "Glider2", "Glider3","Random",
				"New Pattern" };
		ButtonGroup bg = new ButtonGroup();
		this.rbs = new JRadioButton[11];
		for (int i = 0; i < rbs.length; i++) {
			rbs[i] = new JRadioButton(rbText[i]);
			bg.add(rbs[i]);
			c.gridy = i + 1;
			add(rbs[i], c);
		}

		l = new JLabel(" Choose Your Initial Pattern");
		l.setFont(new Font("San Francisco", Font.PLAIN, 16));
		c.ipady = 20;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		add(l, c);

		t = new JTextField("Write Your Customized Pattern Here!");
		c.ipady = 10;
		t.setEditable(false);
		c.gridy = rbs.length + 2;
		add(t, c);

		b = new JButton("Confirm");
		c.gridy = rbs.length + 3;
		c.ipady = 0;
		c.anchor = GridBagConstraints.PAGE_END;
		add(b, c);
		
		for (int i = 0; i < rbText.length - 1; i++) {
			rbs[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					t.setText("Write Your Customized Pattern Here");
					t.setEditable(false);
				}
			});
		}

		rbs[rbs.length - 1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.setText("");
				t.setEditable(true);
			}
		});

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JRadioButton r : rbs) {
					if (r.isSelected()) {
						lp.l[3].setText("Pattern: " + r.getText());
						if (r.getText()=="Random") {
							JOptionPane.showMessageDialog(getParent(), "Your Choice Is " + r.getText());
							gp.patternName = r.getText();
							gp.map.random();
							gp.restart_random();
						}
						else if (r.getText() != "New Pattern") {
							JOptionPane.showMessageDialog(getParent(), "Your Choice Is " + r.getText());
							gp.patternName = r.getText();
							gp.pattern = Library.get(gp.patternName);
							gp.restart();
						} else if (r.getText() == "New Pattern") {
//							JOptionPane.showMessageDialog(getParent(),
//									"Your Customized Pattern Is " + "\r\n" + t.getText());
							try {
								gp.Customized_Pattern = t.getText();
								gp.points = Point.points(t.getText());
								gp.restart_customized_pattern();
							}  catch (NumberFormatException nume){
								JOptionPane.showMessageDialog(getParent(), "Invalid Input");
								return;
							}  catch (ArrayIndexOutOfBoundsException e2){
								JOptionPane.showMessageDialog(getParent(), "Invalid Input");
								return;
							}
//							gp.centralise(gp.points);

						}
					}

				}

			}
		});

	}

}