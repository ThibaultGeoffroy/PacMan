/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.BufferedReader;
import java.io.FileReader;
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

/**
 *
 * @author Antoine
 */
public class JavaFXApplication3 extends Application {

    /**
     * Objet case stocker dans la Gmap
     */
    public class Case {

        private int value;

        /**
         * Constructeur de l'objet Case
         * @param valueset valeur de la case
         */
        public Case(int valueset) {
            this.value = valueset;
        }

        /**
         * Setter de l'objet Case
         * @param valuetoset valeur de la case 
         */
        public void setValue(int valuetoset) {
            this.value = valuetoset;
        }

        /**
         * Getter de l'objet Case 
         * @return valeur de la case
         */
        public int getValue() {
            return this.value;
        }
    }

    final int LENGTH_X = 5; //row
    final int LENGTH_Y = 8; //column
    final int MAX_PIXEL = 50;
    final int MIN_PIXEL = 50;
    final int PREF_PIXEL = 50;

    /**
     * Game Map, contient des cases avec les valeurs respectivent du tableau de 0/1 fourni par fichier txt
     */
    public Case[][] Gmap = new Case[LENGTH_X][LENGTH_Y];

    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pacman map test");
        initMapArray("terrain.txt");
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
    
    /**
     * Renvoi le nombre de colonne d'une gridpane, permet de vérifié la bonne création de la gridpane
     * @param pane gridpane à tester
     * @return nombre de colonne
     */
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
    
    /**
     * Renvoi le nombre de rangée d'une gridpane, permet de vérifié la bonne création de la gridpane
     * @param pane gridpane à tester
     * @return nombre de rangée
     */
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

    /**
     * Constructeur de la grille, initialise la grille, la remplie en fonction de Gmap
     * @return grille complétée
     */
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

