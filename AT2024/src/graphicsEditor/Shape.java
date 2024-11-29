package graphicsEditor;


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Shape {

	protected int x, y, width, height;
	protected Color c;
	protected String type;
	
	public Shape(int x,int y, int w, int h, Color c) {
		this.x = x; this.y = y;
		width = w; height = h;
		this.c = c;
	}
	
	public void move(int x1, int y1, int x2, int y2) {
		x = x2-x1; y = y2-y1;
	}
	
	public abstract Shape copy();
	public abstract void draw(Graphics g);
	public abstract boolean isOn(int x, int y);
	public abstract void resize(int x1, int y1, int x2, int y2);
	
}
