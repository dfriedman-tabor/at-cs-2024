package pokemon;
import java.util.ArrayList;

public abstract class PokeTeam {
	
	// each team receives a score - the sum of each pokemon's score
	// a team's score may not exceed 1050
	// a team must contain 3 pokemon

	private ArrayList<Pokemon> team;
	private String name;
	public static final int SCORELIMIT = 1050;
	
	public PokeTeam(String name) {
		this.team = createTeam(); this.name = name;
	}
	
	public abstract ArrayList<Pokemon> createTeam();
	
	public String getName() {return name;}
	public ArrayList<Pokemon> getTeam() {return team;}
	
	public boolean isAlive() {return !team.isEmpty();}
	
	public boolean isLegal() { 
		int score = 0; 
		for (Pokemon p : team) {
			score += p.score();
			if (p.moves.length != 4 || p.getClass().getDeclaredMethods().length!=0)
				return false;
		}
		return score <= SCORELIMIT && team.size() == 3;
		
	}
}
