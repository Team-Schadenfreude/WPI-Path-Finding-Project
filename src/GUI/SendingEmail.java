package GUI;

import java.util.*; 

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 


public class SendingEmail {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	public static boolean generateAndSendEmail(String emailAddr, String content, String from, String to) {
		

    		// Step1
        	// System.out.println("\n 1st ===> setup Mail Server Properties..");
    		mailServerProperties = System.getProperties();
    		mailServerProperties.put("mail.smtp.port", "587");
    		mailServerProperties.put("mail.smtp.auth", "true");
    		mailServerProperties.put("mail.smtp.starttls.enable", "true");
    		// System.out.println("Mail Server Properties have been setup successfully..");
     
    		// Step2
    		// System.out.println("\n\n 2nd ===> get Mail Session..");
    		try {
        		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        		generateMailMessage = new MimeMessage(getMailSession);
        		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr));
        		generateMailMessage.setSubject("Your Directions from Randy");
        		String emailBody = "Directions from " + from + " to " + to + ": <br><br>" + content + "<br><br>Regards, <br>Randy App";
        		generateMailMessage.setContent(emailBody, "text/html");
        		System.out.println("Mail session has been created successfully..");
         
        		// Step3
        		// System.out.println("\n\n 3rd ===> Get Session and Send mail");
        		Transport transport = getMailSession.getTransport("smtp");
         
        		// Enter your correct gmail UserID and Password
        		// if you have 2FA enabled then provide App Specific Password
        		transport.connect("smtp.gmail.com", "schadeufreude@gmail.com", "SoftEngTeam6");
        		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        		transport.close();
        		return true;
    		} 
    		catch (AddressException ae) {return false;} 
    		catch (MessagingException me) {return false;}
    }
}
