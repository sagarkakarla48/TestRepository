import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class HarshadNumber {

	/**
	 * @param args
	 * A number is divisible by it's sum of digits
	 * Ex: 21%(2+1)=0;
	 */
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		
		int num=sc.nextInt();
		int temp=num;
		int sum=0;
		while(num>0){
			int i=num%10;
			sum=sum+i;
			num=num/10;
		}
		int result=temp%sum;
		if(result==0){
			System.out.println(temp+" is Harshad Number");
		}else{
			System.out.println(temp+" is not a Harshad Number");
		}

	}

}
