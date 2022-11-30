package Client;

import Objects.*;
import Query.Query;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
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

    public static Marketplace marketplace;

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

            writer.writeObject("I need marketplace");
            BookApp.marketplace = (Marketplace) reader.readObject();

            System.out.println(marketplace);

            do {
                String loginSignup;
                boolean validUser = false;
                do {
                    System.out.println("1. Login\n2. Sign Up\n3. EXIT");
                    loginSignup = scan.nextLine();
                    if (!Objects.equals(loginSignup, "1") && !Objects.equals(loginSignup, "2") && !Objects.equals(loginSignup, "3")) {
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

                marketplace.saveMarketplace();

                // Main loop
                do {
                    Query currentUserQuery = queryServer(Query.Action.GET, null, "currentUser");
                    User currentUser = (User) currentUserQuery.getObject();
                    if (currentUser == null) {
                        break;
                    }
//                    User currentUser = marketplace.getCurrentUser();

                    if (currentUser instanceof Buyer) {
                        CustomerHomepage customerHomepage = new CustomerHomepage();
                        boolean mainMenu = customerHomepage.present(scan);
                        if (!mainMenu) {
                            break;
                        }
                    } else {
                        Seller seller = (Seller) marketplace.getCurrentUser();
                        boolean mainMenu = seller.editStore(scan);
                        if (!mainMenu) {
                            break;
                        }
                    }

                } while (true); // Main loop

                marketplace.saveMarketplace();
            } while (true); // Login or Sign up Loop

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR CONNECTING TO SERVER");
            System.out.println("Tip: Make sure to start the server first");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Query queryServer(Query.Action a, Object o, String opt) {
        try { // TODO: FINSIH
            Query query = new Query(o, opt);
            writer.writeObject(query);
            return (Query) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Query();
    }
}


