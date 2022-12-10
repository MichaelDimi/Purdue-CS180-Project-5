package Server;

import Client.BookApp;
import Query.*;
import Objects.*;

import java.util.*;

public class Helpers {

    private Marketplace marketplace;

    public Helpers(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    synchronized public GetQuery get(GetQuery get) {  // Always have * as first case and default (no opt or params) as last
        String opt = get.getOptions();
        String params = get.getParams();
        switch (opt) {
            case "users":
                switch (params) {
                    case "*":
                        get.setObject(marketplace.getUsers());
                        break;
                    case "name":
                        get.setObject(marketplace.getUserByUsername((String) get.getObject()));
                        break;
                    case "currentUser":
                        User user = (User) get.getObject();
                        if (user == null) return new GetQuery(null, "", "");
                        String username = user.getName();
                        if (username == null) return new GetQuery(null, "", "");
                        user = marketplace.getUserByUsername(username);
                        if (user == null) return new GetQuery(null, "", "");
                        get.setObject(user);
                        break;
                    default:
                        break; // Sends the query back as null
                }
                break;
            case "purchaseHistory":
                switch (params) {
                    case "*":
                        get.setObject(((Buyer) get.getObject()).getPurchaseHistory());
                        break;
                    default:
                        break; // Sends the query back as null
                }
                break;
            case "books":
                switch (params) {
                    case "*":
                        get.setObject(marketplace.getBooks());
                        break;
                    case "find":
                        get.setObject(marketplace.findBooks((String) get.getObject()));
                        break;
                    case "quantity":
                        HashMap<Book, Integer> books = marketplace.getBooks();
                        get.setObject(books.get((Book) get.getObject()));
                        break;
                }
                break;
            case "stores":
                switch (params) {
                    case "*":
                        get.setObject(marketplace.getStores());
                        break;
                    case "name": {
                        String storeName = (String) get.getObject();
                        get.setObject(marketplace.getStoreByName(storeName));
                        System.out.println(storeName);
                        break;
                    }
                    case "user":
                        String sellerName = ((Seller) get.getObject()).getName();
                        ArrayList<Store> ownedStores = new ArrayList<>();

                        for (Store store : marketplace.getStores()) {
                            if (store.getSellerName().equals(sellerName)) {
                                ownedStores.add(store);
                            }
                        }

                        get.setObject(ownedStores);
                        break;
                    case "books": {
                        String storeName = (String) get.getObject();
                        get.setObject(marketplace.getStoreByName(storeName).getStock());
                    }
                }
                break;
            case "cart":
                switch (params) {
                    case "currentBuyer":
                        User user = (User) get.getObject();
                        if (user == null) return new GetQuery(null, "", "");
                        String username = user.getName();
                        if (username == null) return new GetQuery(null, "", "");
                        user = marketplace.getUserByUsername(username);
                        if (user == null) return new GetQuery(null, "", "");
                        get.setObject(((Buyer) user).getCart());
                        break;
                    default:
                        break; // Sends the query back as null
                }
                break;
            case "sellers":
                switch (params) {
                    case "book":
                        Book book = (Book) get.getObject();
                        if (book == null) break;
                        System.out.println(marketplace.getSellerByBook(book));
                        get.setObject(marketplace.getSellerByBook(book));
                        break;
                }
                break;
            default:
                break;
        }
        return get;
    }

    synchronized public Query update(UpdateQuery update) {
        String opt = update.getOptions();
        String params = update.getParams();
        switch (opt) {
            case "users":
                switch (params) {
                    case "add": {
                        String[] input = (String[]) update.getNewVal();
                        String username = input[0];
                        String email = input[1];
                        String rawPassword = input[2];
                        boolean isBuyer = input[3].equals("T");

                        boolean isValidName = marketplace.validateName(username);
                        boolean isValidEmail = marketplace.validateEmail(email);
                        boolean validationSuccess = isValidEmail && isValidName;
                        if (!validationSuccess) return new Query(false, "validation err");

                        // HASHING
                        String hashedPassword = User.hashPassword(rawPassword);
                        if (hashedPassword == null) {
                            return new Query(false, "hash err");
                        }

                        User newUser;
                        if (isBuyer) {
                            newUser = new Buyer(username, email, hashedPassword, rawPassword);
                        } else {
                            newUser = new Seller(username, email, hashedPassword, rawPassword);
                        }

                        marketplace.addToUsers(newUser);
                        return new Query(newUser, "");
                    }
                    case "name": {
                        User user = (User) update.getObject();
                        if (user == null) break;
                        user = marketplace.getUserByUsername(user.getName());
                        if (user == null) break;
                        user.setName(update.getNewVal().toString());
                        return new Query(true, "");
                    }
                    case "email": {
                        User user = (User) update.getObject();
                        if (user == null) break;
                        user = marketplace.getUserByUsername(user.getName());
                        if (user == null) break;
                        user.setEmail(update.getNewVal().toString());
                        return new Query(true, "");
                    }
                    case "password": {
                        User user = (User) update.getObject();
                        if (user == null) break;
                        user = marketplace.getUserByUsername(user.getName());
                        if (user == null) break;
                        String[] input = (String[]) update.getNewVal();
                        user.setPassword(input[0], input[1]);
                        return new Query(true, "");
                    }
                    case "cart": {
                        Buyer user = (Buyer) update.getObject();
                        if (user == null) break;
                        user = (Buyer) marketplace.getUserByUsername(user.getName());
                        if (user == null) break;
                        HashMap<Book, Integer> input = (HashMap<Book, Integer>) update.getNewVal();
                        user.setCart(input);
                        return new Query(true, "");
                    }
                    case "purchaseHistory": {
                        Buyer user = (Buyer) update.getObject();
                        if (user == null) break;
                        user = (Buyer) marketplace.getUserByUsername(user.getName());
                        if (user == null) break;
                        HashMap<Book, Integer> input = (HashMap<Book, Integer>) update.getNewVal();
                        user.setPurchaseHistory(input);
                        return new Query(true, "");
                    }
                }
                break;
            case "stores":
                switch (params) {
                    case "add": {
                        Seller seller = (Seller) update.getObject();
                        if (seller == null) break;
                        User user = marketplace.getUserByUsername(seller.getName());
                        if (user instanceof Seller) {
                            seller = (Seller) user;
                        } else break;
                        ArrayList<Store> stores = seller.getStores();
                        stores.add((Store) update.getNewVal());
                        return new Query(true, "");
                    }
                    case "reviews": {
                        Store store = (Store) update.getObject();
                        if (store == null) break;
                        store = marketplace.getStoreByName(store.getName());
                        if (store == null) break;
                        store.setReviews((ArrayList<Review>) update.getNewVal());
                        return new Query(true, "");
                    }
                    case "name":
                        Store store = (Store) update.getObject();
                        String newName = (String) update.getNewVal();

                        if (newName.equals(store.getName())) {
                            return new Query(false, "taken");
                        }

                        store = marketplace.getStoreByName(store.getName());
                        store.setName(newName);
                        return new Query(true, "");
                }
                break;
            case "stock":
                switch (params) {
                    case "set": {
                        Store store = (Store) update.getObject();
                        if (store == null) break;
                        store = marketplace.getStoreByName(store.getName());
                        if (store == null) break;
                        store.setStock((HashMap<Book, Integer>) update.getNewVal());
                        return new Query(true, "");
                    }
                    case "name": {
                        Book bookToEdit = (Book) update.getObject();
                        if (bookToEdit == null) break;
                        String storeName = bookToEdit.getStore();
                        if (storeName == null) break;
                        Store store = marketplace.getStoreByName(storeName);
                        if (store == null) break;
                        HashMap<Book, Integer> stock = store.getStock();

                        int currentBookQuantity = stock.get(bookToEdit);

                        // removes old Book object and adds new Book object with the updated name
                        bookToEdit.setName((String) update.getNewVal());
                        stock.remove(bookToEdit);
                        stock.put(bookToEdit, currentBookQuantity);

                        return new Query(true, "");
                    }
                    case "genre": {

                    }

                }
        }
        return new Query(false, "err");
    }

    synchronized public Query delete(DeleteQuery delete) {
        String opt = delete.getOptions();
        switch (opt) {
            case "users": {
                User user = (User) delete.getObject();
                if (user == null) break;
                String username = user.getName();
                if (username == null) break;
                user = marketplace.getUserByUsername(username);
                if (user == null) break;
                marketplace.getUsers().remove(user);
                return new Query(true, "");
            }
            case "stores": {
                Store store = (Store) delete.getObject();
                if (store == null) break;
                String storeName = store.getName();
                if (storeName == null) break;
                store = marketplace.getStoreByName(storeName);
                if (store == null) break;
                for (User user : marketplace.getUsers()) {
                    if (user instanceof Seller) {
                        ((Seller) user).getStores().remove(store);
                    }
                }
                System.out.println(marketplace.getStores());
                return new Query(true, "");
            }
            case "cart": {
                Buyer user = (Buyer) delete.getObject();
                if (user == null) break;
                user = (Buyer) marketplace.getUserByUsername(user.getName());
                if (user == null) break;
                System.out.println(user);
                user.clearCart();
                return new Query(true, "");
            }
//            case "stock": {
//                Book book = (Book) delete.getObject();
//                Store store = (Store) delete.getParams();
//                if (book == null || store == null) break;
//                HashMap<Book, Integer> stock = store.getStock();
//                if (stock == null) break;
//                stock.remove(book);
//                return new Query(true, "");
//            }
        }
        return new Query(false, "err: Couldn't find opt/params");
    }

    synchronized public Query compute(ComputeQuery compute) {
        String opt = compute.getOptions();
        String params = compute.getParams();
        switch (opt) {
            case "users":
                switch (params) {
                    case "login": {
                        String[] input = (String[]) compute.getObject();
                        String usernameEmail = input[0];
                        String hashedPassword = input[1];
                        ArrayList<User> users = marketplace.getUsers();

                        User returningUser = null;

                        for (User user : users) {
//                            System.out.println(user);
                            if (user.getName().equals(usernameEmail) || user.getEmail().equals(usernameEmail)) {
                                if (user.getPassword().equals(hashedPassword)) {
                                    if (user.getName().equals(usernameEmail)) {
                                        returningUser = marketplace.getUserByUsername(usernameEmail);
                                    } else {
                                        returningUser = marketplace.getUserByEmail(usernameEmail);
                                    }
                                    break;
                                }
                            }
                        }
                        return new Query(returningUser, null);
                    } // Use blocks to scope variables, and collapse in intellij
                    case "validate name": {
                        String[] input = (String[]) compute.getObject();
                        String username = input[0];
                        boolean isValidName = marketplace.validateName(username);
                        return new Query(isValidName, "");
                    }
                    case "validate email": {
                        String[] input = (String[]) compute.getObject();
                        String email = input[0];
                        boolean isValidEmail = marketplace.validateEmail(email);
                        return new Query(isValidEmail, "");
                    }
                    default:
                        break; // Sends the query back as null
                }
                break;
            default:
                break;
        }
        return new Query(false, "err: Couldn't find opt/params");
    }
}
