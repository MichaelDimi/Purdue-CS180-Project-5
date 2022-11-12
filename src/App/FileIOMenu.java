package App;

import Objects.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileIOMenu extends Menu {

    public void fileIOMenu(Scanner scan, User user) {
        System.out.println("IMPORT OR EXPORT STOCK");
        System.out.println("*******************");

        if (user instanceof Seller) {
            String response;
            do {
                System.out.println("Would you like to ");
                System.out.println("1. Export your stock");
                System.out.println("2. Import stock list");
                System.out.println("3. CANCEL");
                response = scan.nextLine();
                if (!response.equals("1") && !response.equals("2") && !response.equals("3")) {
                    System.out.println("Whoops: Please enter (1), (2), or (3)");
                }
            } while (!response.equals("1") && !response.equals("2") && !response.equals("3"));

            if (response.equals("1")) {
                sellerExport(scan, user);
            } else if (response.equals("2")) {
                sellerImportMenu(user, scan);
            } else {
                return;
            }
        } else {
            System.out.println("For your own privacy please confirm that you want to export your purchase history:");
            System.out.println("- Enter 'CONFIRM':");
            String response = scan.nextLine();
            response = response.trim();
            if (!response.equals("CONFIRM")) {
                System.out.println("Whoops: Confirmation failed");
            } else {
                buyerExport(scan, user);
            }
        }
    }

    /**
     * Exports the stock of all a seller's stores to a .csv file
     * @param user The seller that whose stock will be exported
     * @param scan The scanner to get where the user wants to save the file
     */
    public void sellerExport(Scanner scan, User user) {
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
            scan.nextLine();
            return;
        }

        File file;
        do {
            System.out.println("Where would you like to save: ");
            String filepath = scan.nextLine();
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


    public void buyerExport(Scanner scan, User user) {
        System.out.println("EXPORT PURCHASE HISTORY");
        System.out.println("*******************");
        if (!(user instanceof Buyer)) {
            System.out.println("Sellers cannot export using this function!");
            return;
        }

        Buyer buyer = (Buyer) user;

        if (buyer.getPurchaseHistory().isEmpty() || buyer.getPurchaseHistory() == null) {
            System.out.println("You have no history to export");
            System.out.println("Press ENTER to continue");
            scan.nextLine();
            return;
        }

        File file;
        do {
            System.out.println("Where would you like to save: ");
            String filepath = scan.nextLine();
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
     * @param scan The scanner for getting user input
     */
    public void sellerImportMenu(User user, Scanner scan) {
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
            String filePath = scan.nextLine();
            if (filePath.equals("CANCEL")) {
                return;
            }

            // check for .csv
            while (!filePath.endsWith(".csv")) {
                if (!filePath.endsWith(".csv")) {

                    System.out.println("Whoops: Your file did not end in .csv");
                    System.out.println("Try again or hit ENTER to cancel");
                    filePath = scan.nextLine();
                    if (filePath.isEmpty()) break;
                }
            }

            // Call sellerImport()
            error = !this.sellerImport(scan, seller, filePath);
        } while (error);
    }

    /**
     * Handles importing user data. Data imported overrides current data for the seller.
     * @param scan The scanner to get user input
     * @param seller The seller where the data will go
     * @param filePath The filepath for the data
     * @return True if the data was imported successfully, false if not
     */
    public boolean sellerImport(Scanner scan, Seller seller, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Whoops: We couldn't find your file.");
            return false;
        }

        System.out.println("Importing data... ");
        try {
            Thread.sleep(1000); // For dramatic effect
        } catch (InterruptedException e) {
            System.out.println("Error: Program interruption");
        }

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
                    int nextIndex = ln.indexOf(',', beginIndex+1);
                    String s = ln.substring(beginIndex, nextIndex);
                    if (s.contains("\"")) {
                        String traverse = ln.substring(beginIndex);
                        for (int j = 1; j < traverse.length(); j++) {
                            if (traverse.charAt(j) == '\"' && traverse.charAt(j+1) == '\"') {
                                j++; // Skip the double quotes
                                continue;
                            }
                            if (traverse.charAt(j) == '\"') {
                                nextIndex = beginIndex+j; // Stop before end quote
                                break;
                            }
                        }
                        s = ln.substring(beginIndex+1, nextIndex); // +1 to exclude start quote
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

                System.out.println("How much would stock do you want for: " + bookName + "?");
                int quantity;
                do {
                    try {
                        quantity = Integer.parseInt(scan.nextLine());
                        if (quantity < 1) {
                            System.out.println("Whoops: Try that again");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Whoops: Try that again");
                    }
                } while (true);
                System.out.println("Which store do you want to set the stock to?");
                do {
                    storeName = scan.nextLine();
                    if (seller.getStoreByName(storeName) != null) {
                        break;
                    }
                    System.out.println("Whoops: Couldn't find your store, please try again");
                } while (true);

                Book addedBook = new Book(bookName, storeName, genre, description, price);

                newStock.put(addedBook, quantity);

            }
            if (store != null) {
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
