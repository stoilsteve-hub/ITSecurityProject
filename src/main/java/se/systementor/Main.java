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
                System.out.println("Not implemented yet");
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
}
