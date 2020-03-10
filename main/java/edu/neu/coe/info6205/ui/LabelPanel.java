package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JLabel;

public class LabelPanel extends Panel {

	private static final long serialVersionUID = 1L;
	public JLabel l[];

	public LabelPanel(int labelPanel_Width, int labelPanel_Height) {
		// TODO Auto-generated constructor stub
		setSize(labelPanel_Width, labelPanel_Height);
		setPreferredSize(new Dimension(labelPanel_Width, labelPanel_Height));
		this.l = new JLabel[6];
		String labelArray[] = { "Program Status: Inactive", "Game Status: Inactive","FPS: ", "Pattern: ", "Generation Number: ",
				"Live Cell Number: " };
		Font f = new Font("San Francisco", Font.PLAIN, 14);
		for (int i = 0; i < 6; i++) {
			l[i] = new JLabel(labelArray[i]);
			add(l[i]);
			l[i].setFont(f);
		}
		setLayout(new GridLayout(6, 1));
	}

}
