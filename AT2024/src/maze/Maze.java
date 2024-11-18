package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Maze {
	

	private int WWIDTH = 700, WHEIGHT = 700;
	public final int ROWS = 25, COLS = 25;
	public int BOXW = WWIDTH / ROWS, BOXH = WHEIGHT / COLS;

	// true = white, false = black
	private boolean[][] maze;
	
	private final int ANIMATESPEED = 30;
	
	private final double PCTFILLED = .8;
	
	private ArrayList<Point> mainPathPoints;
	
	private final double[] DIRECTIONWEIGHTS = {4, 3, 1}; // right, up/down, left
	
	private JFrame frame;
	
	public Maze(boolean animate) {
		if (animate) {
			frame = new JFrame();
			frame.setSize(WWIDTH, WHEIGHT);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JPanel canvas = new JPanel() {
				public void paintComponent(Graphics g) {
					g.setColor(Color.white);
					g.fillRect(0, 0, WWIDTH, WHEIGHT);
					g.setColor(Color.BLACK);
					int extraPixelsW = WWIDTH - BOXW * COLS;
					int extraPixelsH = WHEIGHT - BOXH * ROWS;
					for (int i = 0; i < maze.length; i++) {
						int shiftH = i < extraPixelsH ? 1 : 0;
						for (int j = 0; j < maze[i].length; j++) {
							int shiftW = j < extraPixelsW ? 1 : 0;
							if (!maze[i][j])
								g.fillRect(j * BOXW + Math.min(j, extraPixelsW), i * BOXH + Math.min(i, extraPixelsH),
										BOXW + shiftW, BOXH + shiftH);
							else
								g.drawRect(j * BOXW + Math.min(j, extraPixelsW), i * BOXH + Math.min(i, extraPixelsH),
										BOXW + shiftW, BOXH + shiftH);

						}
					}
				}
			};
			
			// resizes the maze as the user drags edges
			canvas.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent evt) {
					WWIDTH = canvas.getWidth();
					WHEIGHT = canvas.getHeight();
					BOXW = WWIDTH / ROWS;
					BOXH = WHEIGHT / COLS;
				}
			});

			frame.add(canvas);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			frame.getContentPane().repaint();
		}
		while (!fillMaze(animate));
	}
	
	
	public boolean fillMaze(boolean animate) {
		maze = new boolean[ROWS][COLS];
		mainPathPoints = new ArrayList<Point>();
		Point cp = new Point(0, (int)(Math.random() * ROWS));
		maze[cp.y][cp.x] = true;
		
		while (cp.x < COLS - 2) {
			if ((cp = randPoint(cp))== null) {
				return false;
			}
			maze[cp.y][cp.x] = true;
			if (animate) {
				try {
					Thread.sleep(ANIMATESPEED);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.getContentPane().repaint();
			}
			mainPathPoints.add(cp);
		}
		maze[cp.y][cp.x+1] = true;
		
		if (animate)
			frame.getContentPane().repaint();
		
		while (pctFilled() < PCTFILLED && buildBranch(animate));
		
		return true;
		
	}
	
	// returns a random neighbor that isn't already white and 
	// isn't adjacent to any white squares
	private Point randPoint(Point cp) {
		ArrayList<Point> nei = neighbors(cp, false);
		for (int i = 0; i < nei.size(); i++) {
			Point p = nei.get(i);
			if (p.x == COLS-2) return p;
			if (maze[p.y][p.x]) {
				nei.remove(i);
				i--;
			}
			
			else if (p.y < maze.length-1&& p.y+1 != cp.y && maze[p.y + 1][p.x]) {
				nei.remove(i); i--;}
			else if (p.x < maze[0].length-1 && p.x+1 != cp.x && maze[p.y][p.x+1]) {
				nei.remove(i);i--;}
			else if (p.y > 0 && p.y-1 != cp.y && maze[p.y - 1][p.x]) {
				nei.remove(i);i--;}
			else if (p.x > 0 && p.x-1 != cp.x && maze[p.y][p.x-1]) {
				nei.remove(i);i--;}
		}
		
		if (nei.size() == 0) return null;
		
		ArrayList<Double> prob = new ArrayList<Double>();
		double sum = 0;
		for (int i = 0; i < nei.size(); i++) {
			if (nei.get(i).x > cp.x)
				prob.add(DIRECTIONWEIGHTS[0]);
			else if (nei.get(i).x == cp.x)
				prob.add(DIRECTIONWEIGHTS[1]);
			else
				prob.add(DIRECTIONWEIGHTS[2]);
			
			sum += prob.get(i);
		}
		
		for (int i = 0; i < prob.size(); i++) {
			prob.set(i, prob.get(i)/sum);
		}
		for (int i = 1; i < prob.size(); i++) {
			prob.set(i, prob.get(i) + prob.get(i-1));
		}
		
		double randprob = Math.random();
		for (int i = 0; i < nei.size()-1; i++)
			if (prob.get(i) > randprob)
				return nei.get(i);
		
		return nei.get(nei.size()-1);
	}
	
	// returns the legal neighbors of point p
	private ArrayList<Point> neighbors(Point p, boolean filled) {
		ArrayList<Point> neighbors = new ArrayList<Point>();
		if (p.y > 1 && maze[p.y - 1][p.x] == filled)
			neighbors.add(new Point(p.x, p.y - 1));
		if (p.y < maze.length-2 && maze[p.y + 1][p.x] == filled)
			neighbors.add(new Point(p.x, p.y + 1));
		if (p.x > 1 && maze[p.y][p.x - 1] == filled)
			neighbors.add(new Point(p.x - 1, p.y));
		if (p.x < maze[0].length-2 && maze[p.y][p.x + 1]== filled)
			neighbors.add(new Point(p.x + 1, p.y));
		return neighbors;
	}
	// adds a side branch to the maze
	private boolean buildBranch(boolean animate) {
		Point lastP = null;
		Point lastDir = null;
		int branchLength = (int)(Math.random()*maze.length+maze.length/4);
		if (mainPathPoints.size() <= 0) 
			return false;
		Point cp = mainPathPoints.remove((int)(Math.random()*mainPathPoints.size()));
		for (int i = 0; i < branchLength && cp.y < maze.length-1; i++) {
			if (animate) {
				try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
				frame.getContentPane().repaint();
			}
			cp = randPoint(cp);
			if (cp == null) {
				if (lastDir!= null && lastP.x+lastDir.x < maze[0].length-1 && lastP.x+lastDir.x > 0
						&& lastP.y+lastDir.y < maze.length && lastP.y+lastDir.y > 0)
					maze[lastP.y+lastDir.y][lastP.x+lastDir.x] = true;
				return true;
			}
			maze[cp.y][cp.x] = true;
			if (lastP!= null) lastDir = new Point(cp.x-lastP.x,cp.y-lastP.y);
			lastP = cp;
		}
		return true;
	}
	
	public double pctFilled() {
		double numFilled = 0;
		for (boolean[] row : maze) 
			for (boolean b : row) 
				if (b) numFilled++;
					
		return numFilled/(maze.length*maze[0].length);
	}

public static void main(String[] args) {
	new Maze(true);
}
}
