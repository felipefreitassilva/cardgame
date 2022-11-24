public class Trainer {
    private String nome;
    private Pokemon[] pokemon;

    public Trainer(String nome, Pokemon[] pokemon) {
        this.nome = nome;
        this.pokemon = pokemon;
    }

    public String getNome() {
        return nome;
    }

    public Pokemon[] getPokemon() {
        return pokemon;
    }

    public String toString() {
        return ("Treinador " + nome);
    }
}
