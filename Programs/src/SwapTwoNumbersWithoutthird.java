
public class SwapTwoNumbersWithoutthird {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int a=100;
		int b=300;
		System.out.println("a ="+a+" and b = "+b);
		b=a+b;
		a=b-a;
		b=b-a;
		System.out.println("a ="+a+" and b = "+b);
	}
}
