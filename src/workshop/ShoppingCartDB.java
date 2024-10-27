package workshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDB {
    private String currentUser;
    private List<String> cartItems;

    public ShoppingCartDB() {
        cartItems = new ArrayList<>();
    }

    // When a customer logs in, their cart is either loaded from storage or created fresh
    public void login(String username) throws IOException {
        currentUser = username;
        File userFile = new File(ShoppingCartApp.getCartDBDirectory(), currentUser + ".db");
        
        if (userFile.exists()) {
            loadCart(userFile);
        } else {
            userFile.createNewFile();
            System.out.println(username + ", your cart is empty");
        }
    }

    // Load a cart from the drawer (file)
    private void loadCart(File userFile) throws IOException {
        cartItems.clear();
        BufferedReader reader = new BufferedReader(new FileReader(userFile));
        String item;
        while ((item = reader.readLine()) != null) {
            cartItems.add(item);
        }
        reader.close();
        System.out.println(currentUser + ", your cart contains the following items:");
        listCart();
    }

    // Save the cart to the drawer (file)
    public void save() throws IOException {
        if (currentUser == null) {
            System.out.println("Please login before saving.");
            return;
        }
        File userFile = new File(ShoppingCartApp.getCartDBDirectory(), currentUser + ".db");
        BufferedWriter writer = new BufferedWriter(new FileWriter(userFile));
        for (String item : cartItems) {
            writer.write(item);
            writer.newLine();
        }
        writer.close();
        System.out.println("Your cart has been saved.");
    }

    // Add item(s) to the customer's cart
    public void addItems(String... items) {
        for (String item : items) {
            cartItems.add(item);
            System.out.println(item + " added to cart");
        }
    }

    // List all the items in the current cart
    public void listCart() {
        for (int i = 0; i < cartItems.size(); i++) {
            System.out.println((i + 1) + ". " + cartItems.get(i));
        }
    }

    // List all customers (files in the cart directory)
    public void listUsers() {
        File directory = new File(ShoppingCartApp.getCartDBDirectory());
        String[] users = directory.list((dir, name) -> name.endsWith(".db"));
        if (users != null) {
            System.out.println("The following users are registered:");
            for (int i = 0; i < users.length; i++) {
                System.out.println((i + 1) + ". " + users[i].replace(".db", ""));
            }
        } else {
            System.out.println("No users found.");
        }
    }
}


