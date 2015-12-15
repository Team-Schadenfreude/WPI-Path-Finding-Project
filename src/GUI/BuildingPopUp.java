package GUI;

import DataAccess.Building;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BuildingPopUp {
	private static VBox vbox =  new VBox();

	public static VBox getPopUp()
	{
		return vbox;
	}
	public static void setPopUp(VBox newVBox)
	{
		vbox = newVBox;
	}
	public static void setupPopUp(Building b)
	{
		StackPane imgContainer = new StackPane();
		System.out.println(b.getId());
		String bName =b.getId();
		ImageView image = new ImageView();
		Slideshow slideshow;
		ImageReader imageReader;
		switch(bName){


		case "AtwaterKent":
			imgContainer.setMaxSize(210, 200);

			slideshow = new Slideshow("res/BuildingImages/Atwater/");
			imgContainer.getChildren().addAll(slideshow.slides);
			image.getStyleClass().add("image_display");
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Atwater/Atwater.html","res/BuildingImages/Atwater");
			
			break;
			
		case "Fuller Labs":
			slideshow = new Slideshow("res/BuildingImages/Fuller/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Fuller/Fuller.html","res/BuildingImages/Fuller");
			image.getStyleClass().add("image_display");
			break;
			
		case "CampusCenter":
			slideshow = new Slideshow("res/BuildingImages/Center/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Center/Center.html","res/BuildingImages/Center");
			image.getStyleClass().add("image_display");
			break;
			
		case "Boynton Hall":
			slideshow = new Slideshow("res/BuildingImages/Boynton/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Boynton/Boynton.html","res/BuildingImages/Boynton");

			image.getStyleClass().add("image_display");
			break;
			
		case "Salisbury":
			slideshow = new Slideshow("res/BuildingImages/Salisbury/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Salisbury/Salisbury.html","res/BuildingImages/Salisbury");
			image.getStyleClass().add("image_display");
			break;
			
		case "Project Center":
			slideshow = new Slideshow("res/BuildingImages/Project Center/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/ProjectCenter/Project Center.html","res/BuildingImages/Project Center");
			image.getStyleClass().add("image_display");
			break;
			
		case "Library":
			slideshow = new Slideshow("res/BuildingImages/Library/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Library/Library.html","res/BuildingImages/Library");
			image.getStyleClass().add("image_display");
			break;

		case "Stratton":
			slideshow = new Slideshow("res/BuildingImages/Stratton/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Stratton/Stratton.html","res/BuildingImages/Stratton");
			image.getStyleClass().add("image_display");
			break;
			
			
			
			
		case "Higgins House":
			slideshow = new Slideshow("res/BuildingImages/Higgins House/");
			imgContainer.getChildren().addAll(slideshow.slides);
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Salisbury/HigginsHouse.html","res/BuildingImages/Higgins House");
			image.getStyleClass().add("image_display");
			break;
			
			
		default:
			System.out.println("default");
		}
		vbox.getChildren().clear();

		if (!bName.equals("Campus"))
		{
			Label name = new Label(b.getId());
			Label description =  new Label(b.getDescription());
			description.setWrapText(true);
			vbox.getChildren().add(name);
			vbox.getChildren().add(imgContainer);
		}
	}

}
