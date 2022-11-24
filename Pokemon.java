public class Pokemon {
    private int number;
    private String name;
    private String image;
    private Type[] type;
    private int healthPoints;
    private Attack[] attacks;

    public Pokemon(int number, String name, String image, Type[] type, Attack[] attacks) {
        this.number = number;
        this.name = name;
        this.image = image;
        this.type = type;
        this.healthPoints = 100;
        this.attacks = attacks;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Type[] getType() {
        return type;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public Attack[] geAttacks() {
        return attacks;
    };

    public void damaged(int damageTaken) {
        healthPoints -= damageTaken;
        if (healthPoints < 0)
            healthPoints = 0;
    }

    public boolean isAlive() {
        return healthPoints > 0;
    }

    @Override
    public String toString() {
        return (name + "(Nº " + number + ")" +
                "\nType: " + type[0] + " | " + type[1] +
                "\nhas " + healthPoints + "HP.");
    }
}
