package Exceptions;

/**
* This class contains the exception that will
* be thrown when a book is not found in the
* database.
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

public class BookNotFoundException extends Exception{
    public BookNotFoundException(String message) {
        super(message);
    }
}
