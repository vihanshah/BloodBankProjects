package application;

import java.time.LocalDate;

public class Donor {
    private int donorId;
    private String name;
    private String bloodGroup;
    private String city;
    private String contact;
    private boolean availability;
    private LocalDate lastDonationDate;

    // Constructors
    public Donor() {}

    public Donor(String name, String bloodGroup, String city, String contact, boolean availability, LocalDate lastDonationDate) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.contact = contact;
        this.availability = availability;
        this.lastDonationDate = lastDonationDate;
    }

    public Donor(int donorId, String name, String bloodGroup, String city, String contact, boolean availability, LocalDate lastDonationDate) {
        this.donorId = donorId;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.contact = contact;
        this.availability = availability;
        this.lastDonationDate = lastDonationDate;
    }

    // Getters and Setters
    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "donorId=" + donorId +
                ", name='" + name + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", city='" + city + '\'' +
                ", contact='" + contact + '\'' +
                ", availability=" + availability +
                ", lastDonationDate=" + lastDonationDate +
                '}';
    }
}