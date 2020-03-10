package ui;

import java.util.List;
import java.util.Random;

import life.base.Point;

//import edu.neu.coe.info6205.life.ui.Point;

public class Map {
	private int width;
	private int height;
	public boolean[][] map;

	public Map(int width, int height) {
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
		map = new boolean[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = false;
			}
		}
	}

	public boolean[][] getMap() {
		return map;
	}

	public boolean getValue(int x, int y) {
		// TODO Auto-generated method stub
		return map[x][y];
	}

	public void setValue(int x, int y, boolean nbr) {
		// TODO Auto-generated method stub
		map[x][y] = nbr;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public void Update(List<Point> points) {
		// TODO Auto-generated method stub
		if (points != null) {
			for (Point point : points) {
				map[point.getX()][point.getY()] = true;
			}
		}
	}

	public void display() {
		// TODO Auto-generated method stub
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				System.out.println("(" + i + "," + j + ")= " + map[i][j]);
			}
		}
	}

	public int isalive(int i, int j) {
		// TODO Auto-generated method stub
		return map[i][j] ? 1 : 0;
	}

	public void random() {
		// TODO Auto-generated method stub
		Random rd = new Random();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = rd.nextBoolean();
			}
		}
	}

	public int getlive() {
		// TODO Auto-generated method stub
		int counter = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map[i][j]) {
					counter++;
				}

			}
		}
		return counter;
	}
}
