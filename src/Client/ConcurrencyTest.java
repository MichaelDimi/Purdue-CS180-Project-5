package Client;

import java.util.Scanner;

public class ConcurrencyTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        final SignUpConcurrencyTest clientOne = new SignUpConcurrencyTest();
        final SignUpConcurrencyTest clientTwo = new SignUpConcurrencyTest();

        new Thread(() -> clientOne.present(scan)).start();
        new Thread(() -> clientTwo.present(scan)).start();
    }
}
