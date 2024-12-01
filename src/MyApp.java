import java.util.List;
import java.util.Scanner;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyApp {

    public static void main(String[] args) throws IOException {

        String cartdb;                                                         // String cartdb to hold console input

        if (args.length > 0) {                                                 // if cartdb directory was speficied
            cartdb = args[0];                                                  // cartdb = console input
            File cartDirectory = new File(cartdb);                             // Instance of File to use classes, pass in value in cartdb
            if (!cartDirectory.exists()) {                                     // If directory does not exist
                cartDirectory.mkdirs();                                        // Create it
            }
    
        } else {                                                               // no cartdb directory was specified
            cartdb = "db";                                                     // cartdb = db (default)
            File db = new File(cartdb);                                        // Instance of File to use class, pass in value in cartdb
            if (!db.exists()) {                                                // if db directory does not exist
                db.mkdirs();                                                   // Create it
            }
        }

        List<String> cartItems = new ArrayList<>();                     // Initialize a new cart for the new user
        String username = "";
        String filePath = "";
        System.out.println("WELCOME TO YOUR SHOPPING CART");                 // Print welcome & instructions
        instructions();


        while (true) {                                                         // While loop so program continues to run


            Console console = System.console();                                    // Initialize console to read user input from terminal
            String keyboardInput = "";                                             // Create keyBoard input String to store input
        
            keyboardInput = console.readLine("> ");                     // keyboardInput set to read console
            keyboardInput = keyboardInput.toLowerCase();                       // toLowerCase for uniformity


            if (keyboardInput.startsWith("login")) {                     // If login

                username = keyboardInput.substring(5).trim(); // extract username from keyboardInput. We begin at 5 and 
                                                                         // trim to account for people who just type in login
                                                                         // trim will remove space before username
                if (username.isEmpty()) {
                    System.out.println("Please enter a username");
                    continue;
                }

                filePath = cartdb + File.separator + username + ".db";   // define full path name with cartdb and username
                File f = new File(filePath);                                    // Initialize file object

                if (username.length() > 0) {                                    // If a username was entered
                    if (f.exists()) {                                           // If user's file already exists
                        cartItems.clear();                                      // Clear the items in the cart whenever there's a valid login
                        System.out.println(username + ", your cart contains");  // Print loaded header
                        ShoppingCartDB.loadCart(filePath, cartItems);                      // Load cart
                        for (int i = 0; i < cartItems.size(); i++) {
                            System.out.println((i+1) + ". " + cartItems.get(i));
                        }
                    } else {
                        f.createNewFile();
                        System.out.println("New cart(file) created");
                    } 
                }
                

            } else if (keyboardInput.equals("save")) {

                if (username.isEmpty()) {
                    System.out.println("You must login before saving");
                } else {
                    ShoppingCartDB.saveCart(filePath, cartItems);
                    System.out.println("Your cart has been saved");
                }

            
            } else if (keyboardInput.equals("list")) {                       // if keyboardInput .equals to "list"
                if (cartItems.size() > 0) {                                    // If there are items in the cart
                    for (int i = 0; i < cartItems.size(); i++) {               // For every items in cartItems
                        System.out.println((i+1) + ". " + cartItems.get(i));   // Print user-friendly index and item
                    }                                                          // ^^this works in this particular example because we will check and    
                                                                               //      not allow duplicate items in the list (done in another location).
                                                                               //      However, if we allowed for duplicate entries, the index would
                                                                               //      always show the indexOf the first entry
                } else {                                                       // else (there are no items in the cart)
                    System.out.println("Your cart is empty");                // Print
                }

            } else if (keyboardInput.startsWith("add")) {                       // If keyboardInput .startsWith "add"
                keyboardInput = keyboardInput.replace(',', ' ');       // Replace commas with spaces

                Scanner scan = new Scanner(keyboardInput.substring(4));      // Initialize scanner to use methods, start at .substring(4) to skip "add "
                String tempString = "";                                                 // tempSting to hold cart item
                while (scan.hasNext()) {                                                // while there are still items to be processed
                    tempString = scan.next();                                           // tempString = next word in the cartItems substring
                    if (cartItems.contains(tempString)) {                               // if item is already in the cart
                        System.out.println("You have " + tempString + " in your cart"); // Print
                    } else {                                                            // else (item is not in the cart)
                        cartItems.add(tempString);                                      // add item to cart
                        System.out.println(tempString + " added to cart");              // print
                    }
                }
                scan.close();                                                           // Good practice - close the scanner

            } else if (keyboardInput.startsWith("delete")) {                     // If keyboardInput starts with delete
                keyboardInput = keyboardInput.replace(',', ' ');        // Replace commas with spaces
                Scanner scan = new Scanner(keyboardInput.substring(6));      // Initialize scanner to use methods, start at .substring(6) to skip "delete "
                String deleteIndexString = "";                                          // deleteIndexString to hold index
                while (scan.hasNext()) {                                                // while there are still numbers to process
                    deleteIndexString = scan.next();                                    // deleteIndexString = number keyed in
                    try {
                        int deleteIndexInt = (Integer.parseInt(deleteIndexString) - 1);     // Parse the number to an int, -1 because index start from 0              
                        if (deleteIndexInt < 0 || deleteIndexInt >= cartItems.size()) {     // If invalid index
                            System.out.println("Incorrect item index");                   // Print
                        } else {                                                            // Else (index is valid)
                            System.out.println(cartItems.get(deleteIndexInt) +              // Print BEFORE
                            " removed from cart");
                        cartItems.remove(deleteIndexInt);                               // Deleting item
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command");
                    }                  
                }
                scan.close();                                                           // Good practice - close the scanner
            

            } else if (keyboardInput.equals("users")) {
                ShoppingCartDB.viewUsers(cartdb);


            } else if (keyboardInput.equals("quit")) {                         // if keyboardInput is quit
                System.out.println("SHOPPING CART QUIT - GOODBYE");                   // print
                break;                                                                  // exit the while loop

            } else {                                                                    // if keyboardInput is anything else
                System.out.println("Command not recognised, try again");              // command not recognized, try again
            }

        }

    }


    public static void instructions(){                                                  // method to print instructions
        System.out.println("====================");
        System.out.println("INSTRUCTIONS");
        System.out.println("To login: login [your name]");
        System.out.println("To view your cart: list");
        System.out.println("To add an item: add [item]");
        System.out.println("To delete an item: delete [item number]");
        System.out.println("To save your cart: save");
        System.out.println("To view users registered on database: users");
        System.out.println("To exit: quit");
        System.out.println("====================");
    }


}