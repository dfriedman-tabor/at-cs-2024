package maze;

import java.awt.Color;

public class RandomBot extends Bot {

	public RandomBot(MazeRunner mr, Color c) {
		super(mr, c);
	}

	@Override
	public void move() {
		
		if (Math.random()>.8)
			turnLeft();
		else
			moveForward();
		
	}
	
	

}
