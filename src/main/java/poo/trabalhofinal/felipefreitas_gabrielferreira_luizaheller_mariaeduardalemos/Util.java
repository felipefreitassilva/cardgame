package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
}
