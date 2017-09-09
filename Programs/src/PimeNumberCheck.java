import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class PimeNumberCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 Scanner sc=new Scanner(System.in);  
	     
		   System.out.println("Enter number");  
		   int number=sc.nextInt();  
				int count=0;
				for(int i=1;i<=number/2;i++){
					int result=number%i;
					//System.out.println(result);
					if(result==0){
						count++;
					}
				}
				if(count<=1){
					System.out.println(number+" is prime number");
				}else{
					System.out.println(number+" is not a prime number");
				}
				
		 
		}

}
