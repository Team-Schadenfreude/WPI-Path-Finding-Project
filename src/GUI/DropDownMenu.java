package GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import AStar.Node;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class DropDownMenu {

	public static void setDropDownMenu(final List<Node> nlist, final ComboBox startB, final ComboBox startR, final ComboBox endB, final ComboBox endR, final Button submit, final Label notification) {
		
		// construct a list with all the building names
		List<String> buildings = Arrays.asList( 
				"157 West Street",
				"Alden Hall",
				"Alumni Gym",
				"Atwater Kent Labs",
				"Bartlett Center",
				"Boynton Hall",
				"Campus Center",
				"Fuller Labs",
				"Gordon Library",
				"Harrington Auditorium",
				"Higgins House and Garage",
				"Higgins Labs",
				"Project Center",
				"Salisbury Labs",
				"Stratton Hall",
				"Washburn Shops"
				);
		
		StringBuilder start = new StringBuilder();
		StringBuilder end = new StringBuilder();
		List<String> sRooms = new ArrayList<String>();
		List<String> eRooms = new ArrayList<String>();
		
		// Disable the drop down boxes for selecting rooms, enable them until the building is selected
		startR.setDisable(true);
		endR.setDisable(true);
		
		// Set properties for the Start Building drop down
        startB.getItems().addAll(buildings);
        startB.setPromptText("Start Building");
        startB.setEditable(false);
        startB.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	start.delete(0, start.length());
            	start.append(t1);
            	sRooms.clear();
            	for(Node n: nlist) {
                	if (n.nodeName.contains(start.toString())) {
                		sRooms.add(n.nodeName.replace(start.toString() + " ", ""));
//                		System.out.println(n.nodeName.replace(start.toString() + " ", ""));
                	}
                };
//                for(String s: sRooms)
//                	System.out.println(s);
            	startR.setDisable(false);
            	startR.setValue(null);
            	startR.setPromptText("Start Room");
            	startR.getItems().removeAll(startR.getItems());
            	startR.getItems().addAll(sRooms);
            }
        });
        
        // Set properties for the Start Room drop down
        startR.setPromptText("Start Room");
        startR.setEditable(false);
        startR.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	start.delete(0, start.length());
            	if (t1 != null && !t1.isEmpty()) {
            		start.append(startB.getValue()).append(" ").append(t1);
            		System.out.println(start.toString());
            	}
            }
        });
        
        // Set properties for the End Building drop down
        endB.getItems().addAll(buildings);
        endB.setPromptText("End Building");
        endB.setEditable(false);
        endB.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	end.delete(0, end.length());
            	end.append(t1);
            	eRooms.clear();
            	for(Node n: nlist) {
                	if (n.nodeName.contains(end.toString())) {
                		eRooms.add(n.nodeName.replace(end.toString() + " ", ""));
//                		System.out.println(n.nodeName.replace(end.toString() + " ", ""));
                	}
                };
//                for(String s: eRooms)
//                	System.out.println(s);
            	endR.setDisable(false);
            	endR.setValue(null);
            	endR.setPromptText("End Room");
            	endR.getItems().removeAll(endR.getItems());
            	endR.getItems().addAll(eRooms);
            }
        });
        
        // Set properties for the End Room drop down 
        endR.setPromptText("End Room");
        endR.setEditable(false);
        endR.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	end.delete(0, end.length());
            	if (t1 != null && !t1.isEmpty()) {
            		end.append(endB.getValue()).append(" ").append(t1);
            		System.out.println(end.toString());
            	}
            }
        });
        
        // Set on action when the submit button is pressed
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               if (start.toString().equals(end.toString())) {
            	   notification.setText("Please enter a different destination!");
               }
               else {
            	   notification.setText("");
               }
            }
        });
	}
}


