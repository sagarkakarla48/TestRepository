/**
 * 
 */
package com.ltts.picktolight.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author 90001332
 *
 */
public class SessionIdentifierGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SessionIdentifierGenerator.class);

	private static SecureRandom random = new SecureRandom();

	static String fileName = "D:\\Apache Software Foundation\\Tomcat 8.0\\bin\\startup.bat";

	static String[] commands = {"cmd", "/d", "start", "\"DummyTitle\"",fileName};

	/**
	 * generates a random value
	 * @return value
	 */
	public static String nextSessionId() {
		logger.info("nextSessionId() method in SessionIdentifierGenerator Class :- Start");
		String value = new BigInteger(60, random).toString(32);
		logger.info("nextSessionId() method in SessionIdentifierGenerator Class :- End");
		return value;
	}


	public static void main(String args[]) throws IOException{
		logger.info("main() method in SessionIdentifierGenerator Class :- Start");
		Runtime.getRuntime().exec(fileName);
		String java = System.getProperty("java.home") + "/bin/java";
		System.out.println(java);
		logger.info("main() method in SessionIdentifierGenerator Class :- End");
	}
}
