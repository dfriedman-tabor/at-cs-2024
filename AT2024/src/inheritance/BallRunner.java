package inheritance;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallRunner extends JPanel implements Runnable {
	
	ArrayList<Ball> ball_list;
	
	public void run() {

		while (true) {
			
			for (Ball b : ball_list)
				b.move();
				
			repaint();
			
			try {
				Thread.sleep(25);
			} catch (Exception ex) {}

		}
	}
	
	public void setup() {
		ball_list = new ArrayList<Ball>();
		ball_list.add(new Ball(randPos(), randPos(), 30, randSpeed(), randSpeed(), Color.BLUE));
		ball_list.add(new BouncingBall(randPos(), randPos(), 30, randSpeed(), randSpeed(), Color.RED));
		ball_list.add(new SineBall(randPos(), randPos(), 30, randSpeed(), randSpeed(), Color.GREEN));
		ball_list.add(new CirclePathBall(randPos(), randPos(), 30, randSpeed(), randSpeed(), Color.BLACK));
		ball_list.add(new CollisionBall(randPos(), randPos(), 30, randSpeed(), randSpeed(), ball_list, Color.CYAN));
		ball_list.add(new ColorBall(randPos(), randPos(), 30, randSpeed(), randSpeed()));
		ball_list.add(new ShrinkingBall(randPos(), randPos(), 30, randSpeed(), randSpeed(), Color.MAGENTA));
	}
	
	public int randPos() {
		return (int)(Math.random()*500);
	}
	
	public int randSpeed() {
		return (int)(Math.random()*10) + 1;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 600, 600);
		
		try {
		
			for (Ball b: ball_list)
				b.draw(g);
		}
		catch (ConcurrentModificationException e) {
			
		}
	}
	
	public static void main(String[] args) {
		BallRunner game = new BallRunner(); 
		game.start_game();
	}
 
	public void start_game() {
		JFrame frame = new JFrame();
		frame.setSize(600, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		setup();
		
		Thread t = new Thread(this);
		t.start();
	}
}