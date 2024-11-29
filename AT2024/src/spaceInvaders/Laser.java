package spaceInvaders;
// Laser class for Space Invaders by Mr. David
// no need to edit this class

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Laser extends Rectangle {
	
	// constructor takes a location as parameter
	public Laser(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void moveY(int dY) {
		y += dY;
	}
	
	// draws the laser
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}