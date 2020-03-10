package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import life.base.Point;

public class GridPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private int Grid_x0, Grid_y0, Grid_width, Grid_height;
    private int block_x, block_y, block_width, block_height;
    private Random r;
    public String patternName;
    public String pattern;
    public String Customized_Pattern;
    public List<Point> points;
    private ArrayList<boolean[][]> game_history;
    public Map map;
    public boolean live_flag = true;
    public boolean pause_flag = false;

    private Graphics2D g;
    private BufferedImage im;

    public Timer timer;
    public int refresh_speed, FPS, FPS_new, refresh_speed_new;
    public LabelPanel lp;
    public int generation_number;
    public int live_cells_number;

    public GridPanel(int width, int height, int block_x, int block_y, String pattern, String patternName,
                     LabelPanel labelpanel) {
        this.r = new Random();
        this.Grid_x0 = 10;
        this.Grid_y0 = 10;
        this.Grid_width = width - 20;
        this.Grid_height = height - 20;
        this.block_x = block_x;
        this.block_y = block_y;
        this.block_width = Grid_width / block_x;
        this.block_height = Grid_height / block_y;
        this.FPS = 20;
        this.FPS_new = this.FPS;
        this.refresh_speed = 1000 / FPS;
        this.refresh_speed_new = refresh_speed;
        this.pattern = pattern;
        this.patternName = patternName;
        this.points = Point.points(pattern);
        centralise(this.points);
        this.map = new Map(block_x, block_y);
        map.Update(points);
        this.game_history = new ArrayList<>();
        game_history.add(map.map);
        this.generation_number = -1;
        this.live_cells_number = map.getlive();
        this.lp = labelpanel;
        lp.l[3].setText("Pattern: " + patternName);
        this.im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.g = im.createGraphics();
        this.g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.timer = new Timer(this.refresh_speed, display);
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
    }

    public void centralise(List<Point> points) {
        // TODO Auto-generated method stub
        for (Point point : points) {
            int index = points.indexOf(point);
            points.set(index, point.move(block_x / 2 - 1, block_y / 2 - 1));
        }
    }

    public void draw() {
        if (live_flag) {
            if (!pause_flag) {
                if (g != null) {
                    this.live_cells_number = this.map.getlive();
                    this.generation_number++;
                    super.paint(g);
                    drawMap();
                    repaint();
                    isAlive();
                    rules();
                    lp.l[4].setText("Gerneration Number: " + generation_number);
                    lp.l[5].setText("Live Cell Number: " + live_cells_number);
                }
            }
        }
    }

    private void drawMap() {
        // TODO Auto-generated method stub
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (map.getValue(i, j)) {

                    g.setColor(Color.BLUE);
                    fillGrid(g, i, j);
                }
            }
        }
    }

    ActionListener display = event -> draw();

    public void draw_step() {
        timer.stop();
        if (!pause_flag) {
            lp.l[0].setText("Program Status: Drawing Graphic Step by Step");
        }
        draw();
    }

    public void draw_auto() {
        if (!pause_flag) {
            lp.l[0].setText("Program Status: Drawing Graphic Automatically");
            timer.start();
        }
    }

    public void isAlive() {
        live_cells_number = map.getlive();
        if (this.live_cells_number == 0) {
            live_flag = false;
            lp.l[1].setText("Game Status: Terminated");
        } else {
            live_flag = true;
            lp.l[1].setText("Game Status: Running");
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(this.im, 0, 0, this);
        drawGrid(g);
    }

    public void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        g2d.drawRect(Grid_x0, Grid_y0, Grid_width, Grid_height);
        for (int i = 1; i <= block_x; i++)
            g2d.drawLine(Grid_x0 + i * block_width, Grid_y0, Grid_x0 + i * block_width, Grid_y0 + Grid_height);
        for (int i = 1; i <= block_y; i++)
            g2d.drawLine(Grid_x0, Grid_y0 + i * block_height, Grid_x0 + Grid_width, Grid_y0 + i * block_height);
        g2d.dispose();
    }

    private void fillGrid(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(1.0f));
