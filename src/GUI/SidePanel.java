package GUI;


import java.util.List;

import com.twilio.sdk.TwilioRestException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SidePanel {
	
	public static GridPane setUpSidePanel(String from, String to, List<String> directions) {
		
		final GridPane grid = new GridPane();
		final ListView<String> dList = new ListView<String>();
		final TextField emailAddrInput = new TextField();
		final TextField SMSNumInput = new TextField();
		final Button send = new Button("Send");
		final Label notificationEmail = new Label();
		final Label notificationSMS = new Label();
		final HBox hbEmail = new HBox();
		final HBox hbSMS = new HBox();
		final String emailContent = String.join("<br>", directions);
		final String txtContent = String.join("\r\n", directions);
		
		
	    notificationEmail.setWrapText(true);
	    notificationSMS.setWrapText(true);
		
		hbEmail.getChildren().addAll(new Label("Email directions: "), emailAddrInput);
		hbSMS.getChildren().addAll(new Label("SMS directions: "), SMSNumInput);
		HBox.setHgrow(emailAddrInput, Priority.ALWAYS);
		HBox.setHgrow(SMSNumInput, Priority.ALWAYS);

		
		send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String emailAddr = emailAddrInput.getText();
                String smsNum = SMSNumInput.getText();
            	if (emailAddr != null && !emailAddr.isEmpty()){  
            		if (SendingEmail.generateAndSendEmail(emailAddr, emailContent, from, to))
            			notificationEmail.setText("Email has sent to " + emailAddr + "!");
            		else
            			notificationEmail.setText("Emailing failed!");
            	}
            	if (smsNum != null && !smsNum.isEmpty()){  
            		try {
						if (SendingSMS.generateAndSendSMS(smsNum, txtContent, from, to))
							notificationSMS.setText("SMS has sent to " + smsNum + "!");
						else
							notificationSMS.setText("Texting failed!");
					} catch (TwilioRestException e1) {}
            	}
            }
		});
		
		grid.setVgap(13);
        grid.setHgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("YOUR DIRECTIONS: "), 0, 1);
        grid.add(dList, 0, 2, 4, 5);
        grid.add(hbEmail, 0, 8);
        grid.add(notificationEmail, 0, 9);
        grid.add(hbSMS, 0, 10);
        grid.add(notificationSMS, 0, 11);
        grid.add(send, 0, 12);

		
		dList.getItems().addAll(directions);
		
		return grid;
	}
}