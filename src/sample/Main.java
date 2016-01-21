package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {



    public void gameStage(Stage primaryStage){
        Label game = new Label("JEU");
        StackPane root = new StackPane();
        root.getChildren().add(game);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void start(Stage primaryStage) throws Exception{
        Button btn = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        btn.setText("<");
        btn2.setText("^");
        btn3.setText(">");

        Circle c = new Circle();

        c.setCenterX(100);
        c.setCenterY(100);
        c.setRadius(30);
        c.setFill(Color.BLACK);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c.setCenterX(c.getCenterX() - 10);
                primaryStage.show();
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c.setCenterY(c.getCenterY() - 10);
                primaryStage.show();
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                c.setCenterX(c.getCenterX() + 10);
                primaryStage.show();
            }
        });
        StackPane root = new StackPane();
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setHgap(10);
        grid.add(btn , 0, 1);
        grid.add(btn2 , 1, 1);
        grid.add(btn3 , 2, 1);
        root.getChildren().add(grid);
        root.getChildren().add(c);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);

    }


    public static void main(String[] args) {
        launch(args);
    }
}