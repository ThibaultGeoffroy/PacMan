/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import static java.awt.Color.black;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JavaFXApplication3 extends Application {

    public class Case {

        private int value;

        public Case(int valueset) {
            this.value = valueset;
        }

        public void setValue(int valuetoset) {
            this.value = valuetoset;
        }

        public int getValue() {
            return this.value;
        }
    }
    final int LENGTH_X = 3;
    final int LENGTH_Y = 3;
    public Case[][] Gmap = new Case[LENGTH_X][LENGTH_Y];

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pacman map test");

        initMapArray();
        //   myPane.getChildren().add(getGrid());
        //   GridPane myPane = getGrid();   
        GridPane grid = getGrid();

        scene.setRoot(grid);
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private int getNbCol(GridPane pane) {
        int CptCol = pane.getColumnConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowId = GridPane.getColumnIndex(child);
                if (rowId != null) {
                    CptCol = Math.max(CptCol, rowId + 1);
                }
            }
        }
        return CptCol;
    }

    private int getNbRow(GridPane pane) {
        int CptRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowId = GridPane.getRowIndex(child);
                if (rowId != null) {
                    CptRows = Math.max(CptRows, rowId + 1);
                }
            }
        }
        return CptRows;
    }

    private GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(3);
        grid.setHgap(3);

        final Image jelly = new Image(getClass().getResourceAsStream("Jellyfish.jpg"));
        final ImageView jell = new ImageView();
        jell.setImage(jelly);

        //Pane wall = new Pane();
        //wall.setStyle("-fx-background-color: blue");
        Pane pictureRegion = new Pane();
        pictureRegion.getChildren().add(jell);
        for (int i = 0; i < LENGTH_X; i++) {        
                RowConstraints con = new RowConstraints();
                con.setPrefHeight(20);
                grid.getRowConstraints().add(con);
            }
        for (int x = 0; x < LENGTH_Y; x++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPrefWidth(20);
            grid.getColumnConstraints().add(col);
        }
        for (int i = 0; i < getNbRow(grid); i++) {
            for (int x = 0; x < getNbCol(grid); x++) {
                if (Gmap[i][x].getValue() == 1) {
                    grid.setConstraints(pictureRegion, i, x);
                    grid.getChildren().add(pictureRegion);
                   
                }
            }
        }
        return grid;
    }

    private void initMapArray() {
        for (int i = 0; i < LENGTH_X; i++) {
            for (int x = 0; x < LENGTH_Y; x++) {
                Gmap[i][x] = new Case((int) (Math.random() * 2));
            }
        }
    }
}
