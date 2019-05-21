import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class PalindromeString {

	/**
	 * @param args
	 * 
	 */

	// Using In built functions
	public static String checkPalindrome(String str) {
		String reverse = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			reverse = reverse + str.charAt(i);
		}
		if (str.equalsIgnoreCase(reverse)) {
			return str + " is Palindrome String";
		} else {
			return str + " is not an Palindrome String";
		}
	}

	/*
	 * * This method check if a given String is palindrome or not using
	 * recursion
	 * http://javarevisited.blogspot.in/2015/06/2-ways-to-check-if-string-is-palindrome-java.html
	 */
	public static boolean isPalindrome(String input) {
		if (input == null) {
			return false;
		}
		String reversed = reverse(input);
		return input.equals(reversed);
	}

	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		if (str.length() <= 1) {
			return str;
		}
		System.out.println(str);
		
		return reverse(str.substring(1)) + str.charAt(0);
	}


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter StringS : ");
		String str = sc.nextLine();
		System.out.println(checkPalindrome(str));
		
		System.out.println(isPalindrome(str));
	}
}
