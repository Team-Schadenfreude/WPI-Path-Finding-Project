package GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MainController {
    @FXML private Text actiontarget;
    @FXML private Button loadMapBtn;
    @FXML private ImageView mapView;
    public MainController(){
    	
    }
    @FXML
    private void intialize()
    {
    	
    }
    @FXML 
    protected void handleLoadMap(ActionEvent event) {
        System.out.println("ClickedTheButton");
        loadMapBtn.setText("YouClickedMe");
        Image mapImage = new Image("stratton_2.png");
        mapView.setImage(mapImage);
    }

}