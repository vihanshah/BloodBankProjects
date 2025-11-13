-- Create the bloodbank database
CREATE DATABASE IF NOT EXISTS bloodbank;

-- Use the bloodbank database
USE bloodbank;

-- Create the donors table
CREATE TABLE IF NOT EXISTS donors (
  donor_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50),
  blood_group VARCHAR(5),
  city VARCHAR(50),
  contact VARCHAR(15),
  availability BOOLEAN,
  last_donation_date DATE
);

-- Insert some sample data for testing
INSERT INTO donors (name, blood_group, city, contact, availability, last_donation_date) VALUES
('John Doe', 'O+', 'New York', '1234567890', true, '2025-10-15'),
('Jane Smith', 'A-', 'Los Angeles', '0987654321', true, '2025-09-20'),
('Robert Johnson', 'B+', 'Chicago', '1122334455', false, '2025-08-10'),
('Emily Davis', 'AB-', 'Houston', '6677889900', true, '2025-10-01'),
('Michael Wilson', 'O-', 'Phoenix', '5544332211', false, '2025-07-25');

-- Verify the data
SELECT * FROM donors;