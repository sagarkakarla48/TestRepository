import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class MatrixTranspose {

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
		int transarr[][]=new int[columns][rows];
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				System.out.println("Enter value for arr["+i+"]["+j+"]");
				int val=sc.nextInt();
				arr[i][j]=val;
				transarr[j][i]=val;
			}
		}
		System.out.println("Matrix : ");
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				System.out.print(arr[i][j]);
			}
			System.out.println("");
		}
		System.out.println("Transpose Matrix : ");
		for(int i=0;i<columns;i++){
			for(int j=0;j<rows;j++){
				System.out.print(transarr[i][j]);
			}
			System.out.println("");
		}
	}

}
