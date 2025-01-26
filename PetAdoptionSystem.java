
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PetAdoptionSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/petdb";
        String username = "root";
        String password = "Akriti@26";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PetDAO petDAO = new PetDAO(connection);
            AdopterDAO adopterDAO = new AdopterDAO(connection);

            while (true) {
                System.out.println("\nPet Adoption System");
                System.out.println("1. Add Pet");
                System.out.println("2. List All Pets");
                System.out.println("3. Delete Pet");
                System.out.println("4. Add Adopter");
                System.out.println("5. List All Adopters");
                System.out.println("6. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        petDAO.addPet(scanner);
                        break;
                    case 2:
                        petDAO.listAllPets();
                        break;
                    case 3:
                        petDAO.deletePet(scanner);
                        break;
                    case 4:
                        adopterDAO.addAdopter(scanner);
                        break;
                    case 5:
                        adopterDAO.listAllAdopters();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Pet {
    private int id;
    private String name;
    private String species;
    private int age;
    private boolean adopted;

    public Pet(int id, String name, String species, int age, boolean adopted) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.adopted = adopted;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Species: " + species + " | Age: " + age + " | Adopted: " + adopted;
    }
}

class Adopter {
    private int id;
    private String name;
    private String email;

    public Adopter(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Email: " + email;
    }
}

class PetDAO {
    private Connection connection;

    public PetDAO(Connection connection) {
        this.connection = connection;
    }

    public void addPet(Scanner scanner) {
        try {
            System.out.print("Enter pet name: ");
            String name = scanner.nextLine();
            System.out.print("Enter species: ");
            String species = scanner.nextLine();
            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Is the pet adopted (true/false): ");
            boolean adopted = scanner.nextBoolean();
            scanner.nextLine(); // Consume newline

            String query = "INSERT INTO pets (name, species, age, adopted) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, species);
                stmt.setInt(3, age);
                stmt.setBoolean(4, adopted);
                stmt.executeUpdate();
                System.out.println("Pet added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAllPets() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pets")) {
            System.out.println("All Pets:");
            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getInt("age"),
                        rs.getBoolean("adopted")
                );
                System.out.println(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePet(Scanner scanner) {
        try {
            System.out.print("Enter pet ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String query = "DELETE FROM pets WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Pet deleted successfully!");
                } else {
                    System.out.println("No pet found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class AdopterDAO {
    private Connection connection;

    public AdopterDAO(Connection connection) {
        this.connection = connection;
    }

    public void addAdopter(Scanner scanner) {
        try {
            System.out.print("Enter adopter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter adopter email: ");
            String email = scanner.nextLine();

            String query = "INSERT INTO adopters (name, email) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.executeUpdate();
                System.out.println("Adopter added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAllAdopters() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM adopters")) {
            System.out.println("All Adopters:");
            while (rs.next()) {
                Adopter adopter = new Adopter(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                System.out.println(adopter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
