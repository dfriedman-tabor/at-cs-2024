package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIExample {
	
	// constants (to avoid magic numbers)
	final int WIDTH = 800, HEIGHT = 600, CANVAS_HEIGHT = HEIGHT * 2 / 3;

	public GUIExample() {
		
		JFrame window = new JFrame("GUI Example");
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);	// centers the window 
		
		// will hold everything - given a vertical format
		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		
		// holds all buttons 
		JPanel buttonContainer = new JPanel();
		buttonContainer.setPreferredSize(new Dimension(WIDTH, HEIGHT - CANVAS_HEIGHT));
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

		// a container for each row of buttons
		// no formatting bc horizontal alignment is default
		JPanel row1 = new JPanel();
		JPanel row2 = new JPanel();
		JPanel row3 = new JPanel();
		
		// create a button titled 'circle'
		// give it a listener that reacts when button is clicked
		JButton circleButton = new JButton("Circle");
		circleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("circle");
				
			}
		});
		
		
		row1.add(circleButton);
		
		
		buttonContainer.add(row1);
		buttonContainer.add(row2);
		buttonContainer.add(row3);
		
		
		// create our canvas, and define how it should appear 
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				
				// draws a white rectangle the size of the canvas
				g.setColor(Color.white);		
				g.fillRect(0, 0, GUIExample.this.WIDTH, CANVAS_HEIGHT);
			}
		};
		canvas.setPreferredSize(new Dimension(WIDTH, CANVAS_HEIGHT));

		
		
		mainContainer.add(buttonContainer);
		mainContainer.add(canvas);
		window.add(mainContainer);
		
		
		// terminate the program when the window is closed
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// make the window and all inner components visible
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new GUIExample();
	}
	
	
	
	
}
