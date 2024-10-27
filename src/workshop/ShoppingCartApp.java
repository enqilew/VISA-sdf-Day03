package workshop;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ShoppingCartApp {
    private static ShoppingCartDB shoppingCartDB = new ShoppingCartDB();
    private static String cartDBDirectory;

    public static void main(String[] args) {
        // Set up the directory for storing shopping carts
        if (args.length > 0) {
            cartDBDirectory = args[0];
        } else {
            cartDBDirectory = "db"; // Default directory
        }

        // Create directory if it doesn't exist
        File dbDir = new File(cartDBDirectory);
        if (!dbDir.exists()) {
            if (dbDir.mkdirs()) {
                System.out.println("Directory " + cartDBDirectory + " created.");
            } else {
                System.out.println("Failed to create directory " + cartDBDirectory);
            }
        }

        System.out.println("Welcome to your shopping cart");

        // Enter the store's main loop to interact with users
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();
            String[] parts = command.split(" ", 2);     // parts[0]: "add", parts[1]: "apple, orange"

            switch (parts[0]) {
                case "login":
                    if (parts.length > 1) {
                        try {
                            shoppingCartDB.login(parts[1]);
                        } catch (IOException e) {
                            System.out.println("Error logging in: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Please provide a username.");
                    }
                    break;

                case "add":
                    if (parts.length > 1) {
                        String[] items = parts[1].split(", ");
                        shoppingCartDB.addItems(items);
                    } else {
                        System.out.println("Please specify item(s) to add.");
                    }
                    break;

                case "list":
                    shoppingCartDB.listCart();
                    break;

                case "save":
                    try {
                        shoppingCartDB.save();
                    } catch (IOException e) {
                        System.out.println("Error saving cart: " + e.getMessage());
                    }
                    break;

                case "users":
                    shoppingCartDB.listUsers();
                    break;

                case "exit":
                    System.out.println("Exiting.");
                    return;

                default:
                    System.out.println("Unknown command.");
                    break;
            
            }

        }
    }

    public static String getCartDBDirectory() {
        return cartDBDirectory;
    }
}

