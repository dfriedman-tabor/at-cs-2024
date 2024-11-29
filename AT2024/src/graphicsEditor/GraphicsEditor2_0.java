package graphicsEditor;


// Graphics Editor Example by Mr. David
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
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GraphicsEditor2_0 {
	
	// constant width and height
	public int WIDTH = 900, HEIGHT = 700, BUTTONSHEIGHT = 100;
	
	// list of previous editor states for undo functionality
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
	private JTextArea lineWidth, fontSize, textContent;
	private HashMap<String, JButton> buttons = new HashMap<String, JButton>();
	
	// names of buttons
	private final String[] topRow = {"Rectangle", "Circle","Line"},
			middleRow = {"Choose Color", "Text"},
			bottomRow = {"Delete", "Move", "Front","Back","Pen","Save","Undo"};
	
	
	
	// sets up our graphics
	public GraphicsEditor2_0() {
		shapes.add(currList = new ArrayList<Shape>());
		
		// our main container
		JPanel outerPanel = new JPanel();
		
		// our graphics panel
		canvas = new Canvas() {
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.fillRect(0, 0, GraphicsEditor2_0.this.WIDTH, 
						GraphicsEditor2_0.this.HEIGHT-GraphicsEditor2_0.this.BUTTONSHEIGHT);
				
				// draws all the current shapes
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
		
		textContent = new JTextArea();
		textContent.setEditable(true);
		textContent.setPreferredSize(new Dimension(100,18));
		JTextArea contentPrompt = new JTextArea();
		contentPrompt.setPreferredSize(new Dimension(90,18));
		contentPrompt.setText("Text Content: ");
		
		// sets up buttons
		for (String str : topRow) buttons.put(str, new JButton(str));
		for (String str : middleRow) buttons.put(str, new JButton(str));
		for (String str : bottomRow) buttons.put(str, new JButton(str));
		buttons.get("Rectangle").setBackground(Color.blue);
		
		// adds listeners to our buttons
		for (String str : buttons.keySet()) {
			if (str.equals("Save") || str.equals("Choose Color") || str.equals("Undo"))
				continue;
			buttons.get(str).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mode = e.getActionCommand();
					for (JButton b : buttons.values())
						b.setForeground(Color.BLACK);
					((JButton)e.getSource()).setForeground(Color.blue);
				}
			});
		}
		buttons.get("Save").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveImage();
			}
		});
		buttons.get("Choose Color").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currColor = JColorChooser.showDialog(canvas,
		                  "Choose a color", Color.LIGHT_GRAY);
			}
		});
		buttons.get("Undo").addActionListener(new ActionListener() {
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

		for (String str : topRow)
			innerPanels[0].add(buttons.get(str));
		widthPrompt.setBackground(innerPanels[0].getBackground());
		innerPanels[0].add(widthPrompt);
		lineWidth.setText("3");
		innerPanels[0].add(lineWidth);
		
		for (String str : middleRow)
			innerPanels[1].add(buttons.get(str));
		sizePrompt.setBackground(innerPanels[1].getBackground());
		innerPanels[1].add(sizePrompt);
		fontSize.setText("24");
		innerPanels[1].add(fontSize);
		contentPrompt.setBackground(innerPanels[1].getBackground());
		innerPanels[1].add(contentPrompt);
		textContent.setText("");
		innerPanels[1].add(textContent);
		
		for (String str : bottomRow)
			innerPanels[2].add(buttons.get(str));
		
		for (int i = 0; i <= 2; i++)
			outerPanel.add(innerPanels[i]);
		outerPanel.add(canvas);
		
		// adds the mouse click/release listener
		canvas.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				if (mode.equals("Rectangle") || mode.equals("Circle") 
						|| mode.equals("Line") || mode.equals("Pen")) {
					actionOccurred();
					startX = e.getX();
					startY = e.getY();
					if (mode.equals("Rectangle"))
						currList.add(new Rect(startX,startY,0,0, currColor));
					else if (mode.equals("Circle"))
						currList.add(new Circle(startX,startY,0,currColor));
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
			}

			public void mouseClicked(MouseEvent e) {
				if (mode.equals("Delete") || mode.equals("Front") || mode.equals("Back")) {
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
				else if (mode.equals("Text") && textContent.getText().length()>0) {
					actionOccurred();
					currList.add(new Text(e.getX(),e.getY(), currColor,textContent.getText(),
							new Font("Verdana",Font.PLAIN,Integer.parseInt(fontSize.getText().trim()))));
				}
				
				canvas.repaint();
			}
			
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		
		// adds a listener for mouse motion
		canvas.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (mode.equals("Rectangle") || mode.equals("Circle") 
						|| mode.equals("Line") || mode.equals("Pen")) {
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
	
	// updates a new state in the undo functionality
	public void actionOccurred() {
		shapes.add(currList = new ArrayList<Shape>());
		for (Shape s : shapes.get(shapes.size()-2))
			currList.add(s.copy());
	}
	
	// saves the current panel state to an jpeg
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
		new GraphicsEditor2_0();
	}
}
