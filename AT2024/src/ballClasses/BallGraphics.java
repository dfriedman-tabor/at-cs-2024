package ballClasses;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallGraphics {
	
	final static int WIDTH = 800, HEIGHT = 800;
	
	ArrayList<Ball> ball_list = new ArrayList<Ball>();
	
	
	public void setup() {
		
		ball_list.add(new Ball(Color.blue, 30, 10, 5, 200, 200));
//		ball_list.add(new BouncingBall(Color.green, 30, 10, 5, 200, 200));

		
	}
	
	public void runMovement(JPanel canvas) {
		
		while (true) {
			
			
			for (Ball b : ball_list) {
				b.move();
			}
			
			canvas.repaint();
			
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public BallGraphics() {
		setup();
		
		JFrame window = new JFrame("Ball types");
		window.setSize(WIDTH,HEIGHT);
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.fillRect(0, 0, BallGraphics.this.WIDTH, BallGraphics.this.HEIGHT);
				
				for (Ball b : ball_list) {
					b.draw(g);
				}
			}
		};
		
		
		window.add(canvas);
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		
		runMovement(canvas);
	}
	
	public static void main(String[] args) {
		new BallGraphics();
	}

}
