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
        while (true) {
            System.out.print("Enter username (or type 'cancel' to abort): ");
            String username = scanner.nextLine();
            
            if (username.equalsIgnoreCase("cancel")) {
                break;
            }
            
            if (Database.userExists(username)) {
                System.out.println("Error: Username already exists. Please try another one.");
                continue;
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            boolean success = Database.registerUser(username, password);
            if (success) {
                System.out.println("User registered successfully!");
                break;
            } else {
                System.out.println("Registration failed!");
                break;
            }
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
            System.out.println("3. Edit Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
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
                System.out.print("Enter note ID to edit: ");
                try {
                    int noteId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new content: ");
                    String newContent = scanner.nextLine();
                    if (Database.editNote(noteId, userId, newContent)) {
                        System.out.println("Note updated successfully!");
                    } else {
                        System.out.println("Failed to update note. Make sure the ID is correct and belongs to you.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID.");
                }
            } else if (choice.equals("4")) {
                System.out.print("Enter note ID to delete: ");
                try {
                    int noteId = Integer.parseInt(scanner.nextLine());
                    if (Database.deleteNote(noteId, userId)) {
                        System.out.println("Note deleted successfully!");
                    } else {
                        System.out.println("Failed to delete note. Make sure the ID is correct and belongs to you.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID.");
                }
            } else if (choice.equals("5")) {
                System.out.print("Enter your new password: ");
                String newPassword = scanner.nextLine();
                if (Database.updatePassword(userId, newPassword)) {
                    System.out.println("Password updated successfully!");
                } else {
                    System.out.println("Failed to update password.");
                }
            } else if (choice.equals("6")) {
                System.out.println("Logged out!");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}
