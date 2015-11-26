package GUI;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class EmailSending {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	public static void generateAndSendEmail(TextField toAddr, TextField ccAddr, TextField subject, TextArea content, Button send, Label notification) {
		
		send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	System.out.println(toAddr.getText());
            	System.out.println(ccAddr.getText());
            	System.out.println(subject.getText());
            	System.out.println(content.getText());
                if (toAddr.getText() != null && !toAddr.getText().isEmpty()){   
	
            		// Step1
//            		System.out.println("\n 1st ===> setup Mail Server Properties..");
            		mailServerProperties = System.getProperties();
            		mailServerProperties.put("mail.smtp.port", "587");
            		mailServerProperties.put("mail.smtp.auth", "true");
            		mailServerProperties.put("mail.smtp.starttls.enable", "true");
//            		System.out.println("Mail Server Properties have been setup successfully..");
             
            		// Step2
//            		System.out.println("\n\n 2nd ===> get Mail Session..");
            		try {
	            		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
	            		generateMailMessage = new MimeMessage(getMailSession);
	            		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr.getText()));
	            		if (ccAddr.getText() != null && !ccAddr.getText().isEmpty())
	            			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddr.getText()));
	            		generateMailMessage.setSubject(subject.getText());
	            		String emailBody = content.getText() + "<br><br> Regards, <br>Randy App";
	            		generateMailMessage.setContent(emailBody, "text/html");
	            		System.out.println("Mail session has been created successfully..");
	             
	            		// Step3
	//            		System.out.println("\n\n 3rd ===> Get Session and Send mail");
	            		Transport transport = getMailSession.getTransport("smtp");
	             
	            		// Enter your correct gmail UserID and Password
	            		// if you have 2FA enabled then provide App Specific Password
	            		transport.connect("smtp.gmail.com", "schadeufreude@gmail.com", "SoftEngTeam6");
	            		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
	            		transport.close();
            		} 
            		catch (AddressException ae) {} 
            		catch (MessagingException me) {}
            		
                    notification.setText("Your message was successfully sent" + " to " + toAddr.getText());
                    
                    toAddr.clear();
                    ccAddr.clear();
                    subject.clear();
                    content.clear();
                }
    			else 
    				notification.setText("You have not selected a recipient!");
            }
		});
	}
}

/*	
	public void generateAndSendEmail(String toAddr, String ccAddr, String subject, String content) throws AddressException, MessagingException {
 
		// Step1
//		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
//		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
//		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddr));
		generateMailMessage.setSubject(subject);
		String emailBody = content + "<br><br> Regards, <br>Randy App";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Directions has been created successfully..");
 
		// Step3
//		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "kewen.gu@gmail.com", "Gkw989877");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
*/