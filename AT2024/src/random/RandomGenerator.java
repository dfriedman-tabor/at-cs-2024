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
		for (int i = 0; i < 10; i++) {
			System.out.println(rand1to7());
		}
	}

}
