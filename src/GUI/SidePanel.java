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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;
public class SidePanel {
	private static GridPane grid = new GridPane();
	public static GridPane getGridPane()
	{
		return grid;
	}
	public static void setGridPane(GridPane newGrid)
	{
		grid = newGrid;
	}
	public static void setUpSidePanel(String from, String to, List<String> directions, VBox boxToControl, SimpleStringProperty stringProperty) {

		//final GridPane grid = new GridPane();
		grid.getChildren().clear();
		final ListView<HBox> dList = new ListView<HBox>();
		final TextField emailAddrInput = new TextField();
		final TextField SMSNumInput = new TextField();
		emailAddrInput.setPromptText("Email");
		SMSNumInput.setPromptText("SMS");
		emailAddrInput.setStyle("-fx-text-color: #606060;");
		SMSNumInput.setStyle("-fx-text-color: #606060;");

		final Button send = new Button();
		final Label notificationEmail = new Label();
		final Label notificationSMS = new Label();
		final HBox hbEmail = new HBox();
		final HBox hbSMS = new HBox();
	
		final List<HBox> hbList = new ArrayList<HBox>();
		HBox hbItem;
		Label label;

		send.setGraphic(new ImageView(new Image("/res/icons/send.png", 50, 50, true, true)));
		//send.setStyle("-fx-background-color: #DEDEDE;"); // Background of Send Button
		System.out.println(send.getStyle());

		notificationEmail.setWrapText(true);
		notificationSMS.setWrapText(true);
		label = new Label("");
		label.setWrapText(true);
		label.setTextFill(Color.web("#FFFFF0"));
		//	hbEmail.getChildren().addAll(new Label("Email: "), emailAddrInput);
		//hbSMS.getChildren().addAll(new Label("Text: "), SMSNumInput);



		int iconSize = 30;

		hbEmail.getChildren().addAll(label, emailAddrInput);
		label = new Label("");
		label.setTextFill(Color.web("#FFFFF0"));
		hbSMS.getChildren().addAll(label, SMSNumInput);
		HBox.setHgrow(emailAddrInput, Priority.ALWAYS);
		HBox.setHgrow(SMSNumInput, Priority.ALWAYS);
		//dList.getItems().addAll(directions);
		

for(String s: directions) {
			
			if(s.toLowerCase().contains("go straight")) {
				label = new Label(s);
				label.getStyleClass().add("direction_label");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/go_straight.png", iconSize, iconSize, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("right turn")) {
				label = new Label(s);
				label.getStyleClass().add("direction_label");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_right.png", iconSize, iconSize, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("left turn")) {
				label = new Label(s);
				label.getStyleClass().add("direction_label");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_left.png", iconSize, iconSize, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("slight right turn")) {
				label = new Label(s);
				label.getStyleClass().add("direction_label");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_slightly_right.png", iconSize, iconSize, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("slight left turn")) {
				label = new Label(s);
				label.getStyleClass().add("direction_label");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_slightly_left.png", iconSize, iconSize, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("sharp right turn")) {
				label = new Label(s);
				label.getStyleClass().add("direction_label");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_sharp_right.png", iconSize, iconSize, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("sharp left turn")) {
				label = new Label(s);
				label.getStyleClass().add("direction_label");
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/turn_sharp_left.png", iconSize, iconSize, true, true)), label);
				hbList.add(hbItem);
			}
			else if(s.toLowerCase().contains("proceed into")) {
				System.out.println("Yo Im in Here");
 				label = new Label(s);
 				label.getStyleClass().add("direction_label");
				label.setOnMouseClicked(new EventHandler<MouseEvent>() {
				    @Override public void handle(MouseEvent e) {
				    	System.out.println("Clicked Label");
				    	String s2 = s;
				    	s2 = s2.replace("Proceed into ", "");
				    	stringProperty.set(s2);
				    }});
				hbItem = new HBox(10, new ImageView(new Image("/res/icons/arrive.png", iconSize, iconSize, true, true)), label);
 				hbList.add(hbItem);
 			}
		}
	
		
		dList.getItems().addAll(hbList);
		dList.setStyle("-fx-background-color: #606060;");

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

//		grid.setVgap(13);
//		grid.setHgap(4);
//		grid.setPadding(new Insets(5, 5, 5, 5));
//		grid.add(new Label("Your Directions: "), 0, 1);
//		grid.add(dList, 0, 2, 4, 5);
//		grid.add(hbEmail, 0, 8);
//		grid.add(notificationEmail, 0, 9);
//		grid.add(hbSMS, 0, 10);
//		grid.add(notificationSMS, 0, 11);
//		grid.add(send, 0, 12);
		
		
		grid.setVgap(14);
        grid.setHgap(5);
        grid.setStyle("-fx-background-color: #415A69;"); // Direction Box Background Color
        grid.setPadding(new Insets(5, 5, 5, 5));
        label = new Label("YOUR DIRECTIONS: ");
        label.setTextFill(Color.web("#FFFFF0"));
	    label.setStyle("-fx-font-weight: bolder; -fx-font-size: 12px;");
	    label.setWrapText(true);
        grid.add(label, 0, 1);
        grid.add(dList, 0, 2, 5, 10);
        grid.add(hbEmail, 0, 12, 4, 1);
        grid.add(hbSMS, 0, 13, 4, 1);
        grid.add(send, 4, 12, 1, 2);
        Button closeBtn = new Button("Close");
		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				grid.getChildren().clear();
				boxToControl.getChildren().clear();
			}});
		grid.add(closeBtn, 0, 15);
		

		//return grid;
	}
}