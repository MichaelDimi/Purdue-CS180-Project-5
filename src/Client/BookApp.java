package Client;

import Objects.*;
import Query.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class contains the main method and is the
 * center of our program. It runs on a loop until the
 * user exits.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class BookApp {
    public static ObjectOutputStream writer;
    public static ObjectInputStream reader;

//    public static Marketplace marketplace;

    public static User currentUser;

    public static String appName = "BOOK APP";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

//        BookApp.marketplace = new Marketplace();

        System.out.println("WELCOME to " + appName);
        System.out.println("*******************");

        // Figures out if user wants to log in or sign up
        // Login or Sign up Loop
        try {
            Socket socket = new Socket("localhost", 1111);

            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            do {
                String loginSignup;
                boolean validUser = false;
                do {
                    currentUser = null;
                    System.out.println("1. Login\n2. Sign Up\n3. EXIT");
                    loginSignup = scan.nextLine();
                    if (!loginSignup.equals("1") && !loginSignup.equals("2") && !loginSignup.equals("3")) {
                        System.out.println("Whoops: Please enter (1), (2), or (3)");
                        continue;
                    }

                    if (loginSignup.equals("1")) {
                        LoginMenu loginMenu = new LoginMenu();
                        validUser = loginMenu.present(scan); // if false, return to login or signup
                    } else if (loginSignup.equals("2")) {
                        SignUpMenu signUpMenu = new SignUpMenu();
                        validUser = signUpMenu.present(scan); // Ignore this if we are signing up
                    } else {
                        System.out.println("Thanks for using " + appName + "\nGoodbye!");
                        writer.close();
                        reader.close();
                        return;
                    }
                } while (!validUser);

                // Main loop
                do {
                    if (currentUser == null) {
                        break; // Exit to the login loop
                    }

                    if (currentUser instanceof Buyer) {
                        CustomerHomepage customerHomepage = new CustomerHomepage();
                        boolean mainMenu = customerHomepage.present(scan);
                        if (!mainMenu) {
                            break;
                        }
                    } else {
                        Seller seller = (Seller) currentUser;
                        boolean mainMenu = seller.editStore(scan);
                        if (!mainMenu) {
                            break;
                        }
                    }

                } while (true); // Main loop

            } while (true); // Login or Sign up Loop

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR CONNECTING TO SERVER");
            System.out.println("Tip: Make sure to start the server first");
        }
    }

    public static Query getQuery(Object o, String opt, String params) {
        try {
            GetQuery get = new GetQuery(o, opt, params);
            writer.writeObject(get);
            return (Query) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Query(false, "err");
    }

    public static Query computeQuery(String[] input, String opt, String params) {
        try {
            ComputeQuery get = new ComputeQuery(input, opt, params);
            writer.writeObject(get);
            return (Query) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Query(false, "err");
    }

    public static Query updateQuery(Object o, String opt, String params, Object newVal) {
        try {
            UpdateQuery update = new UpdateQuery(o, opt, params, newVal);
            writer.writeObject(update);
            return (Query) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Query(false, "err");
    }

    public static Query deleteQuery(String opt, String params) {
        try {
            DeleteQuery delete = new DeleteQuery(opt, params);
            writer.writeObject(delete);
            return (Query) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Query(false, "err");
    }
}


