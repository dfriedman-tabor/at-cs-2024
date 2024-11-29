package graphicsEditor;


// Graphics Editor Class by Mr. David
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;


public class GraphicsEditor {
	
	// constant width and height
	public int WIDTH = 900, HEIGHT = 700, BUTTONSHEIGHT = 100;
	
	private ArrayList<ArrayList<Shape>> shapes = new ArrayList<ArrayList<Shape>>();
	private ArrayList<Shape> currList;
	
	// keeps track of current info
	private String mode = "Rectangle";
	private int startX, startY;
	private Color currColor = Color.blue;
	private int fileNum = 0;
	private int currI;
	
	// components to keep track of
	private Canvas canvas;
	private JTextArea lineWidth, fontSize, fontStyle;
	private JButton[] buttons = new JButton[13];
	
	// sets up our graphics
	public GraphicsEditor() {
		shapes.add(currList = new ArrayList<Shape>());
		
		// our main container
		JPanel outerPanel = new JPanel();
		
		// our graphics panel
		canvas = new Canvas() {
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.fillRect(0, 0, GraphicsEditor.this.WIDTH, 
						GraphicsEditor.this.HEIGHT-GraphicsEditor.this.BUTTONSHEIGHT);
				
				for (Shape s : currList) 	
					s.draw(g);
			}
		};
		
		// stack our components top to bottom
		BoxLayout boxlayout = new BoxLayout(outerPanel, BoxLayout.Y_AXIS);
		outerPanel.setLayout(boxlayout);
		
		// gives our panel a nice looking border
		outerPanel.setBorder(BorderFactory.createTitledBorder("Graphics Editor"));
		
		// initializes the text containers
		lineWidth = new JTextArea();
		lineWidth.setEditable(true);
		lineWidth.setPreferredSize(new Dimension(22,18));
		JTextArea widthPrompt = new JTextArea();
		widthPrompt.setPreferredSize(new Dimension(70,18));
		widthPrompt.setText("Line Width: ");
		
		fontSize = new JTextArea();
		fontSize.setEditable(true);
		fontSize.setPreferredSize(new Dimension(30,18));
		JTextArea sizePrompt = new JTextArea();
		sizePrompt.setPreferredSize(new Dimension(63,18));
		sizePrompt.setText("Text Size: ");
		
		// sets up buttons
		buttons[0] = new JButton("Rectangle");
		buttons[0].setForeground(Color.BLUE);
		buttons[1] = new JButton("Line");
		buttons[2] = new JButton("Circle");
		buttons[3] = new JButton("Triangle");
		buttons[4] = new JButton("Text");
		buttons[5] = new JButton("Delete");
		buttons[6] = new JButton("Move");
		buttons[7] = new JButton("Front");
		buttons[8] = new JButton("Back");
		buttons[9] = new JButton("Pen");
		buttons[10] = new JButton("Save");
		buttons[11] = new JButton("Choose Color");
		buttons[12] = new JButton("Undo");
		
