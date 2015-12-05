package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InfoPanelGUI extends Application{

	public static void main(String[] args) {
        launch(args);
    }
	

    @Override public void start(Stage stage) {
    	
    	final AnchorPane anchor = new AnchorPane();
        final GridPane grid = new GridPane();
        grid.setVisible(false);
        
        Image image = new Image("/res/map.png");
        ImageView imageView = new ImageView(image);
        
        AnchorPane anchor_map = new AnchorPane(imageView);
        ScrollPane scroll_map = new ScrollPane();
        scroll_map.setContent(anchor_map);
        anchor.getChildren().addAll(scroll_map, grid);
        
        AnchorPane.setTopAnchor(scroll_map, 0.0);
        AnchorPane.setLeftAnchor(scroll_map, 0.0);
        AnchorPane.setBottomAnchor(scroll_map, 0.0);
        AnchorPane.setRightAnchor(scroll_map, 0.0);
        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 0.0);
        
        
        
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
            public void handle(MouseEvent t) {
        		System.out.println("Clicked");
        		InfoPanel.setUpInfoPanel(grid, "Stratton Hall");
        		if (grid.isVisible())
        			grid.setVisible(false);
        		else
        			grid.setVisible(true);
            }
        });
        
        scroll_map.setOnZoom(new EventHandler<ZoomEvent>() {
        	@Override
        	public void handle(ZoomEvent event) {
        		imageView.setScaleX(imageView.getScaleX() * event.getZoomFactor());
        		imageView.setScaleY(imageView.getScaleY() * event.getZoomFactor());
        			
        		event.consume();
        	}
        });

        
        stage.setTitle("Info Panel Demo");
        Scene scene = new Scene(new Group(), anchor.getPrefWidth(), anchor.getPrefHeight());
        Group root = (Group)scene.getRoot();
        root.getChildren().add(anchor);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
