package graphicsEditor;


import java.awt.Color;
import java.awt.Graphics;

public class Rect extends Shape{

	public Rect(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}
	
	public boolean isOn(int x, int y) {
		if (x <= this.x + width && x >= this.x)
			if (y <= this.y + height && y >= this.y)
				return true;
		return false;
	}
	
	public void resize(int x1, int y1, int x2, int y2) {
		if (x2 >= x1) {
			x = x1;
			width = x2-x1;
		}
		else {
			x = x2;
			width = x1 - x2;
		}
		if (y2 >= y1) {
			y = y1;
			height = y2-y1;
		}
		else {
			y = y2;
			height = y1 - y2;
		}
	}
	
	public Shape copy() {
		return new Rect(x, y, width, height, c);
	}
}
