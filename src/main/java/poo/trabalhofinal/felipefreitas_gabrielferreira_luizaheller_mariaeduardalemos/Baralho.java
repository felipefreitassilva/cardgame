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
        }
        pocoes.push(new MaxRevive());

        for (int i = 0; i < 15; i++) {
            switch (r.nextInt(3)) {
                case 0:
                    energias.push(new Energy("Energia de Água", Type.WATER));
                    break;

                case 1:
                    energias.push(new Energy("Energia de Fogo", Type.FIRE));
                    break;

                case 2:
                    energias.push(new Energy("Energia de Grama", Type.GRASS));
                    break;

                default:
                    break;
            }
        }
    }

    public Potion getPotion() {
        return pocoes.pop();
    }

    public Energy getEnergy() {
        return energias.pop();
    }

    public int size() {
        return pocoes.size() + energias.size();
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
