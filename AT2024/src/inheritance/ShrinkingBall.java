package inheritance;
import java.awt.Color;

public class ShrinkingBall extends BouncingBall{

	private int count = 0, startRad;
	private boolean shrink = true;
	
	public ShrinkingBall(int startx, int starty, int startrad, int startxspeed, int startyspeed, Color startcolor) {
		super(startx, starty, startrad, startxspeed, startyspeed, startcolor);
		this.startRad = startrad;
	}
	
	public void move() {
		if (count == 10) {

			if (shrink) {
				setRad(getRad()-1);
				if (getRad() <= 5) shrink = false;
			}
			else {
				setRad(getRad()+1);
				if (getRad() >= startRad) shrink = true;
			}
			count = 0;
		}
		count ++;
		super.move();
	}

}
