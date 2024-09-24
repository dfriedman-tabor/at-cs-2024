package inheritance;

public class BankAccount {

	private int amount;
	public String name;
	
	// constructor for somebody bringing money into this account
	public BankAccount(String n, int a) {
		amount = a;
		name = n;
	}
	
	// constructor for somebody starting an empty account
	public BankAccount(String n) {
		name = n;
		amount = 0;
	}
	
	public void withdraw(int m) {
		amount -= m;
	}
	public void deposit(int m) {
		amount += m;
	}
	
	// to be able to view the amount in other classes
	public int getAmount() {
		return amount;
	}
	
	// what we want to see when this class is printed
	public String toString() {
		return "Your account holds $" + amount + ", " + name;
	}
 }

