//package LocalTests;
//
//// Testing imports
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//
//import static org.junit.Assert.fail;
//
//// Local Imports
//import Objects.*;
//
//public class TestTemplate {
//
//    public static void main(String[] args) {
//        Result result = JUnitCore.runClasses(TestCase.class);
//        if (result.wasSuccessful()) {
//            System.out.println("Excellent - Tests ran successfully");
//        } else {
//            for (Failure failure : result.getFailures()) {
//                System.out.println(failure.toString());
//            }
//        }
//    }
//
//    public static class TestCase {
//
//        private final PrintStream originalOutput = System.out;
//        private final InputStream originalSysin = System.in;
//
//        @SuppressWarnings("FieldCanBeLocal")
//        private ByteArrayInputStream testIn;
//
//        @SuppressWarnings("FieldCanBeLocal")
//        private ByteArrayOutputStream testOut;
//
//        @Before
//        public void outputStart() {
//            testOut = new ByteArrayOutputStream();
//            System.setOut(new PrintStream(testOut));
//        }
//
//        @After
//        public void restoreInputAndOutput() {
//            System.setIn(originalSysin);
//            System.setOut(originalOutput);
//        }
//
//        @Test(timeout = 1000)
//        public void testTest() {
//            try {
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                fail();
//            }
//        }
//
//
//    }
//}
