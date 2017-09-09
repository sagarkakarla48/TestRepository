import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class ArmstrongNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Armstrong number is a number that is equal to the sum of cubes of its digits for example 0, 1, 153, 370, 371, 407 etc.
		// 153=(1*1*1)+(5*5*5)+(3*3*3);
		//https://www.javatpoint.com/armstrong-number-in-java
		
		int temp=0,sum=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Number:");
		int number=sc.nextInt();
		temp=number;
		while(temp!=0){
			int i=temp%10;
			sum=sum+(i*i*i);
			temp=temp/10;
		}
		if(sum==number){
			System.out.println(number+" is Armstrong number");
		}else{
			System.out.println(number+" is not an Armstrong number");
		}
		
	}

}
