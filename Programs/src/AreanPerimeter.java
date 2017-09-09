import java.util.Scanner;
/**
 * 
 */

/**
 * @author acer
 *
 */
public class AreanPerimeter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		//area = PI * radius *Radius
		Scanner sc=new Scanner(System.in);
		System.out.println("Please enter radius");
		int radius=sc.nextInt();
		System.out.println("Area of circle = "+Math.PI*radius*radius);
		
		//perimeter=2 * PI * radius
		
		double perimeter=2* Math.PI * radius;
		
		System.out.println("Perimeter of circle = "+perimeter);
	}

}
