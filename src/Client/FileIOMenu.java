package Client;

import Objects.*;
import Query.Query;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class contains the menu in which
 * A seller can import or export their stock
 * list, and where buyers can export their
 * purchase history
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */
public class FileIOMenu extends Menu {

    public void fileIOMenu(User user) {
        if (user instanceof Seller) {
            String[] options = {"Export your stock", "Import stock list", "Cancel"};
            int response = JOptionPane.showOptionDialog(
                    null,
                    "Would you like to do?",
                    "Import or Export Stock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,     //no custom icon
                    options,  //button titles
                    options[0] //default button
            );

            if (response == 0) {
                sellerExport(user);
            } else if (response == 1) {
                sellerImportMenu(user);
            }
        } else {
            // Buyer export
            buyerExport(user);
        }
    }

    /**
     * Exports the stock of all a seller's stores to a .csv file
     * @param user The seller that whose stock will be exported
     */
    public void sellerExport(User user) {
        System.out.println("EXPORT STOCK");
        System.out.println("*******************");
        if (!(user instanceof Seller)) {
            System.out.println("Buyers cannot export using this function!");
            return;
        }

        Seller seller = (Seller) user;

        boolean noStock = true;
        for (Store store : seller.getStores()) {
            if (!store.getStock().isEmpty() && store.getStock() != null) {
                noStock = false;
                break;
            }
        }
        if (noStock) {
            System.out.println("You have no inventory to export");
            System.out.println("Press ENTER to continue");
            return;
        }

        File file;
        do {
            String filepath = JOptionPane.showInputDialog("Where would you like to save:");
            // cancel option selected
            if (filepath == null) return;
            if (!filepath.endsWith(".csv")) {
                System.out.println("Whoops: Invalid filetype - Must be '.csv'");
                continue;
            }
            try {
                file = new File(filepath);
                file.createNewFile();
                break;
            } catch (Exception e) {
                System.out.println("Whoops: Invalid filepath");
            }
        } while (true);

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));

            for (Book book : seller.getSellerBooks().keySet()) {
                // Escape with "...", if you see " anywhere add another. Anything containing quotes must also "..."
                String name = book.getName();
                if (name.contains("\"") || name.contains(",")) {
                    name = "\"" + name.replace("\"", "\"\"") + "\"";
                }
                String storeName = book.getStore(); // The seller will set the store when importing
                if (storeName.contains("\"") || storeName.contains(",")) {
                    storeName = "\"" + storeName.replace("\"", "\"\"") + "\"";
                }
                String genre = book.getGenre();
                if (genre.contains("\"") || genre.contains(",")) {
                    genre = "\"" + genre.replace("\"", "\"\"") + "\"";
                }
                String description = book.getDescription();
                if (description.contains("\"") || description.contains(",")) {
                    description = "\"" + description.replace("\"", "\"\"") + "\"";
                }

                pw.print(name + ",");
                pw.print(storeName + ",");
                pw.print(genre + ",");
                pw.print(description + ",");
                pw.print(book.finalPrice() + ",");
                pw.println("");
            }

            pw.write("");
            pw.flush();
            pw.close();
        } catch (IOException e) {
            System.out.println("Whoops: Failed to export. Please try again");
            return;
        }
    }


    public void buyerExport(User user) {
        if (!(user instanceof Buyer)) {
            JOptionPane.showMessageDialog(null, "Sellers cannot export using this function!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Buyer buyer = (Buyer) user;

        if (buyer.getPurchaseHistory().isEmpty() || buyer.getPurchaseHistory() == null) {
            JOptionPane.showMessageDialog(null, "You have no history to export",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        File file;
        do {
            String filepath = JOptionPane.showInputDialog("Where would you like to save:");
            // cancel option selected
            if (filepath == null) return;
            if (!filepath.endsWith(".csv")) {
                System.out.println("Whoops: Invalid filetype - Must be '.csv'");
                continue;
            }
            try {
                file = new File(filepath);
                file.createNewFile();
                break;
            } catch (Exception e) {
                System.out.println("Whoops: Invalid filepath");
            }
        } while (true);

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));

            for (Book book : buyer.getPurchaseHistory().keySet()) {
                // Escape with "...", if you see " anywhere add another. Anything containing quotes must also "..."
                String name = book.getName();
                if (name.contains("\"") || name.contains(",")) {
                    name = "\"" + name.replace("\"", "\"\"") + "\"";
                }
                String storeName = book.getStore();
                if (storeName.contains("\"") || storeName.contains(",")) {
                    storeName = "\"" + storeName.replace("\"", "\"\"") + "\"";
                }
                String genre = book.getGenre();
                if (genre.contains("\"") || genre.contains(",")) {
                    genre = "\"" + genre.replace("\"", "\"\"") + "\"";
                }
                String description = book.getDescription();
                if (description.contains("\"") || description.contains(",")) {
                    description = "\"" + description.replace("\"", "\"\"") + "\"";
                }

                pw.print(name + ",");
                pw.print(storeName + ",");
                pw.print(genre + ",");
                pw.print(description + ",");
                pw.print(book.finalPrice() + ",");
                pw.println("");
            }

            pw.write("");
            pw.flush();
            pw.close();

        } catch (IOException e) {
            System.out.println("Whoops: Failed to export. Please try again");
            return;
        }
    }

    /**
     * Presents a menu where the seller will type the data they want to import
     * @param user The seller whose data will import
     */
    public void sellerImportMenu(User user) {
        System.out.println("IMPORT STOCK");
        System.out.println("*******************");

        if (!(user instanceof Seller)) {
            System.out.println("Buyers cannot import using this function!");
            return;
        }

        Seller seller = (Seller) user;
//        System.out.println(seller.getSellerBooks());

        boolean error;
        do {
            System.out.println("Please enter the path to the data to import: ");
            System.out.println("Importing overrides your current stock - type \"CANCEL\" to stop");
            System.out.println("- Note: Must be .csv ");
            String filePath = JOptionPane.showInputDialog("Please enter the path to the data to import" +
                                                          "\nImporting overrides your current stock +" +
                                                          "\nNote: Must be .csv");
            // cancel option selected
            if (filePath == null) return;

            // check for .csv
            while (!filePath.endsWith(".csv")) {
                if (!filePath.endsWith(".csv")) {

                    System.out.println("Whoops: Your file did not end in .csv");
                    filePath = JOptionPane.showInputDialog("Whoops: Your file did not end in .csv" +
                            "\nPlease enter the path to the data to import" +
                            "\nNote: Must be .csv");
                    // cancel option selected
                    if (filePath == null) return;

                    if (filePath.isEmpty()) break;
                }
            }

            // Call sellerImport()
            error = !this.sellerImport(seller, filePath);
        } while (error);
    }

    /**
     * Handles importing user data. Data imported overrides current data for the seller.
     * @param seller The seller where the data will go
     * @param filePath The filepath for the data
     * @return True if the data was imported successfully, false if not
     */
    public boolean sellerImport(Seller seller, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Whoops: We couldn't find your file.");
            return false;
        }

//        try {
//            Thread.sleep(1000); // For dramatic effect
//        } catch (InterruptedException e) {
//            System.out.println("Error: Program interruption");
//        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Get the lines
            ArrayList<String> lines = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            br.close();

            Store store = null;
            HashMap<Book, Integer> newStock = new HashMap<>();
            for (String ln : lines) {
                ArrayList<String> splitLine = new ArrayList<>();

                int beginIndex = 0;
                for (int i = 0; i < 5; i++) { // We know the csv is 5 long
                    // If no quotes in the substring, then go from beginning to next comma
                    int nextIndex = ln.indexOf(',', beginIndex + 1);
                    String s = ln.substring(beginIndex, nextIndex);
                    if (s.contains("\"")) {
                        String traverse = ln.substring(beginIndex);
                        for (int j = 1; j < traverse.length(); j++) {
                            if (traverse.charAt(j) == '\"' && traverse.charAt(j + 1) == '\"') {
                                j++; // Skip the double quotes
                                continue;
                            }
                            if (traverse.charAt(j) == '\"') {
                                nextIndex = beginIndex + j; // Stop before end quote
                                break;
                            }
                        }
                        s = ln.substring(beginIndex + 1, nextIndex); // +1 to exclude start quote
                        nextIndex++; // skip end quote to start the next substring
                        // Replace double quotes with single
                        s = s.replace("\"\"", "\"");
                    }
                    beginIndex = nextIndex + 1;

                    splitLine.add(s);
                }

                // Save each split line to the seller - Check for mod 5 first
                if (splitLine.size() % 5 != 0) {
                    System.out.println("Whoops: Couldn't read the contents of your file");
                    return false;
                }
                String bookName = splitLine.get(0);
                String storeName = splitLine.get(1); // This won't matter
                String genre = splitLine.get(2);
                String description = splitLine.get(3);
                double price;
                try {
                    price = Double.parseDouble(splitLine.get(4));
                } catch (NumberFormatException e) {
                    System.out.println("Whoops: Couldn't read the contents of your file");
                    return false;
                }
                store = seller.getStoreByName(storeName);

                int quantity;
                do {
                    try {
                        String quantityInput = JOptionPane.showInputDialog("How much stock do you want for: " + bookName + "?");
                        // cancel option selected
                        if (quantityInput == null) return false;

                        quantity = Integer.parseInt(quantityInput);

                        if (quantity < 1) {
                            System.out.println("Whoops: Try that again");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Whoops: Try that again");
                    }
                } while (true);

                do {
                    // gets updated list of all stores from server
                    Query storesQuery = new ClientQuery().getQuery(null, "stores", "*");
                    if (storesQuery.getObject().equals(false)) {
                        JOptionPane.showMessageDialog(null, "Whoops: There was an error getting the stores from the server");
                    }

                    ArrayList<Store> stores = (ArrayList<Store>) storesQuery.getObject();

                    // array contains names of all stores
                    String[] storeNamesArr = new String[stores.size()];

                    // gets the name of all stores
                    for (int i = 0; i < storeNamesArr.length; i++) {
                        storeNamesArr[i] = stores.get(i).getName();
                    }

                    if (storeNamesArr.length < 1) {
                        JOptionPane.showMessageDialog(null, "There are no stores in the market yet\nYou can create an new account and become a seller to open a store");
                        return false;
                    }

                    storeName = JOptionPane.showInputDialog(null, "Which store do you want to set the stock to?", "Choose Store", JOptionPane.PLAIN_MESSAGE, null, storeNamesArr, storeNamesArr[0]).toString();

                    if (seller.getStoreByName(storeName) != null) {
                        break;
                    }
                    System.out.println("Whoops: Couldn't find your store, please try again");
                } while (true);

                Book addedBook = new Book(bookName, storeName, genre, description, price);

                newStock.put(addedBook, quantity);

            }
            if (store != null) {
                // updates stock on server
                Query setStockQuery = new ClientQuery().updateQuery(store, "stock", "set", newStock);
                if (setStockQuery.getObject().equals(false)) {
                    System.out.println("Whoops: Couldn't set stock of store");
                    return false;
                }

                // updates local stock
                store.setStock(newStock);
            } else {
                System.out.println("Whoops: Error, check your csv file and try again");
            }

        } catch (IOException e) {
            System.out.println("Whoops: Couldn't read the contents of your file");
            return false;
        }

        return true;
    }
}
