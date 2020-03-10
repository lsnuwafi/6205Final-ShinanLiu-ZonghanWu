package ui;

import java.awt.BorderLayout;

import javax.swing.*;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title = "Game of Life";
	private int GridPanel_Width = 500;
	private int GridPanel_Height = 500;
	private int OperationlPanel_Width = 500;
	private int OperationlPanel_Height = 80;
	private int LabelPanel_Width = 500;
	private int LabelPanel_Height = 100;
	private int RadioButtonpPanel_Width =220;
	private int RadioButtonpPanel_Height =400;
	private int ConfigurationPanel_Width=220;
	private int ConfigurationPanel_Height=400;
	public int GridSize_x = 120;
	public int GridSize_y = 120;

	public MainFrame(String pattern, String patternName) {
		super();
		setTitle(title);
		LabelPanel lp = new LabelPanel(LabelPanel_Width, LabelPanel_Height);
		GridPanel gp = new GridPanel(GridPanel_Width, GridPanel_Height, GridSize_x, GridSize_y, pattern, patternName,
				lp);
		OperationlPanel op = new OperationlPanel(OperationlPanel_Width, OperationlPanel_Height, gp);
		RadioButtonpPanel rp = new RadioButtonpPanel(RadioButtonpPanel_Width,RadioButtonpPanel_Height,gp,lp);
		ConfigurationPanel cp = new ConfigurationPanel(ConfigurationPanel_Width,ConfigurationPanel_Height,gp);
		add(lp, BorderLayout.NORTH);
		add(gp, BorderLayout.CENTER);
		add(op, BorderLayout.SOUTH);
		add(rp, BorderLayout.EAST);
		add(cp,BorderLayout.WEST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
}