package GUI;

import java.util.List;
import java.util.Random;

import AStar.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ImFeelingLuckyButton {
	public static void setImFeelingLuckyButton(final List<Node> nlist, final Button imFeelingLucky, final ComboBox endB, final ComboBox endR) {
		
		imFeelingLucky.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	// Select random node name
            	Random random = new Random();
            	int index = random.nextInt(nlist.size() - 1);
            	String randEndLoc = nlist.get(index).nodeName;
            	String delims = "[ ]+";
            	String[] tokens = randEndLoc.split(delims);
            	// Split building and room names
            	String randEndB = randEndLoc.replace(" " + tokens[tokens.length - 1], "");;
            	String randEndR = tokens[tokens.length - 1];
            	
            	endB.setValue(randEndB);
            	endB.setPromptText(randEndB);
            	endR.setValue(randEndR);
            	endR.setPromptText(randEndR);
            }
		});
	}
}
