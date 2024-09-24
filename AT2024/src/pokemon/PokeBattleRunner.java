package pokemon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PokeBattleRunner {

	public Image background, pokeballImg;
	public static int WIDTH = 950, HEIGHT = WIDTH*700/1280;
	public static int POKEWIDTH, POKEHEIGHT;
	public static int HEALTHWIDTH, HEALTHHEIGHT;
	public static int ATTACKWIDTH, ATTACKHEIGHT;
	public static final int POISONDAMAGE = 7, CONFUSEDAMAGE = 15;
	public static int[] POKEX, POKEY;
	public Pokemon[] pokes = new Pokemon[2];
	private int turn = (int)(Math.random()*2);
	public JPanel canvas;
	public JTextArea text;
	private int attackType = -1;
	private int attackX, attackY;
	
	public static final String[] types = {"normal","fire","water","grass"};
	public static final String[] stati = {"", "put to sleep","poisoned","confused","paralyzed"};
	
	
	public static final String[] statiLabels = {"","ASP", "PSN", "CNF","PAR"};
	public static final Color[] statiColor = {Color.white, Color.GRAY, new Color(200,0,250),Color.RED,Color.orange};
	public static final double[][] effectiveness = {{1,1,1,1},{1,.5,.5,2},{1,2,.5,.5},{1,.5,2,.5}};
	public static final Image[] attackImgs = new Image[types.length];
	
	public PokeBattleRunner() {
		for (int i = 0; i < types.length; i++) 
			attackImgs[i] = new ImageIcon(getClass().getClassLoader().getResource("resources/"+types[i]+".png")).getImage();			
		
		getClasses();
		initGraphics();
		
		battle();
	}
	
	public void battle() {
		System.out.println(text);

		System.out.println(pokes[0]);

		System.out.println(pokes[1]);

		text.setText(pokes[0].getName() +" vs "+ pokes[1].getName());
		rest(1);
		while (pokes[0].getHealth()>0 && pokes[1].getHealth()>0) {
			text.setText(pokes[turn].getName()+"'s turn!");
			canvas.repaint();
			rest(1);
			
			String message = pokes[turn].effects();
			text.setText(message);
			if (message.length() != 0) {
				canvas.repaint();
				rest(1);
			}
			
			Move m = pokes[turn].attack();
			if (m != null) {
				text.setText(pokes[turn].getName() + " used " + m.name+".\n");
				attackType = m.type;
				animateAttack();
				message = pokes[(turn+1)%2].suffer(m);
			
				text.setText(text.getText()+message);

				canvas.repaint();
				rest(1);
			}
			turn = (turn+1)%2;
		}
		int fainted = pokes[0].getHealth()==0 ? 0:1;
		text.setText(pokes[fainted].getName() +" fainted.");
		canvas.repaint();
	}
	
	public void drawBattle(Graphics g) { 
		for (int i=0; i<pokes.length; i++) pokes[i].draw(g,i==1);
		
		if (attackType!= -1) {
			g.drawImage(attackImgs[attackType],attackX, attackY, ATTACKWIDTH,ATTACKHEIGHT,null);
		}
	}
	
	public void animateAttack() {
		attackX = POKEX[turn]+POKEWIDTH/2;
		attackY = POKEY[turn]+POKEHEIGHT/4;
		while (turn==0 ? attackX < POKEX[1] : attackX > POKEX[0]+POKEWIDTH/2) {
			attackX += (POKEX[turn]-POKEX[(turn+1)%2]+(turn==0?-1:1)*POKEWIDTH/2)/-20;
			attackY += (POKEY[turn]-POKEY[(turn+1)%2])/-20;
			canvas.repaint();
			rest(.05);
		}
		attackType = -1;
	}
	
	public void rest(double time) {
		try {
			Thread.sleep((int)(time*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void getClasses() {
		try {
			String p1 = "pokemon."+JOptionPane.showInputDialog("Enter the first Pokemon Name");
			String p2 = "pokemon."+JOptionPane.showInputDialog("Enter the second Pokemon Name");
			//String p1 = "Shrek", p2="JohnCena";
			@SuppressWarnings("rawtypes")
			Class cls = Class.forName(p1);
			pokes[0] = (Pokemon) cls.getDeclaredConstructor().newInstance();
			cls = Class.forName(p2);
			pokes[1] = (Pokemon) cls.getDeclaredConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("Pokemon does not exist..");
			System.exit(1);
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	public void initGraphics() {
		text = new JTextArea();
		text.setEditable(false);
		text.setText("hello everyone");
		updateSizes();
		background = new ImageIcon(getClass().getClassLoader().getResource("resources/pokeBG.png")).getImage();
		pokeballImg = new ImageIcon(getClass().getClassLoader().getResource("resources/pokeball.png")).getImage();
		JFrame frame = new JFrame("Pokemon Battle");
		frame.setSize(WIDTH, HEIGHT);
		
		canvas = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(background, 0, 0, PokeBattleRunner.WIDTH, PokeBattleRunner.HEIGHT, null);
				super.paintComponents(g);
				drawBattle(g);
			}
		};
		canvas.setLayout(null);
		canvas.add(text);
		frame.add(canvas);
		canvas.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				WIDTH = canvas.getWidth();
				HEIGHT = canvas.getHeight();
				updateSizes();
				canvas.repaint();
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	public void updateSizes() {
		POKEWIDTH = WIDTH*4/15;
		POKEHEIGHT = HEIGHT*10/24;
		HEALTHWIDTH = WIDTH/5;
		HEALTHHEIGHT = HEIGHT/45;
		ATTACKWIDTH = POKEWIDTH/4;
		ATTACKHEIGHT = POKEHEIGHT/4;
		POKEX = new int[]{WIDTH/10,WIDTH*6/10}; POKEY = new int[]{HEIGHT*8/24,HEIGHT*3/40};
		text.setFont(new Font("Courier",Font.PLAIN, PokeBattleRunner.HEIGHT/20));
		text.setBounds(WIDTH/20,HEIGHT*78/100,WIDTH*17/20,HEIGHT/5);
	}
	
	public static void main(String[] args) {
		new PokeBattleRunner();
	}
}
