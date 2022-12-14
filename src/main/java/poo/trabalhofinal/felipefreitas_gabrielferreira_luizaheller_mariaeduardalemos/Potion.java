package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public abstract class Potion extends Card {
    private String id;
    private int cura;
    private String cor;

    protected Potion(String name, String imageName, int cura, String cor) {
        super(name, imageName);
        this.cura = cura;
        this.cor = cor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCura() {
        return cura;
    }

    public String getCor() {
        return cor;
    }

    public String toString() {
        return name + "\t Healing Power: " + cura;
    }
}
