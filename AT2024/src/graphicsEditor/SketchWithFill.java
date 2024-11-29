package graphicsEditor;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class SketchWithFill extends Shape {
	
	public ArrayList<Point> points = new ArrayList<Point>(), 
			dots = new ArrayList<Point>();
	public boolean fill = false;
	private int lineWidth;

	public Sketch(int x, int y, int lw, Color c) {
		super(0,0,0,0, c);
		points.add(new Point(x,y));
		lineWidth = lw;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(c);
		g2.setStroke(new BasicStroke(lineWidth));
		g2.setColor(c);
		for (int i = 0; i < points.size()-1; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			g2.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		if (fill) {
			System.out.println("here");
			int[] x = new int[dots.size()], y = new int[dots.size()];
			for (int i = 0; i < x.length; i++) {
				g2.fillPolygon(x,y,x.length);
			}
		}
	}

	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resize(int x1, int y1, int x2, int y2) {
		if (points.get(points.size()-1).x != x2 ||
				points.get(points.size()-1).y != y2)
			points.add(new Point(x2,y2));
	}
	
	public void fill(int x1, int y1) {
		
		ArrayList<Point> allPoints = new ArrayList<Point>();
		ArrayList<Point> outline = new ArrayList<Point>();
		for (int i = 0; i < points.size()-1; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			
			double slope = (double)(p2.y-p1.y)/(p2.x-p1.x);
			double y0 = p2.y-slope*p2.x;
			
			if (p1.x == p2.x) {
				if (p1.y <= p2.y)
					for (int yI = p1.y; yI <= p2.y; yI++) 
						allPoints.add(new Point(p1.x, yI));
				else
					for (int yI = p2.y; yI <= p1.y; yI++) 
						allPoints.add(new Point(p1.x, yI));
			}
			
			else if (slope <= 1 && slope >= -1) {
				if (p1.x <= p2.x) 
					for (int xI = p1.x; xI < p2.x; xI++) 
						allPoints.add(new Point(xI, (int)(slope*xI+y0)));
				else 
					for (int xI = p2.x; xI < p1.x; xI++) 
						allPoints.add(new Point(xI, (int)(slope*xI+y0)));
			}
			else {
				if (p1.y <= p2.y) 
					for (int yI = p1.y; yI < p2.y; yI++) 
						allPoints.add(new Point((int)((yI-y0)/slope), yI));
				else 
					for (int yI = p2.y; yI < p1.y; yI++) 
						allPoints.add(new Point((int)((yI-y0)/slope), yI));
			}
		}
		
		ArrayList<Point> toVisit = new ArrayList<Point>(), 
				visited = new ArrayList<Point>();
		Point curr = new Point(x1,y1);
		toVisit.add(curr);
		fill = true;
		while (!toVisit.isEmpty()) {
			curr = toVisit.remove(0);
			//System.out.println(curr);
			visited.add(curr);
			ArrayList<Point> newPoints = new ArrayList<Point>();
			if (curr.x > 0) 
				newPoints.add(new Point(curr.x-1,curr.y));
			else fill = false;
			if (curr.x < GraphicsEditor.WIDTH)
				newPoints.add(new Point(curr.x+1,curr.y));
			else fill = false;
			if (curr.y > 0)
				newPoints.add(new Point(curr.x, curr.y-1));
			else fill = false;
			if (curr.y < GraphicsEditor.HEIGHT-GraphicsEditor.BUTTONSHEIGHT)
				newPoints.add(new Point(curr.x, curr.y+1));
			else fill = false;
			
			if (!fill) break;
			
			for (Point p : newPoints)
				if (!toVisit.contains(p) && !visited.contains(p) && !allPoints.contains(p))
					toVisit.add(p);
				else if (allPoints.contains(p)) 
					outline.add(p);
					
		}
		
		if (fill) dots = outline;
	}
}
