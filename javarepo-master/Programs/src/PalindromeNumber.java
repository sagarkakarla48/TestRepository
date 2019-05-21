import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class PalindromeNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Reverse of a number is equal to itself then that number is called as Palindrome 
		// Ex: 121, 151, 323 etc..
		
		int temp;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Number:");
		int number=sc.nextInt();
		temp=number;
		int reverse=0;
		while(number!=0){
			int i=number%10;
			reverse=(reverse*10)+(i);
			number=number/10;
		}
		if(temp==reverse){
			System.out.println(temp+" is Palindrome number");
		}else{
			System.out.println(temp+" is not an Palindrome number");
		}
	}

}
