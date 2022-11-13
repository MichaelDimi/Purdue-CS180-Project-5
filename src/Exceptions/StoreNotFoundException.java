package Exceptions;

/**
* This class contains the exception that will
* be thrown when a store is not found in the
* database.
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

public class StoreNotFoundException extends Exception {
    public StoreNotFoundException(String message) {
        super(message);
    }
}
