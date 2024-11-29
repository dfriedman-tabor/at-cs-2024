package maze;

import java.awt.Color;

public class LoserBot extends Bot {

    public LoserBot(MazeRunner mr, Color c) {
        super(mr, c);
    }

    @Override
    public void move() {
    	if (Math.random() > .6) {
    		moveForward();
    	}
    	else {
    		turnLeft();
    	}
    }
}