		// adds listeners to our buttons
		for (int i = 0; i <= 9; i++) {
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mode = e.getActionCommand();
					for (JButton b : buttons)
						b.setForeground(Color.BLACK);
					((JButton)e.getSource()).setForeground(Color.blue);
				}
			});
		}
		buttons[10].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveImage();
			}
		});
		buttons[11].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currColor = JColorChooser.showDialog(canvas,
		                  "Choose a color", Color.LIGHT_GRAY);
			}
		});
		buttons[12].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapes.remove(shapes.size()-1);
				if (shapes.isEmpty()) 
					shapes.add(currList = new ArrayList<Shape>());
				else 
					currList = shapes.get(shapes.size()-1);
				canvas.repaint();
			}
		});
		
		
		// create inner containers for the  buttons and add it to our main panel
		JPanel[] innerPanels = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			innerPanels[i] = new JPanel();
			innerPanels[i].setPreferredSize(new Dimension(WIDTH,30));
		}
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT-BUTTONSHEIGHT));

		for (int i = 0; i <=3; i++)
			innerPanels[0].add(buttons[i]);
		
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // min, max, initial value
        slider.setMajorTickSpacing(10); // Major tick spacing
        slider.setMinorTickSpacing(5); // Minor tick spacing
        slider.setPaintTicks(true); // Show ticks
        
		
		widthPrompt.setBackground(innerPanels[0].getBackground());
		innerPanels[0].add(widthPrompt);
		lineWidth.setText("3");
		innerPanels[0].add(lineWidth);
		innerPanels[1].add(buttons[11]);
        innerPanels[1].add(slider);

		innerPanels[1].add(buttons[4]);
		sizePrompt.setBackground(innerPanels[1].getBackground());
		innerPanels[1].add(sizePrompt);
		fontSize.setText("24");
		innerPanels[1].add(fontSize);
		for (int i = 5; i <= 10; i++)
			innerPanels[2].add(buttons[i]);
		innerPanels[2].add(buttons[12]);
		
		for (int i = 0; i <= 2; i++)
			outerPanel.add(innerPanels[i]);
		outerPanel.add(canvas);
		
		canvas.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				if (mode.equals("Rectangle") || mode.equals("Circle") 
						|| mode.equals("Line") || mode.equals("Pen") ||
						mode.equals("Triangle")) {
					actionOccurred();
					startX = e.getX();
					startY = e.getY();
					int opacity = slider.getValue()*255/100;
					currColor = new Color(currColor.getRed(), currColor.getGreen(), currColor.getBlue(), opacity);
					
					if (mode.equals("Rectangle"))
						currList.add(new Rect(startX,startY,0,0, currColor));
					else if (mode.equals("Circle"))
						currList.add(new Circle(startX,startY,0,currColor));
					else if (mode.equals("Triangle")) {
						currList.add(new Triangle(startX, startY, startX, startY,startX,startY, currColor));
					}
					else if (mode.equals("Pen"))
						currList.add(new Sketch(startX,startY,Integer.parseInt(lineWidth.getText().trim()),currColor));
					else 
						currList.add(new Line(startX,startY,0,0,Integer.parseInt(lineWidth.getText().trim())
								,currColor));
				}
				else if (mode.equals("Move")) {
					actionOccurred();
					currI = -1;
					for (int i = 0; i < currList.size(); i++) 
						if (currList.get(i).isOn(e.getX(),e.getY())) {
							startX = e.getX()-currList.get(i).x;
							startY = e.getY()-currList.get(i).y;
							currI = i;
						}
				}
				
				else if (mode.equals("Delete") || mode.equals("Front") || mode.equals("Back")) {
					for (int i = currList.size()-1; i >=0; i--) {
						if (currList.get(i).isOn(e.getX(), e.getY())) {
							actionOccurred();
							if (mode.equals("Delete")) 
								currList.remove(i);
							else if (mode.equals("Front")) 
								currList.add(currList.remove(i));
							else 
								currList.add(0,currList.remove(i));
							break;
						}
					}
				}
				else if (mode.equals("Text")) {
					actionOccurred();
					int opacity = slider.getValue()*255/100;
					currColor = new Color(currColor.getRed(), currColor.getGreen(), currColor.getBlue(), opacity);

					String input = JOptionPane.showInputDialog("Text:");
					currList.add(new Text(e.getX(),e.getY(), currColor,input,
							new Font("Verdana",Font.PLAIN,Integer.parseInt(fontSize.getText().trim()))));
				}
				
				canvas.repaint();
			}

			public void mouseClicked(MouseEvent e) {}
			
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		
		canvas.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (mode.equals("Rectangle") || mode.equals("Circle") 
						|| mode.equals("Line") || mode.equals("Pen")
						|| mode.equals("Triangle")) {
					currList.get(currList.size()-1).resize(startX, startY, e.getX(),e.getY());
				}
				else if (mode.equals("Move") && currI != -1) {
					currList.get(currI).move(startX, startY, e.getX(), e.getY());
				}
		
				canvas.repaint();
			}
			public void mouseMoved(MouseEvent e) {}
		});
		
		// the main container, with the usual setup
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(outerPanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		//canvas.setFocusable(true);
		
		// resizes the maze as the user drags edges
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				WIDTH = frame.getWidth();
				HEIGHT = frame.getHeight();
				
				canvas.setSize(WIDTH, HEIGHT - BUTTONSHEIGHT);
			}
		});
	}
	
	public void actionOccurred() {
		shapes.add(currList = new ArrayList<Shape>());
		for (Shape s : shapes.get(shapes.size()-2))
			currList.add(s.copy());
	}
	
	public void saveImage() {
		BufferedImage imagebuf=null;;
	    try {
	        imagebuf = new Robot().createScreenCapture(canvas.getBounds());
	    } catch (AWTException e1) {
    			System.out.println("Error");}  
	     Graphics2D graphics2D = imagebuf.createGraphics();
	     canvas.paint(graphics2D);
	     try {
	        ImageIO.write(imagebuf,"jpeg", new File("save"+fileNum+".jpeg"));
	        fileNum++;
	    } catch (Exception e) {
	    		System.out.println("Error");
	    }
	}
	
	// the usual basic main method to get our window running
	public static void main(String[] args) {
		new GraphicsEditor();
	}
}
