import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class EvenOrOdd {

	/**
	 * @param args
	 */

	// % gives remainder
	public static void usingMod(int number) {

		if (number % 2 == 0) {
			System.out.println("Entered number is even");
		} else {
			System.out.println("Entered number is odd");
		}
	}

	// / gives quotient
	public static void usingDivision(int number) {
		if ((number/2)*2==number) {
			System.out.println("Entered number is even");
		} else {
			System.out.println("Entered number is odd");
		}
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Number:");
		int number = sc.nextInt();
		usingMod(number);
		usingDivision(number);
	}

}
