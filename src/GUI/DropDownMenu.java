package GUI;

import java.util.Arrays;
import java.util.List;

import AStar.NodeList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DropDownMenu {
	NodeList nlist;
	
	public DropDownMenu() {
		this.nlist = new NodeList();
	}
	
	public DropDownMenu(NodeList nlist) {
		this.nlist = nlist;
	}
	
	
	public void setDropDownMenu(final ComboBox startB, final ComboBox startR, final ComboBox endB, final ComboBox endR) {
		
		String sb = "";
		String sr = "";
		String eb = "";
		String er = "";
		
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
		
        startB.getItems().addAll(buildings);
        startB.setPromptText("Start Building");
        startB.setEditable(true);
        startB.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	String ss = t1;
                System.out.println(t);
                System.out.println(t1);
            }
        });

        startR.getItems().addAll(
                "jacob.smith@example.com",
                "isabella.johnson@example.com",
                "ethan.williams@example.com",
                "emma.jones@example.com",
                "michael.brown@example.com"
            );
        startR.setPromptText("Start Room");
        startR.setEditable(true);
        
        endB.getItems().addAll(buildings);
        endB.setPromptText("End Building");
        endB.setEditable(true);
        
        endR.getItems().addAll(
                "jacob.smith@example.com",
                "isabella.johnson@example.com",
                "ethan.williams@example.com",
                "emma.jones@example.com",
                "michael.brown@example.com"
            );
        endR.setPromptText("End Room");
        endR.setEditable(true);
	}
}
