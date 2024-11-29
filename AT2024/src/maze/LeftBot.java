package maze;

import java.awt.Color;

public class LeftBot extends Bot {
	
	String[] modes = {"forward","turnLeft","turnRight"};
	int mode = 0;
	int turnCount = 0;

	public LeftBot(MazeRunner mr, Color c) {
		super(mr, c);
	}
	
	public void move() {
		if (mode == 0) {
			if (moveForward())
				mode = 1;
			else mode = 2;
		}
		else if (mode == 1) {
			turnLeft(); mode = 0;
		}
		else  { 
			turnLeft();
			if (turnCount < 2) turnCount ++;
			else {
				turnCount = 0; mode = 0;
			}
		}
		
	}
}
