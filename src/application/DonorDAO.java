package application;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {
    
    // Method to initialize the database (create table if not exists)
    public static void initializeDatabase() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS donors (" +
                "donor_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "blood_group VARCHAR(10) NOT NULL, " +
                "city VARCHAR(50) NOT NULL, " +
                "contact VARCHAR(20) NOT NULL, " +
                "availability BOOLEAN DEFAULT TRUE, " +
                "last_donation_date DATE NOT NULL)";
        
        try (Connection conn = DBConnect.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully!");
        }
    }
    
    // Method to add sample data
    public static void addSampleData() throws SQLException {
        // Create an instance to call non-static methods
        DonorDAO donorDAOInstance = new DonorDAO();
        
        // Check if we already have data
        if (donorDAOInstance.getAllDonors().size() > 0) {
            System.out.println("Sample data already exists. Skipping...");
            return;
        }
        
        System.out.println("Adding sample data...");
        
        // Sample donors
        Donor[] sampleDonors = {
            new Donor(0, "John Smith", "A+", "New York", "123-456-7890", true, LocalDate.of(2023, 5, 15)),
            new Donor(0, "Emily Johnson", "B-", "Los Angeles", "234-567-8901", true, LocalDate.of(2023, 3, 22)),
            new Donor(0, "Michael Brown", "O+", "Chicago", "345-678-9012", false, LocalDate.of(2022, 12, 10)),
            new Donor(0, "Sarah Davis", "AB+", "Houston", "456-789-0123", true, LocalDate.of(2023, 1, 5)),
            new Donor(0, "David Wilson", "A-", "Phoenix", "567-890-1234", true, LocalDate.of(2023, 7, 18)),
            new Donor(0, "Lisa Miller", "B+", "Philadelphia", "678-901-2345", false, LocalDate.of(2022, 11, 30)),
            new Donor(0, "James Taylor", "O-", "San Antonio", "789-012-3456", true, LocalDate.of(2023, 4, 12)),
            new Donor(0, "Jennifer Anderson", "AB-", "San Diego", "890-123-4567", true, LocalDate.of(2023, 2, 8)),
            new Donor(0, "Robert Thomas", "A+", "Dallas", "901-234-5678", true, LocalDate.of(2023, 6, 25)),
            new Donor(0, "Patricia Jackson", "B-", "San Jose", "012-345-6789", false, LocalDate.of(2022, 10, 14))
        };
        
        for (Donor donor : sampleDonors) {
            try {
                // Create a new donor object for insertion
                Donor newDonor = new Donor();
                newDonor.setName(donor.getName());
                newDonor.setBloodGroup(donor.getBloodGroup());
                newDonor.setCity(donor.getCity());
                newDonor.setContact(donor.getContact());
                newDonor.setAvailability(donor.isAvailability());
                newDonor.setLastDonationDate(donor.getLastDonationDate());
                
                // Insert the donor
                donorDAOInstance.registerDonor(newDonor);
            } catch (SQLException e) {
                System.err.println("Failed to insert sample donor " + donor.getName() + ": " + e.getMessage());
            }
        }
        
        System.out.println("Sample data added successfully!");
    }
    
    // Get all donors
    public List<Donor> getAllDonors() throws SQLException {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors ORDER BY donor_id";
        
        try (Connection conn = DBConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getInt("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setCity(rs.getString("city"));
                donor.setContact(rs.getString("contact"));
                donor.setAvailability(rs.getBoolean("availability"));
                donor.setLastDonationDate(rs.getDate("last_donation_date").toLocalDate());
                donors.add(donor);
            }
        }
        return donors;
    }
    
    // Register a new donor
    public boolean registerDonor(Donor donor) throws SQLException {
        String sql = "INSERT INTO donors (name, blood_group, city, contact, availability, last_donation_date) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, donor.getName());
            stmt.setString(2, donor.getBloodGroup());
            stmt.setString(3, donor.getCity());
            stmt.setString(4, donor.getContact());
            stmt.setBoolean(5, donor.isAvailability());
            stmt.setDate(6, Date.valueOf(donor.getLastDonationDate()));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Search donors by blood group
    public List<Donor> searchDonorsByBloodGroup(String bloodGroup) throws SQLException {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group LIKE ? ORDER BY donor_id";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + bloodGroup + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getInt("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setCity(rs.getString("city"));
                donor.setContact(rs.getString("contact"));
                donor.setAvailability(rs.getBoolean("availability"));
                donor.setLastDonationDate(rs.getDate("last_donation_date").toLocalDate());
                donors.add(donor);
            }
        }
        return donors;
    }
    
    // Search donors by name
    public List<Donor> searchDonorsByName(String name) throws SQLException {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE name LIKE ? ORDER BY donor_id";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getInt("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setCity(rs.getString("city"));
                donor.setContact(rs.getString("contact"));
                donor.setAvailability(rs.getBoolean("availability"));
                donor.setLastDonationDate(rs.getDate("last_donation_date").toLocalDate());
                donors.add(donor);
            }
        }
        return donors;
    }
    
    // Search donors by contact
    public List<Donor> searchDonorsByContact(String contact) throws SQLException {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE contact LIKE ? ORDER BY donor_id";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + contact + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getInt("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setCity(rs.getString("city"));
                donor.setContact(rs.getString("contact"));
                donor.setAvailability(rs.getBoolean("availability"));
                donor.setLastDonationDate(rs.getDate("last_donation_date").toLocalDate());
                donors.add(donor);
            }
        }
        return donors;
    }
    
    // Filter donors by city
    public List<Donor> filterDonorsByCity(String city) throws SQLException {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE city LIKE ? ORDER BY donor_id";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + city + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getInt("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setCity(rs.getString("city"));
                donor.setContact(rs.getString("contact"));
                donor.setAvailability(rs.getBoolean("availability"));
                donor.setLastDonationDate(rs.getDate("last_donation_date").toLocalDate());
                donors.add(donor);
            }
        }
        return donors;
    }
    
    // Get donor by ID
    public Donor getDonorById(int donorId) throws SQLException {
        String sql = "SELECT * FROM donors WHERE donor_id = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getInt("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setCity(rs.getString("city"));
                donor.setContact(rs.getString("contact"));
                donor.setAvailability(rs.getBoolean("availability"));
                donor.setLastDonationDate(rs.getDate("last_donation_date").toLocalDate());
                return donor;
            }
        }
        return null;
    }
    
    // Update donor
    public boolean updateDonor(Donor donor) throws SQLException {
        String sql = "UPDATE donors SET name = ?, blood_group = ?, city = ?, contact = ?, availability = ?, last_donation_date = ? WHERE donor_id = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, donor.getName());
            stmt.setString(2, donor.getBloodGroup());
            stmt.setString(3, donor.getCity());
            stmt.setString(4, donor.getContact());
            stmt.setBoolean(5, donor.isAvailability());
            stmt.setDate(6, Date.valueOf(donor.getLastDonationDate()));
            stmt.setInt(7, donor.getDonorId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Delete donor
    public boolean deleteDonor(int donorId) throws SQLException {
        String sql = "DELETE FROM donors WHERE donor_id = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, donorId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Get available donors by blood group (for emergency mode)
    public List<Donor> getAvailableDonorsByBloodGroup(String bloodGroup) throws SQLException {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group LIKE ? AND availability = true ORDER BY donor_id";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + bloodGroup + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getInt("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setCity(rs.getString("city"));
                donor.setContact(rs.getString("contact"));
                donor.setAvailability(rs.getBoolean("availability"));
                donor.setLastDonationDate(rs.getDate("last_donation_date").toLocalDate());
                donors.add(donor);
            }
        }
        return donors;
    }
}