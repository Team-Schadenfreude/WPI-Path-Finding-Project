package GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class MainController {
    @FXML private Text actiontarget;
    @FXML private Button loadMapBtn;
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
    }

}