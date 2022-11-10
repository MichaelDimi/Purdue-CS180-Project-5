import Exceptions.IdenticalStoreException;
import Objects.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileIOMenu extends Menu { // TODO: Make sure this function works

    public void sellerExport(User user) {
        if (!(user instanceof Seller)) {
            System.out.println("Buyers cannot export using this function!");
            return;
        }

        Seller seller = (Seller) user;

        // Give the seller some test data // TODO: REMOVE
        try {
            seller.createNewStore("Store 1");
        } catch (IdenticalStoreException e) {
            System.out.println("Whoops: You cannot create a store with the same name");
            return;
        }
        Store store = seller.getStoreByName("Store 1");
        HashMap<Book, Integer> stock1 = new HashMap<>();
        stock1.put(new Book("Book 1", store.getName(), "Spooky", "Super spooky book 1", 20), 4);
        stock1.put(new Book("Book 2", store.getName(), "Funny", "He said \"Haha\"", 50), 2);
        stock1.put(new Book("Book 3", store.getName(), "Bad", "Terrible book, don't recommend", 5), 6);
        stock1.put(new Book("Book 3", store.getName(), "Bad", "\"\"", 5), 6);
        store.setStock(stock1);

        // Save to CSV
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(seller.getName() + ".csv"));

            for (Book book : seller.getSellerBooks().keySet()) {
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
                pw.print(book.getPrice() + ",");
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

    public void sellerImportMenu(User user, Scanner scan) {
        if (!(user instanceof Seller)) {
            System.out.println("Buyers cannot import using this function!");
            return;
        }

        Seller seller = (Seller) user;
        System.out.println(seller.getSellerBooks());

        System.out.println("*******************");

        boolean error;
        do {
            System.out.println("Please enter the path to the data to import: ");
            System.out.println("- Note: Must be .csv ");
            String filePath = scan.nextLine();

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

    public boolean sellerImport(Scanner scan, Seller seller, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Whoops: We couldn't find your file. ");
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
                String storeName = splitLine.get(1);
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
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Whoops: Try that again");
                    }
                } while (true);

                Book addedBook = new Book(bookName, storeName, genre, description, price);
                System.out.println(addedBook);

                newStock.put(addedBook, quantity);

            }
            if (store != null) {
                store.setStock(newStock);
            } else {
                System.out.println("Whoops: Error, check your csv file and try again");
            }

            System.out.println(seller.getStores().get(0).getStock());

        } catch (IOException e) {
            System.out.println("Whoops: Couldn't read the contents of your file");
            return false;
        }

        return true;
    }
}
