/**
 * 
 */
package com.ltts.picktolight.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * @author 90001332
 *
 */
public class DateTimeDifference {
	
	public static long getTimeDifference(String date1,String date2){
				
				Date startDate = stringToDate(date1); // Set start date
				Date endDate   = stringToDate(date2);// Set end date

				long duration  = endDate.getTime() - startDate.getTime();

				long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
				long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
				long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
				
				System.out.println("sec  ==  "+diffInSeconds+"  min  ==  "+diffInMinutes+" hou == "+diffInHours);
				
				return diffInMinutes;
	}
	
	public static Date stringToDate(String date){

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime =null;
        try
        {
        	datetime = simpleDateFormat.parse(date);
            System.out.println("date : "+simpleDateFormat.format(datetime));
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        } 
        return datetime;
	}
	
	public static void main(String args[]){
		
		DateTimeDifference.getTimeDifference("2013/03/24 21:54:33","2013/03/24 22:54:44");
	}
}
