# CS 180 Project 5
Barnes & Novel is the perfect solution for book lovers and sellers alike. Our app makes it easy to browse and purchase books from a wide selection of titles from the comfort of your own home.

With Barnes & Novel, you can create your own account and start buying and selling books right away. Our intuitive GUI makes it easy to navigate the app and find the books you want. Our user experience design choices make the app a pleasure to use, so you can focus on finding the perfect book without any hassle. Our app is fully concurrent and works over a network, allowing multiple users to use the app at the same time. But that's not all - we've also included several security features to protect your personal information and ensure a safe and secure shopping experience.

As a seller, you'll love our platform because it allows you to reach a wider audience and make some extra cash. You can easily make listings for your books and set your own prices, making it easy to sell your books to buyers all over the world.

### Authors
Aaron Ni, Diya Singh, Federico Lebron, Michael Dimitrov, Sanya Mehra

###  Contents

- [Compiling and Running](#How-to-compile-and-run-the-project)
- [Parts Submitted](#Parts-Submitted)
- [Classes](#Classes)
  - [Queries Package](#Queries-Package)
    - [Query](#Query)
    - [GetQuery](#GetQuery)
    - [DeleteQuery](#DeleteQuery)
    - [UpdateQuery](#UpdateQuery)
    - [ComputeQuery](#ComputeQuery)
  - [Server Package](#Server-Package)
    - [Server](#Server)
      - [ClientHandler](#ClientHandler)
    - [Helpers](#Helpers)
  - [Client Package](#App-Package)
    - [BookApp](#BookApp)
    - [CustomerHomepage](#CustomerHomepage)
    - [AccountMenu](#AccountMenu)
    - [FileIOMenu](#FileIOMenu)
    - [LoginMenu](#LoginMenu)
    - [SignUpMenu](#SignUpMenu)
    - [SalesMenu](#SalesMenu)
    - [ReviewMenu](#ReviewMenu)
    - [Menu](#Menu)
  - [Objects Package](#Objects-Package)
    - [Book](#Book)
    - [Buyer](#Buyer)
    - [Marketplace](#Marketplace)
    - [Review](#Review)
    - [Seller](#Seller)
    - [SellerStats](#SellerStats)
    - [Stats](#Stats)
    - [Store](#Store)
    - [User](#User)
  - [Exceptions Package](#Exceptions-Package)
    - [BookNotFoundException](#BookNotFoundException)
    - [StoreNotFoundException](#StoreNotFoundException)
    - [IdenticalStoreException](#IdenticalStoreException)
  - [LocalTests Package](#LocalTests-Package)
    - [BookTest](#BookTest)
    - [BuyerTest](#BuyerTest)
    - [MarketplaceTest](#MarketplaceTest)
    - [ReviewTest](#ReviewTest)
    - [SellerTest](#SellerTest)
    - [StatsTest](#StatsTest)
    - [StoreTest](#StoreTest)
    - [UserTest](#UserTest)

## How to compile and run the project
Using the project is fairly simple. Just compile and run the Server class and then run the BookApp class for each client. Follow the GUI after starting BookApp.

## Parts Submitted
| Person | Assignment Part |
|--|--|
| Aaron Ni | Submitted Presentation Video on Brightspace |
| Michael Dimitrov | Submitted Vocareum workspace |
| Sanya Mehra | Submitted Report on Brightspace |

# Classes

## GUI Package

### Customer
The Customer class serves as the main menu for a user with a Customer account. It consists of eight buttons that open new screens, allowing the user to manage their purchases and account: Purchase a Book, Search for a Book, View Stores and Reviews, Leave a Review, View Purchase History, Your Shopping Cart, Edit Account, and Sign Out.

### Login

### Seller Menu
This is the initial menu that is displayed to a Seller when they first login into their account. It contains buttons for creating a store, managing a store and its stock, viewing a store's reviews and signing out. 
### SignUp

### Start

### CustomerPages Package

#### EditAccount

#### LeaveReview
The LeaveReview class displays a menu in which the Customer can select a store from a dropdown that they would like to create a review for. Then they can enter a headline and description for their review as well as a numerical rating.

#### PurchaseBook
The PurchaseBook class displays a screen that allows the user to select a book to purchase from a dropdown menu. By entering a number in the provided textbox, they can select a quantity of the book to purchase, and add their purchase to their cart. The screen contains a Refresh button that allows the Customer to see the most up-to-date stock as well as Sort by Price and Sorrt by Quantity buttons to view the current stock accorrding to their needs.

#### PurchaseHistory
The PurchaseHistory class displays a list of all the books the Customer purchased, along with each book's important information such as price and quantity. By clicking the button on the screen, a Customer can export their purchase history, downloading a CSV file to their computer containing a record of their purchase.

#### SearchBook
The SearchBook class displays a screen with a text box that allows the user to enter a search query consisting of either the name, genre, or description of a book they would like to purchase. If the search matches the name, genre, or description of any currently in stock books in the marketplace, the information of these books in the marketplace will be displayed on the screen.

#### SelectedStoreView

#### ViewCart
The ViewCart class displays all books in the Customer's cart, along with their imporrtant information. Additionally, it contains three buttons that allow the Customer to manage their carrt: Remove Items, Clear Cart, and Checkout.

#### ViewStores
The ViewStores class displays a list of all stores in the marketplace, along with the name of the owner, average ratings, and number of products offered for each of these stores. The Customer has the ability to select between four buttons to sort by number of products from lowest to highest or highest to lowest or by frequently shopped at stores from lowest to highest or highest to lowest.

### SellerPages Package

#### AddBook
Contains a GUI that allows a Seller to add books to their stock. In here they specify fields like name, description, genre, price and quantity (Hashmap). 
#### CreateStore
Contains a GUI that allows a Seller to create a store. Only the name of the store will be specified at creation, all the other details can be done under the ManageStore GUI.
#### EditBook
THis GUI is used to edit the fields of a book. This includes its name, description, genre and price. Note that the quanitity of a book cannot be modifed here as there is a specific button under the ManageStore GUI that allows this.
#### ManageStore
This class contains many features used to modify a store. It allows a Seller to change the name of a store, add books, remove books, edit books, add books to stock and delete a store.
#### ViewReviews
This GUI allows a Seller to view reviews place upon a certain store. Contains details like title, description and rating (out of 5). An important thing to note is that reviews are placed on stores and not on books.

## Queries Package

### Query
A query is an object that wraps data being sent between the client and server. Like all our other objects, queries are Serializable, meaning they can be written and read by ObjectStreams. Queries store an Object and String, both of which can be null. Think of Queries like a box, with istructions. The box can be sent empty or full, and the String acts as instructions. Some other types of queries have other properties for instruction the server or chaning values, but this is the gist of it. Note the below Queries are just the structure. They themselves have no functionality, but are be used in the way that is described. 

### GetQuery
All the 4 types of query extend the above Query class. They give additional specificity and functionality to what can be sent and what instructions can be made. The GetQuery allows requests to be made for the server to return an object. Essentially the server returns a query "filled" with the obejct that is being requested. It can also return a Query filled with false if an error occured, or nothing was found. 

### DeleteQuery
DeleteQuery is just like what it sounds. This query allows delete requests to be made. An object can be sent, and instructions where it should be deleted from. The server can then compare to the object being sent to confirm and then delete. A true or false is returned in the query if the request was completed or if there was an error. 

### UpdateQuery
UpdateQuery works the same as DeleteQuery, except instead of deleting it updates. There is also a property that stores the new value that is being assigned. 

### ComputeQuery
ComputeQueries are the most interesting and novel of the query types. We needed some way to make large computations on the data server-side. The main use of compute queries is to validate emails and usernames when signing up, and to do login validation. This computation is instructed by the client, and multiple objects are passed into the Query constructor through an object array. This way different objects can be passed as input, as long as they are casted correctly. When the computation is complete, the result is passed in to the query back to the client. 

## Server Package

### Server
When the server is first started it loads the marketplace from the .ser file, and stores it. Then this class always awaits for a connection from a client. When a connection is made, the client socket is passed to a ClientHandler, which is spawned in a new thread. The server also passes the marketplace object to the ClientHandler.

#### ClientHandler
ClientHandler is a nested class of Server. This class will handle the communication back and forth with the client. It decides what type of query is being sent, and calls the apporpriate helper function, using and instance of the Helpers class. The Helpers are given access to the marketplace. 

### Helpers
The Helpers class handles the functionality behind the Queries that are being sent. It figures out what the instructions are saying, and does the appropriate computation and returns the correct value. Essentially, this class handles the actual functionality for the Server. 

## Client Package

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

## Objects Package

### Book
The Book class contains the mutator and accessor methods for the product's attributes. These include a book's name, store, genre, description, price, and sale percentage. The class also contains a method to display all of these attributes in addition to the quantity of books. There is a method that generates a hash code based on the properties of a book, and an equals method that compares two Book objects.

### Buyer
The Buyer class is a subclass of the User superclass. It contains methods to add and remove books from the buyer's cart. There are functionalities that increment the quantity of a book if a buyer already contains a book in the cart and to add a purchase to the buyer's purchase history. The personal information of the buyer including name, email, password, cart, and purchase history is displayed by the class's toString.

### Marketplace
The Marketplace class initializes an ArrayList of User objects consisting of buyers and sellers and identifies the current user logged in. It contains methods to validate the user’s username and email to ensure they are not already taken or of an invalid format. Additionally, there are several methods to sort books by different attributes such as price and quantity and stores by variety and frequency of purchases.

### Review
The Review class creates several attributes for a book rating that include rating, buyer, seller's name, title, and description. The numerical value of the rating attribute is converted to a string of stars that reflect the rating. The class's toString displays buyer, seller, and book information, along with the stars rating given to the book.

### Seller
The Seller class is a subclass of the User superclass. The Seller class contains all the general menu options for the seller’s dashboard. The Seller class contains attributes that store information related to the seller’s stores. The class also contains methods that allow the user to create new stores, manage their current stores, update the sock in their stores, add sales to products within stores, view reviews of stores, see their selling statistics, and see the carts of buyers that contain items that they sell. Sellers can view all the products they sell in stores and modify any attributes about the stores or products in the store. Each Seller class also contains a SellerStats attribute which holds things such as their books sold, their buyers, revenue, and related methods.

### SellerStats
There is a unique SellerStats attribute for each seller. The SellerStats class contains various attributes and methods related to a seller’s stats such as what books they’ve sold, their revenue, what the most popular genre they sell is, and who has bought what books. For listing off books sold and their buyers, sellers can choose to sort the lists. For listing off books sold, sellers can choose to sort their book list by the most/least popular and frequently bought books. For listing off buyers, sellers can choose to sort the buyer list by most/least frequent and repeat buyers.

### Stats
The Stats class contains a HashMap for the books sold by a particular Seller and another HashMap for the buyers who bought books from the particular Seller. The class uses mutator and accessor methods to set and retrieve the values for these two HashMaps and then displays the books sold and buyers for a Seller in its toString.

### Store
Store is the class used to initialzed Store objects created by a Seller. It contains the fields name, stock, reviews and Seller name. The constructor of this class initializes all of these fields. The class contains getters and setters for all of those fields. It has methods for adding and removing books to its stock. Contains a method to get the average rating of all reviews. Also has a ToString method that was overriden from the Object parent class.

### User
User is the parent class of both Buyer and Seller classes. This class has the fields name, email, password and displayPassowrd. The constructor of this class initializes all of these fields. The class contains getters and setters for all of those fields. It also has a method for hashing a password using SHA-512. 

## Exceptions Package
All custom exceptions are handled in the app and none cause a crash.

### BookNotFoundException
BookNotFoundException is thrown whenever a book is being searched for but cannot be found.

### StoreNotFoundException
StoreNotFoundException is thrown whenever a store is being searched for but cannot be found.

### IdenticalStoreException
This is an exception that is thrown whenever a store is created, but one with the same name already exists. 

## LocalTests Package

### BookTest
The BookTest class handles testing of the Book class. This tests all the important methods, including the hashCode and equals methods. The test makes several books to ensure they are correct, and uses them to test the methods, to make sure they don't break even as we add new features. This class interacts directly with the Book class. 

### BuyerTest
The BuyerTest class tests the buyer's cart, and makes sure that Buyer classes are being created and accessed correctly. This test interacts with both the User and Buyer classes. 

### MarketplaceTest
This class tests the Marketplace class, and the saving of the marketplace object. The test makes sure that all important methods of the marketplace work appropriately, and that the marketplace can be saved as needed. **When using this test, the marketplace.ser file will be cleared, so be careful.** This class also makes sure the Marketplace interacts with Books, Stores, and Users correctly, since the marketplace object is the root of our datastructure. 

### ReviewTest
The ReviewTest class tests adding a review, printing a review, and displaying the star system, all key aspects of the review object. This test interacts with the Review, Buyer, and Seller classes. 

### SellerTest
This test makes sure that creating a store, creating books, saving books to a store, and updating stock, work as intended. This test works with the Book, Store, and Seller classes. 

### StatsTest
The stats test ensures that all apsects of Stats work. This test must interact with the Buyer, Seller, and Stats objects. The test makes sure that Stats can be created and work with the buyers and sellers classes. 

### StoreTest
This class tests the Store object of the marketplace. It makes sure that stock is added to a user's store correctly, and that the average rating is calculated correctly. This class interacts with Store and Seller primarily. 

### UserTest
The UserTest tests aspects of the User class. The test makes sure that users are being created correctly and that passwords are hashed correctly. The test also checks displayPassword is working. This class primarily interacts with the User class. 
