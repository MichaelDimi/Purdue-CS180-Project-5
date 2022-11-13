package LocalTests;// Testing imports

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

// Local Imports
import Objects.*;

import static org.junit.Assert.*;

/**
* This class contains test cases for 
* Methods in Book.java
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

public class BookTest {

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
        public void testSetPercentOff() {
            try {
                Book book = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
                book.setPercentOff(10);
                double actual = book.finalPrice();
                assertEquals("Check to make sure setPercentOff works correctly", 90, actual, 0);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testPrintBookListItem() {
            try {
                Book book = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
                book.printBookListItem(5, 10);
                String output = getOutput().trim(); // Call trim to ignore new lines
                assertEquals("Check printBookListItem()",
                        output,
                        "5. Book 1 -- Store: Store 1 -- Original Price: $100.00 -- Percent off: 0.00% -- Final price: $100.00 -- Quantity: [10]");

                outputStart(); // Clears the output

                book.printBookListItem(null, 10);
                output = getOutput().trim();
                assertEquals("Check printBookListItem() with i = null",
                        output,
                        "- Book 1 -- Store: Store 1 -- Original Price: $100.00 -- Percent off: 0.00% -- Final price: $100.00 -- Quantity: [10]");
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testHashCode() {
            try {
                Book book = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
                int actual = book.hashCode();
                int expected = 813932422;
                assertEquals("Check the hashcode() function", expected, actual);

                Book book2 = new Book("Book 2", "Store 2", "Romance", "A romantic book", 100);
                actual = book2.hashCode();
                expected = -772692187;
                assertEquals("Check the hashcode() function", expected, actual);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

        @Test(timeout = 1000)
        public void testEquals() {
            try {
                Book book = new Book("Book 1", "Store 1", "Horror", "Scary Book", 100);
                Book book2 = new Book("Book 2", "Store 2", "Romance", "A romantic book", 100);
                assertNotEquals("Check the equals() method", book, book2);

                Book book3 = new Book("Book 3", "Store 1", "Horror", "Scary Book", 100);
                Book book4 = new Book("Book 3", "Store 1", "Horror", "Scary Book", 100);
                assertEquals("Check the equals() method", book3, book4);
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

    }
}
