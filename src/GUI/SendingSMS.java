package GUI;

//You may want to be more specific in your imports 
import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*; 
import com.twilio.sdk.resource.factory.*; 
import com.twilio.sdk.resource.instance.*; 
import com.twilio.sdk.resource.list.*; 

public class SendingSMS { 
	// Find your Account Sid and Token at twilio.com/user/account 
	public static final String ACCOUNT_SID = "AC50a233b06eb4afa3c3c127a63175166b"; 
	public static final String AUTH_TOKEN = "e546ed2bd4b6fcaf816440402093eb93"; 
	
	public static boolean generateAndSendSMS(String smsNum, String content, String from, String to) throws TwilioRestException { 
		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
		
			 // Build the parameters 
			 List<NameValuePair> params = new ArrayList<NameValuePair>();  
			 params.add(new BasicNameValuePair("From", "+13478365454"));
			 params.add(new BasicNameValuePair("To", smsNum));   
			 params.add(new BasicNameValuePair("Body", "Directions from " + from + " to " + to + ": \r\n\r\n" + content + "\r\n\r\nRegards, \r\nRandy App")); 
	//		 params.add(new BasicNameValuePair("MediaUrl", "http://farm2.static.flickr.com/1075/1404618563_3ed9a44a3a.jpg"));  	 
		
			 MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
			 Message message = messageFactory.create(params); 
	//		 System.out.println(message.getSid()); 
			 return true;
			 
		} catch(TwilioRestException e1) {return false;}
	} 
}