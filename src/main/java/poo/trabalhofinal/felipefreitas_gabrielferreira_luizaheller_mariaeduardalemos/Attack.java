package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

public class Attack extends Card {
    int power;
    Type type;

    public Attack(String name, int power, Type type) {
        super(name, "assets//" + name);
        this.power = power;
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + "\tPower" + power + "\tType=" + type;
    }
}
