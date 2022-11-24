import java.util.HashMap;
import java.util.Map;

public class Attack {
    private Map<String, AttackVariables> about;

    public Attack(String name, int power, double accuracy, Type type, int uses) {
        about = new HashMap<>();
        about.put(name, new AttackVariables(power, accuracy, type, uses));
    }

    public Map<String, AttackVariables> about() {
        return about;
    }

    public class AttackVariables {
        private int power;
        private double accuracy;
        private Type type;
        private int uses;

        public AttackVariables(int power, double accuracy, Type type, int uses) {
            this.power = power;
            this.accuracy = accuracy;
            this.type = type;
            this.uses = uses;
        }

        public int getPower() {
            return power;
        }

        public double getAccuracy() {
            return accuracy;
        }

        public Type getType() {
            return type;
        }

        public int getUses() {
            return uses;
        }
    }
}
