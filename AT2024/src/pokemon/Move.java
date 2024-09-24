package pokemon;

public class Move {

	public int type, damage, effect;
	public String name;
	
	public Move(String name, int type, int dam, int eff) {
		this.name = name; this.type = type;
		damage = dam; effect = eff;
	}
	
	
}
