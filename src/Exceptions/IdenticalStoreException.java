package Exceptions;

/**
* This class contains the exception that will
* be thrown when a store is being created but one
* with the same name already exists
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

public class IdenticalStoreException extends Exception {
    public IdenticalStoreException(String message) {
        super(message);
    }
}
