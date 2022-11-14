# CS 180 Project 4
For Project 4 we made a Bookstore app, where users can create accounts for buying or selling books. Our project runs in the command line, and uses text menus and text user input to navigate the app. There are several security features and user experience design choices that went into making our app.

### Authors
Aaron Ni, Diya Singh, Federico Lebron, Michael Dimitrov, Sanya Mehra

###  Contents

- [Compiling and Running](#How-to-compile-and-run-the-project)
- [Parts Submitted](#Parts-Submitted)
- [Classes](#Classes)
    - [ClassOne](#ClassOne)
    - [ClassTwo](#ClassTwo)



## How to compile and run the project
The easiest way to run our project is to download the "work" folder from Vocareum, unzip it, and compile and run through an IDE like IntelliJ.  Inside the work folder is a folder called "cs180-project-4", which is what you should open in your IDE to run. 

Alternatively, you can compile each file in terminal, however, we strongly suggest you use an IDE since there are many files and they are in different packages. 

When you run the project, for the best experience we suggest making the terminal/run window as big as possible. Some of the menus have many options, so extending the size of the window will help you see what is happening. 

## Parts Submitted
| Person | Assignment Part |
|--|--|
| Person 1 | Submitted Report on Brightspace |
| Person 2 | Submitted Vocareum workspace |

## Classes

### BookApp
BookApp is where the main method exists for our program. It contains two loops, the outer one, is the logging in loop, and the inner one is the main screen loop. The login loop will always show the login menu when the user is not signed in, and break when the user exits the program. The main menu loop either shows the customer hompage or seller homepage, depending on if the user is a buyer or seller. To test this class we went through all the possible inputs the user could enter to break the menu or cause a crash. This class is the central hub where all other menus connect to. 

### CustomerHomepage
The customer homepage presents the menu and functionality for the main screen for customers. Here buyers can choose to search for books, buy books, view their shopping cart and purchase history, export purchase history, leave reviews, edit their account, or sign out. To test this menu, much like the BookApp class, we had to explore all possible inputs a user could make, and how it could break the program. Many other menus connect to this one such as the AccountMenu and FileIOMenu. 

### AccountMenu
This class controls the functionality and display for the edit account menu. Here users can see and change their username, email, or password. The entire password does not show. Users can select to change their name, email, password, or delete their account. The name and email that is changed to, must not already exist in the marketplace. To change the password or delete the account, users must enter their password to confirm. Much like the other menus, this class required trying all possible inputs from the user, and printing debug statements of the users state during usage of the menu. This class connects mainly to the User class and Marketplace class. 


