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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class App extends Application {
    /** Java FX Variables */
    private final AnchorPane root = new AnchorPane();
    private Stage stage = new Stage();

    /** Stage Grid */
    private static final int TRAINER1_BASELINE = 110;
    private static final int TRAINER2_BASELINE = 430;
    private static final int CARDS_SPACE_BETWEEN = 220;
    private static final int CARDS_MARGIN_LEFT = 260;

    /** Utils */
    private final Pokemon[] pokemons = new Pokemon[6];
    private final Baralho baralho = new Baralho();
    private static final String TRAINER1 = "Treinador 1";
    private static final String TRAINER2 = "Treinador 2";
    private Map<String, List<ImageView>> playersHand = new HashMap<>();
    private Map<String, List<Potion>> playersPotions = new HashMap<>();
    private Map<String, List<Energy>> playersEnergies = new HashMap<>();

    /** Round Variables */
    private int nroRodada = 1;
    private Attack attack;
    private Pokemon attacker;
    private Pokemon defender;

    /** Pokemons JavaFX components */
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

    private Label noPokemonSelectedError;
    private Label noPotionAvailableError;
    private Label noEnergyAvailableError;

    @Override
    public void start(Stage primaryStage) throws IOException {
        /** Included Pokeball Icon to Game Window */
        stage.getIcons().add(Util.generateImage("icon.png"));
        stage.setTitle("Pokemon Card Game");
        // stage.setResizable(false);

        /** Include Game Rules */
        Label rulesLabel = new Label();
        rulesLabel.setText("Regras");
        rulesLabel.setFont(new Font(16));
        rulesLabel.setLayoutX(25);
        rulesLabel.setLayoutY(680);
        rulesLabel.setPrefHeight(15);
        rulesLabel.setCursor(Cursor.HAND);
        ImageView rulesImg = new ImageView(Util.generateImage("regras_pokemon.jpg"));
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

        /** Include deck images */
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

        /** Include pokemon for both players */
        Scanner reader = new Scanner(new File(Util.ASSETS_PATH + "/pokemons.txt"));
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

        /** Initialize players hands and items */
        playersHand.put(TRAINER1, new ArrayList<>());
        playersHand.put(TRAINER2, new ArrayList<>());
        playersPotions.put(TRAINER1, new ArrayList<>());
        playersPotions.put(TRAINER2, new ArrayList<>());
        playersEnergies.put(TRAINER1, new ArrayList<>());
        playersEnergies.put(TRAINER2, new ArrayList<>());

        /** Create title for whose round it is */
        labelTrainer = new Label();
        labelTrainer.setLayoutX(450);
        labelTrainer.setLayoutY(18);
        labelTrainer.setText("Rodada " + nroRodada + "\nVez do: " + getCurrentTrainer());
        labelTrainer.setFont(new Font(24));
        include(labelTrainer);

        /** Include pokemon cards */
        createPokemonCards();

        /** Include label warnings */
        noPokemonSelectedError = new Label();
        noPokemonSelectedError.setText("Favor selecionar o pokemon.");
        noPokemonSelectedError.setLayoutX(20);
        noPokemonSelectedError.setLayoutY(500);
        noPokemonSelectedError.setTextFill(Color.RED);
        noPokemonSelectedError.setVisible(false);
        include(noPokemonSelectedError);
        noPotionAvailableError = new Label();
        noPotionAvailableError.setText("Você não tem poções disponíveis.");
        noPotionAvailableError.setLayoutX(20);
        noPotionAvailableError.setLayoutY(520);
        noPotionAvailableError.setTextFill(Color.RED);
        noPotionAvailableError.setVisible(false);
        include(noPotionAvailableError);
        noEnergyAvailableError = new Label();
        noEnergyAvailableError.setText("Você não tem energia suficiente.");
        noEnergyAvailableError.setLayoutX(20);
        noEnergyAvailableError.setLayoutY(540);
        noEnergyAvailableError.setTextFill(Color.RED);
        noEnergyAvailableError.setVisible(false);
        include(noEnergyAvailableError);

        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    private void createPokemonCards() {
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

            /** Include static label about pokemon */
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
                label.setLayoutX(CARDS_MARGIN_LEFT + i * CARDS_SPACE_BETWEEN);
                label.setLayoutY(TRAINER1_BASELINE);
                HPLabelText.setLayoutX(CARDS_MARGIN_LEFT + i * CARDS_SPACE_BETWEEN);
                HPLabelText.setLayoutY(TRAINER1_BASELINE + 260);
            } else {
                label.setText(getTrainer2PokemonBaseInfo()[i - 3]);
                label.setLayoutX(CARDS_MARGIN_LEFT + (i - 3) * CARDS_SPACE_BETWEEN);
                label.setLayoutY(TRAINER2_BASELINE);
                HPLabelText.setLayoutX(CARDS_MARGIN_LEFT + (i - 3) * CARDS_SPACE_BETWEEN);
                HPLabelText.setLayoutY(TRAINER2_BASELINE + 240);
            }
            labels[i] = label;
            include(label);
            HPLabelTexts[i] = HPLabelText;
            include(HPLabelText);

            /** Include pokemon images */
            pokemonImageView = new ImageView(Util.generateImage(pokemons[i].getName().toLowerCase() + ".png"));
            pokemonImageView.setId("pokemonImageView " + i);
            pokemonImageView.setFitWidth(150);
            pokemonImageView.setFitHeight(200);
            if (i < 3) {
                pokemonImageView.setX(CARDS_MARGIN_LEFT + i * CARDS_SPACE_BETWEEN);
                pokemonImageView.setY(TRAINER1_BASELINE + 30);
            } else {
                pokemonImageView.setX(CARDS_MARGIN_LEFT + (i - 3) * CARDS_SPACE_BETWEEN);
                pokemonImageView.setY(TRAINER2_BASELINE + 20);
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
                buttonBaseAttack.setLayoutX(CARDS_MARGIN_LEFT + i * CARDS_SPACE_BETWEEN);
                buttonBaseAttack.setLayoutY(TRAINER1_BASELINE + 180);
                buttonBaseAttack.setVisible(false);
            } else {
                buttonBaseAttack.setLayoutX(CARDS_MARGIN_LEFT + (i - 3) * CARDS_SPACE_BETWEEN);
                buttonBaseAttack.setLayoutY(TRAINER2_BASELINE + 160);
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
                buttonSpecialAttack.setLayoutX(CARDS_MARGIN_LEFT + i * CARDS_SPACE_BETWEEN);
                buttonSpecialAttack.setLayoutY(TRAINER1_BASELINE + 220);
            } else {
                buttonSpecialAttack.setText(getTrainer2Pokemon()[i - 3].getSpecialAttack().getName());
                buttonSpecialAttack.setLayoutX(CARDS_MARGIN_LEFT + (i - 3) * CARDS_SPACE_BETWEEN);
                buttonSpecialAttack.setLayoutY(TRAINER2_BASELINE + 200);
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
                buttonChooseAttacked.setLayoutX(CARDS_MARGIN_LEFT + i * CARDS_SPACE_BETWEEN);
                buttonChooseAttacked.setLayoutY(TRAINER1_BASELINE + 220);
            } else {
                buttonChooseAttacked.setLayoutX(CARDS_MARGIN_LEFT + (i - 3) * CARDS_SPACE_BETWEEN);
                buttonChooseAttacked.setLayoutY(TRAINER2_BASELINE + 200);
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
                HPProgressBar.setLayoutX(CARDS_MARGIN_LEFT + 20 + i * CARDS_SPACE_BETWEEN);
                HPProgressBar.setLayoutY(TRAINER1_BASELINE + 260);
                HPLabel.setLayoutX((CARDS_MARGIN_LEFT + 130) + i * CARDS_SPACE_BETWEEN);
                HPLabel.setLayoutY(TRAINER1_BASELINE + 260);
            } else {
                HPPokemon = getTrainer2Pokemon()[i - 3].getHealthPoints();
                HPProgressBar.setLayoutX(CARDS_MARGIN_LEFT + 20 + (i - 3) * CARDS_SPACE_BETWEEN);
                HPProgressBar.setLayoutY(TRAINER2_BASELINE + 240);
                HPLabel.setLayoutX((CARDS_MARGIN_LEFT + 130) + (i - 3) * CARDS_SPACE_BETWEEN);
                HPLabel.setLayoutY(TRAINER2_BASELINE + 240);
            }
            HPProgressBar.setCursor(Cursor.HAND);
            HPProgressBar.setOnMouseClicked(event -> receberCura(
                    HPProgressBars[Integer.parseInt(HPProgressBar.getId().split(" ")[1])]));
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
    }

    EventHandler<MouseEvent> handleDeckClick = event -> {
        if (baralho.isEmpty()) {
            ivCardBacks[0].setVisible(false);
        } else {
            boolean cardNull;
            ImageView currentCard = null;
            do {
                cardNull = false;
                /* 50/50 for getting either energy or potion */
                if (new Random().nextBoolean()) {
                    Energy energy = baralho.getEnergy();
                    if (energy == null)
                        cardNull = true;
                    else {
                        energy.setId(energy.getName() + "  " + nroRodada + "  " + getCurrentTrainer());
                        currentCard = new ImageView(Util.generateImage(energy.getImageName()));
                        currentCard.setId(energy.getName() + "  " + nroRodada + "  " + getCurrentTrainer());
                        playersEnergies.get(getCurrentTrainer()).add(energy);
                    }
                } else {
                    Potion potion = baralho.getPotion();
                    if (potion == null)
                        cardNull = true;
                    else {
                        potion.setId(potion.getName() + "  " + nroRodada + "  " + getCurrentTrainer());
                        currentCard = new ImageView(Util.generateImage(potion.getImageName()));
                        currentCard.setId(potion.getName() + "  " + nroRodada + "  " + getCurrentTrainer());
                        playersPotions.get(getCurrentTrainer()).add(potion);
                    }
                }
            } while (cardNull);

            if (currentCard != null) {
                if (getCurrentRound())
                    currentCard.setY(TRAINER1_BASELINE + 40);
                else
                    currentCard.setY(TRAINER2_BASELINE + 20);
                currentCard.setFitWidth(165);
                currentCard.setFitHeight(210);
                playersHand.get(getCurrentTrainer()).add(currentCard);
                include(currentCard);
            }

            if (baralho.size() < 4)
                ivCardBacks[baralho.size()].setVisible(false);
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
        Energy energy = null;
        for (Energy e : playersEnergies.get(getCurrentTrainer())) {
            switch (e.getName()) {
                case "Energia de Fogo":
                    energy = e;
                    break;

                case "Energia de Agua":
                    energy = e;
                    break;

                case "Energia de Grama":
                    energy = e;
                    break;

                default:
                    System.out.println("e => Tenho uma " + e);
                    break;
            }
        }
        if (energy == null)
            noEnergyAvailableError.setVisible(true);
        else {
            attacker = pokemons[id];
            attack = attacker.getSpecialAttack();

            for (Label label : labels)
                label.setTextFill(Color.BLACK);
            labels[id].setTextFill(Color.RED);

            for (int i = 0; i < playersHand.get(getNextTrainer()).size(); i++)
                if (playersHand.get(getNextTrainer()).get(i).getId().equals(energy.getId())) {
                    playersHand.get(getNextTrainer()).get(i).setVisible(false);
                    playersHand.get(getNextTrainer()).remove(i);
                }
            playersEnergies.get(getNextTrainer()).remove(energy);
        }
    }

    private void tomarDano(Button button) {
        if (attacker == null)
            noPokemonSelectedError.setVisible(true);
        else {
            int id = Integer.parseInt(button.getId().split(" ")[1]);
            defender = pokemons[id];

            if (attack.getType() == Type.NORMAL)
                defender.receberDano(attack.getPower());
            else
                defender.receberDano(attacker, attack);

            passarAVez();
        }
    }

    private void receberCura(ProgressBar progressBar) {
        int id = Integer.parseInt(progressBar.getId().split(" ")[1]);
        Potion potion = null;
        for (Potion p : playersPotions.get(getNextTrainer())) {
            switch (p.getName()) {
                case "Pocao Comum":
                    potion = p;
                    break;

                case "Hiper Pocao":
                    potion = p;
                    break;

                case "Super Pocao":
                    potion = p;
                    break;

                case "Max Revive":
                    potion = p;
                    break;

                default:
                    System.out.println("e => Tenho um " + p);
                    break;
            }
        }
        if (potion == null)
            noPotionAvailableError.setVisible(true);
        else {
            for (int i = 0; i < playersHand.get(getNextTrainer()).size(); i++)
                if (playersHand.get(getNextTrainer()).get(i).getId().equals(potion.getId())) {
                    playersHand.get(getNextTrainer()).get(i).setVisible(false);
                    playersHand.get(getNextTrainer()).remove(i);
                }
            playersPotions.get(getNextTrainer()).remove(potion);
            pokemons[id].receberCura(potion);
            passarAVez();
        }
    }

    private void passarAVez() {
        nroRodada++;
        novaRodada();
    }

    private void novaRodada() {
        labelTrainer.setText("Rodada " + nroRodada + "\nVez do: " + getCurrentTrainer());

        attack = null;
        attacker = null;
        defender = null;

        noPokemonSelectedError.setVisible(false);
        noPotionAvailableError.setVisible(false);
        noEnergyAvailableError.setVisible(false);

        for (Label label : labels)
            label.setTextFill(Color.BLACK);

        /** Mostrar Botões */
        if (getCurrentRound()) {
            for (int i = 0; i < 3; i++) {
                buttonBaseAttacks[i].setVisible(false);
                buttonChooseAttackeds[i].setVisible(true);
            }
            for (int i = 3; i < 6; i++) {
                buttonBaseAttacks[i].setVisible(true);
                buttonChooseAttackeds[i].setVisible(false);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                buttonBaseAttacks[i].setVisible(true);
                buttonChooseAttackeds[i].setVisible(false);
            }
            for (int i = 3; i < 6; i++) {
                buttonBaseAttacks[i].setVisible(false);
                buttonChooseAttackeds[i].setVisible(true);
            }
        }

        /** Mostrar Mãos */
        int i = 0;
        for (ImageView iv : playersHand.get(getCurrentTrainer())) {
            iv.setX(900 + i * 35);
            iv.setVisible(false);
            i++;
        }

        i = 0;
        for (ImageView iv : playersHand.get(getNextTrainer())) {
            iv.setX(900 + i * 35);
            iv.setVisible(true);
            i++;
        }

        int idPB;
        double HPPokemon;
        for (ProgressBar pb : HPProgressBars) {
            idPB = Integer.parseInt(pb.getId().split(" ")[1]);
            HPPokemon = pokemons[idPB].getHealthPoints();
            pb.setProgress(HPPokemon / 100);
            HPLabels[idPB].setText(" " + HPPokemon);
            if (HPPokemon >= 70) {
                pb.setStyle("-fx-accent: #00b31e;");
            } else if (HPPokemon >= 40) {
                pb.setStyle("-fx-accent: #DD0;");
            } else {
                pb.setStyle("-fx-accent: red;");
            }
        }
    }

    private boolean getCurrentRound() {
        return nroRodada % 2 != 0;
    }

    private String getCurrentTrainer() {
        if (getCurrentRound())
            return TRAINER1;
        return TRAINER2;
    }

    private String getNextTrainer() {
        if (!getCurrentRound())
            return TRAINER1;
        return TRAINER2;
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