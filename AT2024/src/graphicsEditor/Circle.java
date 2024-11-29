package graphicsEditor;


import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

	public Circle(int x, int y, int rad, Color c) {
		super(x, y, rad, rad, c);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x -width, y-width, width*2, width*2);
	}

	public boolean isOn(int x, int y) {
		int dx = this.x-x;
		int dy = this.y-y;
		int dist = (int)Math.sqrt(dx*dx+dy*dy);
		if (dist < width)
			return true;
		return false;
	}
	
	public void resize(int x1, int y1, int x2, int y2) {
		int w = x2-x1; int h = y2-y1;
		width = (int)Math.sqrt(w*w+h*h);
	}
	
	public Shape copy() {
		return new Circle(x, y, width, c);
	}
}
