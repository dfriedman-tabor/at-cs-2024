package ballClasses;
import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	Color color;
	int rad;
	int speedx, speedy;
	int x, y;
	
	public Ball(Color c, int r, int sx, int sy, int x, int y) {
		color = c;
		rad = r; 
		speedx = sx;
		speedy = sy;
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		x += speedx;
		y += speedy;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, rad, rad);
	}
	
}
