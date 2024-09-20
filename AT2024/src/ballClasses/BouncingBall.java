package ballClasses;
import java.awt.Color;

public class BouncingBall extends Ball {

	public BouncingBall(Color c, int r, int sx, int sy, int x, int y) {
		
		super(c, r, sx, sy, x, y);
	}
	
	public void move() {
		super.move();
		
		if (x + rad >= BallGraphics.WIDTH || ) {
			speedx *= -1;
		}
	}
	
	
	
	
}
