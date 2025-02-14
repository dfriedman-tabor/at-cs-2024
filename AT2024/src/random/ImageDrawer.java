package random;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageDrawer {
	 
	final int WIDTH = 500, HEIGHT = 400;
	
	public ImageDrawer() {
		
		JFrame window = new JFrame();
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);

		
		// if your image is only in your project, nothing else
//		Image img = window.getToolkit().getImage("World Map.jpg");
		
		// if your image is in a package titled 'resources'
		Image img = window.getToolkit().getImage(getClass().getClassLoader().getResource("resources/World Map.jpg"));
		
		window.add(new JPanel() {
			
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, ImageDrawer.this.WIDTH, ImageDrawer.this.HEIGHT, this);
			}
		});
		window.setVisible(true);
		window.getContentPane().repaint();
	}

	public static void main(String[] args) {
		new ImageDrawer();
	}

}
