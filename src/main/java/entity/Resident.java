package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResident", nullable = false)
    private long idResident;
    @Basic
    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;
    @Basic
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;
    @Basic
    @Column(name = "isUsingElevator", nullable = false)
    private boolean isUsingElevator;
    @Basic
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "Apartment_id", referencedColumnName = "idApartment", nullable = false)
    private Apartment apartmentByApartmentId;

    public Resident() {
    }

    public Resident(Date dateOfBirth, String firstName, boolean isUsingElevator, String lastName) {
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.isUsingElevator = isUsingElevator;
        this.lastName = lastName;
    }

    public Resident(long idResident) {
        this.idResident = idResident;
    }

    public Resident(long idResident, Date dateOfBirth, String firstName, boolean isUsingElevator, String lastName) {
        this.idResident = idResident;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.isUsingElevator = isUsingElevator;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resident resident = (Resident) o;
        return idResident == resident.idResident && isUsingElevator == resident.isUsingElevator && Objects.equals(dateOfBirth, resident.dateOfBirth) && Objects.equals(firstName, resident.firstName) && Objects.equals(lastName, resident.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResident, dateOfBirth, firstName, isUsingElevator, lastName);
    }

    @Override
    public String toString() {
        return "Resident{" +
                "idResident=" + idResident +
                ", dateOfBirth=" + dateOfBirth +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isUsingElevator=" + isUsingElevator +
                '}';
    }

    public long getIdResident() {
        return idResident;
    }

    public void setIdResident(long idResident) {
        this.idResident = idResident;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isUsingElevator() {
        return isUsingElevator;
    }

    public void setUsingElevator(boolean usingElevator) {
        isUsingElevator = usingElevator;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Apartment getApartmentByApartmentId() {
        return apartmentByApartmentId;
    }

    public void setApartmentByApartmentId(Apartment apartmentByApartmentId) {
        this.apartmentByApartmentId = apartmentByApartmentId;
    }
}
