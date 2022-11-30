package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public class Pokemon extends Card implements IPokemon {
    private int number;
    private Type type;
    private Type weakness;
    private int healthPoints;
    private Attack[] attacks;

    public Pokemon(int number, String name, Type type, Type weakness, Attack[] attacks) {
        super(name, name + ".png");
        this.number = number;
        this.type = type;
        this.weakness = weakness;
        this.healthPoints = 100;
        this.attacks = attacks;
    }

    public int getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }

    public Type getWeakness() {
        return weakness;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public Attack getBaseAttack() {
        return attacks[0];
    }

    public Attack getSpecialAttack() {
        return attacks[1];
    }

    public void receberDano(Pokemon enemy, Attack attack) {
        healthPoints -= hasAdvantage(enemy) * attack.getPower();
        if (healthPoints < 0)
            healthPoints = 0;
    }

    public void receberDano(int amount) {
        healthPoints -= amount;
        if (healthPoints < 0)
            healthPoints = 0;
    }

    public void receberCura(Potion p) {
        healthPoints += p.getCura();
        if (healthPoints > 100)
            healthPoints = 100;
    }

    public void reviver(MaxRevive maxRevive) {
        this.healthPoints = 100;
    }

    public boolean isFainted() {
        return healthPoints == 0;
    }

    public double hasAdvantage(Pokemon pokemon) {
        if (this.type == pokemon.getWeakness())
            return 0.5;

        if (this.weakness == pokemon.getType())
            return 1.5;

        return 1;
    }

    @Override
    public String toString() {
        return (name + " (No " + number + ")" +
                "\tType: " + type +
                "\nAttacks: " +
                "\n\t" + attacks[0] +
                "\n\t" + attacks[1] +
                "\nHP: " + healthPoints);
    }
}
