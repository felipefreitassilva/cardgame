package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public interface IPokemon {
    void receberDano(int amount);

    void receberCura(Potion p);

    boolean isFainted();
}
