package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmailInterfaceGUI extends Application{
    public static void main(String[] args) {
    launch(args);
	}
	
	
	@Override public void start(Stage stage) {
		stage.setTitle("Email Sending Demo");
	    Scene scene = new Scene(new Group(), 700, 300);
	    
	    final TextField toAddr = new TextField();
	    final TextField ccAddr = new TextField();
	    final TextField subject = new TextField();
	    final TextArea content = new TextArea();
	    final Button send = new Button ("Send");
	    final Label notification = new Label ();
	    
	    SendingEmail.generateAndSendEmail(toAddr, ccAddr, subject, content, send, notification);	    
	
	    GridPane grid = new GridPane();
	    grid.setVgap(4);
	    grid.setHgap(10);
	    grid.setPadding(new Insets(5, 5, 5, 5));
	    grid.add(new Label("To: "), 0, 1);
	    grid.add(toAddr, 1, 1);
	    grid.add(new Label("Cc: "), 2, 1);
	    grid.add(ccAddr, 3, 1);
	    grid.add(new Label("Subject: "), 0, 2);
	    grid.add(subject, 1, 2);
	    grid.add(new Label("Content: "), 0, 3);
	    grid.add(content, 1, 3, 4, 5);
	    grid.add(send, 0, 9);
	    grid.add(notification, 1, 9);
	
	    Group root = (Group)scene.getRoot();
	    root.getChildren().add(grid);
	    stage.setScene(scene);
	    stage.show();
	
	}
}