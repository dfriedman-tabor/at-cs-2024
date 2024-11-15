package maps;

import java.util.HashMap;

public class MapExamples {
	
	public static void main(String[] args) {
		
		HashMap<String, Integer> ages = new HashMap<String, Integer>();
		
		ages.put("Cam", 18);
		
		ages.put("Nobre", 12);
		
		ages.put("Sully", 16);
		
		ages.put("Nobre", 20);
		
		
		
		System.out.println(ages.get("Hamza"));
		
		
		
		
	}

}
