/**
 * 
 */

/**
 * @author acer
 *
 */
public class NumberPyramid {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		  int num=10;
		 
		  for (int i = 1; i < num; i++) {
		 
		   for (int j = 1; j<=num-i;j++) {
		 
		    System.out.print(" ");
		   } 

		  for (int k = 1; k <= i; k++) {
		   System.out.print(""+k+" ");
		  } 

		  for (int l =i-1; l >0; l--) {
		   System.out.print(""+l+" ");
		  } 

		   System.out.println();
		  }
/*
 	Out Put :	  
 	 		   1 
	         1 2 1 
	        1 2 3 2 1 
	       1 2 3 4 3 2 1 
	      1 2 3 4 5 4 3 2 1 
	     1 2 3 4 5 6 5 4 3 2 1 
	    1 2 3 4 5 6 7 6 5 4 3 2 1 
	   1 2 3 4 5 6 7 8 7 6 5 4 3 2 1 
	  1 2 3 4 5 6 7 8 9 8 7 6 5 4 3 2 1 
*/  
	}
}
