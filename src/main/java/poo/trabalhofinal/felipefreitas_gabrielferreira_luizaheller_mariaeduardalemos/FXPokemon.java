package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXPokemon {

    private static final String ASSETS_PATH = "src/main/resources/poo/trabalhofinal/felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos";
    private Image CARD_BACK;
    private Pokemon[] pokemons;
    private Image pokemonImage;
    private ImageView pokemonImageView;
    private Button button;
    private Label label;
    private Label HPLabelText;
    private ProgressBar HPProgressBar;
    private double HPPokemon;
    private Label HPLabel;

    public FXPokemon(Pokemon[] pokemons) throws FileNotFoundException {
        this.pokemons = pokemons;
        this.CARD_BACK = new Image(new FileInputStream(ASSETS_PATH + "/cardBack.jpg"));
    }

    public List<Node> buildForFX() throws FileNotFoundException {
        List<Node> cardElements = new ArrayList<>();

        for (int i = 0; i < pokemons.length; i++) {

            /** Create poke cards */
            pokemonImage = new Image(
                    new FileInputStream(ASSETS_PATH + "/" + pokemons[i].getName().toLowerCase() + ".png"));
            pokemonImageView = new ImageView(pokemonImage);
            if (i < 3) {
                pokemonImageView.setY(110);
                pokemonImageView.setX(250 + i * 200);
            } else {
                pokemonImageView.setY(420);
                pokemonImageView.setX(250 + (i - 3) * 200);
            }
            pokemonImageView.setPreserveRatio(true);
            pokemonImageView.setFitWidth(150);
            pokemonImageView.setFitHeight(200);
            cardElements.add(pokemonImageView);

            /** Include buttons for pokemon */
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
            cardElements.add(button);

            button = new Button();
            button.setPrefWidth(150);
            button.setPrefHeight(25);
            if (i < 3) {
                button.setText(getTrainer1Pokemon()[i].getSpecialAttack().getName());
                button.setLayoutX(260 + i * 200);
                button.setLayoutY(300);
            } else {
                button.setText(getTrainer2Pokemon()[i - 3].getSpecialAttack().getName());
                button.setLayoutX(260 + (i - 3) * 200);
                button.setLayoutY(600);
            }
            cardElements.add(button);

            /** Include static label for pokemon */
            label = new Label();
            HPLabelText = new Label();
            label.prefWidth(40);
            label.prefHeight(17);
            HPLabelText.prefWidth(40);
            HPLabelText.prefHeight(10);
            HPLabelText.setText("HP ");
            if (i < 3) {
                label.setText(getTrainer1PokemonBaseInfo()[i]);
                label.setLayoutX(260 + i * 200);
                label.setLayoutY(92);
                HPLabelText.setLayoutX(260 + i * 200);
                HPLabelText.setLayoutY(340);
            } else {
                label.setText(getTrainer2PokemonBaseInfo()[i - 3]);
                label.setLayoutX(260 + (i - 3) * 200);
                label.setLayoutY(400);
                HPLabelText.setLayoutX(260 + (i - 3) * 200);
                HPLabelText.setLayoutY(640);
            }
            cardElements.add(label);
            cardElements.add(HPLabelText);

            /** Include variable label for pokemon */
            HPLabel = new Label();
            HPProgressBar = new ProgressBar();
            HPLabel.prefWidth(40);
            HPLabel.prefHeight(10);
            HPProgressBar.prefWidth(120);
            HPProgressBar.prefHeight(20);
            if (i < 3) {
                HPPokemon = getTrainer1Pokemon()[i].getHealthPoints();
                HPLabel.setLayoutX(380 + i * 200);
                HPLabel.setLayoutY(340);
                HPProgressBar.setLayoutX(280 + i * 200);
                HPProgressBar.setLayoutY(340);
            } else {
                HPPokemon = getTrainer2Pokemon()[i - 3].getHealthPoints();
                HPLabel.setLayoutX(380 + (i - 3) * 200);
                HPLabel.setLayoutY(640);
                HPProgressBar.setLayoutX(280 + (i - 3) * 200);
                HPProgressBar.setLayoutY(640);
            }
            HPLabel.setText("\t" + HPPokemon);
            HPProgressBar.setProgress(HPPokemon / 100);
            if (HPPokemon >= 70) {
                HPProgressBar.setStyle("-fx-accent: #00b31e;");
            } else if (HPPokemon >= 40) {
                HPProgressBar.setStyle("-fx-accent: yellow;");
            } else {
                HPProgressBar.setStyle("-fx-accent: red;");
            }
            cardElements.add(HPProgressBar);
            cardElements.add(HPLabel);
        }

        return cardElements;
    }

    private Pokemon[] getTrainer1Pokemon() {
        Pokemon[] trainer1Pokemon = new Pokemon[3];

        for (int i = 0; i < 3; i++)
            trainer1Pokemon[i] = pokemons[i];

        return trainer1Pokemon;
    }

    private String[] getTrainer1PokemonBaseInfo() {
        String[] trainer1PokemonBaseInfo = new String[3];

        for (int i = 0; i < 3; i++) {
            Pokemon pokemon = getTrainer1Pokemon()[i];
            trainer1PokemonBaseInfo[i] = pokemon.getName() +
                    " " + pokemon.getNumber() + "  -" +
                    "  " + pokemon.getType();
        }

        return trainer1PokemonBaseInfo;
    }

    private Pokemon[] getTrainer2Pokemon() {
        Pokemon[] trainer2Pokemon = new Pokemon[3];

        for (int i = 0; i < 3; i++)
            trainer2Pokemon[i] = pokemons[i + 3];

        return trainer2Pokemon;
    }

    private String[] getTrainer2PokemonBaseInfo() {
        String[] trainer2PokemonBaseInfo = new String[3];

        for (int i = 0; i < 3; i++) {
            Pokemon pokemon = getTrainer2Pokemon()[i];
            trainer2PokemonBaseInfo[i] = pokemon.getName() +
                    " nº" + pokemon.getNumber() +
                    "  -  " + pokemon.getType();
        }

        return trainer2PokemonBaseInfo;
    }
}
