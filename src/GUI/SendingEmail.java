package GUI;

import java.util.*;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


public class SendingEmail {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static Message generateMailMessage;
	static MimeMultipart multipart = new MimeMultipart("related");
	static BodyPart messageBodyPart;
	static int i = 0; // iterator 

	public static boolean generateAndSendEmail(String emailAddr, List<String> directions, String from, String to) {
			
		String content = "";
		
		for(String s: directions) {
			
			if (s.contains(".png")) {

	    		try{
	    		messageBodyPart = new MimeBodyPart();
	    		DataSource fds = new FileDataSource(s);
	    		messageBodyPart.setDataHandler(new DataHandler(fds));
	    		messageBodyPart.setHeader("Content-ID", "<image" + Integer.toString(i) + ">");
	    		multipart.addBodyPart(messageBodyPart);

				content = content + "<img src=\"cid:image" + Integer.toString(i) + "\">";
				i++;
	    		} catch(MessagingException e){}
			}
			else if(s.toLowerCase().contains("straight")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/go_straight.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("slight right turn")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_slightly_right.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("slight left turn")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_slightly_left.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("sharp right turn")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_sharp_right.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("sharp left turn")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_sharp_left.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("right turn")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_right.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("left turn")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/turn_left.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("proceed")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/proceed.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
			else if(s.toLowerCase().contains("arrive")) {
				content = content + "<p><img src='https://randy-image-server.herokuapp.com/public/img/arrive.png' height='50' width='50' style=\"margin-right: 10px; vertical-align: middle;\"><span style=\"vertical-align: middle;\">" + s + "</span></p>";
			}
		}
		
		content = "<html style=\"background-image: url('https://randy-image-server.herokuapp.com/public/img/WPI_LOGO.png'); background-size: 90% auto; background-position: center; background-repeat: no-repeat;\">"
					+ "<body>"
						+ "<h1 style=\"font-family: Copperplate, 'Copperplate Gothic Light', fantasy; font-size: 3em; margin: 80px auto 80px 0\">"
							+ "Directions from " + from + " to " + to + ":"
						+ "</h1>"
						+ "<section style=\"font-family: 'Century Gothic', CenturyGothic, AppleGothic, sans-serif; font-size: 1.2em;\">" + content + "</section>"
					+ "</body>"
					+ "<foot style=\"font-family: Papyrus, fantasy; font-size: 1.2em;\"><br><br>Thank you for using Randy!<br><br>Regards, <br>Team Schadeufreude</foot>"
				+ "</html>";
		
		System.out.println(content);

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
    		
//    		messageBodyPart = new MimeBodyPart();
//    		String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
//    		messageBodyPart.setContent(htmlText, "text/html");
//    		multipart.addBodyPart(messageBodyPart); 
    		
    		messageBodyPart = new MimeBodyPart();
    		messageBodyPart.setContent(content, "text/html");
    		multipart.addBodyPart(messageBodyPart);
    		
//    		messageBodyPart = new MimeBodyPart();
//    		DataSource fds = new FileDataSource("res/BuildingImages/AtwaterKent.jpg");
//    		messageBodyPart.setDataHandler(new DataHandler(fds));
//    		messageBodyPart.setHeader("Content-ID", "<image>");
//    		multipart.addBodyPart(messageBodyPart);
    		
    		generateMailMessage.setContent(multipart);
    		System.out.println("Mail session has been created successfully..");
     
    		// Step3
    		// System.out.println("\n\n 3rd ===> Get Session and Send mail");
    		Transport transport = getMailSession.getTransport("smtp");
     
    		// Enter your correct gmail UserID and Password
    		// if you have 2FA enabled then provide App Specific Password
    		transport.connect("smtp.gmail.com", "schadeufreude@gmail.com", "SoftEngTeam6");
    		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
    		transport.close();
    		System.out.println("Sent message successfully..");
    		return true;
		} 
		catch (AddressException ae) {return false;} 
		catch (MessagingException me) {return false;}
    }
}