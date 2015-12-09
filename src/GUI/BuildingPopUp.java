package GUI;

import DataAccess.Building;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		
		System.out.println(b.getName());
		String bName =b.getName();
		ImageView image = new ImageView();
		switch(bName){
		
		case "AtwaterKent":
			Image image2 = new Image("/res/BuildingImages/AtwaterKent.jpg",210,200,true,true);
			image.setImage(image2);
			image.getStyleClass().add("image_display");
			break;
			
		case "Fuller Labs":
			Image image3 = new Image("/res/BuildingImages/FullerLabs.jpg",210,200,true,true);
			image.setImage(image3);
			image.getStyleClass().add("image_display");
			break;
			
		case "CampusCenter":
			Image image4 = new Image("/res/BuildingImages/CampusCenter.jpg",210,200,true,true);
			image.setImage(image4);
			image.getStyleClass().add("image_display");
			break;
			
		case "Boynton Hall":
			Image image5 = new Image("/res/BuildingImages/BoyntonHall.jpg",210,200,true,true);
			image.setImage(image5);
			image.getStyleClass().add("image_display");
			break;
			
		case "Salisbury":
			Image image6 = new Image("/res/BuildingImages/SalisburyLabs.jpg",210,200,true,true);
			image.setImage(image6);
			image.getStyleClass().add("image_display");
			break;
			
		case "Project Center":
			Image image7 = new Image("/res/BuildingImages/ProjectCenter.jpg",210,200,true,true);
			image.setImage(image7);
			image.getStyleClass().add("image_display");
			break;
			
		case "Library":
			Image image8 = new Image("/res/BuildingImages/GordonLibrary.jpg",210,200,true,true);
			image.setImage(image8);
			image.getStyleClass().add("image_display");
			break;

		case "Stratton":
			Image image9 = new Image("/res/BuildingImages/StrattonHall.jpg",210,200,true,true);
			image.setImage(image9);
			image.getStyleClass().add("image_display");
			break;
		default:
			System.out.println("default");
		}
		vbox.getChildren().clear();
		Label name = new Label(b.getName());
		Label description =  new Label(b.getDescription());
		Label hours = new Label(b.getHours());
		description.setWrapText(true);
		vbox.getChildren().add(name);
	//	vbox.getChildren().add(hours);
		//vbox.getChildren().add(description);
		image.setFitWidth(vbox.getWidth());
		vbox.getChildren().add(image);
	}

}
