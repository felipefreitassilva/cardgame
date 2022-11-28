package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
    private final AnchorPane root = new AnchorPane();
    private Stage stage = new Stage();
    private final Pokemon[] pokemons = new Pokemon[6];
    private FXPokemon pokemon;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /** Included Pokeball Icon to Game Window */
        stage.getIcons().add(new Image(new FileInputStream(ASSETS_PATH + "/icon.png")));
        stage.setResizable(false);
        stage.setTitle("Pokemon Card Game");

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

        Scanner reader = new Scanner(new File(ASSETS_PATH + "/pokemons.txt"));
        /** Include pokemon for both players */
        for (int i = 0; i < 6; i++) {
            String lineRaw = reader.nextLine();
            String[] line = lineRaw.split(",");
            int numero = Integer.parseInt(line[0]);
            String nome = line[1];
            Type type = Type.valueOf(line[2].toUpperCase());
            Type weakness = Type.valueOf(line[3].toUpperCase());
            Attack baseAttack = new Attack("Tackle", 15, Type.NORMAL);
            Attack specialAttack = new Attack(line[4], 30, type);
            Attack[] attacks = { baseAttack, specialAttack };
            pokemons[i] = (new Pokemon(numero, nome, type, weakness, attacks));
        }
        pokemon = new FXPokemon(pokemons);

        /** Include pokemon images */
        for (Node n : pokemon.buildForFX())
            include(n);

        /** Have an img for window background */
        // root.setBackground(
        // new Background(new BackgroundImage(
        // new Image(new FileInputStream(ASSETS_PATH + "/background.png")),
        // BackgroundRepeat.NO_REPEAT,
        // BackgroundRepeat.NO_REPEAT,
        // BackgroundPosition.CENTER,
        // new BackgroundSize(
        // BackgroundSize.AUTO,
        // BackgroundSize.AUTO,
        // true,
        // true,
        // true,
        // false))));
        stage.setScene(new Scene(root, 1366, 768, Color.RED));
        stage.show();
    }

    private void include(Node e) {
        root.getChildren().add(e);
    }

    public static void main(String[] args) {
        launch();
    }
}