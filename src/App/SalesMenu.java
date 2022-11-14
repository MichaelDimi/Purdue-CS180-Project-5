package App;

import java.util.HashMap;
import java.util.Scanner;
import Objects.*;

/**
* This class contains the menu in which a seller
Can create sales for their books.
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

public class SalesMenu {

    public void createSale(Scanner scan, User user) {

        if (user instanceof Buyer) {
            System.out.println("Whoops: Buyers cannot create sales");
            return;
        }

        Seller seller = (Seller) user;

        HashMap<Book, Integer> books = seller.getSellerBooks();
        if (books.isEmpty()) {
            System.out.println("You have no books.");
            System.out.println("Would you like to add a book? (Y/N):");
            String response = scan.nextLine();
            if (response.equalsIgnoreCase("y")) {
                if (!seller.addBookMenu(scan, null)) {
                    System.out.println("Whoops: You don't have any stores to add a book to");
                    return;
                }
            } else {
                return;
            }
            books = seller.getSellerBooks(); // Get the books again
        }

        BookApp.marketplace.saveMarketplace();

        Book[] booksArr = new Book[books.size()];
        booksArr = books.keySet().toArray(booksArr);

        System.out.println("Select a book below to add a sale to it: ");
        int i = 1;
        for (Book book : booksArr) {
            book.printBookListItem(i, books.get(book));
            i++;
        }
        System.out.println(i + ". EXIT");
        int response = Menu.selectFromList(i, scan);

        if (response == i) {
            return;
        } else {
             Book selectedBook = booksArr[response-1];
             for (Book b : books.keySet()) {
                 if (b.equals(selectedBook)) {
                     String confirm;
                     do {
                         System.out.println("What would you like the percent sale to be?");
                         System.out.println("- Eg. 20 = 20% off");
                         double percentOff = 0;
                         boolean percentOffInputError;
                         do {
                             percentOffInputError = false;
                             try {
                                 percentOff = Double.parseDouble(scan.nextLine());
                                 if (percentOff < 0 || percentOff > 100) {
                                     System.out.println("Percent off must be a number between 0 and 100");
                                     percentOffInputError = true;
                                 }
                             } catch (NumberFormatException e) {
                                 System.out.println("Percent off must be a number between 0 and 100");
                                 percentOffInputError = true;
                             }
                         } while (percentOffInputError);

                         b.setPercentOff(percentOff);

                         // Confirmation
                         System.out.println("Is this the final price you want: " + b.finalPrice() + " (Y/N)");
                         confirm = scan.nextLine();

                     } while (!confirm.equalsIgnoreCase("y"));

                     System.out.println("Setting percent off...");
                     try {
                         Thread.sleep(1000); // For dramatic effect
                     } catch (InterruptedException e) {
                         System.out.println("Error: Program interruption");
                     }
                     System.out.println("Done!");

                     break;
                 }
             }
        }
    }
}
