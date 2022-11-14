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

# Classes

## App Package:

### BookApp
BookApp is where the main method exists for our program. It contains two loops, the outer one, is the logging in loop, and the inner one is the main screen loop. The login loop will always show the login menu when the user is not signed in, and break when the user exits the program. The main menu loop either shows the customer hompage or seller homepage, depending on if the user is a buyer or seller. To test this class we went through all the possible inputs the user could enter to break the menu or cause a crash. This class is the central hub where all other menus connect to. 

### CustomerHomepage
The customer homepage presents the menu and functionality for the main screen for customers. Here buyers can choose to search for books, buy books, view their shopping cart and purchase history, export purchase history, leave reviews, edit their account, or sign out. To test this menu, much like the BookApp class, we had to explore all possible inputs a user could make, and how it could break the program. Many other menus connect to this one such as the AccountMenu and FileIOMenu. 

### AccountMenu
This class controls the functionality and display for the edit account menu. Here users can see and change their username, email, or password. The entire password does not show. Users can select to change their name, email, password, or delete their account. The name and email that is changed to, must not already exist in the marketplace. To change the password or delete the account, users must enter their password to confirm. Much like the other menus, this class required trying all possible inputs from the user, and printing debug statements of the users state during usage of the menu. This class connects mainly to the User class and Marketplace class. 

### FileIOMenu
This menu controls imports and exports for sellers or buyers. The menu that is presented checks if the user is buyer or seller, and displays the appropriate messages. The other methods of this class handle the CSV writing and parsing. This class was tested with all possible characters. The first obvious issue is having commas in the user input, given that the data in the CSV file is comma separated. Thus, double quotes are used around inputs where there are commas, however, this presents another problem. Now quotation marks must also be delimited. If there is commas or quotes anywhere in the input, the quotes are replaced by two double quotes, and quotes are put around the input. This is taken into account when reading the file. This class interacts with the user and marketplace primarily, as well as with the customer and seller homepages.

### LoginMenu
This menu control logging a user in. It is pretty straightforward, as it gets the input for a user's email or username, and password hashes the password, and tries to find the user in the marketplace. If the user logs in successfully, they are set as the current user of the marketplace. We tested many different ways the user could break the input to cause an error, but there is not much that can be done to break it. This class works with the User and Marketplace classes primarily. 

### SignUpMenu
This class is simialirly straightforward as LoginMenu. Here, the user can create their account for the app, and decide if their account will be for buyer or seller. Before the account can be created, the name and email are validated to make sure they don't already exist in the marketplace. The password is also hashed with SHA-512 before saving to the marketplace. We tested this by making numerous accounts, and making sure the password always saves correctly, and can be used to log back in. We also tested to make sure dulicate accounts cannot be created. This class interacts with User and Marketplace. 

### SalesMenu
The sales menu is where sellers can create sales for books. The menu ensures that the input is valid, ie in range [0,100], and that it is the correct final price a seller wants to add. If the user does not have any books, the menu also guides them through making a book, if they want. This class was tested to make sure the final price was correct, and that the interface was inuitive. It interacts directly with the Book class and Marketplace. 

### ReviewMenu
The ReviewMenu class contains funcitonality for buyers to leave a review, and for any user to view the reviews of a particular store. There is custom display for the ratings that this class makes use of. During testing we found, that sellers were able to leave reviews, which was not the correct functionality, so we added a check for that. This class interacts with the Review class, the Buyer and Seller classes, and the Marketplace. 

### Menu
The Menu is an abstract class that the other menus should subclass. It provides some useful functions used across multiple of the methods. It acts like an interface except the subclasses do not need to inherit all the methods, only the ones they need. The testing for this class is part of the testing for other classes, since the methods are used as part of other menus. This class is a parent of the other menus in the app, and interacts with them. It also interacts with the Marketplace. 




