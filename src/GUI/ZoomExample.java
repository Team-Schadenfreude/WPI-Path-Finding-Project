package GUI;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

public class ZoomExample extends Application {

private ImageView imageView = new ImageView();
private ScrollPane scrollPane = new ScrollPane();
final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

@Override
public void start(Stage stage) throws Exception {

zoomProperty.addListener(new InvalidationListener() {
@Override
public void invalidated(Observable arg0) {
imageView.setFitWidth(zoomProperty.get() * 4);
imageView.setFitHeight(zoomProperty.get() * 3);
}
});

scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
@Override
public void handle(ScrollEvent event) {
if (event.getDeltaY() > 0) {
zoomProperty.set(zoomProperty.get() * 1.1);
} else if (event.getDeltaY() < 0) {
zoomProperty.set(zoomProperty.get() / 1.1);
}
}
});

imageView.setImage(new Image("http://mikecann.co.uk/wp-content/uploads/2009/12/javafx_logo_color_1.jpg"));
imageView.preserveRatioProperty().set(true);
scrollPane.setContent(imageView);

stage.setScene(new Scene(scrollPane, 400, 300));
stage.show();

}

/**
* @param args
*/
public static void main(String[] args) {
launch(args);
}
}
