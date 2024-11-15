package random;
import java.math.BigInteger;


public class Fibonacci {
	


	static final BigInteger two = BigInteger.valueOf(2), one = BigInteger.valueOf(1), zero = BigInteger.valueOf(0);
	
	
	public static BigInteger fib(BigInteger n) {
		if (n.equals(zero)) return zero;
		if (n.equals(one)) return one;
		if (n.equals(two)) return one;
		
		if (n.compareTo(zero)<0) {
			if (n.mod(two).equals(zero)) return fib(n.multiply(BigInteger.valueOf(-1))).multiply(BigInteger.valueOf(-1));
			else return fib(n.multiply(BigInteger.valueOf(-1)));
		}
		
		
		if (n.mod(two).equals(one))
			return fib(n.divide(two).add(one)).pow(2).add(fib(n.divide(two)).pow(2));
		else {
			BigInteger t1 = fib(n.divide(two));
			return t1.multiply(two.multiply(fib(n.divide(two).add(one))).subtract(t1));
		}
	}
	
	public static BigInteger fib(int n) {
		return fib(new BigInteger(n+""));
	}
	
	

	public static void main(String[] args) {
		
		System.out.println(		fib(10000001)
);

	}

}
