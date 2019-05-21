/**
 * 
 */

/**
 * @author acer
 * points to remember:
 *  1. Static and final methods cannot be overridden
 *	2. Constructors cannot be overridden
 *	3. It is also known as Runtime polymorphism.
 *  4. Return type should be same for overriding method
 */
public class MethodOverriding{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BaseClass bclass=new BaseClass();
		bclass.methodToOverride();
		bclass=new DerivedClass();
		bclass.methodToOverride();
		DerivedClass dclass=new DerivedClass();
		dclass.methodToOverride();
	}

}

