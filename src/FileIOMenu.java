import Exceptions.IdenticalStoreException;
import Objects.*;

import java.util.HashMap;

public class FileIOMenu extends Menu {

    public void sellerExport(User user) {
        if (!(user instanceof Seller)) {
            System.out.println("Buyers cannot export using this function!");
            return;
        }

        Seller seller = (Seller) user;

        // Give the seller some test data
        try {
            seller.createNewStore("Store 1");
        } catch (IdenticalStoreException e) {
            System.out.println("Error: You cannot create a store with the same name");
            return;
        }
        Store store = seller.getStoreByName("Store 1");
        HashMap<Book, Integer> stock1 = new HashMap<>();
        stock1.put(new Book("Book 1", store.getName(), "Spooky", "Super spooky book 1", 20), 4);
        stock1.put(new Book("Book 2", store.getName(), "Funny", "this is the funniest book ever", 50), 2);
        stock1.put(new Book("Book 3", store.getName(), "Bad", "Terrible book, don't recommend", 5), 6);
        store.setStock(stock1);

        System.out.println(seller.getSellerBooks());

        // Save to CSV

    }
}
