package pokemon;


// represents a move that a pokemon can perform
public class Move {

	public int type, effect;	// refer to PokeBattleRunner for type/effect indices
	public int damage;
	public String name;		// move's name (e.g. water gun)
	
	public Move(String name, int type, int dam, int eff) {
		this.name = name; this.type = type;
		damage = dam; effect = eff;
	}
	
	
}
