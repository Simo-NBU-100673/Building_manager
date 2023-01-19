package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Apartment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idApartment", nullable = false)
    private long idApartment;
    @Basic
    @Column(name = "floor", nullable = false)
    private int floor;
    @ManyToOne
    @JoinColumn(name = "Building_id", referencedColumnName = "idBuilding", nullable = false)
    private Building buildingByBuildingId;

    @ManyToOne
    @JoinColumn(name = "Owner_id", referencedColumnName = "idOwner", nullable = false)
    private Owner owner;
    @OneToMany(mappedBy = "apartmentByApartmentsId")
    private Collection<Paymentshistory> paymentshistoriesByIdApartment;
    @OneToMany(mappedBy = "apartmentByApartmentId")
    private Collection<Pet> petsByIdApartment;
    @OneToMany(mappedBy = "apartmentByApartmentId")
    private Collection<Resident> residentsByIdApartment;


}
