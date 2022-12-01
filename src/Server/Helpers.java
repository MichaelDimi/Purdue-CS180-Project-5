package Server;

import Client.BookApp;
import Query.*;
import Objects.*;

import java.util.ArrayList;

public class Helpers {

    private Marketplace marketplace;

    public Helpers(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public GetQuery get(GetQuery get) {  // Always have * as first case and default (no opt or params) as last
        String opt = get.getOptions();
        String params = get.getParams();
        switch (opt) {
            case "users":
                switch (params) {
                    case "*":
                        get.setObject(marketplace.getUsers());
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
                }
                break;
            case "stores":
                switch (params) {
                    case "*":
                        get.setObject(marketplace.getStores());
                        break;
                }
            default:
                break;
        }
        return get;
    }

    public Query update(UpdateQuery update) {
        String opt = update.getOptions();
        String params = update.getParams();
        switch (opt) {
            case "users":
                switch (params) {
                    case "add": {
                        marketplace.addToUsers((User) update.getNewVal());
                        return new Query(true, "");
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
                        String[] input = (String[]) update.getObject();
                        user.setPassword(input[0], input[1]);
                        return new Query(true, "");
                    }

                }
                break;
        }
        return new Query(false, "err");
    }

    public Query delete(DeleteQuery delete) {
        String opt = delete.getOptions();
        switch (opt) {
            case "users":
                marketplace.getUsers().remove((User) delete.getObject());
                return new Query(true, "");
        }
        return new Query(false, "err: Couldn't find opt/params");
    }

    public Query compute(ComputeQuery compute) {
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
