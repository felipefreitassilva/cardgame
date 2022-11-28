package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final String ASSETS_PATH = "src/main/resources/poo/trabalhofinal/felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos";
    private static final AnchorPane root = new AnchorPane();
    private static final Scene scene = new Scene(root, 1366, 768, Color.SNOW);
    private Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scanner reader = new Scanner(new File(ASSETS_PATH + "/pokemons.txt"));

        /*
         * Include deck image
         */
        Image iCardBack = new Image(new FileInputStream(ASSETS_PATH + "/cardBack.jpg"));
        ImageView ivCardBack;
        for (int i = 0; i < 4; i++) {
            ivCardBack = new ImageView(iCardBack);
            ivCardBack.setPreserveRatio(true);
            ivCardBack.setX(25);
            ivCardBack.setY(258 + i * 8);
            ivCardBack.setFitHeight(230);
            ivCardBack.setFitWidth(165);
            ivCardBack.setCursor(Cursor.HAND);
            include(ivCardBack);
        }

        /*
         * Include pokemon for both players
         */
        Pokemon[] pokemonPlayer1 = new Pokemon[3];
        Pokemon[] pokemonPlayer2 = new Pokemon[3];
        Image pokemonImage;
        ImageView pokemonImageView;
        for (int i = 0; reader.hasNext(); i++) {
            String lineRaw = reader.nextLine();
            String[] line = lineRaw.split(",");
            int numero = Integer.parseInt(line[0]);
            String nome = line[1];
            pokemonImage = new Image(new FileInputStream(ASSETS_PATH + "/" + nome.toLowerCase() + ".png"));
            Type type = Type.valueOf(line[2].toUpperCase());
            Type weakness = Type.valueOf(line[3].toUpperCase());
            Attack baseAttack = new Attack("Tackle", 15, Type.NORMAL);
            Attack specialAttack = new Attack(line[4], 30, type);
            Attack[] attacks = { baseAttack, specialAttack };
            pokemonImageView = new ImageView(pokemonImage);
            if (i < 3) {
                pokemonPlayer1[i] = new Pokemon(numero, nome, type, weakness, attacks);
                pokemonImageView.setY(110);
                pokemonImageView.setX(250 + i * 200);
            } else {
                pokemonPlayer2[i - 3] = new Pokemon(numero, nome, type, weakness, attacks);
                pokemonImageView.setY(420);
                pokemonImageView.setX(250 + (i - 3) * 200);
            }
            pokemonImageView.setPreserveRatio(true);
            pokemonImageView.setFitWidth(150);
            pokemonImageView.setFitHeight(200);
            include(pokemonImageView);
        }

        /*
         * Include buttons for pokemon
         */
        Button button;
        for (int i = 0; i < 6; i++) {
            button = new Button();
            button.setText("Ataque Básico");
            button.setPrefWidth(150);
            button.setPrefHeight(25);
            if (i < 3) {
                button.setLayoutX(260 + i * 200);
                button.setLayoutY(260);
            } else {
                button.setLayoutX(260 + (i - 3) * 200);
                button.setLayoutY(560);
            }
            include(button);

            button = new Button();
            button.setPrefWidth(150);
            button.setPrefHeight(25);
            if (i < 3) {
                button.setText(pokemonPlayer1[i].getSpecialAttack().getName());
                button.setLayoutX(260 + i * 200);
                button.setLayoutY(300);
            } else {
                button.setText(pokemonPlayer2[i - 3].getSpecialAttack().getName());
                button.setLayoutX(260 + (i - 3) * 200);
                button.setLayoutY(600);
            }
            include(button);
        }

        /*
         * Include static label for pokemon
         */
        Label label;
        for (int i = 0; i < 6; i++) {
            label = new Label();
            label.prefWidth(40);
            label.prefHeight(17);
            if (i < 3) {
                label.setText(pokemonPlayer1[i].getName() + " nº" + pokemonPlayer1[i].getNumber() + "    -    "
                        + pokemonPlayer1[i].getType());
                label.setLayoutX(260 + i * 200);
                label.setLayoutY(92);
            } else {
                label.setText(pokemonPlayer2[i - 3].getName() + " nº" + pokemonPlayer2[i - 3].getNumber() + "    -    "
                        + pokemonPlayer2[i - 3].getType());
                label.setLayoutX(260 + (i - 3) * 200);
                label.setLayoutY(400);
            }
            include(label);
        }

        /*
         * Include variable label for pokemon
         */

        stage.getIcons().add(new Image(new FileInputStream(ASSETS_PATH + "/icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Pokemon Card Game");
        stage.show();
    }

    private void include(Node e) {
        root.getChildren().add(e);
    }

    public static void main(String[] args) {
        launch();
    }
}