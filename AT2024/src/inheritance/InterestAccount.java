package inheritance;

public class InterestAccount extends BankAccount{

	private double rate;
	
	// for users creating an account with initial money
	public InterestAccount(String n, int a, double r) {
		super(n, a);
		rate =r;
	}
	
	// for users creating an empty account
	public InterestAccount(String n, double r) {
		super(n);
		rate =r;
	}
	
	public void addInterest() {
		
		// we can't directly change the amount because it is private
		double interest = rate * getAmount();
		deposit((int)interest);
	}
	
	
	public static void main(String[] args) {
		
		
		InterestAccount myaccount = new InterestAccount("David", 5000, .05);
		
		myaccount.addInterest();
		myaccount.withdraw(1000);
		
		System.out.println(myaccount);
	}
}
