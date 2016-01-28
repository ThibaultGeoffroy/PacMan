/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    final int LENGTH_X = 22; //row
    final int LENGTH_Y = 14; //column
    final int MAX_PIXEL = 50;
    final int MIN_PIXEL = 50;
    final int PREF_PIXEL = 50;
    public Case[][] Gmap = new Case[LENGTH_Y][LENGTH_X];

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pacman map test");
        initMapArray(); 
        GridPane grid = getGrid();
        StackPane st = new StackPane();
        st.getChildren().add(grid);
        scene.setRoot(st);
        scene.setFill(Color.BLACK);
        root.getChildren().add(st);
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
        grid.setVgap(0);
        grid.setHgap(0);
        grid.setGridLinesVisible(true);
        //Pane wall = new Pane();
        //wall.setStyle("-fx-background-color: blue");

        for (int i = 0; i < LENGTH_X; i++) {
            RowConstraints con = new RowConstraints();
            con.setMaxHeight(MAX_PIXEL);
            con.setMinHeight(MIN_PIXEL);
            con.setPrefHeight(PREF_PIXEL);
            grid.getRowConstraints().add(con);
        }
        for (int x = 0; x < LENGTH_Y; x++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMaxWidth(MAX_PIXEL);
            col.setMinWidth(MIN_PIXEL);
            col.setPrefWidth(PREF_PIXEL);
            grid.getColumnConstraints().add(col);
        }
        for (int i = 0; i < getNbCol(grid); i++) {
            for (int x = 0; x < getNbRow(grid); x++) {
                // Possibilité methode pour éviter duplication
                if (Gmap[i][x].getValue() == 0) {
                    final Image koala = new Image(getClass().getResourceAsStream("Koala.jpg"));
                    final ImageView koal = new ImageView();
                    koal.setImage(koala);
                    Pane pictureRegion = new Pane();
                    koal.fitWidthProperty().bind(pictureRegion.widthProperty());
                    koal.fitHeightProperty().bind(pictureRegion.heightProperty());
                    pictureRegion.getChildren().add(koal);                 
                    grid.setConstraints(pictureRegion, i, x);
                    grid.add(pictureRegion, i, x);
                }
                
                if (Gmap[i][x].getValue() == 1) {
                    final Image jelly = new Image(getClass().getResourceAsStream("Jellyfish.jpg"));
                    final ImageView jell = new ImageView();
                    jell.setImage(jelly);
                    Pane pictureRegion = new Pane();
                    jell.fitWidthProperty().bind(pictureRegion.widthProperty());
                    jell.fitHeightProperty().bind(pictureRegion.heightProperty());
                    pictureRegion.getChildren().add(jell);
                    grid.setConstraints(pictureRegion, i, x);
                    grid.add(pictureRegion, i, x);

                }
            }
        }
        return grid;
    }

    private void initMapArray() {
        for (int i = 0; i < LENGTH_Y; i++) {
            for (int x = 0; x < LENGTH_X; x++) {
                Gmap[i][x] = new Case((int) (Math.random() * 2));
            }
        }
    }
}
