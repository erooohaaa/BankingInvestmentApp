package accounts;

import java.time.LocalDateTime;

public class User {
    private String firstName;
    private String lastName;
    private String id;
    private String email;

    public User(String firstName, String lastName, String id, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
    }


    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getId() { return id; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("User: %s %s, ID: %s, Email: %s", firstName, lastName, id, email);
    }

    public String toFileString() {
        return String.format("%s|%s|%s|%s", firstName, lastName, id, email);
    }

    public void saveToFile(String accountType) {
        try {
            java.io.File file = new java.io.File("users.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created new users.txt file");
            }

            java.io.FileWriter writer = new java.io.FileWriter("users.txt", true);
            writer.write(toFileString() + "|" + accountType + "|" + LocalDateTime.now() + "\n");
            writer.close();
            System.out.println("User data saved to file: " + this);
        } catch (java.io.IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }
}