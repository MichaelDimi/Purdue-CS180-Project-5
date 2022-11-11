import Objects.Book;
import Objects.Marketplace;
import Objects.*;
import java.io.*;
import java.util.*;



public abstract class CustomerHomepage implements Serializable, Comparable<Double> {
    /**
     * Displays homepage view for buyer type user.
     * 1. Access Stores
     *      1.1 View list of stores and choose from them
     *      1.2 View products from chosen store
     *      1.3 View product (Can add to cart from here)
     * 2. View purchase history
     * 3. Search products (name, store or description)
     *      3.1 sort products on price or quantity
     * 4. Shopping cart
     */
    public static void DisplayOne(Scanner scan) {
        String choice = "";
        do {
            System.out.println("What would you like to do?\n1. View all Books for Sale\n2.Search for a Product\n" +
                    "3.View Purchase History\n4. View Shopping Cart\n5. Exit");
            choice = scan.nextLine();
            if (choice.equalsIgnoreCase("1")) {
                //get stores from .ser and desirialize and list them
                HashMap<Book, Integer> books = new HashMap<>(); //BookApp.marketplace.getBooks(); //getting hashmap of books
                for (int i = 0; i < 3; i++) {
                    String name = scan.nextLine();
                    String store = scan.nextLine();
                    String genre = scan.nextLine();
                    String desc = scan.nextLine();
                    double price = scan.nextDouble();
                    scan.nextLine();
                    int qty = Integer.parseInt(scan.nextLine());
                    books.put(new Book(name, store, genre, desc, price), qty);
                }
                System.out.println("How would you like to view the products?\n1. Sorted by price\n2. Sorted by quantity\n3. Not sorted");
                choice = scan.nextLine();
                if (choice.equals("1")) { //Lowest to highest
                    HashMap<Book, Double> bookPrice = new HashMap<>();
                    for (Book book: books.keySet()) {
                        bookPrice.put(book, book.getPrice());
                    }
                    List<Double> sortedbooks = new ArrayList<>(bookPrice.values());
                    Collections.sort(sortedbooks, Collections.reverseOrder());
                    System.out.println(sortedbooks);
                }
                for (Book book: books.keySet()) { //Printing list of books available for sale
                    System.out.println(book.toString() + "Qty: " + books.get(book));
                }
            } else if (choice.equalsIgnoreCase("2")) {
                System.out.println("Enter search query: ");
                String searchBook = scan.nextLine();
                HashMap<Book, Integer> books = BookApp.marketplace.getBooks(); //getting hashmap of books
                for (Book book: books.keySet()) { //Printing list of books available for sale
                    //String name = book.getName();
                    Book b = new Book(book);
                    //b.getName() changed
                    if(book.getName().equalsIgnoreCase(searchBook) || b.getStore().equalsIgnoreCase(searchBook)
                            || b.getDescription().equalsIgnoreCase(searchBook)) {
                        System.out.println(b.toString()); //need to format properly
                    }
                }
            } else if (choice.equalsIgnoreCase("3")) {
                if (BookApp.marketplace.getCurrentUser() instanceof Buyer) {
                    HashMap<Book, Integer> purchaseHist = ((Buyer) BookApp.marketplace.getCurrentUser()).getPurchaseHistory();
                    for (Book book: purchaseHist.keySet()) {
                        System.out.println(book.toString() + "\t" + String.valueOf(purchaseHist.get(book)));
                    }
                }
                
            } else if (choice.equalsIgnoreCase("4")) {
                HashMap<Book, Integer> cart = ((Buyer) BookApp.marketplace.getCurrentUser()).getCart();
                for (Book book: cart.keySet()) {
                    System.out.println(book.toString() + "\t" + String.valueOf(cart.get(book)));
                }
            } else if (choice.equalsIgnoreCase("5")) {
                return;
            } else {
                System.out.println("Whoops: Please enter a number from 1-5");
            }
        } while (!"12345".contains(choice));
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DisplayOne(scan);
    }
}