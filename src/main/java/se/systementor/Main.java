package se.systementor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database.initialize();
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            
            String choice = scanner.nextLine();
            
            if (choice.equals("1")) {
                registerUser(scanner);
            } else if (choice.equals("2")) {
                loginUser(scanner);
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean success = Database.registerUser(username, password);
        if (success) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed!");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        int userId = Database.loginUser(username, password);
        if (userId != -1) {
            System.out.println("Login successful!");
            loggedInMenu(scanner, userId);
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private static void loggedInMenu(Scanner scanner, int userId) {
        while (true) {
            System.out.println("--- LOGGED IN MENU ---");
            System.out.println("1. Create Note");
            System.out.println("2. View Notes");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.print("Enter your note: ");
                String content = scanner.nextLine();
                if (Database.createNote(userId, content)) {
                    System.out.println("Note created successfully!");
                } else {
                    System.out.println("Failed to create note.");
                }
            } else if (choice.equals("2")) {
                var notes = Database.getNotes(userId);
                if (notes.isEmpty()) {
                    System.out.println("You have no notes yet.");
                } else {
                    System.out.println("--- YOUR NOTES ---");
                    for (String note : notes) {
                        System.out.println(note);
                    }
                }
            } else if (choice.equals("3")) {
                System.out.println("Logged out!");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}
