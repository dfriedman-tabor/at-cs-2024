package inheritance;
import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	public final int HEIGHT = 600, WIDTH = 600;

	private int x, y, rad, xspeed, yspeed;
	private Color c;
	
	public Ball(int startx, int starty, int startrad, int startxspeed, int startyspeed, Color startcolor) {
		x = startx;
		y = starty;
		rad = startrad;
		xspeed = startxspeed;
		yspeed = startyspeed;
		c = startcolor;
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x, y, rad, rad);
	}
	
	public void move() {
		x += xspeed;
		y += yspeed;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newx) {
		x = newx;
	}
	
	public void setY(int newy) {
		y = newy;
	}
	
	public int getRad() {
		return rad;
	}
	
	public void setRad(int newrad) {
		rad = newrad;
	}
	
	public int getXSpeed() {
		return xspeed;
	}
	
	public int getYSpeed() {
		return yspeed;
	}
	
	public void setXSpeed(int newspeed) {
		xspeed = newspeed;
	}
	
	public void setYSpeed(int newspeed) {
		yspeed = newspeed;
	}
	
	public Color getColor() {
		return c;
	}
	
	public void setColor(Color newcolor) {
		c = newcolor;
	}
}
