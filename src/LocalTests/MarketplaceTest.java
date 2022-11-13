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
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * IMPORTANT: Running these tests WILL clear the market.
 * Do not use these tests if you have a market that you want to keep
 */

public class MarketplaceTest {

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
        public void testGetCurrentUser() {
            try {
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;
                Seller seller = new Seller("Seller",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.setCurrentUser(seller);

                Seller currentUser = (Seller) market.getCurrentUser();
                assertEquals("Check getting the current user", seller, currentUser);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }


        @Test(timeout = 1000)
        public void testSaveMarketplace() {
            try {
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;

                assertEquals("Check that initializing a market works", "Marketplace{users=[], currentUser=null}",
                        market.toString());

                Seller seller = new Seller("Seller",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.setCurrentUser(seller);
                market.addToUsers(seller);

                market.saveMarketplace();

                Marketplace newMarket = Marketplace.readMarketplace();

                String expected = "Marketplace{users=[Seller<Seller, Someone@email.com, " +
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144, " +
                        "[], Stats{booksSold={}, buyers={}}>], currentUser=null}";

                assertNotNull(newMarket);
                assertEquals("Check that adding users and saving works", expected, newMarket.toString());

                newMarket = new Marketplace();
                newMarket.clearMarketplace();

                assertEquals("Check that clearing the market works", "Marketplace{users=[], currentUser=null}",
                        newMarket.toString());

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testGetUser() {
            try {
                // Setup market
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;

                Seller seller1 = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Seller seller2 = new Seller("Seller 2",
                        "AnotherEmail2gmail.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Buyer buyer1 = new Buyer("Buyer 1",
                        "buyer@yahoo.bg",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.addToUsers(seller1);
                market.addToUsers(seller2);
                market.addToUsers(buyer1);
                market.saveMarketplace();

                assertNull("Getting a user that does not exist should return null", market.getUserByUsername("Null"));

                assertEquals("Make sure getting user by username works", seller1.toString().trim(), market.getUserByUsername("Seller 1").toString().trim());

                assertNull("Getting a user with a non-existent email should return null",
                        market.getUserByEmail("randomEmail@hotmail.com"));

                assertEquals("Make sure getting user by username works", buyer1.toString().trim(), market.getUserByEmail("buyer@yahoo" +
                        ".bg").toString().trim());

                market.clearMarketplace();

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testValidateUser() {
            try {
                // Setup market
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;

                Seller seller1 = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Seller seller2 = new Seller("Seller 1",
                        "AnotherEmail2gmail.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Buyer buyer1 = new Buyer("Buyer 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");

                market.addToUsers(seller1);
                market.saveMarketplace();

                assertFalse("Check that validate name works", market.validateName(seller2.getName()));

                assertFalse("Check that validate email works", market.validateEmail(buyer1.getEmail()));

                market.clearMarketplace();

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testGetSellerByBook() {
            try {
                // Setup market
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;

                Seller seller1 = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Seller seller2 = new Seller("Seller 1",
                        "AnotherEmail2gmail.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.addToUsers(seller1);
                market.addToUsers(seller2);
                market.saveMarketplace();

                Book book1 = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
                Book book2 = new Book("Book 2", "Store 2", "Romance", "A romantic book", 100);
                seller1.createNewStore("Store 1");
                seller2.createNewStore("Store 2");
                Store store1 = seller1.getStoreByName("Store 1");
                Store store2 = seller2.getStoreByName("Store 2");
                store1.addStock(10, book1);
                store2.addStock(17, book2);
                market.saveMarketplace();

                assertEquals("Check getting a seller by book", seller1, market.getSellerByBook(book1));
                assertEquals("Check getting a seller by book", seller2, market.getSellerByBook(book2));

                market.clearMarketplace();
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testGetStoreByName() {
            try {
                // Setup market
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;

                Seller seller1 = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Seller seller2 = new Seller("Seller 1",
                        "AnotherEmail2gmail.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.addToUsers(seller1);
                market.addToUsers(seller2);
                market.saveMarketplace();

                seller1.createNewStore("Store 1");
                seller2.createNewStore("Store 2");
                Store store1 = seller1.getStoreByName("Store 1");
                Store store2 = seller2.getStoreByName("Store 2");

                assertNotEquals("Check getting a store by name", store2, store1);

                market.clearMarketplace();
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testGetStores() {
            try {
                // Setup market
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;

                assertEquals("Check getting all market stores", "[]", market.getStores().toString().trim());

                Seller seller1 = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Seller seller2 = new Seller("Seller 1",
                        "AnotherEmail2gmail.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.addToUsers(seller1);
                market.addToUsers(seller2);
                market.saveMarketplace();

                seller1.createNewStore("Store 1");
                seller2.createNewStore("Store 2");

                String expected = "[Store{name=Store 1,\n" + "stock={},\n" + "reviews=[]}, Store{name=Store 2,\n" + "stock={},\n" + "reviews=[]}]";

                assertEquals("Check getting all market stores", expected.trim(), market.getStores().toString().trim());

                market.clearMarketplace();
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testGetBook() {
            try {
                // Setup market
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;

                Seller seller1 = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Seller seller2 = new Seller("Seller 1",
                        "AnotherEmail2gmail.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.addToUsers(seller1);
                market.addToUsers(seller2);
                market.saveMarketplace();

                Book book1 = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
                Book book2 = new Book("Book 2", "Store 2", "Romance", "A romantic book", 100);
                seller1.createNewStore("Store 1");
                seller2.createNewStore("Store 2");
                Store store1 = seller1.getStoreByName("Store 1");
                Store store2 = seller2.getStoreByName("Store 2");
                store1.addStock(10, book1);
                store2.addStock(17, book2);
                market.saveMarketplace();

                assertEquals("Check getting the quantity of a book", 10, market.getBookQuantity(book1));

                String expected = "{Book{name='Book 2', store='Store 2', genre='Romance', description='A romantic book', " +
                        "price=100.0}=17, Book{name='Book 1', store='Store 1', genre='Horror', description='Scary Book', " +
                        "price=100.0}=10}";
                assertEquals("Check getting all books from market", expected, market.getBooks().toString().trim());

                expected = "{Book{name='Book 1', store='Store 1', genre='Horror', description='Scary Book', price=100.0}=10}";
                assertEquals("Check that searching books works", expected, market.findBooks("Horror").toString().trim());

                market.clearMarketplace();
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testSortBooks() {
            try {
                // Setup market
                BookApp.marketplace = new Marketplace();
                Marketplace market = BookApp.marketplace;
                market.clearMarketplace();

                Seller seller1 = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                Seller seller2 = new Seller("Seller 1",
                        "AnotherEmail2gmail.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");
                market.addToUsers(seller1);
                market.addToUsers(seller2);
                market.saveMarketplace();

                Book book1 = new Book("Book 1", "Store 1", "Horror", "Scary Book", 50);
                Book book2 = new Book("Book 2", "Store 2", "Romance", "A romantic book", 100);
                Book book3 = new Book("Book 3", "Store 2", "Romance", "A romantic book", 20);
                seller1.createNewStore("Store 1");
                seller2.createNewStore("Store 2");
                Store store1 = seller1.getStoreByName("Store 1");
                Store store2 = seller2.getStoreByName("Store 2");
                store1.addStock(10, book1);
                store2.addStock(17, book2);
                store2.addStock(4, book3);
                market.saveMarketplace();

                HashMap<Book, Integer> books = market.getBooks();
                Book[] booksArr = new Book[books.size()];
                booksArr = books.keySet().toArray(booksArr);

                Marketplace.sortBooksByPrice(booksArr);
                String expected = "[Book{name='Book 3', store='Store 2', genre='Romance', description='A romantic book', " +
                        "price=20.0}, Book{name='Book 1', store='Store 1', genre='Horror', description='Scary Book', price=50.0}, " +
                        "Book{name='Book 2', store='Store 2', genre='Romance', description='A romantic book', price=100.0}]";
                assertEquals("", expected, Arrays.toString(booksArr).trim());

                booksArr = Marketplace.sortBooksByQuantity(books);
                expected = "[Book{name='Book 2', store='Store 2', genre='Romance', description='A romantic book', price=100.0}, " +
                        "Book{name='Book 1', store='Store 1', genre='Horror', description='Scary Book', price=50.0}, " +
                        "Book{name='Book 3', store='Store 2', genre='Romance', description='A romantic book', price=20.0}]";
                assertEquals("", expected, Arrays.toString(booksArr).trim());

                market.clearMarketplace();
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }


    }
}
