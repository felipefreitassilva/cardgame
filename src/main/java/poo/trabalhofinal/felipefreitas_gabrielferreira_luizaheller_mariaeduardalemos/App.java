package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class App extends Application {
    private final AnchorPane root = new AnchorPane();
    private Stage stage = new Stage();

    private final Pokemon[] pokemons = new Pokemon[6];
    private final Baralho baralho = new Baralho();

    private ImageView[] ivCardBacks = new ImageView[4];
    private Label labelTrainer;
    private Label[] labels = new Label[6];
    private ImageView[] pokemonImageViews = new ImageView[6];
    private Button[] buttonBaseAttacks = new Button[6];
    private Button[] buttonSpecialAttacks = new Button[6];
    private Button[] buttonChooseAttackeds = new Button[6];
    private Label[] HPLabelTexts = new Label[6];
    private ProgressBar[] HPProgressBars = new ProgressBar[6];
    private Label[] HPLabels = new Label[6];

    private Label labelWarning;

    private Attack attack;
    private Pokemon attacker;
    private Pokemon defender;

    private int nroRodada = 1;
    private final int CARD_DISTANCING = 220;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /** Included Pokeball Icon to Game Window */
        stage.getIcons().add(Util.generateImage("icon.png"));
        stage.setResizable(false);
        stage.setTitle("Pokemon Card Game");

        /** Include Game Rules */
        Label rulesLabel = new Label();
        rulesLabel.setText("Regras");
        rulesLabel.setFont(new Font(16));
        rulesLabel.setLayoutX(25);
        rulesLabel.setLayoutY(680);
        rulesLabel.setPrefHeight(15);
        rulesLabel.setCursor(Cursor.HAND);
        ImageView rulesImg = new ImageView(Util.generateImage("rules.jpg"));
        VBox rulesRoot = new VBox();
        Button backButton = new Button();
        backButton.setText("Voltar");
        rulesRoot.getChildren().addAll(rulesImg, backButton);
        Scene rulesScene = new Scene(rulesRoot);
        Stage rulesStage = new Stage();
        rulesStage.setScene(rulesScene);
        rulesStage.setTitle("Regras Pokemon Card Game");
        rulesStage.getIcons().add(Util.generateImage("icon.png"));
        rulesStage.setResizable(false);
        rulesLabel.setOnMouseClicked(e -> rulesStage.showAndWait());
        backButton.setOnMouseClicked(e -> rulesStage.close());
        include(rulesLabel);

        /** Include deck image */
        ImageView ivCardBack;
        for (int i = 0; i < 4; i++) {
            ivCardBack = new ImageView(Util.generateImage("cardBack.jpg"));
            ivCardBack.setX(25);
            ivCardBack.setY(220 + i * 8);
            ivCardBack.setFitHeight(230);
            ivCardBack.setFitWidth(165);
            ivCardBack.setCursor(Cursor.HAND);
            ivCardBack.setOnMouseClicked(handleDeckClick);
            ivCardBacks[i] = ivCardBack;
            include(ivCardBack);
        }

        Scanner reader = new Scanner(new File(Util.ASSETS_PATH + "/pokemons.txt"));
        /** Include pokemon for both players */
        for (int i = 0; i < 6; i++) {
            String line = reader.nextLine();
            String[] lineContent = line.split(",");
            int numero = Integer.parseInt(lineContent[0]);
            String nome = lineContent[1];
            Type type = Type.valueOf(lineContent[2].toUpperCase());
            Type weakness = Type.valueOf(lineContent[3].toUpperCase());
            Attack baseAttack = new Attack("Tackle", 15, Type.NORMAL);
            Attack specialAttack = new Attack(lineContent[4], 30, type);
            Attack[] attacks = { baseAttack, specialAttack };
            pokemons[i] = new Pokemon(numero, nome, type, weakness, attacks);
        }

        /** Create title for whose round it is */
        labelTrainer = new Label();
        labelTrainer.setLayoutX(450);
        labelTrainer.setLayoutY(18);
        labelTrainer.setText("Rodada " + nroRodada + "\nVez do: " + getCurrentTrainer());
        labelTrainer.setFont(new Font(24));
        include(labelTrainer);

        /** Include pokemon images */
        for (int i = 0; i < pokemons.length; i++) {
            ImageView pokemonImageView;
            Label label;
            Button buttonBaseAttack;
            Button buttonSpecialAttack;
            Button buttonChooseAttacked;
            Label HPLabelText;
            ProgressBar HPProgressBar;
            Label HPLabel;
            double HPPokemon;

            /** Include static label for pokemon */
            label = new Label();
            HPLabelText = new Label();
            label.setId("label " + i);
            HPLabelText.setId("HPLabelText " + i);
            label.prefWidth(40);
            label.prefHeight(17);
            HPLabelText.prefWidth(40);
            HPLabelText.prefHeight(10);
            HPLabelText.setText("HP ");
            if (i < 3) {
                label.setText(getTrainer1PokemonBaseInfo()[i]);
                label.setLayoutX(260 + i * CARD_DISTANCING);
                label.setLayoutY(110);
                HPLabelText.setLayoutX(260 + i * CARD_DISTANCING);
                HPLabelText.setLayoutY(370);
            } else {
                label.setText(getTrainer2PokemonBaseInfo()[i - 3]);
                label.setLayoutX(260 + (i - 3) * CARD_DISTANCING);
                label.setLayoutY(430);
                HPLabelText.setLayoutX(260 + (i - 3) * CARD_DISTANCING);
                HPLabelText.setLayoutY(670);
            }
            labels[i] = label;
            include(label);
            HPLabelTexts[i] = HPLabelText;
            include(HPLabelText);

            /** Create poke cards */
            pokemonImageView = new ImageView(Util.generateImage(pokemons[i].getName().toLowerCase() + ".png"));
            pokemonImageView.setId("pokemonImageView " + i);
            pokemonImageView.setFitWidth(150);
            pokemonImageView.setFitHeight(200);
            if (i < 3) {
                pokemonImageView.setX(250 + i * CARD_DISTANCING);
                pokemonImageView.setY(140);
            } else {
                pokemonImageView.setX(250 + (i - 3) * CARD_DISTANCING);
                pokemonImageView.setY(450);
            }
            pokemonImageViews[i] = pokemonImageView;
            include(pokemonImageView);

            /** Include buttons for pokemon base attack */
            buttonBaseAttack = new Button();
            buttonBaseAttack.setId("buttonBaseAttack " + i);
            buttonBaseAttack.setText("Tackle");
            buttonBaseAttack.setPrefSize(150, 25);
            buttonBaseAttack.setCursor(Cursor.HAND);
            buttonBaseAttack.setOnMouseClicked(
                    event -> handleBaseAttackClick(
                            buttonBaseAttacks[Integer.parseInt(buttonBaseAttack.getId().split(" ")[1])]));
            if (i < 3) {
                buttonBaseAttack.setLayoutX(260 + i * CARD_DISTANCING);
                buttonBaseAttack.setLayoutY(290);
                buttonBaseAttack.setVisible(false);
            } else {
                buttonBaseAttack.setLayoutX(260 + (i - 3) * CARD_DISTANCING);
                buttonBaseAttack.setLayoutY(590);
            }
            buttonBaseAttacks[i] = buttonBaseAttack;
            include(buttonBaseAttack);

            /** Include buttons for pokemon special attack */
            buttonSpecialAttack = new Button();
            buttonSpecialAttack.setId("buttonSpecialAttack " + i);
            buttonSpecialAttack.setPrefSize(150, 25);
            buttonSpecialAttack.setCursor(Cursor.HAND);
            buttonSpecialAttack.setOnMouseClicked(event -> handleSpecialAttackClick(
                    buttonSpecialAttacks[Integer.parseInt(buttonSpecialAttack.getId().split(" ")[1])]));
            if (i < 3) {
                buttonSpecialAttack.setText(getTrainer1Pokemon()[i].getSpecialAttack().getName());
                buttonSpecialAttack.setLayoutX(260 + i * CARD_DISTANCING);
                buttonSpecialAttack.setLayoutY(330);
            } else {
                buttonSpecialAttack.setText(getTrainer2Pokemon()[i - 3].getSpecialAttack().getName());
                buttonSpecialAttack.setLayoutX(260 + (i - 3) * CARD_DISTANCING);
                buttonSpecialAttack.setLayoutY(630);
            }
            buttonSpecialAttacks[i] = buttonSpecialAttack;
            include(buttonSpecialAttack);

            /** Include button for choosing which pokemon is going to be attacked */
            buttonChooseAttacked = new Button();
            buttonChooseAttacked.setId("buttonChooseAttacked " + i);
            buttonChooseAttacked.setText("Atacar");
            buttonChooseAttacked.setPrefSize(150, 25);
            buttonChooseAttacked.setCursor(Cursor.HAND);
            buttonChooseAttacked.setOnMouseClicked(event -> tomarDano(
                    buttonChooseAttackeds[Integer.parseInt(buttonChooseAttacked.getId().split(" ")[1])]));
            if (i < 3) {
                buttonChooseAttacked.setLayoutX(260 + i * CARD_DISTANCING);
                buttonChooseAttacked.setLayoutY(330);
            } else {
                buttonChooseAttacked.setLayoutX(260 + (i - 3) * CARD_DISTANCING);
                buttonChooseAttacked.setLayoutY(630);
                buttonChooseAttacked.setVisible(false);
            }
            buttonChooseAttackeds[i] = buttonChooseAttacked;
            include(buttonChooseAttacked);

            /** Include variable label for pokemon */
            HPProgressBar = new ProgressBar();
            HPLabel = new Label();
            HPProgressBar.setId("HPProgressBar " + i);
            HPLabel.setId("HPLabel " + i);
            HPProgressBar.prefWidth(120);
            HPProgressBar.prefHeight(20);
            HPLabel.prefWidth(40);
            HPLabel.prefHeight(10);
            if (i < 3) {
                HPPokemon = getTrainer1Pokemon()[i].getHealthPoints();
                HPProgressBar.setLayoutX(280 + i * CARD_DISTANCING);
                HPProgressBar.setLayoutY(370);
                HPLabel.setLayoutX(380 + i * CARD_DISTANCING);
                HPLabel.setLayoutY(370);
            } else {
                HPPokemon = getTrainer2Pokemon()[i - 3].getHealthPoints();
                HPProgressBar.setLayoutX(280 + (i - 3) * CARD_DISTANCING);
                HPProgressBar.setLayoutY(670);
                HPLabel.setLayoutX(380 + (i - 3) * CARD_DISTANCING);
                HPLabel.setLayoutY(670);
            }
            HPProgressBar.setProgress(HPPokemon / 100);
            HPLabel.setText(" " + HPPokemon);
            if (HPPokemon >= 70) {
                HPProgressBar.setStyle("-fx-accent: #00b31e;");
            } else if (HPPokemon >= 40) {
                HPProgressBar.setStyle("-fx-accent: #DD0;");
            } else {
                HPProgressBar.setStyle("-fx-accent: red;");
            }
            HPProgressBars[i] = HPProgressBar;
            include(HPProgressBar);
            HPLabels[i] = HPLabel;
            include(HPLabel);
        }

        /** Include label warning */
        labelWarning = new Label();
        labelWarning.setText("Favor selecionar o pokemon.");
        labelWarning.setLayoutX(35);
        labelWarning.setLayoutY(500);
        labelWarning.setVisible(false);
        include(labelWarning);

        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    EventHandler<MouseEvent> handleDeckClick = event -> {
        if (baralho.size() > 0) {
            boolean cardNull;
            do {
                cardNull = false;
                /* 50/50 */
                if (new Random().nextBoolean()) {
                    Energy energy = baralho.getEnergy();
                    if (energy != null)
                        System.out.println(energy);
                    else
                        cardNull = true;
                } else {
                    Potion potion = baralho.getPotion();
                    if (potion != null)
                        System.out.println(potion);
                    else
                        cardNull = true;
                }
            } while (cardNull);
            if (baralho.size() < 4)
                ivCardBacks[baralho.size()].setVisible(false);
        } else {
            ivCardBacks[0].setVisible(false);
            System.out.println("Acabou o deck");
        }

        passarAVez();
    };

    private void handleBaseAttackClick(Button button) {
        int id = Integer.parseInt(button.getId().split(" ")[1]);
        attacker = pokemons[id];
        attack = attacker.getBaseAttack();

        for (Label label : labels)
            label.setTextFill(Color.BLACK);
        labels[id].setTextFill(Color.RED);
    }

    private void handleSpecialAttackClick(Button button) {
        int id = Integer.parseInt(button.getId().split(" ")[1]);
        attacker = pokemons[id];
        attack = attacker.getSpecialAttack();

        for (Label label : labels)
            label.setTextFill(Color.BLACK);
        labels[id].setTextFill(Color.RED);
    }

    private void tomarDano(Button button) {
        if (attacker != null) {
            int id = Integer.parseInt(button.getId().split(" ")[1]);
            defender = pokemons[id];

            if (attack.getType() == Type.NORMAL)
                defender.receberDano(attack.getPower());
            else
                defender.receberDano(attacker, attack);

            HPProgressBars[id].setProgress(defender.getHealthPoints() / 100);
            HPLabels[id].setText(" " + defender.getHealthPoints());
            if (defender.getHealthPoints() >= 70) {
                HPProgressBars[id].setStyle("-fx-accent: #00b31e !important;");
            } else if (defender.getHealthPoints() >= 40) {
                HPProgressBars[id].setStyle("-fx-accent: #DD0 !important;");
            } else {
                HPProgressBars[id].setStyle("-fx-accent: red !important;");
            }

            passarAVez();
        } else {
            labelWarning.setVisible(true);
            ;
        }
    }

    private void passarAVez() {
        nroRodada++;
        attack = null;
        attacker = null;
        defender = null;
        labelWarning.setVisible(false);

        labelTrainer.setText("Rodada " + nroRodada + "\nVez do: " + getCurrentTrainer());

        for (Label label : labels)
            label.setTextFill(Color.BLACK);

        if (getCurrentRound()) {
            for (int i = 0; i < 3; i++) {
                buttonBaseAttacks[i].setVisible(false);
                buttonChooseAttackeds[i].setVisible(true);
            }
        } else {
            for (int i = 3; i < 6; i++) {
                buttonBaseAttacks[i].setVisible(true);
                buttonChooseAttackeds[i].setVisible(false);
            }
        }
    }

    private boolean getCurrentRound() {
        return nroRodada % 2 == 0;
    }

    private String getCurrentTrainer() {
        if (getCurrentRound())
            return "Treinador 2";
        return "Treinador 1";
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
                    " No(" + pokemon.getNumber() +
                    ") - " + pokemon.getType();
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
                    " No(" + pokemon.getNumber() +
                    ") - " + pokemon.getType();
        }

        return trainer2PokemonBaseInfo;
    }

    private void include(Node e) {
        root.getChildren().add(e);
    }

    public static void main(String[] args) {
        launch();
    }
}