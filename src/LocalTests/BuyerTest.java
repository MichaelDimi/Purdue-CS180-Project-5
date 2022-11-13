package LocalTests;// Testing imports

import App.BookApp;
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
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
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

        // TODO: Delete comments or fix test
//        @Test(timeout = 1000)
//        public void testPurchaseHistory() {
//            try {
//                // Add books to market
//                Book book1 = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
//                Book book2 = new Book("Book 2", "Store 2", "Romance", "A romantic book", 100);
//
//                BookApp.marketplace = new Marketplace();
//                Marketplace market = BookApp.marketplace;
//                Seller seller = new Seller("Seller 1",
//                        "seller@email.com",
//                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
//                        "CyberSecure");
//                seller.createNewStore("Store 1");
//                Store store = seller.getStoreByName("Store 1");
//                store.addStock(10, book1);
//                store.addStock(10, book2);
//
//                assertEquals(market.getBookQuantity(book1), "");
//
//                // Add buyer
//                Buyer buyer = new Buyer("Buyer 1",
//                        "Someone@email.com",
//                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
//                        "CyberSecure");
//
//                // Add buyer to cart
//                buyer.addToCart(book1, 5);
//                buyer.addToCart(book2, 10);
//                buyer.checkoutCart();
//
//                HashMap<Book, Integer> expected = new HashMap<>();
//                expected.put(book1, 5);
//                expected.put(book2, 10);
//
//                System.out.println(buyer.getPurchaseHistory());
//                System.out.println(expected);
//
//                assertEquals("Check purchasing", expected, buyer.getPurchaseHistory());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                fail();
//            }
//        }

    }
}
