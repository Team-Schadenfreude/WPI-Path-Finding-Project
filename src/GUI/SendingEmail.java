package GUI;

import java.util.*; 

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
 


public class SendingEmail {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	public static boolean generateAndSendEmail(String emailAddr, List<String> directions, String from, String to) {
			
		String content = "";
		
		for(String s: directions) {
			
			if(s.toLowerCase().contains("straight")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/go_straight.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("turn right")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_right.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("turn left")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_left.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("slightly turn right")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_slightly_right.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("slightly turn left")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_slightly_left.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("sharp turn right")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_sharp_right.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("sharp turn left")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_sharp_left.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("proceed")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/proceed.png' height='30' width='30'>" + s + "</p>";
			}
			else if(s.toLowerCase().contains("arrive")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/arrive.png' height='30' width='30'>" + s + "</p>";
			}
		}

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
        		String emailBody = "<html style=\"background-image: url('https://www.wpi.edu/Images/CMS/Wallpaper/salisbury1024x768.jpg'); background-size: cover;\"><body><h1 style=\"color: ivory; font-family: 'Comic Sans MS', cursive, sans-serif; font-size: 3em; text-align: center;\">Directions from " + from + " to " + to + ": </h1><p>" + content + "</p></body><foot><br>Regards, <br>Randy App</foot></html>";
        		
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
