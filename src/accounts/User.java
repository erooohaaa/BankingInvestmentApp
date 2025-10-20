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
        setEmail(email);
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getId() { return id; }


    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        this.email = email;
    }

    private boolean isValidEmail(String email) {

        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

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