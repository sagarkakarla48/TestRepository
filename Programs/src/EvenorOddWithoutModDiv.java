import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class EvenorOddWithoutModDiv {

	/**
	 * @param args
	 */

	
	public static void usingBitwiseAndOperator(int number) {

		// Ex : Take two number 2 and 3
		// 010 : 2
		// 011 : 3
		// ------
		// 010 : 2
		// ------
		// Take two numbers 2 and 1
		// 010 :2
		// 001 :1
		// -----
		// 000 :0
		// -----
		// From above example we can say that on every even number & 1 gives 0.
		// So this is our logic to be implemented in our program if "Number &
		// 1==0" then its even number.

		if ((number & 1) == 0) {
			System.out.println("Entered number is even");
		} else {
			System.out.println("Entered number is odd");
		}
	}
	
	public static void usingBitwiseShiftOperator(int number) {

		// Ex : Take two number 2 and 3
		// 2 : 010
		// 2>>1 : 010>>1 = 001<<1 = 010 = 2 (Even nbr's wil same after one right and left shift
		// 3>>1 : 011>>1 = 001<<1 = 010 = 2 (Odd nbr's wil not same after one right and left shift

		if ((number>>1)<<1 == number) {
			System.out.println("Entered number is even");
		} else {
			System.out.println("Entered number is odd");
		}
	}

	public static void main(String[] args) {

		// refer :
		// http://www.instanceofjava.com/2015/03/even-or-odd-without-using-mod-division.html

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Number:");
		int number = sc.nextInt();
		usingBitwiseAndOperator(number);
		usingBitwiseShiftOperator(number);
	}

}
