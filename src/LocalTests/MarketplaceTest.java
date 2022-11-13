package LocalTests;// Testing imports

import App.BookApp;
import Objects.Marketplace;
import Objects.Seller;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


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


    }
}
