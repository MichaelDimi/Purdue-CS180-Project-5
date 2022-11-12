package LocalTests;// Testing imports

import App.BookApp;
import Objects.Book;
import Objects.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class SellerTest {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Tests ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCase {

        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }


        @Test(timeout = 1000)
        public void testSellerMenu() {
            try {
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;
                Seller seller = new Seller("Seller",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.setCurrentUser(seller);

                Seller currentUser = (Seller) market.getCurrentUser();

                // MAIN SECTION
                String input = "9" + System.lineSeparator();

                receiveInput(input);
                seller.editStore();

                String output = getOutput();

                String expected = "SELLER PAGE\n" +
                        "*******************\n" +
                        "1. Edit store or manage stock\n" +
                        "2. Create new store\n" +
                        "3. Delete store\n" +
                        "4. View your stores\n" +
                        "5. Add a SALE\n" +
                        "6. View reviews\n" +
                        "7. View seller stats\n" +
                        "8. Import / Export Inventory\n" +
                        "9. SIGN OUT";

                assertEquals("Check the main menu", "output", output);

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }


    }
}
