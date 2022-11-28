package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public abstract class Potion extends Card {
    String cor;

    protected Potion(String nome, String cor) {
        super(nome, cor);
        this.cor = cor;
    }
}
