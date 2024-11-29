package maze;

// class that generates a maze, then uses student-built bots
// to solve the maze blindly

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MazeRunner {
	
	private int speed = 500; // default is 100- smaller = slower
	
	// constructs and adds the bots competing into the maze
	private void addBots() {
		Bot[] bots = {
				new SuperBot(this, Color.red), new LeftBot(this,Color.blue)};
		for (Bot b : bots)
			robots.put(b, new RobotInfo());
	}
	
	
	// feel free to peruse the below code, but you don't need to touch it.
	
	private int WWIDTH = 700, WHEIGHT = 700;
	public final int ROWS = 25, COLS = 25;
	private int BOXW = WWIDTH / ROWS, BOXH = WHEIGHT / COLS;
	private final int diff = 3; // scale starting from 1
	private int count = 0;
	private boolean paused = false;

	// true = white, false = black
	private boolean[][] maze = new boolean[ROWS][COLS];
	
	private ConcurrentHashMap<Bot, RobotInfo> robots = new ConcurrentHashMap<Bot, RobotInfo>();

	private Point begin = new Point(0, (int)(Math.random() * (COLS-2) + 1));
	private final Point goal = new Point(ROWS-1, (int)(Math.random() * (COLS-2) + 1));

	private Point p1, p2;
	
	private double pctFilled = 0;
	private Bot lastbot = null;
	
	private JFrame frame;
	
	private boolean animate = false;
		
	// class to keep track of robots' locations
	private class RobotInfo {
		private Point loc = new Point(begin.x,begin.y), dir = new Point(1,0);
		public String toString() { return loc.x + ", " + loc.y;}
	}
	
	public boolean move(Bot b) {
		
		// not allowed to move twice in one turn
		if (b.equals(lastbot)) {
			System.out.println(b.getClass() + " tried to move twice in one turn. Disqualified");
			robots.put(b, null);
			return false;
		}
		lastbot = b;
		
		// tries to move the bot in its current direction
		RobotInfo r = robots.get(b);
		if (r.loc.x+r.dir.x < 0) return false;
		if (maze[r.loc.y+r.dir.y][r.loc.x+r.dir.x]) {
			r.loc.x += r.dir.x; r.loc.y+=r.dir.y;
			return true;
		}
		
		// if it runs into a wall
		else return false;
	}
	
	public void turnLeft(Bot b) {
		
		// not allowed to move twice in one turn
		if (b.equals(lastbot) && robots.size()>1) {
			System.out.println(b.getClass() + " tried to move twice in one turn. Disqualified");
			robots.put(b, null);
			return;
		}
		lastbot = b;
		// turns the bot left
		RobotInfo r = robots.get(b);
		if (r.dir.x == 1) r.dir = new Point(0,-1);
		else if (r.dir.y == -1) r.dir = new Point(-1,0);
		else if (r.dir.x == -1) r.dir = new Point(0,1);
		else r.dir = new Point(1,0);
	}
	
	private void fillMaze() {
		
		// recreates the maze until it is solveable
		do {
			maze = new boolean[ROWS][COLS];
			maze[begin.y][begin.x] = true;
			maze[goal.y][goal.x] = true;
			p1 = new Point(begin.x+1, begin.y);
			p2 = new Point(goal.x-1, goal.y);
			dfs(true);
		} while(!checkMaze());
		
		// adds side branches until 40% of maze is white
		ArrayList<Point> whites = whites();
		while (pctFilled < .4) {
			int r = (int)(Math.random()*whites.size());
			Point aim = new Point((int)(Math.random()*maze.length), (int)(Math.random()*maze[0].length));
			int length = (int)(Math.random()*maze.length+maze.length/4);
			buildBranch(whites.remove(r), aim, length);
			whites = whites();
		}
		
		for (int i = 0; i < maze.length; i++) {
			maze[i][0] = false;
			maze[i][maze[0].length-1] = false;
		}
		for (int i = 0; i < maze[0].length; i++) {
			maze[0][i] = false;
			maze[maze.length-1][i] = false;
		}
		maze[begin.y][begin.x] = true;
		maze[goal.y][goal.x] = true;
	}
	
	private ArrayList<Point> whites() {
		double numFilled = 0;
		ArrayList<Point> whites = new ArrayList<Point>();
		for (int i = 0; i < maze.length; i++)
			for (int j = 1; j < maze[i].length-1; j++)
				if (maze[i][j]) {
					whites.add(new Point(j,i));
					numFilled++;
				}
		pctFilled = numFilled/(maze.length*maze[0].length);
		return whites;
	}
	
	// adds a side branch to the maze
	private void buildBranch(Point start, Point aim, int length) {
		Point np = start;
		Point lastP = null;
		Point lastDir = null;
		for (int i = 0; i < length && np.y < maze.length-1; i++) {
			if (animate) {
				try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
				frame.getContentPane().repaint();
			}
			np = randPoint(np, aim);
			if (np == null) {
				if (lastDir!= null && lastP.x+lastDir.x < maze[0].length-1 && lastP.x+lastDir.x > 0
						&& lastP.y+lastDir.y < maze.length && lastP.y+lastDir.y > 0)
					maze[lastP.y+lastDir.y][lastP.x+lastDir.x] = true;
				return;
			}
			maze[np.y][np.x] = true;
			if (lastP!= null) lastDir = new Point(np.x-lastP.x,np.y-lastP.y);
			lastP = np;
		}
	}
	
	// uses dfs to build a path from the start point to the end point
	private boolean dfs(boolean forward) {
		Point curr = forward ? p1 : p2; 
		maze[curr.y][curr.x] = true;

		if (p1.equals(p2)) return true;
		if (animate) {
			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}

			frame.getContentPane().repaint();
		}
		
		Point np = randPoint(curr, forward ? p2 : p1);
		
		if (np == null)
			return false;
		
		if (forward) p1 = np;
		else p2 = np;
			
		return dfs(!forward);
	}
	
	// returns a random neighbor that isn't already white and 
	// isn't adjacent to any white squares
	private Point randPoint(Point cp, Point aim) {
		ArrayList<Point> nei = neighbors(cp, false);
		for (int i = 0; i < nei.size(); i++) {
			Point p = nei.get(i);
			if (p.equals(aim)) return p;
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
			if (Math.abs(cp.x-aim.x) > Math.abs(nei.get(i).x - aim.x))
				prob.add(diff+1.0);
			else if (Math.abs(cp.x-aim.x) < Math.abs(nei.get(i).x - aim.x))
				prob.add((double)diff);
			else if (Math.abs(cp.y-aim.y) > Math.abs(nei.get(i).y - aim.y))
				prob.add(diff+1.0);
			else if (Math.abs(cp.y-aim.y) < Math.abs(nei.get(i).y - aim.y))
				prob.add((double)diff);
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
	
	// uses bfs to check if the maze is solveable
	private boolean checkMaze() {
		HashSet<Point> visited = new HashSet<Point>();
		ArrayList<Point> toVisit = new ArrayList<Point>();

		toVisit.add(begin);
		visited.add(toVisit.get(0));

		while (!toVisit.isEmpty()) {
			Point curr = toVisit.remove(0);
			for (Point neighbor : neighbors(curr, true)) {
				if (neighbor.equals(new Point(goal.x-1,goal.y)))
					return true;
				if (!visited.contains(neighbor)) {
					toVisit.add(neighbor);
					visited.add(neighbor);
				}
			}

		}
		return false;
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

	public MazeRunner() {

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
				g.setColor(Color.GREEN);
				g.fillRect(goal.x * BOXW + Math.min(extraPixelsW, goal.x), goal.y * BOXH + Math.min(extraPixelsH, goal.y),
						BOXW, BOXH);
				for (Bot b : robots.keySet()) {
					if (robots.get(b) == null) continue;
					g.setColor(b.color);
					g.fillOval(robots.get(b).loc.x * BOXW + Math.min(extraPixelsW, robots.get(b).loc.x),
							robots.get(b).loc.y * BOXH + Math.min(extraPixelsH, robots.get(b).loc.y), BOXW, BOXH);
				}
				
//				g.setColor(Color.blue);
//				g.fillRect(p1.x * BOXW + Math.min(extraPixelsW, p1.x), p1.y * BOXH + Math.min(extraPixelsH, p1.y),
//						BOXW, BOXH);
//				g.setColor(Color.blue);
//				g.fillRect(p2.x * BOXW + Math.min(extraPixelsW, p2.x), p2.y * BOXH + Math.min(extraPixelsH, p2.y),
//						BOXW, BOXH);
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
		canvas.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false), "Pause");
		canvas.getActionMap().put("Pause", new PauseAction());
		
		frame.getContentPane().repaint();
		fillMaze();
		addBots();
				
		// runs the maze until a robot has reached the end
		while (true) {
			if (!paused) {
				lastbot = null;
				for(Bot b : robots.keySet()) {
					if (robots.get(b) == null) continue;
					b.move();
				}
				count++;
				checkForWin();
			}
			try {
				Thread.sleep(2000/speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			frame.getContentPane().repaint();
		}
	}

	private class PauseAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			paused = !paused;
		}
	}
	
	private Bot checkForWin() {
		for (Bot b : robots.keySet())  {
			if (robots.get(b) == null) continue;
			if (robots.get(b).loc.equals(goal)) {
				System.out.println(b.getClass() + ": "+count);
				robots.remove(b);		}
		}
		return null;
	}

	public static void main(String[] args) {
		new MazeRunner();
	}
}
