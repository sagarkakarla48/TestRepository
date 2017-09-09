/**
 * 
 */

/**
 * @author acer
 *
 */
public class OneToTenWithoutLoop {

	/**
	 * @param args
	 */
	
	public static void recurFun(int n){
		System.out.println(n);
		if(n<10){
		recurFun(n+1);
		}
	}
	public static void main(String[] args) {
		recurFun(1);
	}

}
