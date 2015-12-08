package GUI;

import java.util.ArrayList;
import java.util.List;

import com.twilio.sdk.TwilioRestException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class SidePanel {
	
	public static void setUpSidePanel(GridPane grid, String from, String to, List<String> directions) {
		
		grid.getChildren().clear();
		final ListView<HBox> dList = new ListView<HBox>();
		final TextField emailAddrInput = new TextField();
		final TextField SMSNumInput = new TextField();
		final Button send = new Button();
		final HBox hbEmail = new HBox();
		final HBox hbSMS = new HBox();
		final List<HBox> hbList = new ArrayList<HBox>();
		final String emailContent = String.join("<br>", directions);
		final String txtContent = String.join("\r\n", directions);
		HBox hbItem;
		Label label;
		
		send.setGraphic(new ImageView(new Image("/res/icons/send.png", 50, 50, true, true)));
		send.setStyle("-fx-background-color: #DEDEDE;");
		
	    label = new Label("Email Directions: ");
	    label.setTextFill(Color.web("#FFFFF0"));
		hbEmail.getChildren().addAll(label, emailAddrInput);
		label = new Label("SMS Directions:  ");
		label.setTextFill(Color.web("#FFFFF0"));
		hbSMS.getChildren().addAll(label, SMSNumInput);
		HBox.setHgrow(emailAddrInput, Priority.ALWAYS);
		HBox.setHgrow(SMSNumInput, Priority.ALWAYS);
		
		for(String s: directions) {
			
			if(s.toLowerCase().contains("straight")) {
				label = new Label(s);
				label.setStyle("-fx-font-size: 18px;");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/go_straight.png", 80, 80, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("turn right")) {
				label = new Label(s);
				label.setStyle("-fx-font-size: 18px;");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_right.png", 80, 80, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("turn left")) {
				label = new Label(s);
				label.setStyle("-fx-font-size: 18px;");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_left.png", 80, 80, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("slightly right")) {
				label = new Label(s);
				label.setStyle("-fx-font-size: 18px;");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_slightly_right.png", 80, 80, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("slightly left")) {
				label = new Label(s);
				label.setStyle("-fx-font-size: 18px;");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_slightly_left.png", 80, 80, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("arrive")) {
				label = new Label(s);
				label.setStyle("-fx-font-size: 18px;");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/arrive.png", 80, 80, true, true)), label);
				hbList.add(hbItem);
			}
		}
		dList.getItems().addAll(hbList);
		dList.setStyle("-fx-background-color: #334C5D;");

		
		send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String emailAddr = emailAddrInput.getText();
                String smsNum = SMSNumInput.getText();
            	if (emailAddr != null && !emailAddr.isEmpty()){  
            		if (SendingEmail.generateAndSendEmail(emailAddr, directions, from, to)) {
            			emailAddrInput.clear();
            			emailAddrInput.setPromptText("Emailed to " + emailAddr);
            		}
            		else {
            			emailAddrInput.clear();
            			emailAddrInput.setPromptText("Emailing Failed!");
            		}
            	}
            	if (smsNum != null && !smsNum.isEmpty()){  
            		try {
						if (SendingSMS.generateAndSendSMS(smsNum, directions, from, to)) {
							SMSNumInput.clear();
							SMSNumInput.setPromptText("Texted to " + smsNum);
						}
						else {
							SMSNumInput.clear();
							SMSNumInput.setPromptText("Texting Failed!");
						}
					} catch (TwilioRestException e1) {}
            	}
            }
		});
		
		grid.setVgap(14);
        grid.setHgap(5);
        grid.setStyle("-fx-background-color: #334C5D;");
        grid.setPadding(new Insets(5, 5, 5, 5));
        label = new Label("YOUR DIRECTIONS: ");
	    label.setTextFill(Color.web("#FFFFF0"));
	    label.setStyle("-fx-font-weight: bolder; -fx-font-size: 24px;");
        grid.add(label, 0, 1);
        grid.add(dList, 0, 2, 5, 10);
        grid.add(hbEmail, 0, 12, 4, 1);
        grid.add(hbSMS, 0, 13, 4, 1);
        grid.add(send, 4, 12, 1, 2);
        
	}
}
