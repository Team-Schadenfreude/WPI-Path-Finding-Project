package GUI;

import javafx.scene.image.Image;

public class ImageProxy{
	
	private String url;
	private Image image;
	
	
	public ImageProxy(String url){
	   this.url = url;
	   this.image = new Image(url);
	}
	
	public ImageProxy(String url, double width, double height, boolean preserveRatio, boolean smooth) {
		this.url = url;
		this.image = new Image(url, width, height, preserveRatio, smooth);
	}
	
	public ImageProxy(String url, double width, double height, boolean preserveRatio, boolean smooth, boolean backgroundLoading) {
		this.url = url;
		this.image = new Image(url, width, height, preserveRatio, smooth, backgroundLoading);
	}
	
	public String getPath() {
		return this.url;
	}
	
	public double getWidth(double width, double height) {
		return this.image.getWidth();
	}
	
	public double getHeight(double width, double height) {
		return this.image.getHeight();
	}

}
