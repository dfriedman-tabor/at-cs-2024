package inheritance;
import java.awt.Color;
import java.util.ArrayList;

public class CollisionBall extends BouncingBall {

	private ArrayList<Ball> ball_list;
	
	public CollisionBall(int startx, int starty, int startrad, int startxspeed, int startyspeed, 
			ArrayList<Ball> startball_list, Color startcolor) {
		super(startx, starty, startrad, startxspeed, startyspeed, startcolor);
		ball_list = startball_list;
		setColor(Color.RED);
	}
	
	public void move() {
		super.move();
		for (Ball b: ball_list) {
			if (Math.abs(b.getX() - getX()) < b.getRad() &&
					Math.abs(b.getY() - getY()) < b.getRad()) {
				setXSpeed(getXSpeed()*-1);
				b.setXSpeed(b.getXSpeed()*-1);
				setYSpeed(getYSpeed()*-1);
				b.setYSpeed(b.getYSpeed()*-1);
			}
				
		}
	}
	

}
