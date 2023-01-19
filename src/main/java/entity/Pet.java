package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Pet {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idPet", nullable = false)
    private long idPet;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "Apartment_id", referencedColumnName = "idApartment", nullable = false)
    private Apartment apartmentByApartmentId;

}
