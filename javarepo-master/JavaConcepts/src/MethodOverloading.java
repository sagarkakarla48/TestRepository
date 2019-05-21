/**
 * 
 */

/**
 * @author acer
 *	Points to remember:
 *	1. change of overloaded method return type can't enough to overload
 *	2. method overloading is compile time polymorphism
 *	
 */
public class MethodOverloading {

	/**
	 * @param args
	 */
	void overload(int i){
		System.out.println("int = "+i);
	}
	
	void overload(long i){
		System.out.println("long = "+i);
	}
	
	public static void main(String[] args) {
		
		MethodOverloading obj=new MethodOverloading();
		obj.overload(100);
		obj.overload(100L);
	}

}
