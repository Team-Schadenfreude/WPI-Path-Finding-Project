package GUI;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import AStar.NodeList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class DropDownMenu {
	NodeList nlist;
	
	public DropDownMenu() {
		this.nlist = new NodeList();
	}
	
	public DropDownMenu(NodeList nlist) {
		this.nlist = nlist;
	}
	
	
	public void setDropDownMenu(final ComboBox startB, final ComboBox startR, final ComboBox endB, final ComboBox endR, final Button submit) {
		
		StringBuilder start = new StringBuilder();
		StringBuilder end = new StringBuilder();
		
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
		
		startR.setDisable(true);
		endR.setDisable(true);
		
        startB.getItems().addAll(buildings);
        startB.setPromptText("Start Building");
        startB.setEditable(true);
        startB.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	start.delete(0, start.length());
            	start.append(t1);
            	startR.setDisable(false);
            	System.out.println(start.toString());
            }
        });
        
    	
    	startR.getItems().addAll(buildings);
        startR.setPromptText("Start Room");
        startR.setEditable(true);
        startR.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	start.append(" ").append(t1);
            	System.out.println(start.toString());
            }
        });
        
        
        endB.getItems().addAll(buildings);
        endB.setPromptText("End Building");
        endB.setEditable(true);
        endB.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	end.delete(0, end.length());
            	end.append(t1);
            	endR.setDisable(false);
            	System.out.println(end.toString());
            }
        });
        
        
        endR.getItems().addAll(buildings);
        endR.setPromptText("End Room");
        endR.setEditable(true);
        endR.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	end.append(" ").append(t1);
            	System.out.println(end.toString());
            }
        });
        
        
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               
            }
        });
	}
}
