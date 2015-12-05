package GUI;


import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SidePanelGUI extends Application {
	public static void main(String[] args) {
        launch(args);
    }
	

    @Override public void start(Stage stage) {
        
        GridPane grid = new GridPane();
        List<String> directions = Arrays.asList("Go \nStraight", "Turn \nLeft", "Turn \nSlightly Right", "Keep \nStraight", "Turn \nSlightly Left", "Turn \nRight", "Keep \nStraight", "Turn \nLeft", "Turn \nSlightly Right", "Keep \nStraight", "Turn \nSlightly Left", "Turn \nRight", "Keep \nStraight", "Turn \nLeft", "Turn \nSlightly Right", "Keep \nStraight", "Turn \nSlightly Left", "Turn \nRight", "Keep \nStraight", "Turn \nLeft", "Turn \nSlightly Right", "Keep \nStraight", "Turn \nSlightly Left", "Turn \nRight", "Keep \nStraight", "Turn \nLeft", "Turn \nSlightly Right", "Keep \nStraight", "Turn \nSlightly Left", "Turn \nRight", "Keep \nStraight", "You \nHave Arrived");
        
        SidePanel.setUpSidePanel(grid, "FL", "AK", directions);

        stage.setTitle("Side Panel Demo");
        Scene scene = new Scene(new Group(), grid.getPrefWidth(), grid.getPrefHeight());
        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

    }
}
