package Exceptions;

/**
 * This class contains the exception that will
 * be thrown when a store is being created but one
 * with the same name already exists
 *
 * @author Aaron Ni
 * @author Diya Singh
 * @author Federico Lebron
 * @author Michael Dimitrov
 * @author Sanya Mehra
 * @version 11/13/2022
 */

public class IdenticalStoreException extends Exception {
    public IdenticalStoreException(String message) {
        super(message);
    }
}
