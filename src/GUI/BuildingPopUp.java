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
		
		String bName =b.getName();
		ImageView image = new ImageView();
		switch(bName){
		
		case "AtwaterKent":
			Image image2 = new Image("/res/BuildingImages/AtwaterKent.jpg",210,200,true,true);
			image.setImage(image2);
			break;
			
			
		case "FullerLabs":
			Image image3 = new Image("/res/BuildingImages/FullerLabs.jpg",210,200,true,true);
			image.setImage(image3);
			break;
			
		case "CampusCenter":
			Image image4 = new Image("/res/BuildingImages/FullerLabs.jpg",210,200,true,true);
			image.setImage(image4);
			break;
			
		case "BoyntonHall":
			Image image5 = new Image("/res/BuildingImages/FullerLabs.jpg",210,200,true,true);
			image.setImage(image5);
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
		vbox.getChildren().add(hours);
		vbox.getChildren().add(description);
		image.setFitWidth(vbox.getWidth());
		vbox.getChildren().add(image);
	}

}
