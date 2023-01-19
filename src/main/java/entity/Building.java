package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Building {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idBuilding", nullable = false)
    private long idBuilding;
    @Basic
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @OneToMany(mappedBy = "buildingByBuildingId")
    private Collection<Apartment> apartmentsByIdBuilding;
    @OneToOne(mappedBy = "buildingByBuildingId")
    private Contract contractByIdBuilding;


}
