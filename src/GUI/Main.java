package GUI;
 
import java.io.IOException;

import javafx.animation.FadeTransition;

//import com.svg.fx.SvgImageLoaderFactory;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
 
public class Main extends Application {
	public static final String APPLICATION_ICON =
	            "/res/icons/window_icon.png";
    public static final String SPLASH_IMAGE =
            "/res/icons/preloader.png";
    private Image preloaderImage = new Image(SPLASH_IMAGE);
    private ImageView splash = new ImageView(preloaderImage);
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
	private static Stage mainStage;
	
    public static void main(String[] args) {
    	//SvgImageLoaderFactory.install();
        launch(args);
    }

    
    @Override
    public void init() {
    	splash.setFitWidth(960);
    	splash.setFitHeight(540);
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(splash.getFitWidth() - 20);
        progressText = new Label("Will find friends for peanuts . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                "-fx-background-color: cornsilk; " +
                "-fx-border-width:5; " +
                "-fx-border-color: " +
                    "linear-gradient(" +
                        "to bottom, " +
                        "chocolate, " +
                        "derive(chocolate, 50%)" +
                    ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        final Task<ObservableList<String>> friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> foundFriends = FXCollections.<String>observableArrayList();
                MainController mc = new MainController();
                mc.loadMap();
                Thread.sleep(400);
                updateMessage("Done Loading");

                return foundFriends;
            }
        };
        Image image = new Image(APPLICATION_ICON);
        initStage.getIcons().add(image);
        showSplash(
                initStage,
                friendTask,
                () -> showMainStage(friendTask.valueProperty())
        );
        new Thread(friendTask).start();
    }

    private void showMainStage(
            ReadOnlyObjectProperty<ObservableList<String>> friends
    ) {
    	mainStage = new Stage();
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(Main.class.getResource("Main.fxml"));
        AnchorPane root = new AnchorPane();
		try {
			root = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Image image = new Image(APPLICATION_ICON);
        mainStage.getIcons().add(image);
        mainStage.setMaximized(true);
       // mainStage.setF
        Scene scene = new Scene(root, root.getWidth(), root.getHeight());
        
        scene.getStylesheets().add("GUI/application.css");
        mainStage.setTitle("Randy Path Planner");
        mainStage.setScene(scene);
       
        mainStage.show();
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - splash.getFitWidth() / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - splash.getFitHeight() / 2);
        initStage.show();
    }

    public interface InitCompletionHandler {
        public void complete();
    }
}
