package maze;
import java.awt.Color;

public abstract class Bot {
	
	private MazeRunner maze;
	public Color color;
	
	public Bot(MazeRunner mr, Color c) {
		maze = mr;
		color = c;
	}
	
	public boolean moveForward() {
		return maze.move(this);
	}
	
	public void turnLeft() {
		maze.turnLeft(this);
	}
	
	// this is called each round. In it, you should call either turnLeft or moveForward.
	// you may only use one move/turn per round (you cannot turn left and move forward in one round)
	public abstract void move();
}
