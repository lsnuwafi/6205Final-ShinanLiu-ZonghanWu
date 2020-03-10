package ui;

import life.base.HelloWorld;
import life.base.Point;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConfigurationPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JLabel[] l;
    private JButton b;

    public ConfigurationPanel(int configurationPanel_Width, int configurationPanel_Height, GridPanel gp) {
        // TODO Auto-generated constructor stub
        setPreferredSize(new Dimension(configurationPanel_Width, configurationPanel_Height));
        setSize(new Dimension(configurationPanel_Width, configurationPanel_Height));
        this.l = new JLabel[6];
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.ipady = 40;
        c.gridy = 0;
        String labelArray[] = {"Generate Initial Pattern", "by Genetic Algorithm", "Status: Inactive ", "Chromosome Length: ", " ",
                " "};
        Font f = new Font("San Francisco", Font.PLAIN, 16);
        for (int i = 0; i < 6; i++) {
            l[i] = new JLabel(labelArray[i]);
            if (i < 3) {
                add(l[i], c);
            }
            l[i].setFont(f);
        }
        this.b = new JButton("Generate New Pattern");
        add(b, c);
        add(l[3]);
		Font f1 = new Font("San Francisco", Font.PLAIN, 12);
			l[5]=new JLabel("<html>The cells cannot cross <br>the borders of grid.<br>Thus, boarder cells growth seems <br>different between with the <br>infinite grid</html>", SwingConstants.CENTER);
		l[5].setFont(f1);
		add(l[5]);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                l[2].setText("Status: Computing...");
                JOptionPane.showMessageDialog(getParent(), "Computing...");
                String[] data = HelloWorld.run();
                JOptionPane.showMessageDialog(getParent(), "All Set! Please click step or auto!");
                l[2].setText("Status: All Set");
                l[3].setText("Chromosome Length: " + data[0]);
                gp.Customized_Pattern = data[1];
                gp.points = Point.points(data[1]);
                gp.restart_customized_pattern();
            }
        });

    }

}
