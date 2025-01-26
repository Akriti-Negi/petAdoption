// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// //import java.util.Iterator;
// import java.util.Scanner;

// class Person {
//     private String name;
//     private int age;

//     public Person(String name, int age) {
//         this.name = name;
//         this.age = age;
//     }

//     public String getName() {
//         return name;
//     }

//     public int getAge() {
//         return age;
//     }
//     protected static ArrayList<Person> persons = new ArrayList<>();
//     static void listPersons() {
//         System.out.println("List of Persons:");
//         for (Person person : persons) {
//             System.out.println(person);
//         }
//     }

    
//     public String toString() {
//         return "Name: " + name + " | Age: " + age;
//     }
// }

// class Donor extends Person {
//     private String bloodType;

//     public Donor(String name, int age, String bloodType) {
//         super(name, age);
//         this.bloodType = bloodType;
//     }

//     public String getBloodType() {
//         return bloodType;
//     }
//     static void addDonorToDatabase(Scanner scanner) {
//         System.out.println("Add a Donor to the database");
//         System.out.print("Name: ");
//         String name = scanner.nextLine();
//         System.out.print("Age: ");
//         int age = scanner.nextInt();
//         scanner.nextLine(); 
//         System.out.print("Blood Type: ");
//         String bloodType = scanner.nextLine();

//         Donor donor = new Donor(name, age, bloodType);

//         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cllgassgn", "root", "Akriti@26");
//              PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO persons (name, age, bloodtype) VALUES (?, ?, ?)")) {
//             preparedStatement.setString(1, donor.getName());
//             preparedStatement.setInt(2, donor.getAge());
//             preparedStatement.setString(3, donor.getBloodType());

//             preparedStatement.executeUpdate();
//             System.out.println("Donor added to the database successfully!");
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }



//     static void deleteDonorFromDatabase(Scanner scanner) {
//         System.out.println("Delete a Donor from the database");
//         System.out.print("Enter the name of the donor to delete: ");
//         String nameToDelete = scanner.nextLine();

//         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cllgassgn", "root", "Akriti@26")) {
//             String query = "DELETE FROM persons WHERE name = ? AND bloodtype IS NOT NULL";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                 preparedStatement.setString(1, nameToDelete);

//                 int deletedRows = preparedStatement.executeUpdate();
//                 if (deletedRows > 0) {
//                     System.out.println("Donor " + nameToDelete + " has been deleted from the database.");
//                 } else {
//                     System.out.println("Donor not found or not deleted from the database.");
//                 }
//             }
//         } catch (SQLException e) {
//             System.out.println("Error deleting donor from the database: " + e.getMessage());
//         }
//     }
//     static void printDonors(ArrayList<Person> donors) {
//         if (!donors.isEmpty()) {
//             System.out.println("Available Donors in the database:");
//             for (Person donor : donors) {
//                 System.out.println(donor);
//             }
//         } else {
//             System.out.println("No matching donors found in the database.");
//         }
//     }

//     static ArrayList<Person> getDonorsFromDatabase(Scanner scanner) {
//         System.out.print("Enter the desired blood type: ");
//         String searchBloodType = scanner.nextLine();

//         ArrayList<Person> donors = new ArrayList<>();

//         try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cllgassgn", "root", "Akriti@26");
//              PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE bloodtype = ?")) {
//             preparedStatement.setString(1, searchBloodType);

//             try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                 while (resultSet.next()) {
//                     String name = resultSet.getString("name");
//                     int age = resultSet.getInt("age");
//                     String bloodType = resultSet.getString("BloodType");

//                     donors.add(new Donor(name, age, bloodType));
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return donors;
//     }
//     // static void searchDonorInDatabase(Scanner scanner) {
//     //     System.out.print("Enter the desired blood type: ");
//     //     String searchBloodType = scanner.nextLine();

//     //     ArrayList<Person> donors = getDonorsFromDatabase(searchBloodType);

//     //     if (!donors.isEmpty()) {
//     //         System.out.println("Available Donors with Blood Type " + searchBloodType + " in the database:");
//     //         for (Person donor : donors) {
//     //             System.out.println(donor);
//     //         }
//     //     } else {
//     //         System.out.println("No matching donors found in the database.");
//     //     }
//     // }


