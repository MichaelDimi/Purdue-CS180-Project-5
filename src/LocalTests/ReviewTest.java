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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class contains all the test cases
 * for the Review.java class in Objects
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class ReviewTest {

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

        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000)
        public void testPrint() {
            try {
                Buyer buyer = new Buyer("Buyer 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66" +
                                "a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");

                Review review = new Review(3, buyer,
                        "Seller 1",
                        "This store was okay",
                        "The store could have been nicer, but it was okay");

                System.out.println(review.print());
                String output = getOutput().trim();

                String expected = "★ ★ ★ ☆ ☆\n" + "Buyer 1 says: This store was okay\n" +
                        "Description: The store " +
                        "could have been nicer, but it was okay\n" + "Owner: Seller 1";

                assertEquals("Check the review print function is returning a string", expected, output);

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testAddReview() {
            try {
                Buyer buyer = new Buyer("Buyer 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66" +
                                "a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");

                Review review = new Review(3, buyer,
                        "Seller 1",
                        "This store was okay",
                        "The store could have been nicer, but it was okay");
                Review review2 = new Review(1, buyer,
                        "Seller 1",
                        "Actually its pretty bad",
                        "I remember the store being really unfriendly");

                Seller seller = new Seller("Seller 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66" +
                                "a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");

                seller.createNewStore("Store 1");

                Store store = seller.getStoreByName("Store 1");

                store.getReviews().add(review);
                store.getReviews().add(review2);

                for (Review r : store.getReviews()) {
                    System.out.println(r.print());
                }
                String output = getOutput().trim();
                output = output.replaceAll("\r\n", "\n");
                String expected = "★ ★ ★ ☆ ☆\n" + "Buyer 1 says: This store was okay\n" +
                        "Description: The store could have been nicer, but it was okay\n" +
                        "Owner: Seller 1\n" + "★ ☆ ☆ ☆ ☆\n" + "Buyer 1 says: Actually its pretty bad\n" +
                        "Description: I remember the store being really unfriendly\n" +
                        "Owner: Seller 1";

                assertEquals("Check your reviews print and adding", expected, output);

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testStarDisplay() {
            try {
                Buyer buyer = new Buyer("Buyer 1",
                        "Someone@email.com",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66" +
                                "a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        "CyberSecure");

                Review review = new Review(3, buyer,
                        "Seller 1",
                        "This store was okay",
                        "The store could have been nicer, but it was okay");

                System.out.println(Review.starDisplay(null));
                String output = getOutput().trim();
                String expected = "No Reviews";
                assertEquals("Check your reviews starDisplay()", expected, output);

                outputStart();

                System.out.println(Review.starDisplay(review.getRating()));
                output = getOutput().trim();
                expected = "★ ★ ★ ☆ ☆";
                assertEquals("Check your reviews starDisplay()", expected, output);

            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }


    }
}