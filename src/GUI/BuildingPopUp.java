package GUI;

import DataAccess.Building;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

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
		vbox.getChildren().clear();
		Label name = new Label(b.getName());
		Label description =  new Label(b.getDescription());
		Label hours = new Label(b.getHours());
		description.setWrapText(true);
		vbox.getChildren().add(name);
		vbox.getChildren().add(hours);
		vbox.getChildren().add(description);
	}

}
