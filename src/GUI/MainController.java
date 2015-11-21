package GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainController {
    @FXML private Text actiontarget;

    public MainController(){
    	
    }
    @FXML
    private void intialize()
    {
    	
    }
    @FXML 
    protected void handleLoadMap(ActionEvent event) {
        System.out.println("ClickedTheButton");
    }

}