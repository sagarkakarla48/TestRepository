import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class Matrix {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Number of rows :");
		int rows=sc.nextInt();
		System.out.println("Enter Number of columns :");
		int columns=sc.nextInt();
		int arr[][]=new int[rows][columns];
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				System.out.println("Enter value for arr["+i+"]["+j+"]");
				arr[i][j]=sc.nextInt();
			}
		}
		System.out.println(arr.toString());
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				System.out.print(arr[i][j]);
			}
			System.out.println("");
		}
	}

}
