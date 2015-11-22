package GUI;

import java.util.ArrayList;
import java.util.List;

import AStar.Node;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ComboBoxSample extends Application {
        public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), 800, 200);

        final ComboBox startB = new ComboBox();
        final ComboBox startR = new ComboBox();
        final ComboBox endB = new ComboBox();
        final ComboBox endR = new ComboBox();
        final Button submit = new Button("Submit");
       
        
        Node n1 = new Node(0, 1, "Fuller Labs 101");
        Node n2 = new Node(0, 2, "Fuller Labs 102");
        Node n3 = new Node(0, 3, "Fuller Labs 103");
        Node n4 = new Node(0, 4, "Fuller Labs 104");
        Node n5 = new Node(0, 5, "Fuller Labs 105");
        Node n6 = new Node(0, 6, "Fuller Labs 106");
        Node n7 = new Node(0, 7, "Fuller Labs 107");
        Node n8 = new Node(0, 8, "Fuller Labs 108");
        Node n9 = new Node(1, 1, "Atwater Kent Labs 111");
        Node n10 = new Node(1, 2, "Atwater Kent Labs 112");
        Node n11 = new Node(1, 3, "Atwater Kent Labs 113");
        Node n12 = new Node(1, 4, "Atwater Kent Labs 114");
        Node n13 = new Node(1, 5, "Atwater Kent Labs 115");
        Node n14 = new Node(1, 6, "Atwater Kent Labs 116");
        Node n15 = new Node(1, 7, "Atwater Kent Labs 117");
        Node n16 = new Node(1, 8, "Atwater Kent Labs 118");
        
        List<Node> map = new ArrayList<Node>();
        map.add(n1);
        map.add(n2);
        map.add(n3);
        map.add(n4);
        map.add(n5);
        map.add(n6);
        map.add(n7);
        map.add(n8);
        map.add(n9);
        map.add(n10);
        map.add(n11);
        map.add(n12);
        map.add(n13);
        map.add(n14);
        map.add(n15);
        map.add(n16);
        
        
        DropDownMenu ddm = new DropDownMenu(map);
        ddm.setDropDownMenu(startB, startR, endB, endR, submit);
        
        

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Start Building: "), 0, 1);
        grid.add(startB, 1, 1);
        grid.add(new Label("Start Room: "), 2, 1);
        grid.add(startR, 3, 1);
        grid.add(new Label("End Building: "), 0, 2);
        grid.add(endB, 1, 2);
        grid.add(new Label("End Room: "), 2, 2);
        grid.add(endR, 3, 2);
        grid.add(submit, 0, 4);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();

    }
}
