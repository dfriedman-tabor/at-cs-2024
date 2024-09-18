package arrays2d;

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
	
	final int WIDTH = 800, HEIGHT = 600, CANVAS_HEIGHT = HEIGHT * 2 / 3;

	public GUIExample() {
		
		JFrame window = new JFrame("GUI Example");
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);
		
		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
		
		JPanel buttonContainer = new JPanel();
		buttonContainer.setPreferredSize(new Dimension(WIDTH, HEIGHT - CANVAS_HEIGHT));
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

		JPanel row1 = new JPanel();
		JPanel row2 = new JPanel();
		JPanel row3 = new JPanel();
		
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
		
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.fillRect(0, 0, GUIExample.this.WIDTH, CANVAS_HEIGHT);
			}
		};
		canvas.setPreferredSize(new Dimension(WIDTH, CANVAS_HEIGHT));

		
		
		mainContainer.add(buttonContainer);
		mainContainer.add(canvas);
		window.add(mainContainer);
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new GUIExample();
	}
	
	
	
	
}