        int NbRow = getNbRow(grid);
        int NbCol = getNbCol(grid);
        for (int i = 0; i < NbRow; i++) {
            for (int x = 0; x < NbCol; x++) {
                if (Gmap[i][x].getValue() == 0) {
                    Pane pictureRegion = getPictureRegion("empty.png");
                    grid.setConstraints(pictureRegion, x, i);
                    grid.add(pictureRegion, x, i);
                }
                if (Gmap[i][x].getValue() == 1) {
                    Pane pictureRegion = null;
                    if (isCorner(i, x)) {
                        if (isCornerBD(i, x)) {
                            pictureRegion = getPictureRegion("CornerBD.png");
                        } else {
                            if (isCornerHD(i, x)) {
                                pictureRegion = getPictureRegion("CornerHD.png");
                            } else {
                                if (isCornerBG(i, x)) {
                                    pictureRegion = getPictureRegion("CornerBG.png");
                                } else {
                                    if (isCornerHG(i, x)) {
                                        pictureRegion = getPictureRegion("CornerHG.png");
                                    }
                                }
                            }
                        }
                    } else if (isIntersection3(i, x)) {
                        if (isCornerHG(i, x) && isLineV(i, x)) {
                            pictureRegion = getPictureRegion("Inter3VerticalD.png");
                        } else {
                            if (isCornerBD(i, x) && isLineV(i, x)) {
                                pictureRegion = getPictureRegion("Inter3VerticalG.png");
                            } else {
                                if (isCornerHG(i, x) && isLineH(i, x)) {
                                    pictureRegion = getPictureRegion("Inter3HorizontalB.png");
                                } else {
                                    if (isCornerBD(i, x) && isLineH(i, x)) {
                                        pictureRegion = getPictureRegion("Inter3HorizontalH.png");
                                    }
                                }
                            }
                        }
                    } else if (isIntersection4(i, x)) {
                        pictureRegion = getPictureRegion("Inter4.png");
                    } else if (isLineH(i, x)) {
                        pictureRegion = getPictureRegion("LineHorizontal.png");
                    } else if (isLineV(i, x)) {
                        pictureRegion = getPictureRegion("LineVertical.png");
                    }/*else if (isEndLine(i, x)) {
                        pictureRegion = getPictureRegion(".jpg");
                    }
                     */
                    if (pictureRegion == null) {
                        pictureRegion = getPictureRegion("LineHorizontal.png");
                    }
                    grid.setConstraints(pictureRegion, x, i);
                    grid.add(pictureRegion, x, i);
                }
            }
        }
        return grid;
    }

    /**
     * Compte le nombre de 1 qui se situe sur les cases voisines (non située en diagonale/ en forme de + en partant de i,x) des coordonées fourni en paramètre
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return nombre de 1 autour de i,x
     */
    private int checkWall(int i, int x) {
        int cmpt = 0;
        for (int Subi = -1; Subi < 2; Subi++) {
            for (int Subx = -1; Subx < 2; Subx++) {
                // Exclusion des combinaison interdite ( diagonale )
                if ((Subi != Subx) && (Subi != Subx * -1)) {
                    // Test pour éviter sortie du tableau
                    if (((i + Subi > -1) && (i + Subi < LENGTH_X)) && ((x + Subx > -1) && (x + Subx < LENGTH_Y))) {
                        if (Gmap[i + Subi][x + Subx].getValue() == 1) {
                            cmpt++;
                        }
                    }
                }
            }
        }

        return cmpt;
    }
    
    /**
     * Teste si la case de coordonnée i,x dois etre un "Coin Haut Gauche"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Coin Haut Gauche, faux sinon
     */
    private boolean isCornerHG(int i, int x) {
        boolean CornerHG = false;
        if (((i + 1 < LENGTH_X) && (x + 1 < LENGTH_Y))) {
            if ((Gmap[i + 1][x].getValue() == 1) && (Gmap[i][x + 1].getValue() == 1)) {
                CornerHG = true;
            }
        }
        return CornerHG;
    }
    
    /**
     * Teste si la case de coordonnée i,x dois etre un "Coin Haut Droit"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Coin Haut Droit, faux sinon
     */
    private boolean isCornerHD(int i, int x) {
        boolean CornerHD = false;
        if (((i + 1 < LENGTH_X) && (x - 1 > -1))) {
            if ((Gmap[i + 1][x].getValue() == 1) && (Gmap[i][x - 1].getValue() == 1)) {
                CornerHD = true;
            }
        }
        return CornerHD;
    }
    
    /**
     * Teste si la case de coordonnée i,x dois etre un "Coin Bas Droit"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Coin Bas Droit, faux sinon
     */
    private boolean isCornerBD(int i, int x) {
        boolean CornerBD = false;
        if (((i - 1 > -1) && (x - 1 > -1))) {
            if ((Gmap[i - 1][x].getValue() == 1) && (Gmap[i][x - 1].getValue() == 1)) {
                CornerBD = true;
            }
        }
        return CornerBD;
    }
    
    /**
     * Teste si la case de coordonnée i,x dois etre un "Coin Bas Gauche"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Coin Bas Gauche, faux sinon
     */
    private boolean isCornerBG(int i, int x) {
        boolean CornerBG = false;
        if (((i - 1 > -1) && (x + 1 < LENGTH_Y))) {
            if ((Gmap[i - 1][x].getValue() == 1) && (Gmap[i][x + 1].getValue() == 1)) {
                CornerBG = true;
            }
        }
        return CornerBG;
    }

    /**
     * Teste si la case de coordonnée i,x dois etre une "Line Horizontal"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Line Horizontal, faux sinon
     */
    private boolean isLineH(int i, int x) {
        boolean LineHorizontal = false;
        if (((x - 1 > -1) && (x + 1 < LENGTH_Y))) {
            if ((Gmap[i][x - 1].getValue() == 1) && (Gmap[i][x + 1].getValue() == 1)) {
                LineHorizontal = true;
            }
        }
        return LineHorizontal;
    }
    
    /**
     * Teste si la case de coordonnée i,x dois etre une "Line Vertical"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Line Vertical, faux sinon
     */
    private boolean isLineV(int i, int x) {
        boolean LineVertical = false;
        if (((i - 1 > -1) && (i + 1 < LENGTH_X))) {
            if ((Gmap[i - 1][x].getValue() == 1) && (Gmap[i + 1][x].getValue() == 1)) {
                LineVertical = true;
            }
        }
        return LineVertical;
    }
    
    /**
     * Teste si la case de coordonnée i,x est un Coin, sans précisé son type"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Coin, faux sinon
     */
    private boolean isCorner(int i, int x) {
        return checkWall(i, x) == 2 && !isLineV(i, x) && !isLineH(i, x);
    }

    /**
     * Teste si la case de coordonnée i,x est une intersection de 3 ligne, sans précisé son type, on le calculera en combinant des tests de coin et de ligne"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si Intersection 3, faux sinon
     */
    private boolean isIntersection3(int i, int x) {
        return checkWall(i, x) == 3;
    }

    /**
     * Teste si la case de coordonnée i,x est une intersection de 4 ligne, il n'y a qu'un seul type possible"
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si intersection 4, faux sinon
     */
    private boolean isIntersection4(int i, int x) {
        return checkWall(i, x) == 4;
    }

    /**
     * Teste si la case de coordonnée i,x est une "fin de ligne" sans précisé son type" A IMPLEMENTER 4 DIRECTION POSSIBLE DE ENDLINE
     * @param i coordonnée rangée
     * @param x coordonnée colonne
     * @return vrai si "fin de ligne", faux sinon
     */
    private boolean isEndLine(int i, int x) {
        return checkWall(i, x) == 1;
    }

    /**
     * Construit une pane contenant l'image fourni en paramètre pour insertion dans la gridpane
     * @param file path de l'image
     * @return pane contenant l'image file
     */
    private Pane getPictureRegion(String file) {
        final Image img = new Image(getClass().getResourceAsStream(file));
        final ImageView imgV = new ImageView();
        imgV.setImage(img);
        Pane pictureRegion = new Pane();
        imgV.fitWidthProperty().bind(pictureRegion.widthProperty());
        imgV.fitHeightProperty().bind(pictureRegion.heightProperty());
        pictureRegion.getChildren().add(imgV);
        return pictureRegion;
    }
    
    /**
     * Construit les cases a l'intérieur de Gmap, possibilité d'initialisé gmap ici, et vérifie le format du fichier txt.
     * @param file fichier contenant un "tableau" de 0 et de 1
     * @throws IOException si FileNotFound
     */
    private void initMapArray(String file) throws IOException {
        if (testFileFormat(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    for (int index = 0; index < line.length(); index++) {
                        char aChar = line.charAt(index);
                        Gmap[i][index] = new Case(((int) (aChar)) - 48);
                    }
                    i++;
                }
                br.close();
            }
        }
    }

    /**
     * Teste le format du fichier txt, les rangs du fichiers doivent tous etre de meme longueur (ie: toute les rangées ont le meme nombre de colonnes)
     * @param file fichier txt à testé
     * @return boolean vrai si fichier valide, faux sinon.
     * @throws IOException si FileNotFound
     */
    private boolean testFileFormat(String file) throws IOException {
        boolean res = false;
        int cmptLine;
        int RowLength;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            cmptLine = 0;
            RowLength = -2;
            while ((line = br.readLine()) != null) {
                cmptLine++;
                if (RowLength == -2) {
                    RowLength = line.length();
                } else if (RowLength != line.length()) {
                    res = false;
                    return res;
                }
            }
            br.close();
        }
        res = true;
        return res;

    }
}
