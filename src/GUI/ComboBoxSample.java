package GUI;

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

public class ComboBoxSample extends Application {
        public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), 800, 200);

        final ComboBox startB = new ComboBox();
        final ComboBox startR = new ComboBox();
        final ComboBox endB = new ComboBox();
        final ComboBox endR = new ComboBox();
       
        DropDownMenu ddm = new DropDownMenu();
        ddm.setDropDownMenu(startB, startR, endB, endR);
        
        


        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Start Building: "), 0, 1);
        grid.add(startB, 1, 1);
        grid.add(new Label("Start Room: "), 2, 1);
        grid.add(startR, 3, 1);
        grid.add(new Label("End Building: "), 0, 2);
        grid.add(endB, 1, 2);
        grid.add(new Label("End Room: "), 2, 2);
        grid.add(endR, 3, 2);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();

    }
}
