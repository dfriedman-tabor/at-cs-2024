package random;

public class RandomGenerator {
	
	
	// using only the rand1to3 command, generate a random number between 1 and 7
	// with equal distribution among all 7 possibilities
	public static int rand1to7() {
		
		return -1;
	}
	
	
	// don't touch below code
	
	public static int rand1to3() {
		
		return (int)(Math.random()*3+1);
	}
	

	public static void main(String[] args) {
		int[] outputs = new int[7];
		for (int i = 0; i < 100000; i++) {
			outputs[rand1to7()-1] += 1;
		}
		
		for (int i = 0; i < 7; i++) {
			System.out.println(i+1 + ": " + outputs[i]/100000.0);
		}
	}

}