//     // static void addDonor(Scanner scanner) {
//     //     System.out.println("Add a Donor");
//     //     System.out.print("Name: ");
//     //     String name = scanner.nextLine();
//     //     System.out.print("Age: ");
//     //     int age = scanner.nextInt();
//     //     scanner.nextLine(); 
//     //     System.out.print("Blood Type: ");
//     //     String bloodType = scanner.nextLine();

//     //     Donor donor = new Donor(name, age, bloodType);
//     //     persons.add(donor);

//     //     System.out.println("Donor added successfully!");
//     // }
//     // static void deleteDonor(Scanner scanner) {
//     //     System.out.println("Delete a Donor");
//     //     System.out.print("Enter the name of the donor to delete: ");
//     //     String nameToDelete = scanner.nextLine();

//     //     Iterator<Person> iterator = persons.iterator();
//     //     boolean found = false;

//     //     while (iterator.hasNext()) {
//     //         Person person = iterator.next();
//     //         if (person instanceof Donor && person.getName().equalsIgnoreCase(nameToDelete)) {
//     //             iterator.remove();
//     //             found = true;
//     //             System.out.println("Donor " + nameToDelete + " has been deleted.");
//     //         }
//     //     }

//     //     if (!found) {
//     //         System.out.println("Donor not found or not deleted.");
//     //     }
//     // }
//     // static void searchDonor(Scanner scanner) {
//     //     System.out.print("Enter the desired blood type: ");
//     //     String searchBloodType = scanner.nextLine();

//     //     System.out.println("Available Donors with Blood Type " + searchBloodType + ":");
//     //     boolean found = false;
//     //     for (Person person : persons) {
//     //         if (person instanceof Donor && ((Donor) person).getBloodType().equalsIgnoreCase(searchBloodType)) {
//     //             System.out.println(person);
//     //             found = true;
//     //         }
//     //     }

//     //     if (!found) {
//     //         System.out.println("No matching donors found.");
//     //     }
//     // }
//     // public String toString() {
//     //     return super.toString() + " | Blood Type: " + bloodType;
//     // }
// }

// class Seeker extends Person {
//     private String requiredBloodType;

//     public Seeker(String name, int age, String requiredBloodType) {
//         super(name, age);
//         this.requiredBloodType = requiredBloodType;
//     }

//     public String getRequiredBloodType() {
//         return requiredBloodType;
//     }
//     // static void addSeeker(Scanner scanner) {
//     //     System.out.println("Add a Seeker");
//     //     System.out.print("Name: ");
//     //     String name = scanner.nextLine();
//     //     System.out.print("Age: ");
//     //     int age = scanner.nextInt();
//     //     scanner.nextLine(); 
//     //     System.out.print("Required Blood Type: ");
//     //     String requiredBloodType = scanner.nextLine();

//     //     Seeker seeker = new Seeker(name, age, requiredBloodType);
//     //     persons.add(seeker);

//     //     System.out.println("Seeker added successfully!");
//     // }

    
//     public String toString() {
//         return super.toString() + " | Required Blood Type: " + requiredBloodType;
//     }
// }

// public class BloodDonationSystem {
    

// public static void main(String[] args) throws SQLException
// {


//         Scanner scanner = new Scanner(System.in);

//         while (true) {
//             System.out.println("Blood Donation System");
//             System.out.println("1. Add Donor");
//             // System.out.println("2. Search for Donor");
//             //System.out.println("3. Add Seeker");
//             System.out.println("2. Delete Donor");
//             System.out.println("3. search donor");
//             System.out.println("4. Exit");
//             System.out.print("Select an option: ");

//             int choice = scanner.nextInt();
//             scanner.nextLine(); 

//             switch (choice) {
//                 case 1:
//                     Donor.addDonorToDatabase(scanner);
//                     break;
//                 // case 2:
//                 //     Donor.searchDonorInDatabase(scanner);
//                 //     break;
//                 // case 3:
//                 //     Seeker.addSeeker(scanner);
//                 //     break;
                    

//                 case 2:
//                     Donor.deleteDonorFromDatabase(scanner);
//                     break;
//                 case 3:
//                     Donor.printDonors(Donor.getDonorsFromDatabase(scanner));
//                     break;
//                 case 4:
//                     System.out.println("Exiting...");
//                     System.exit(0);
//                 default:
//                     System.out.println("Invalid option. Please try again.");
//             }
//         }
// }
// }
   




// import java.sql.*;
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
