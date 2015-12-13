package DataAccess;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public abstract class MapComponent extends Group {
	//ImageViewIndex = 0
	//CanvasIndex = 1
	private final static int imageViewIndex = 0;
	private final static int canvasIndex = 1;
	private final static int translateIndex = 0;
	private final static int scaleIndex = 1;
	private final static int rotateIndex = 2;

	public MapComponent() {
		super();
		initChildren();
		initTransforms();
	}
	
	final private void initChildren()
	{
		this.getChildren().clear();
		this.getChildren().add(new ImageView());
		this.getChildren().add(new Canvas());
	}
	final private void initTransforms()
	{
		this.getTransforms().clear();
		this.getTransforms().add(new Translate(0,0));
		this.getTransforms().add(new Scale(1,1));
		this.getTransforms().add(new Rotate(0));
	}
	//Setters
	final public void setCanvas(Canvas c)
	{
		this.getChildren().set(canvasIndex, c);
	}
	final public void setImageView(ImageView iv)
	{
		this.getChildren().set(imageViewIndex, iv);
	}
	final public void setScale(double x, double y)
	{
		this.getScale().setX(x);
		this.getScale().setY(y);
	}
	final public void setRotateAngle(double angle)
	{
		this.getRotation().setAngle(angle);
	}
	final public void setTranslate(double x, double y)
	{
		this.getTranslate().setX(x);
		this.getTranslate().setY(y);
	}
	//Getters
	final public Canvas getCanvas()
	{
		return (Canvas)this.getChildren().get(canvasIndex);
	}
	final public ImageView getImageView()
	{
		return (ImageView) this.getChildren().get(imageViewIndex);
	}
	final public Scale getScale()
	{
		return (Scale)this.getTransforms().get(scaleIndex);
	}
	final public Rotate getRotation()
	{
		return (Rotate)this.getTransforms().get(rotateIndex);
	}
	final public double getAngle()
	{
		return this.getRotation().getAngle();
	}
	final public Translate getTranslate()
	{
		return (Translate)this.getTransforms().get(translateIndex);
	}
	public void setBaseImage(Image image)
	{
		this.getImageView().setImage(image);
		this.getCanvas().setWidth(image.getWidth());
		this.getCanvas().setHeight(image.getHeight());
//		this.getScale().setPivotX(image.getWidth() / 2);
//		this.getScale().setPivotY(image.getHeight() / 2);
//		this.getRotation().setPivotX(image.getWidth() / 2);
//		this.getRotation()
	}
	
}
