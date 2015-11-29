package DataAccess;

import java.io.InputStream;

import javafx.scene.image.Image;

public class AngledImage extends Image {

	String name;
	int angle = 0;
	int x = 0;
	int y = 0;
	double scaleX = 0;
	double scaleY = 0;
	public AngledImage(String url, String name) {
		super(url);
		this.name = name;
	}
	public void setAngle(int angle)
	{
		this.angle = angle;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public void setScaleX(double scaleX)
	{
		this.scaleX = scaleX;
	}
	public void setScaleY(double scaleY)
	{
		this.scaleY = scaleY;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public int getAngle()
	{
		return this.angle;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public double getScaleX()
	{
		return this.scaleX;
	}
	public double getScaleY()
	{
		return this.scaleY;
	}
}
