package LocalTests;// Testing imports

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

import static org.junit.Assert.*;

/**
* This class contains test cases for 
* making sure hashing password and 
* creating and displaying user information
* works
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

public class UserTest {

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
        public void testPassword() {
            try {
                String rawPassword = "CyberSecure";
                String hashedPassword = User.hashPassword(rawPassword);

                User user = new User("Book 1", "dimitrm@purdue.edu", hashedPassword, rawPassword);
                assertEquals("Check to make sure hashing a password works",
                        "135ddb0636296c1cb0aa3f74bd852867a4dc64b97a9f4eb5d68586b47a4b66a6b86a17658fd95f0d28702b4f76ec1c028740caf671f2f50526f8e5a13ebcf144",
                        user.getPassword());

                assertEquals("Check to make sure displaying a password works",
                        "Cyb********",
                        user.getDisplayPassword());

                String newPassword = "newPassword";
                String newHashedPassword = User.hashPassword(newPassword);

                user.setPassword(newHashedPassword, newPassword);
                assertEquals("Check to make sure hashing a password works",
                        "f8a789b2e11b81dda0c014daaa852e62946b88c019be435a323792f0df538f5f95f4889997c1f882ba136e69303027e071d4e7cee5e63828e39b32f19c99c1f1",
                        user.getPassword());

                assertEquals("Check to make sure displaying a password works",
                        "new********",
                        user.getDisplayPassword());
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }

    }
}
