/**
 * 
 */
package com.ltts.picktolight.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author 90001332
 *
 */
public class CurrentDateTime {

	private static final Logger logger = LoggerFactory.getLogger(CurrentDateTime.class);

	/**
	 * here we are getting current system time and date	
	 * @return String Date
	 */
	public static String getCurrentTimeStamp() {

		logger.info("getCurrentTimeStamp() method in CurrentDateTime Class :- Start");

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);

		logger.info("getCurrentTimeStamp() method in CurrentDateTime Class :- End");
		return strDate;
	}


}
