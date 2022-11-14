package Exceptions;

/**
 * This class contains the exception that will
 * be thrown when a store is not found in the
 * database.
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */

public class StoreNotFoundException extends Exception {
    public StoreNotFoundException(String message) {
        super(message);
    }
}
