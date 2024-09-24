package pokemon;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

import inheritance.BankAccount;

public class TeamBattleRunner extends PokeBattleRunner {

	private PokeTeam[] teams;

	public TeamBattleRunner() {
		super();
	}
	
	public void battle() {
	
		System.out.println(text);

		for (PokeTeam t : teams) {
			if (!t.isLegal()) {
				text.setText(t.getName() +" is not a legal team. Disqualified!");
				canvas.repaint();
				rest(5);
				System.exit(1);
			}
		}
		text.setText(teams[0].getName() +" vs "+teams[1].getName());
		canvas.repaint();
		rest(3);
		
		while (teams[0].isAlive() && teams[1].isAlive()) {
			pokes[0] = teams[0].getTeam().get(0);
			pokes[1] = teams[1].getTeam().get(0);
			super.battle();
			
			teams[teams[0].getTeam().get(0).getHealth()==0?0:1].getTeam().remove(0);
		}
		text.setText(teams[teams[1].isAlive()?1:0].getName() +" wins!");
	}
	
	public void getClasses() {
		teams = new PokeTeam[2];
		try {
			String p1 = "pokemon."+JOptionPane.showInputDialog("Enter the first Team Name");
			String p2 = "pokemon."+JOptionPane.showInputDialog("Enter the second Team Name");

			//String p1 = "ExampleTeam1", p2="ExampleTeam2";
			@SuppressWarnings("rawtypes")
			Class cls = Class.forName(p1.trim());
			teams[0] = (PokeTeam) cls.getDeclaredConstructor().newInstance();
			cls = Class.forName(p2.trim());
			teams[1] = (PokeTeam) cls.getDeclaredConstructor().newInstance();
			
		} catch (ClassNotFoundException e) {
			System.out.println("Team does not exist..");
			e.printStackTrace();
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

		pokes[0] = teams[0].getTeam().get(0);
		pokes[1] = teams[1].getTeam().get(0);
	}
	
	
	public static void main(String[] args) {
		new TeamBattleRunner();
	}
	
	public void drawBattle(Graphics g) {
		super.drawBattle(g);
		for (int i = 0; i < teams[1].getTeam().size(); i++)
			g.drawImage(pokeballImg, WIDTH/4 + WIDTH/20*(2-i), HEIGHT/7, WIDTH/30, HEIGHT/24, null);

		for (int i = 0; i < teams[0].getTeam().size(); i++)
			g.drawImage(pokeballImg, WIDTH*5/6 + WIDTH/20*(2-i), HEIGHT*15/28, WIDTH/30, HEIGHT/24, null);
		
	}
	
	
	
	
	
	
	
	
	
	
	
}