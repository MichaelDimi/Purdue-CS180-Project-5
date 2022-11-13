package Objects;

import java.io.Serializable;
import java.util.*;

public class SellerStats implements Serializable {

    /**
     * The number and type of books sold by the Seller
     */
    private HashMap<Book, Integer> booksSold;
    /**
     * The total amount of money the Seller has made
     */
    private double revenue;
    /**
     * The Buyers who has bought books from this Seller
     */
    private HashMap<User, Integer> buyers;

    public SellerStats(HashMap<Book, Integer> booksSold, int revenue, HashMap<User, Integer> buyers) {
        this.booksSold = booksSold;
        this.revenue = revenue;
        this.buyers = buyers;
    }

    public SellerStats() {
        this.booksSold = new HashMap<>();
        this.revenue = 0;
        this.buyers = new HashMap<>();
    }

    // TODO: These suck: listAllSoldBooks, listSoldBooks, listAllBuyers, listBuyers - Abstract more!
    // formats and prints all books Seller has sold
    public void listAllSoldBooks(Scanner scanner) {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO SOLD BOOKS");
        } else {

            System.out.println("How would you like to sort books?");
            System.out.println("1. Most frequently bought books");
            System.out.println("2. Least frequently bought books");
            System.out.println("3. Not sorted");

            // displays all books
            String sortSelection;
            do {
                sortSelection = scanner.nextLine();

                // displays all books
                System.out.println("YOUR SOLD BOOKS");
                System.out.println("*******************");

                // Books sorted by quantity sold
                Book[] sortedBooks = Marketplace.sortBooksByQuantity(booksSold);
                switch (sortSelection) {
                    case "1":
                        // sorted by most frequent
                        for (Book book : sortedBooks) {
                            book.printBookListItem(null, booksSold.get(book));
                        }
                        break;
                    case "2":
                        // sorted by least frequent
                        // prints the sorted array in reverse
                        for (int i = sortedBooks.length - 1; i >= 0; i--) {
                            sortedBooks[i].printBookListItem(null, booksSold.get(sortedBooks[i]));
                        }

                        break;
                    case "3":
                        // no sort
                        for (Book book : booksSold.keySet()) {
                            book.printBookListItem(null, booksSold.get(book));
                        }
                        break;
                }
            } while (!"123".contains(sortSelection));
        }
    }

    // prints all sold books from a specific store
    public void listSoldBooks(Scanner scanner, Store store) {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO SOLD BOOKS");
        } else {

            System.out.println("How would you like to sort books?");
            System.out.println("1. Most frequently bought books");
            System.out.println("2. Least frequently bought books");
            System.out.println("3. Not sorted");

            // displays all books
            String sortSelection;
            do {
                sortSelection = scanner.nextLine();

                // displays all books
                System.out.println("YOUR SOLD BOOKS");
                System.out.println("*******************");

                // Books sorted by quantity sold
                Book[] sortedBooks = Marketplace.sortBooksByQuantity(booksSold);
                switch (sortSelection) {
                    case "1":
                        // sorted by most frequent
                        for (Book book : sortedBooks) {
                            if (book.getStore().equals(store.getName())) {
                                book.printBookListItem(null, booksSold.get(book));
                            }
                        }
                        break;
                    case "2":
                        // sorted by least frequent
                        // prints the sorted array in reverse
                        for (int i = sortedBooks.length - 1; i >= 0; i--) {
                            if (sortedBooks[i].getStore().equals(store.getName())) {
                                sortedBooks[i].printBookListItem(null, booksSold.get(sortedBooks[i]));
                            }
                        }

                        break;
                    case "3":
                        // no sort
                        for (Book book : booksSold.keySet()) {
                            // checks book is from the specified store
                            if (book.getStore().equals(store.getName())) {
                                book.printBookListItem(null, booksSold.get(book));
                            }
                        }
                        break;
                }
            } while (!"123".contains(sortSelection));
        }
    }

    // formats and prints all the customers of Seller
    public void listAllBuyers(Scanner scanner) {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO BUYERS");
        } else {

            System.out.println("How would you like to sort buyers?");
            System.out.println("1. Most frequent purchases");
            System.out.println("2. Least frequent purchases");
            System.out.println("3. Not sorted");

            String sortSelection;
            do {
                sortSelection = scanner.nextLine();

                // displays all books
                System.out.println("YOUR CUSTOMERS");
                System.out.println("*******************");

                // Buyers sorted by the amount of transactions they have with the Seller
                Buyer[] sortedBuyers = sortBuyersByQuantity(buyers);
                switch (sortSelection) {
                    case "1":
                        // sorted by most frequent
                        for (Buyer buyer : sortedBuyers) {
                            System.out.println(buyer.getName() + " | Purchases: " + buyers.get(buyer));

                            for (Book book : buyer.getPurchaseHistory().keySet()) {
                                book.printBookListItem(null, buyer.getPurchaseHistory().get(book));
                            }
                        }
                        break;
                    case "2":
                        // sorted by least frequent
                        // prints the sorted array in reverse
                        for (int i = sortedBuyers.length - 1; i >= 0; i--) {
                            System.out.println(sortedBuyers[i].getName() + " | Purchases: " + buyers.get(sortedBuyers[i]));

                            for (Book book : sortedBuyers[i].getPurchaseHistory().keySet()) {
                                book.printBookListItem(null, sortedBuyers[i].getPurchaseHistory().get(book));
                            }
                        }

                        break;
                    case "3":
                        // no sort
                        for (User buyer : buyers.keySet()) {
                            System.out.println(buyer.getName() + " | Purchases: " + buyers.get(buyer));

                            for (Book book : ((Buyer) buyer).getPurchaseHistory().keySet()) {
                                book.printBookListItem(null, ((Buyer) buyer).getPurchaseHistory().get(book));
                            }
                        }
                        break;
                }
            } while (!"123".contains(sortSelection));
        }
    }

    public void listBuyers(Scanner scanner, Store store) {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO BUYERS");
        } else {

            System.out.println("How would you like to sort buyers?");
            System.out.println("1. Most frequent purchases");
            System.out.println("2. Least frequent purchases");
            System.out.println("3. Not sorted");

            String sortSelection;
            do {
                sortSelection = scanner.nextLine();

                // displays all books
                System.out.println("YOUR CUSTOMERS");
                System.out.println("*******************");

                // Buyers sorted by the amount of transactions they have with the Seller
                Buyer[] sortedBuyers = sortBuyersByQuantity(buyers);
                switch (sortSelection) {
                    case "1":
                        // sorted by most frequent
                        for (Buyer buyer : sortedBuyers) {
                            System.out.println(buyer.getName() + " | Purchases: " + buyers.get(buyer));

                            for (Book book : buyer.getPurchaseHistory().keySet()) {
                                if (book.getStore().equals(store.getName())) {
                                    book.printBookListItem(null, buyer.getPurchaseHistory().get(book));
                                }
                            }
                        }
                        break;
                    case "2":
                        // sorted by least frequent
                        // prints the sorted array in reverse
                        for (int i = sortedBuyers.length - 1; i >= 0; i--) {
                            System.out.println(sortedBuyers[i].getName() + " | Purchases: " + buyers.get(sortedBuyers[i]));

                            for (Book book : sortedBuyers[i].getPurchaseHistory().keySet()) {
                                if (book.getStore().equals(store.getName())) {
                                    book.printBookListItem(null, sortedBuyers[i].getPurchaseHistory().get(book));
                                }
                            }
                        }

                        break;
                    case "3":
                        // no sort
                        for (User buyer : buyers.keySet()) {
                            System.out.println(buyer.getName() + " | Purchases: " + buyers.get(buyer));

                            for (Book book : ((Buyer) buyer).getPurchaseHistory().keySet()) {
                                if (book.getStore().equals(store.getName())) {
                                    book.printBookListItem(null, ((Buyer) buyer).getPurchaseHistory().get(book));
                                }
                            }
                        }
                        break;
                }
            } while (!"123".contains(sortSelection));
        }
    }

    // prints the most popular genre the Seller sells
    public void listMostPopularGenre() {
        if (booksSold.size() == 0) {
            System.out.println("YOU CURRENTLY HAVE NO SOLD BOOKS");
        } else {
            // holds all book genres that have been sold
            HashMap<String, Integer> genres = new HashMap<>();

            // generates hasmap based on genres
            for (Book book : booksSold.keySet()) {
                String bookGenre = book.getGenre();
                // current quantity of specified book
                Integer newBookCount = genres.get(bookGenre);

                // checks if user already has book in cart, increments current quantity if so
                if (newBookCount == null) { // could be replaced with merge, not sure if Vocareum will like?
                    genres.put(bookGenre, booksSold.get(book));
                } else {
                    genres.put(bookGenre, newBookCount + booksSold.get(book));
                }
            }

            // gets most popular genre
            String mostPopular = "";
            for (String genre : genres.keySet()) {
                int numOfOccurrence = (genres.get(mostPopular) == null) ? 0 : genres.get(mostPopular);
                if (genres.get(genre) > numOfOccurrence) {
                    mostPopular = genre;
                }
            }

            System.out.println("YOUR MOST POPULAR GENRE IS: " + mostPopular);
        }
    }

    // sorts Buyers by the frequency of their purchases
    public Buyer[] sortBuyersByQuantity(HashMap<User, Integer> buyers) {
        int n = buyers.size();

        Buyer[] booksArr = new Buyer[buyers.size()];
        booksArr = buyers.keySet().toArray(booksArr);

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (buyers.get(booksArr[j]) < buyers.get(booksArr[j+1])) {
                    Buyer temp = booksArr[j];
                    booksArr[j] = booksArr[j+1];
                    booksArr[j+1] = temp;
                }
            }
        }

        return booksArr;
    }

    public HashMap<Book, Integer> getBooksSold() {
        return booksSold;
    }

    public void setBooksSold(HashMap<Book, Integer> booksSold) {
        this.booksSold = booksSold;
    }

    public HashMap<User, Integer> getBuyers() {
        return buyers;
    }

    public void setBuyers(HashMap<User, Integer> buyers) {
        this.buyers = buyers;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void incrementRevenue(double profit) {
        this.revenue += profit;
    }

    public double getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return "Stats{" + "booksSold=" + booksSold + ", buyers=" + buyers + '}';
    }
}
