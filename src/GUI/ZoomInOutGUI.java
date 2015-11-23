package GUI;

import java.io.File;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ZoomInOutGUI extends Application {
	public void start(Stage primaryStage) throws Exception {
		ImageView imageView = new ImageView();
		File file = new File("src/res/Stratton-2-Building.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        double imageWidth = image.getWidth() / 3;
        double imageHeight = image.getHeight() / 3;
        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imageView);
        ToggleGroup group = new ToggleGroup();
        ZoomingPane zoomingPane = new ZoomingPane(scrollPane);
        
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
          @Override public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle new_toggle) {
              zoomingPane.setZoomFactor((double)group.getSelectedToggle().getUserData());
        	  System.out.println(zoomingPane.getZoomFactor());
          }
        });

        ToggleButton tb1 = new ToggleButton("1.0x");
        tb1.setToggleGroup(group);
        tb1.setUserData(1.0);
//      tb1.setSelected(true);
        ToggleButton tb2 = new ToggleButton("1.5x");
        tb2.setToggleGroup(group);
        tb2.setUserData(1.5);
        ToggleButton tb3 = new ToggleButton("2.0x");
        tb3.setToggleGroup(group);
        tb3.setUserData(2.0);
        ToggleButton tb4 = new ToggleButton("2.5x");
        tb4.setToggleGroup(group);
        tb4.setUserData(2.5);
        
        HBox hbox = new HBox();
        hbox.getChildren().add(tb1);
        hbox.getChildren().add(tb2);
        hbox.getChildren().add(tb3);
        hbox.getChildren().add(tb4);
        
        primaryStage.setTitle("Zoom In/Out Demo");
        primaryStage.setScene(new Scene(new BorderPane(zoomingPane, null, null, hbox, null)));
        primaryStage.setWidth(imageWidth + 10);
        primaryStage.setHeight(imageHeight + 60);
        primaryStage.show();
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}

