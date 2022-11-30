package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public class Energy extends Card {
    private String id;
    private Type type;

    public Energy(String name, String imageName, Type type) {
        super(name, imageName);
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return name;
    }
}
