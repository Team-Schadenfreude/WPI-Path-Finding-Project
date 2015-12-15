package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
Alonso
*/
public class Slideshow {
	 ImageView[] slides;
	 
	 
	 
	 
	 public Slideshow(String path){
		 
		 File directory = new File(path);
		 System.out.println(directory.isDirectory());
		 int sizeOfFolder = directory.listFiles().length;
		 
		 this.slides = new ImageView[sizeOfFolder];
		 int i = 0;
		 BufferedImage image = null;
		 ImageView image2;
		 for(File file: directory.listFiles()){
			 System.out.println("Doing");
			    if(file.getName().toLowerCase().endsWith(".jpg")){
			    	System.out.println(file.getAbsolutePath());
			    	System.out.println(file);
			    	System.out.println(i);
			    	//image = new Image(file.getPath());
			    	try {
						image = ImageIO.read(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Image imageImg = SwingFXUtils.toFXImage(image, null);
					 System.out.println("Before IMageView");
			    	image2 = new ImageView();
			    	image2.setImage(imageImg);
			    	image2.setFitWidth(210);
			    	image2.setFitHeight(200);
					 System.out.println("After IMageView");
					
			    	this.slides[i] = image2;
			    	i++;
			    }

		 }
		 i= 0;
		 System.out.println("Out of loop");
		 playFiles();
	 }
	 
	 
	 
	 public void playFiles() {

	      SequentialTransition slideshow = new SequentialTransition();
	      
	      for (ImageView slide : slides) {

	        SequentialTransition sequentialTransition = new SequentialTransition();

	        FadeTransition fadeIn = getFadeTransition(slide, 0.0, 1.0, 2000);
	        PauseTransition stayOn = new PauseTransition(Duration.millis(2000));
	        FadeTransition fadeOut = getFadeTransition(slide, 1.0, 0.0, 2000);

	        sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
	        slide.setOpacity(0);
	        slideshow.getChildren().add(sequentialTransition);
	        
	      }
	    slideshow.setCycleCount(slideshow.INDEFINITE);
	    slideshow.play();
	 }

	 
	 public FadeTransition getFadeTransition(ImageView imageView, double fromValue, double toValue, int durationInMilliseconds) {

	      FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
	      ft.setFromValue(fromValue);
	      ft.setToValue(toValue);

	      return ft;

	    }
	  
}
