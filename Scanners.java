import java.util.InputMismatchException;
import java.util.Scanner;

public class Scanners {
    static int inputInt() {
        try {
            return new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            return inputInt();
        }
    }

    static String inputString() {
        try {
            return new Scanner(System.in).nextLine();
        } catch (InputMismatchException e) {
            return inputString();
        }
    }
}
