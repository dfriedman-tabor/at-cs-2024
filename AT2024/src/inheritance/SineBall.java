package inheritance;
import java.awt.Color;

public class SineBall extends BouncingBall {
	
	private double count = 0;

	public SineBall(int startx, int starty, int startrad, int startxspeed, int startyspeed, Color startcolor) {
		super(startx, starty, startrad, startxspeed, startyspeed, startcolor);
	}
	
	public void move() {
		super.move();
		setY((int)(Math.sin(count)*50.0) + HEIGHT/2);
		count += 1.0/Math.PI;
	}

}
