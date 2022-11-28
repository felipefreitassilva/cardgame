package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public class Energy extends Card {
    private Type type;

    public Energy(String name, Type type) {
        super(name, "assets//" + name);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
