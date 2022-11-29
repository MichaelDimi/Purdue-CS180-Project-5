package LocalTests; // Testing imports

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

import static org.junit.Assert.*;

/**
 * This class contains test cases for adding
 * And removing books to a shopping cart.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class BuyerTest {

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

    /**
     * @author Group
     * @version 11/13/22
     */
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

        @Test(timeout = 1000)
        public void testCart() {
            try {
                Buyer buyer = new Buyer("Buyer 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47" +
                                "a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Book book1 = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
                Book book2 = new Book("Book 2", "Store 2", "Romance", "A romantic book", 100);

                buyer.addToCart(book1, 5);
                buyer.addToCart(book2, 10);

                HashMap<Book, Integer> expected = new HashMap<>();
                expected.put(book1, 5);
                expected.put(book2, 10);

                assertEquals("Check adding to cart", buyer.getCart(), expected);

                // Add two more
                buyer.addToCart(book1, 2);
                expected.put(book1, 7);
                assertEquals("Adding to cart the same book should increase quantity", buyer.getCart(), expected);

                // Remove 8
                buyer.removeFromCart(book2, 8);
                expected.put(book2, 2);
                assertEquals("Removing from cart the same book should decrease quantity", buyer.getCart(), expected);

                // Try to remove more than are in there
                buyer.removeFromCart(book2, 5);
                expected.remove(book2);
                assertEquals("Removing too many books, should remove as many as are in the cart", buyer.getCart(),
                        expected);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }


    }
}
