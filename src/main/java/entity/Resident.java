package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Resident {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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

}
