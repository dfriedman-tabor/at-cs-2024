package inheritance;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class ColorBall extends BouncingBall {
	
	private int count = 0;

	public ColorBall(int startx, int starty, int startrad, int startxspeed, int startyspeed) {
		super(startx, starty, startrad, startxspeed, startyspeed, Color.BLACK);
	}

	public void move() {
		super.move();
		count ++;
	}
	
	public void draw(Graphics g) {
		Random rand = new Random();
		if (count == 25) {
			setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
			count = 0;
		}
		super.draw(g);
	}
	
}
