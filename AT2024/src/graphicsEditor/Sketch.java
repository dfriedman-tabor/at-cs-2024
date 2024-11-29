package graphicsEditor;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Sketch extends Shape {
	
	public ArrayList<Point> points = new ArrayList<Point>();
	public boolean fill = false;
	private int lineWidth;

	public Sketch(int x, int y, int lw, Color c) {
		super(0,0,0,0, c);
		points.add(new Point(x,y));
		lineWidth = lw;
	}
	
	public Sketch(ArrayList<Point> pts, int lw, Color c) {
		super(0,0,0,0, c);
		for (Point p : pts)
			points.add(p);
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
	
	public Shape copy() {
		ArrayList<Point> copiedPoints = new ArrayList<Point>();
		for (Point p : points) 
			copiedPoints.add(new Point(p.x, p.y));
		return new Sketch(copiedPoints, lineWidth, c);
	}
}
