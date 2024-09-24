package inheritance;

import java.awt.Color;

public class BouncingBall extends Ball {

	public BouncingBall(int startx, int starty, int startrad, int startxspeed, int startyspeed, Color startcolor) {
		super(startx, starty, startrad, startxspeed, startyspeed, startcolor);
	}
	
	public void move() {
		if (getX() >= 600 - getRad() || getX() <= 0) {
			setXSpeed(-getXSpeed());
		}
		if (getY() >= 600 - getRad() || getY() <= 0) {
			setYSpeed(-getYSpeed());
		}
		super.move();
	}
	

}
