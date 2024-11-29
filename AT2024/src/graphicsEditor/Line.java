package graphicsEditor;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape {

	private int thickness;
	
	public Line(int x1, int y1, int w, int h, int lineWidth, Color c) {
		super(x1, y1, w, h, c);
		thickness = lineWidth;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(c);
		g2.setStroke(new BasicStroke(thickness));
		g2.drawLine(x, y, x+width, y+height);
	}
	
	public boolean isOn(int x, int y) {
		return false; // line checking is funky
	}
	
	public void resize(int x1, int y1, int x2, int y2) {
		width = x2 - x1;
		height = y2 - y1;
	}
	
	public Shape copy() {
		return new Line(x, y, width, height, thickness, c);
	}
}
