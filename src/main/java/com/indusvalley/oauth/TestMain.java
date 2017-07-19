package com.indusvalley.oauth;

import org.jose4j.jwt.consumer.InvalidJwtException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class TestMain {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static void main(String[] args) throws InvalidJwtException, ParseException {
		//System.out.println(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE.toString());

		long addUnixTimeSeconds = 3600 * 24 * 30 * 6;

		DateFormat gmtDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		long currentUnixTimeStamp = Instant.now().getEpochSecond();

		Date time = new Date(currentUnixTimeStamp * 1000);
		//long currentGMTUnixTimeStamp = gmtDateFormat.parse(String.valueOf(currentUnixTimeStamp * 1000L)).getTime();
		String result = gmtDateFormat.format(time);
		long result1 = gmtDateFormat.parse(result).getTime();
		System.out.println("currentUnixTimeStamp:" + currentUnixTimeStamp);
		System.out.println("result:" + result);
		System.out.println("result1:" + result1);


		time = new Date((currentUnixTimeStamp + addUnixTimeSeconds) * 1000);
		result = gmtDateFormat.format(time);
		result1 = gmtDateFormat.parse(result).getTime();
		System.out.println("result2:" + result1);
/*

		Date date = new Date();
		Timestamp currentTimeStamp = new Timestamp(date.getTime());
		Timestamp futureTimeStamp = new Timestamp(date.getTime() + (addUnixTimeSeconds * 1000L));


		TimeZone gmtTime = TimeZone.getTimeZone("GMT");

		*/
/*DateFormat gmtFormat = new SimpleDateFormat();
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		gmtFormat.setTimeZone(gmtTime);
		System.out.println("Current Time: "+date);
		System.out.println("GMT Time: " + gmtFormat.format(date));*//*



		System.out.println(date.getTime());
		System.out.println(date.getTime() + (addUnixTimeSeconds * 1000L));
*/

		//long unixTime = (long) date.getTime()
	}
}