//		g2d.setColor(Color.RED);
        g2d.fillRect(Grid_x0 + x * block_width, Grid_y0 + y * block_height, block_width, block_height);
        g2d.dispose();
    }

    public void pause_flag_setting() {
        if (this.pause_flag) {
            timer.start();
            this.pause_flag = false;
            lp.l[0].setText("Program Status: Resume");
        } else {
            this.pause_flag = true;
            timer.stop();
            lp.l[0].setText("Program Status: Pause");
        }

    }

    public void restart() {
        restart_default();
        this.points = Point.points(pattern);
        centralise(points);
        map = new Map(block_x, block_y);
        map.Update(points);
        lp.l[1].setText("Game Status: Inactive");
        this.live_cells_number = map.getlive();
        this.game_history = new ArrayList<>();
        game_history.add(map.map);
        repaint();
    }

    public void restart_customized_pattern() {
        restart_default();
        this.points = Point.points(Customized_Pattern);
//		centralise(points);
        map = new Map(block_x, block_y);
        map.Update(points);
        this.live_cells_number = map.getlive();
        this.game_history = new ArrayList<>();
        game_history.add(map.map);
        repaint();
    }

    public void restart_random() {
        // TODO Auto-generated method stub
        restart_default();
        map = new Map(block_x, block_y);
        map.random();
        this.live_cells_number = map.getlive();
        this.game_history = new ArrayList<>();
        game_history.add(map.map);
        repaint();
    }

    /**
     *
     */
    private void restart_default() {
        super.paint(g);
        timer.stop();
        lp.l[0].setText("Program Status: Restart");
        lp.l[2].setText("FPS:" + this.FPS);
        this.FPS_new = this.FPS;
        this.refresh_speed_new = this.refresh_speed;
        pause_flag = false;
		live_flag = true;
        this.timer = new Timer(refresh_speed, display);
        this.generation_number = -1;
    }

    public void speed_setting(String key) {
        // TODO Auto-generated method stub
        timer.stop();
        switch (key) {
            case "1":
                if (this.FPS_new < 195) {
                    this.FPS_new = this.FPS_new + 5;
                    lp.l[2].setText("FPS:" + this.FPS_new);
                } else {
                    this.FPS_new = 200;
                    lp.l[2].setText("FPS:" + this.FPS_new + "  Maximum FPS!!!");
                }
                if (!pause_flag) {
                    lp.l[0].setText("Program Status: Speed UP");
                }
                this.refresh_speed_new = 1000 / FPS_new;

                break;
            case "2":
                if (this.FPS_new > 5) {
                    this.FPS_new = this.FPS_new - 5;
                    this.refresh_speed_new = 1000 / FPS_new;
                    lp.l[2].setText("FPS:" + this.FPS_new);
                } else {
                    this.FPS_new = 0;
                    this.refresh_speed_new = 1000000;
                    lp.l[2].setText("FPS:" + this.FPS_new + "  Mininum FPS!!!");
                }
                if (!pause_flag) {
                    lp.l[0].setText("Program Status: Speed Down");
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + key);
        }
        this.timer = new Timer(refresh_speed_new, display);
        timer.start();
        super.paint(g);
    }

    public void rules() {
        int nbr = 0;
        Map map_new = new Map(block_x, block_y);
        for (int i = 0; i < block_x; i++) {
            for (int j = 0; j < block_y; j++) {
                nbr = getNeighbor(i, j);
                if (map.getValue(i, j)) {
                    if (nbr < 2) {
                        map_new.setValue(i, j, false);
                    } else if (nbr <= 3) {
                        map_new.setValue(i, j, map.getValue(i, j));
                    } else {
                        map_new.setValue(i, j, false);
                    }
                } else if (!map.getValue(i, j)) {
                    if (nbr == 3) {
                        map_new.setValue(i, j, true);
                    } else
                        map_new.setValue(i, j, false);
                }
            }
        }
        map = map_new;

    }

//	private void isCycle(ArrayList<boolean[][]> gh, boolean[][] map_new) {
//		// TODO Auto-generated method stub
//		for (boolean[][] map : gh) {
//			for (int i = 0; i < this.getWidth(); i++) {
//				for (int j = 0; j < this.getHeight(); j++) {
//					if (map[i][j] != map_new[i][j]) {
//						break;
//					}
//					break;
//				}
//			}
//		}
//
//	}
private int getNeighbor(int x, int y) {
    int liveNeighbors = 0;
    int[] neighbors = {0, 1, -1};
    int rows = map.getHeight();
    int cols = map.getWidth();
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {

            if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                int r = (x + neighbors[i]);
                int c = (y + neighbors[j]);

                // Check the validity of the neighboring cell.
                // and whether it was originally a live cell.
                if ((r < rows && r >= 0) && (c < cols && c >= 0) && map.isalive(r, c) == 1) {
                    liveNeighbors += 1;
                }

            }
        }
    }
   // System.out.println("x:" + x + "y:" + y + "number neighbor:" + liveNeighbors );
    return liveNeighbors;
}




    public boolean isCollision(Point point) {
        boolean isCollision_flag = point.getX() < block_x && point.getX() >= 0 && point.getY() < block_y
                && point.getY() >= 0;
        return (!isCollision_flag);
    }

}
