package inheritance;
import java.awt.Color;

public class CirclePathBall extends Ball {

	double count = 0;
	
	public CirclePathBall(int startx, int starty, int startrad, int startxspeed, int startyspeed, Color startcolor) {
		super(startx, starty, startrad, startxspeed, startyspeed, startcolor);
	}
	
	public void move() {
		setX((int)(Math.sin(count)*200.0) + WIDTH/2);
		setY((int)(Math.cos(count)*200.0) + HEIGHT/2);
		count += 1.0/(Math.PI*10);
	}

}
