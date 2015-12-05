package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class InfoPanel {
	
	public static void setUpInfoPanel(GridPane grid, String name) {

		grid.getChildren().clear();
		grid.setStyle("-fx-background-color: rgba(51, 76, 93, 0.7);");
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
        grid.setHgap(5);
		
		Label label_name = new Label(name);
		label_name.setTextFill(Color.web("#FFFFF0"));
		label_name.setStyle("-fx-font-weight: bolder; -fx-font-size: 24px;");
		grid.add(label_name, 0, 0, 5, 1);
		
		File file1 = new File("src/res/" + name +".png");
		System.out.println(file1.getAbsolutePath());;
		if(file1.exists() && !file1.isDirectory()) {
			System.out.println(file1.getPath());
			Image image = new Image(file1.toURI().toString(), 300, 300, true, true);
			ImageView imageview = new ImageView(image);
			grid.add(imageview, 0, 1, 5, 3);
		}
		
		
		File file2 = new File("src/res/" + name +".csv");
		if(file2.exists() && !file2.isDirectory()) {
			System.out.println(file2.getPath());
			String buffer = "";
			try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(file2.getPath());

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            buffer = bufferedReader.readLine();

	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(IOException ex) {}
			
			if(buffer != null && !buffer.isEmpty()) {
				String[] parts = buffer.split(",");
				String info = "Description: \n     " + parts[1] + " \n" +
								"Opening Hours: \n     " + parts[2] + " \n" +
									"Men's Bath Rooms Located at: \n     " + parts[3] + " \n" +
										"Women's Bath Rooms Located at: \n     " + parts[4] + " \n" + 
											"Vending Machines Located at: \n     " + parts[5] + " \n";
				System.out.println(info);
				Label label_info = new Label(info);
				label_info.setTextFill(Color.web("#FFFFF0"));
				label_info.setStyle("-fx-font-weight: bolder; -fx-font-family: Optima; -fx-font-size: 18px;");
				grid.add(label_info, 0, 4, 5, 1);
			}
	    }
	}


}
