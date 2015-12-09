package GUI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;


public class ZoomingPane extends Pane {
    Node content;
    private double zoomFactor = 1;
    Scale scale;
    ZoomingPane(Node content) {
        this.content = content;
        getChildren().add(content);
        scale = new Scale(1, 1, content.getLayoutX(), content.getLayoutY());
        content.getTransforms().add(scale);
    }

    protected void layoutChildren() {
        Pos pos = Pos.CENTER;
        double width = getWidth();
        double height = getHeight();
        double top = getInsets().getTop();
        double right = getInsets().getRight();
        double left = getInsets().getLeft();
        double bottom = getInsets().getBottom();
        double contentWidth = (width - left - right)/zoomFactor;
        double contentHeight = (height - top - bottom)/zoomFactor;
        layoutInArea(content, left, top,
                contentWidth, contentHeight,
                0, null,
                pos.getHpos(),
                pos.getVpos());
    }

    public double getZoomFactor() {
        return zoomFactor;
    }
    public void setZoomFactor(double zoomFactor) {
    	this.zoomFactor = zoomFactor;
    	scale.setX(zoomFactor);
        scale.setY(zoomFactor);
        setMinSize(content.getBoundsInLocal().getWidth() * scale.getX(), content.getBoundsInLocal().getHeight() * scale.getY());
        requestLayout();
    }
    public void setPivot(double pivotX, double pivotY)
    {
    	this.scale.setPivotX(pivotX);
    	this.scale.setPivotY(pivotY);
    }
}
