# Testing

## Test 1: Login
1. Press login...

Expected result: Logs user in\
Test Status: Passed

## Test 2: Login with invalid credentials
1. Input invalid credentials
2. Press login

Expected result: Logs user in\
Test Status: Passed

## Test 3: Sign up
1. Input username
2. Input email
3. Input password
4. Press sign up

Expected result: Creates an account\
Test Status: Passed

## Test 4: Sign up with existing account
1. Input existing username
2. Input existing email
3. Input password
4. Press sign up

Expected result: Account is unable to be made\
Test Status: Passed

## Test 5: Sign up
1. Input username
2. Input email
3. Input password
4. Press sign up

Expected result: Creates an account\
Test Status: Passed

## Test 6: Purchase book
1. Add book to cart
2. Checkout cart
3. View purchase history

Expected result: Purchased book shows up in history\
Test Status: Passed

## Test 7: Search book
1. Input search query
2. View search results

Expected result: Correct book is displayed in search results\
Test Status: Passed

## Test 8: Write review
1. Select book
2. View book details
3. Write review
4. Submit review

Expected result: Review is posted and visible to seller\
Test Status: Passed

## Test 9: Edit profile
1. Go to edit account
2. Edit account information
3. Save changes

Expected result: Account information is updated\
Test Status: Passed

## Test 10: Reset password
1. Go to edit account settings
2. Validate with current password
3. Reset password

Expected result: Password is successfully reset and can login\
Test Status: Passed

## Test 11: View store list
1. Go to store list page
2. View list of available stores

Expected result: All available stores are displayed in the list\
Test Status: Passed

## Test 12: Sort stores
1. Go to store list page
2. Select sort option (e.g. by number of products, most frequently shopped at)
3. View sorted list of stores

Expected result: Stores are sorted according to the selected option\
Test Status: Passed

## Test 13: View cart
1. Go to cart page
2. View items in cart

Expected result: All items in the cart are displayed\
Test Status: Passed

## Test 14: Edit book
1. Select store
2. Select manage store stock
3. Select book to edit
4. Edit book details
5. Save changes

Expected result: Book details are successfully updated\
Test Status: Passed

## Test 15: Create store
1. Click create new store on seller page
2. Input store details
3. Submit store creation

Expected result: New store is successfully created and added to the store list\
Test Status: Passed

## Test 16: Add books to store
1. Select store to add books to
2. Input details for a new book (e.g. name, genre, price, quantity)
3. Input quantity
4. Add books to store

Expected result: Selected books are successfully added to the store's inventory\
Test Status: Passed

## Test 17: View reviews
1. Seller select store to view reviews for
2. View reviews for the store

Expected result: All reviews for the selected book are displayed\
Test Status: Passed

## Test 18: Checkout cart
1. Go to cart page
2. Review items in cart
3. Click checkout cart

Expected result: Purchases show up in history and items are removed from cart\
Test Status: Passed

## Test 19: Clear cart
1. Go to cart page
2. View items in cart
3. Clear cart

Expected result: All items are removed from the cart\
Test Status: Passed

## Test 20: Remove books from store
1. Select store to remove books from
2. Select book to remove
3. Input number of books to remove

Expected result: Selected books are successfully removed from the store's inventory\
Test Status: Passed

## Test 21: Change store name
1. Select store to edit
2. Go to store page
3. Click edit store name

Expected result: Store name is successfully updated\
Test Status: Passed

## Test 22: View purchase history
1. Purchase a book
2. Click view purchase history

Expected result: All previously purchases are listed on purchase history page\
Test Status: Passed

## Test 23: Export purchase history
1. Purchase a book
2. Click view purchase history
3. Click export purchase history
4. Input a destination directory and csv file name

Expected result: A csv file with user's purchase history is created\
Test Status: Passed