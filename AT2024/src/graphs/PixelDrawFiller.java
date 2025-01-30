package graphs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelDraw {
	
	// used to practice implementing BFT on a non-graph object. 
	// use the Point class to keep track of coordinates, remember that x -> j and y -> i when converting from coordinates to indices
	// click and drag to draw, right click (2-press on mac) to fill
	

	
	public void autofill(int x, int y) {
		
		// your code here
	}
	

	// DON'T TOUCH THE BELOW CODE // 
	
	
	final int WIDTH = 600, HEIGHT = 600, ROWS = 60, COLS = 60, PIXELWIDTH = WIDTH/ROWS, PIXELHEIGHT = HEIGHT/COLS;
	
	boolean[][] pixels = new boolean[ROWS][COLS];

	
	public void draw(int x, int y) {
		if (x >=0 && x < WIDTH && y >= 0 && y < HEIGHT)
			pixels[y/PIXELHEIGHT][x/PIXELWIDTH] = true;
	}
	
	public PixelDraw() {
		
		JFrame window = new JFrame();
		window.setSize(WIDTH, HEIGHT + 28);
		
		window.add(new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.fillRect(0, 0, PixelDraw.this.WIDTH, PixelDraw.this.HEIGHT);
				
				g.setColor(Color.black);
				for (int i = 0; i < COLS; i++) 
					for (int j = 0; j < ROWS; j++) {
						g.drawRect(j*PIXELWIDTH, i*PIXELHEIGHT, PIXELWIDTH, PIXELHEIGHT);
						if (pixels[i][j]) 
							g.fillRect(j*PIXELWIDTH, i*PIXELHEIGHT, PIXELWIDTH, PIXELHEIGHT);
					}
				
			}
		});
		
		window.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				draw(e.getX(), e.getY()-28);
				window.getContentPane().repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {}
			
		});
		
		window.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					System.out.println("Filling started");
					autofill(e.getX(), e.getY()-28);
					System.out.println("Filling completed\n");
					window.getContentPane().repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
			
		});
		
		
		
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		new PixelDraw();
	}

}
