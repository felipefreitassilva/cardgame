package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.scene.image.Image;

public class Util {
    public static final String ASSETS_PATH = "src/main/resources/poo/trabalhofinal/felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos";

    public static final Image generateImage(String imageName) {
        try {
            return new Image(
                    new FileInputStream(ASSETS_PATH + "/" + imageName));
        } catch (FileNotFoundException fnfe) {
            System.out.println("Couldn't locate " + ASSETS_PATH + "/" + imageName);
        } catch (Exception e) {
            System.out.println("e => " + e);
        }
        return null;
    }

    public static final File getRandomFile() {
        String[] pokemonFiles = {
                "pokemons.txt",
                "pokemons2.txt",
                "pokemons3.txt",
                "pokemons4.txt",
                "pokemons5.txt",
                "pokemons6.txt"
        };
        String imageName = pokemonFiles[new Random().nextInt(6)];
        return new File(ASSETS_PATH + "/" + imageName);
    }
}
