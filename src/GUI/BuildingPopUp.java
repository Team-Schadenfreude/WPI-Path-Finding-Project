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
		String path;
		switch(bName){


		case "AtwaterKent":
			path = "res/BuildingImages/Atwater Kent/";
			imgContainer.setMaxSize(210, 200);
			
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			image.getStyleClass().add("image_display");
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Atwater/Atwater.html",path);
			
			break;
			
		case "Fuller Labs":
			path = "res/BuildingImages/Fuller Labs/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Fuller/Fuller.html",path);
			image.getStyleClass().add("image_display");
			break;
			
		case "CampusCenter":
			path = "res/BuildingImages/Campus Center/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Center/Center.html",path);
			image.getStyleClass().add("image_display");
			break;
			
		case "Boynton Hall":
			path = "res/BuildingImages/Boynton Hall/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Boynton/Boynton.html",path);

			image.getStyleClass().add("image_display");
			break;
			
		case "Salisbury":
			path = "res/BuildingImages/Salisbury/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Salisbury/Salisbury.html",path);
			image.getStyleClass().add("image_display");
			break;
			
		case "Project Center":
			path = "res/BuildingImages/Project Center/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/ProjectCenter/ProjectCenter.html",path);
			image.getStyleClass().add("image_display");
			break;
			
		case "Library":
			path = "res/BuildingImages/Library/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Library/Library.html",path);
			image.getStyleClass().add("image_display");
			break;

		case "Stratton":
			path = "res/BuildingImages/Stratton/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/Stratton/Stratton.html",path);
			image.getStyleClass().add("image_display");
			break;
			
			
			
			
		case "Higgins House":
			path = "res/BuildingImages/Higgins House/";
			slideshow = new Slideshow(path);
			imgContainer.getChildren().addAll(slideshow.getSlides());
			imageReader = new ImageReader("http://greatestsoftengteamschadenfreude.comxa.com/HigginsHouse/HigginsHouse.html",path);
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
