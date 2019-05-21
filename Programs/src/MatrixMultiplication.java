import java.util.Scanner;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class MatrixMultiplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int r1,r2,c1,c2;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Number of rows1 :");
		r1=sc.nextInt();
		System.out.println("Enter Number of columns1 :");
		c1=sc.nextInt();
		System.out.println("Enter Number of rows2 :");
		r2=sc.nextInt();
		System.out.println("Enter Number of columns2 :");
		c2=sc.nextInt();
		
		int arr1[][]=new int[r1][c1];
		int arr2[][]=new int[r2][c2];
		int mularr[][]=new int[r1][c2];
		
		for(int i=0;i<r1;i++){
			for(int j=0;j<c1;j++){
				System.out.println("Enter value for arr1["+i+"]["+j+"]");
				int val=sc.nextInt();
				arr1[i][j]=val;
			}
		}
		
		for(int i=0;i<r2;i++){
			for(int j=0;j<c2;j++){
				System.out.println("Enter value for arr2["+i+"]["+j+"]");
				int val=sc.nextInt();
				arr2[i][j]=val;
			}
		}
		System.out.println("Matrix 1 : ");
		for(int i=0;i<r1;i++){
			for(int j=0;j<c1;j++){
				System.out.print(arr1[i][j]);
			}
			System.out.println("");
		}
		System.out.println("Matrix 2 : ");
		for(int i=0;i<r2;i++){
			for(int j=0;j<c2;j++){
				System.out.print(arr2[i][j]);
			}
			System.out.println("");
		}
		for(int i=0;i<r1;i++){
			for(int j=0;j<c2;j++){
				int sum=0;
				for(int k=0;k<r2;k++){
					sum=sum+arr1[i][k]*arr2[k][j];
				}
				mularr[i][j]= sum;
			}
			System.out.println("");
		}
		
		System.out.println("Multiplied Matrix : ");
		for(int i=0;i<r1;i++){
			for(int j=0;j<c2;j++){
				System.out.print(mularr[i][j]+" ");
			}
			System.out.println("");
		}

	}

}
