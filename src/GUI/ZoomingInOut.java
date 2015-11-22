package GUI;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class ZoomingInOut extends Application{
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        Slider slider = new Slider(0.5,2,1);
        ZoomingPane zoomingPane = new ZoomingPane(webView);
        
        final ToggleGroup group = new ToggleGroup();

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
          public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle new_toggle) {
              System.out.println((Color) group.getSelectedToggle().getUserData());
          }
        });

        ZoomingInOut tb1 = new ZoomingInOut("A");
        tb1.setToggleGroup(group);
        tb1.setUserData(Color.LIGHTGREEN);
        tb1.setSelected(true);


        ZoomingInOut tb2 = new ZoomingInOut("B");
        tb2.setToggleGroup(group);
        tb2.setUserData(Color.LIGHTBLUE);
        ZoomingInOut tb3 = new ZoomingInOut("C");
        tb3.setToggleGroup(group);
        tb3.setUserData(Color.SALMON);
        

        HBox hbox = new HBox();

        hbox.getChildren().add(tb1);
        hbox.getChildren().add(tb2);
        hbox.getChildren().add(tb3);
        
        zoomingPane.zoomFactorProperty().bind(slider.valueProperty());
        primaryStage.setScene(new Scene(new BorderPane(zoomingPane, null, null, zoom, null)));
        webView.getEngine().load("http://www.google.com");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    private class ZoomingPane extends Pane {
        Node content;
        private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);

        private ZoomingPane(Node content) {
            this.content = content;
            getChildren().add(content);
            Scale scale = new Scale(1, 1);
            content.getTransforms().add(scale);

            zoomFactor.addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    scale.setX(newValue.doubleValue());
                    scale.setY(newValue.doubleValue());
                    requestLayout();
                }
            });
        }

        protected void layoutChildren() {
            Pos pos = Pos.TOP_LEFT;
            double width = getWidth();
            double height = getHeight();
            double top = getInsets().getTop();
            double right = getInsets().getRight();
            double left = getInsets().getLeft();
            double bottom = getInsets().getBottom();
            double contentWidth = (width - left - right)/zoomFactor.get();
            double contentHeight = (height - top - bottom)/zoomFactor.get();
            layoutInArea(content, left, top,
                    contentWidth, contentHeight,
                    0, null,
                    pos.getHpos(),
                    pos.getVpos());
        }

        public final Double getZoomFactor() {
            return zoomFactor.get();
        }
        public final void setZoomFactor(Double zoomFactor) {
            this.zoomFactor.set(zoomFactor);
        }
        public final DoubleProperty zoomFactorProperty() {
            return zoomFactor;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}