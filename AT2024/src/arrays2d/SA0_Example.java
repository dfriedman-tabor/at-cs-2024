// Mr. Friedman
// SA 0
// Help from chatgpt to figure out how to use thread.sleep
package arrays2d;

public class SA0_Example {
	
	public static void q1() throws InterruptedException {
		
		System.out.println("Where are you from?");

		Thread.sleep(750);
		
		System.out.println("I grew up in Marion! I went to ORR though.");
	
		Thread.sleep(750);
	}
	
	
	public static void q2() throws InterruptedException {
		
		System.out.println("What's your favorite spot in Marion?");
		
		Thread.sleep(750);
		
		System.out.println("I love running on the old railroad bed on the other side of route 6");
		
		Thread.sleep(750);

	}
	
	public static void q3() throws InterruptedException {
		
		System.out.println("Where were you before Tabor?");
		
		Thread.sleep(750);
		
		System.out.println("I taught at a school in southern California. "
				+ "But not being by the water and missing trees "
				+ "brought me back to MA. ");
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		q1();
		q2();
		q3();
	}

}
