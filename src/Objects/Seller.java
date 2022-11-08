package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Seller extends User implements Serializable {
    /**
     * List of the stores owned by Seller
     */
    private ArrayList<Store> stores;
    /**
     * Reference to class containing the statistics for Seller
     */
    private Stats stats;

    public Seller(String name, String email, String password) {
        super(name, email, password);

        this.stores = new ArrayList<>();
    }

    // lets user add new store which gets added to seller's store arraylist
    public void createNewStore() {
        System.out.println("CREATING A NEW STORE");
        System.out.println("*******************");
        System.out.println("Please enter a new store name (enter 0 to cancel):");
        Scanner scanner = new Scanner(System.in);
        String storeName = scanner.nextLine();

        if (storeName.equals("0")) {
            System.out.println("STORE CREATION CANCELED");
        } else {
            stores.add(new Store(storeName));
            System.out.println("STORE SUCCESSFULLY CREATED");
        }
    }

    // displays all stores a seller owns and lets seller select a store
    public Store selectStore() {
        Scanner scanner = new Scanner(System.in);

        // checks that seller owns at least 1 store and asks if user wants to make a new store
        if (stores.size() <= 0) {
            System.out.println("You currently do not own any stores.\nWould you like to create one?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            // brings up prompt to create a new store
            if (scanner.nextLine().equals("1")) {
                createNewStore();

                // prompts user to select store again from the updated list
                return selectStore();
            }

        } else {
            System.out.println("CHOOSE STORE TO EDIT");
            System.out.println("*******************");

            int storeSelection = -1;
            while (storeSelection > stores.size() || storeSelection < 0) {
                // displays stores the seller owns
                for (int i = 0; i < stores.size(); i++) {
                    System.out.println((i + 1) + ". " + stores.get(i).getName());
                }

                System.out.println((stores.size() + 1) + ". CANCEL");

                storeSelection = scanner.nextInt() - 1;
                scanner.nextLine();

                // invalid input
                if (storeSelection > stores.size())
                    System.out.println("That is not a valid input.");
            }

            //  selection was not the cancel option or invalid
            if (storeSelection < stores.size()) {
                System.out.println("CURRENT STORE SELECTION: " + stores.get(storeSelection).getName());
                return stores.get(storeSelection);
            }
        }

        return null;
    }

    public void editStore() {

    }

    public void editStoreName(Store store) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a new name (enter 0 to cancel):");
        String storeName = scanner.nextLine();

        if (storeName.equals("0")) {
            System.out.println("STORE RENAMING CANCELED");
        } else {
            store.setName(storeName);
        }
    }

    public void editStoreInventory(Store store) {
        // TODO: ADD AND REMOVE BOOK LISTINGS
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "Seller<" + this.getName() + ", " +
                this.getEmail() + ", " +
                this.getPassword() + ", " +
                stores + ", " +
                stats +
                ">";
    }
}
