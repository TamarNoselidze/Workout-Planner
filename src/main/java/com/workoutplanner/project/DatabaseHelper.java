package com.workoutplanner.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * The DatabaseHelper class provides methods for loading, saving, registering, and logging in users.
 */
public class DatabaseHelper {

    // local database file
    private static final String DATABASE_FILE = "database.json";
    private static final Type USER_MAP_TYPE = new TypeToken<Map<String, User>>(){}.getType();
    private static Map<String, User> users;

    /**
     * Constructor for the DatabaseHelper class.
     * Initializes the user map by loading users from the database file.
     */
    public DatabaseHelper(){
        users = loadUsers();
    }

    /**
     * Loads users from the database file.
     * @return A map of username to User objects.
     */
    private static Map<String, User> loadUsers() {
        try (Scanner scanner = new Scanner(new FileReader(DATABASE_FILE))) {
            String json = scanner.useDelimiter("\\A").next();
            return new Gson().fromJson(json, USER_MAP_TYPE);
        } catch (Exception e) {
            // If an exception occurs (e.g., file not found), return an empty map
            return new HashMap<>();
        }
    }


    /**
     * Saves the user map to the database file.
     * @param users The map of username to User objects to save.
     */
    private static void saveUsers(Map<String, User> users) {
        try (Writer writer = new FileWriter(DATABASE_FILE)) {
            new Gson().toJson(users, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates user information in the user map and saves it to the database file.
     * @param user The User object to update.
     */
    public static void updateUserInfo(User user) {
        users.put(user.getUsername(), user);
        saveUsers(users);
    }


    /**
     * Prompts the user to log in by entering a username and password.
     * If the username is incorrectly entered 3 times, it redirects to the main menu.
     * @return The User object if login is successful, or null if login fails.
     */
    public static User loginUser() {
        Scanner scanner = new Scanner(System.in);
        User user;
        System.out.println("Login to an existing account");
        System.out.print("Please, enter your username: ");

        int i = 0;
        while (true){
            String username = scanner.nextLine().trim();
            user = users.get(username);
            if (user != null){
                System.out.print("Please, enter your password: ");
                while(true){
                    String password = scanner.nextLine().trim();
                    if (user.getPassword().equals(password)){ break; }
                    else{ System.out.println("The password is incorrect. Please, try again."); }
                }
                break;
            }
            else{
                i++;
                if (i==3){
                    System.out.println("You have tried to login with a wrong username 3 times. \nYou will be redirected to the initial menu, so you can try registering instead.\n");
                    break;
                }
                System.out.println("There is no account under this username. Please, try again.");
            }
        }
        return user;
    }


    /**
     * Prompts the user to register a new account by entering a username and password.
     * @return The newly created User object.
     */
    public static User registerUser() {
        System.out.println("Register a new account");
        String username = obtainUsername();
        String password = obtainPassword(username);
        // Add the user
        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUsers(users);

        return newUser;
    }

    /**
     * Prompts the user to enter a valid username for registration.
     * @return The valid username entered by the user.
     */

    private static String obtainUsername(){
        String username = "";
        System.out.print("Enter username: ");
        Scanner scanner1 = new Scanner(System.in);
        while (true){
            username = scanner1.nextLine().trim();
            if (username.length() <4){
                System.out.print("The username should have more than 3 characters. Please, enter a different username: ");
            }
            else if (users.containsKey(username)){
                System.out.print("This username already exists in the database. Please, enter a different username: ");
            }
            else {
                break;
            }
        }
        return username;
    }


    /**
     * Prompts the user to enter a valid password for registration.
     * @param username The username for which the password is being set.
     * @return The valid password entered by the user.
     */
    private static String obtainPassword(String username){
        System.out.print("Enter password: ");
        String password = "";
        Scanner scanner1 = new Scanner(System.in);
        while (true){
            password = scanner1.nextLine().trim();
            if (password.length() <6){
                System.out.print("The password should have at least 6 characters. Please, enter a different password: ");
            }
            else if (password.equals(username)){
                System.out.print("The password cannot be the same as the username. Please, enter a different password: ");
            }
            else {
                break;
            }
        }
        return password;
    }

}
