package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public class Energy extends Card {
    private Type type;

    public Energy(String name, String imageName, Type type) {
        super(name, imageName);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return name;
    }
}
