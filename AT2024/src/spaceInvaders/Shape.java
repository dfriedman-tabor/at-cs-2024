package spaceInvaders;

import java.awt.Graphics;

public abstract class Shape {
	
	// all the other stuff
	
	public abstract void draw(Graphics g);
	
	
	public abstract boolean isOn(int mouseX, int mouseY);
	
}
