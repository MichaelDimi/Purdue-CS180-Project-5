package Client;

public class ConcurrencyTest {
    public static void main(String[] args) {
        final SignUpConcurrencyTest clientOne = new SignUpConcurrencyTest();
        final SignUpConcurrencyTest clientTwo = new SignUpConcurrencyTest();

        new Thread(() -> {
            clientOne.signup(new String[]{"Aaron", "aaron@email.com", "password"});
        }).start();
        new Thread(() -> {
            clientTwo.signup(new String[]{"Aaron", "aaron@email.com", "password"});
        }).start();
    }
}
