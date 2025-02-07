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

public class MazeGenerator {
	
	final int WIDTH = 600, HEIGHT = 600, ROWS = 30, COLS = 30, PIXELWIDTH = WIDTH/ROWS, PIXELHEIGHT = HEIGHT/COLS;
	
	boolean[][] pixels = new boolean[ROWS][COLS];

	public void genMaze() {
		
		// your code here 
		
	}
	
	
	// don't touch below
	
	
	public MazeGenerator() {
		
		genMaze();
		
		JFrame window = new JFrame();
		window.setSize(WIDTH, HEIGHT + 28);
		
		window.add(new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.black);
				g.fillRect(0, 0, MazeGenerator.this.WIDTH, MazeGenerator.this.HEIGHT);
				
				for (int i = 0; i < COLS; i++) 
					for (int j = 0; j < ROWS; j++) {
						g.setColor(Color.black);
						g.drawRect(j*PIXELWIDTH, i*PIXELHEIGHT, PIXELWIDTH, PIXELHEIGHT);
						if (pixels[i][j])  {
							g.setColor(Color.white);
							g.fillRect(j*PIXELWIDTH, i*PIXELHEIGHT, PIXELWIDTH, PIXELHEIGHT);
						}
					}
				
			}
		});
		
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		new MazeGenerator();
	}

}
