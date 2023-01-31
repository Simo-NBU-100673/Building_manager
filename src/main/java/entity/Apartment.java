package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Owner ownerByOwnerId;
    @OneToMany(mappedBy = "apartmentByApartmentsId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Paymentshistory> paymentshistoriesByIdApartment;
    @OneToMany(mappedBy = "apartmentByApartmentId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Pet> petsByIdApartment;
    @OneToMany(mappedBy = "apartmentByApartmentId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Resident> residentsByIdApartment;

    @Override
    public String toString() {
        return "Apartment{" +
                "idApartment=" + idApartment +
                ", floor=" + floor +
                ", buildingByBuildingId=" + buildingByBuildingId +
                ", ownerByOwnerId=" + ownerByOwnerId +
                '}';
    }
}
