package pokemon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Pokemon {

	private String name;
	private int type, health;
	private double maxHealth;
	private Image img;
	private int status = 0;
	public Move[] moves;
	private boolean canMove = true;
	
	public Pokemon(String pokeName, int type, int health, String imgName, Move[] moves) {
		img = new ImageIcon(getClass().getClassLoader().getResource("resources/"+imgName)).getImage();
		name = pokeName; this.type = type; this.health = health; maxHealth = health;
		this.moves = moves;
		System.out.println(name+" "+score());
	}
	
	public int score() {
		int score = health*2;
		for (Move m : moves) {
			score += m.damage;
			score += m.effect==0?0:30;
		}
		return score;
	}
	
	public String effects() {
		String output = "";
		if (status == 1) {
			if (Math.random() < .33) {
				output += name +" woke up!\n";
				status = 0;
			}else {
				canMove = false;
				return name +" is asleep.\n";
			}
		}
		else if (status == 2) {
			output += name +" is hurt by poison.\n";
			takeDamage(PokeBattleRunner.POISONDAMAGE);
			if (health == 0) {
				canMove = false;
				return output;
			}
		}
		else if (status == 3) {
			if (Math.random() < .25) {
				output += name +" snapped out of confusion!\n";
				status = 0;
			}
			output += name +" is confused.\n";
			if (Math.random() < .5) {
				output += name + " hurt itself in its confusion.\n";
				takeDamage(PokeBattleRunner.CONFUSEDAMAGE);
				canMove = false;
				return output;
			}
		}
		else if (status == 4) {
			if (Math.random() < .3) {
				canMove = false;
				return name + " is paralyzed!\n";
			}
		}
		
		canMove = true;
		return output;
	}

	public Move attack() {
		if (!canMove) return null;
		
		return moves[(int)(Math.random()*4)];
	}

	public String suffer(Move move) {
		String output = "";
		
		double multiplier = PokeBattleRunner.effectiveness[move.type][this.type];
		
		if (multiplier == .5)
			output += "Not very effective...\n";
		else if (multiplier == 2)
			output += "super effective!\n";
		this.takeDamage((int)(move.damage*multiplier));
		
		if (move.effect != 0) {
			this.setStatus(move.effect);
			output += this.name + " was " +PokeBattleRunner.stati[move.effect];
		}
		
		return output;
	}
	
	public void takeDamage(int dam) {
		health = Math.max(health-dam, 0);
	}
	
	public int getHealth() {return health;}
	public int getType() {return type;}
	public String getName() {return name;}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void draw(Graphics g, boolean top) {
		if (!top) {
			if (health > 0)
				g.drawImage(img, PokeBattleRunner.POKEX[0], PokeBattleRunner.POKEY[0], PokeBattleRunner.POKEWIDTH, PokeBattleRunner.POKEHEIGHT, null);
			if (health/maxHealth >= .5)
				g.setColor(Color.GREEN);
			else if (health/maxHealth >= .25)
				g.setColor(Color.orange);
			else
				g.setColor(Color.red);
			g.fillRect((int)(PokeBattleRunner.WIDTH/1.3), (int)(PokeBattleRunner.HEIGHT/1.64), (int)(PokeBattleRunner.HEALTHWIDTH*(health/maxHealth)), PokeBattleRunner.HEALTHHEIGHT);
			g.setColor(Color.black);
			g.setFont(new Font("Courier",Font.PLAIN, PokeBattleRunner.HEIGHT/20));
			g.drawImage(PokeBattleRunner.attackImgs[type],PokeBattleRunner.WIDTH*35/60,PokeBattleRunner.HEIGHT*22/40, PokeBattleRunner.WIDTH/30, PokeBattleRunner.HEIGHT/30, null);
			g.drawString(name, (int)(PokeBattleRunner.WIDTH*37/60 ), (int)(PokeBattleRunner.HEIGHT*23/40));
			g.setColor(PokeBattleRunner.statiColor[status]);
			g.drawString(PokeBattleRunner.statiLabels[status], (int)(PokeBattleRunner.WIDTH*3/5 ), (int)(PokeBattleRunner.HEIGHT*25/40));
		}
		else {
			if (health > 0)
				g.drawImage(img, PokeBattleRunner.POKEX[1], PokeBattleRunner.POKEY[1], PokeBattleRunner.POKEWIDTH, PokeBattleRunner.POKEHEIGHT, null);
			if (health/maxHealth >= .5)
				g.setColor(Color.GREEN);
			else if (health/maxHealth >= .25)
				g.setColor(Color.orange);
			else
				g.setColor(Color.red);
			g.fillRect((int)(PokeBattleRunner.WIDTH/5.22), (int)(PokeBattleRunner.HEIGHT/4.65), (int)(PokeBattleRunner.HEALTHWIDTH*(health/maxHealth)), PokeBattleRunner.HEALTHHEIGHT);
			g.setColor(Color.black);
			g.setFont(new Font("Courier",Font.PLAIN, PokeBattleRunner.HEIGHT/20));
			g.drawImage(PokeBattleRunner.attackImgs[type],(int)(PokeBattleRunner.WIDTH/200 ), (int)(PokeBattleRunner.HEIGHT*11/80), PokeBattleRunner.WIDTH/30, PokeBattleRunner.HEIGHT/30, null);
			g.drawString(name, (int)(PokeBattleRunner.WIDTH/30 ), (int)(PokeBattleRunner.HEIGHT*7/40));
			g.setColor(PokeBattleRunner.statiColor[status]);
			g.drawString(PokeBattleRunner.statiLabels[status], (int)(PokeBattleRunner.WIDTH/35 ), (int)(PokeBattleRunner.HEIGHT*9/40));
		}
	}
	
	
}
