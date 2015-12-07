package GUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import DataAccess.Building;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class PopUpBuildInfo extends Application {
	static HashMap<String,Building> buildings = new HashMap<String,Building>();
	public static void main(String[] args) {
		getBuildingsInfoFromFile("src/res/BuildingInfo.csv");
		launch(args);
		System.out.println(buildings.size());
	}

	private static void getBuildingsInfoFromFile(String filePath) {
		
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		String name;
		String desc;
		String times;

		try {

			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] bInfo = line.split(delimiter);
				name = bInfo[0];
				desc = bInfo[1];
				times = bInfo[2];
				Building b = new Building(name, desc, times);
				buildings.put(name,b);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Map");
		stage.setWidth(500);
		stage.setHeight(500);
		//taking higgins house as an example
		Building higgins = buildings.get("Higgins House");
		String buildingName =higgins.getName()+"\n\n";
		String buildingDesc = higgins.getDesc()+"\n\n";
		String buildingTimes = higgins.getTimes()+"\n";
		//black Rectangle symbolizes the higgins house on the map
		Rectangle map_higgins = new Rectangle(0, 0, 100, 100);
		Tooltip t = new Tooltip(buildingName + buildingDesc + buildingTimes);
		Tooltip.install(map_higgins, t);
		((Group) scene.getRoot()).getChildren().add(map_higgins);

		stage.setScene(scene);
		stage.show();
	}
}
