package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import javafx.scene.image.Image;

public abstract class Card {
    protected String name;
    protected String imageName;
    protected Image image;

    protected Card(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
        image = Util.generateImage(imageName);
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
    }

    public Image getImage() {
        return image;
    }
}
