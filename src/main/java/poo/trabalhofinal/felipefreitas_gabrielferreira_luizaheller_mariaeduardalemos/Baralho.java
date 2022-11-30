package poo.trabalhofinal.felipefreitas_gabrielferreira_luizaheller_mariaeduardalemos;

import java.util.Random;
import java.util.Stack;

public class Baralho {
    private Stack<Potion> pocoes;
    private Stack<Energy> energias;

    public Baralho() {
        pocoes = new Stack<>();
        energias = new Stack<>();
        shuffle();
    }

    private void shuffle() {
        Random r = new Random();
        boolean inseridoMaxRevive = false;
        for (int i = 0; i < 18; i++) {
            switch (r.nextInt(3)) {
                case 0:
                    pocoes.push(new CommonPotion());
                    break;

                case 1:
                    pocoes.push(new SuperPotion());
                    break;

                case 2:
                    pocoes.push(new HyperPotion());
                    break;

                default:
                    break;
            }
            if (!inseridoMaxRevive && r.nextBoolean()) {
                pocoes.push(new MaxRevive());
                inseridoMaxRevive = true;
            }
        }

        for (int i = 0; i < 15; i++) {
            switch (r.nextInt(3)) {
                case 0:
                    energias.push(new Energy("Energia de Agua", "waterEnergy.jpg", Type.WATER));
                    break;

                case 1:
                    energias.push(new Energy("Energia de Fogo", "fireEnergy.jpg", Type.FIRE));
                    break;

                case 2:
                    energias.push(new Energy("Energia de Grama", "grassEnergy.jpg", Type.GRASS));
                    break;

                default:
                    break;
            }
        }
    }

    public Potion getPotion() {
        if (pocoes.size() > 0)
            return pocoes.pop();
        return null;
    }

    public Energy getEnergy() {
        if (energias.size() > 0)
            return energias.pop();
        return null;
    }

    public int size() {
        return pocoes.size() + energias.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String toString() {
        String str = "";
        for (Potion potion : pocoes) {
            str.concat(potion.name);
        }
        for (Energy energy : energias) {
            str.concat(energy.name);
        }
        return str;
    }
}
