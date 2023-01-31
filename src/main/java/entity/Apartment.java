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

    public Apartment() {
    }

    public Apartment(long idApartment) {
        this.idApartment = idApartment;
    }

    public Apartment(long idApartment, int floor, Building buildingByBuildingId, Owner ownerByOwnerId) {
        this.idApartment = idApartment;
        this.floor = floor;
        this.buildingByBuildingId = buildingByBuildingId;
        this.ownerByOwnerId = ownerByOwnerId;
    }

    public Apartment(int floor, Building buildingByBuildingId, Owner ownerByOwnerId) {
        this.floor = floor;
        this.buildingByBuildingId = buildingByBuildingId;
        this.ownerByOwnerId = ownerByOwnerId;
    }

    public long getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(long idApartment) {
        this.idApartment = idApartment;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Building getBuildingByBuildingId() {
        return buildingByBuildingId;
    }

    public void setBuildingByBuildingId(Building buildingByBuildingId) {
        this.buildingByBuildingId = buildingByBuildingId;
    }

    public Owner getOwnerByOwnerId() {
        return ownerByOwnerId;
    }

    public void setOwnerByOwnerId(Owner ownerByOwnerId) {
        this.ownerByOwnerId = ownerByOwnerId;
    }

    public Collection<Paymentshistory> getPaymentshistoriesByIdApartment() {
        return paymentshistoriesByIdApartment;
    }

    public void setPaymentshistoriesByIdApartment(Collection<Paymentshistory> paymentshistoriesByIdApartment) {
        this.paymentshistoriesByIdApartment = paymentshistoriesByIdApartment;
    }

    public Collection<Pet> getPetsByIdApartment() {
        return petsByIdApartment;
    }

    public void setPetsByIdApartment(Collection<Pet> petsByIdApartment) {
        this.petsByIdApartment = petsByIdApartment;
    }

    public Collection<Resident> getResidentsByIdApartment() {
        return residentsByIdApartment;
    }

    public void setResidentsByIdApartment(Collection<Resident> residentsByIdApartment) {
        this.residentsByIdApartment = residentsByIdApartment;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "idApartment=" + idApartment +
                ", floor=" + floor +
                ", buildingByBuildingId=" + buildingByBuildingId +
                ", ownerByOwnerId=" + ownerByOwnerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return idApartment == apartment.idApartment && floor == apartment.floor && Objects.equals(buildingByBuildingId, apartment.buildingByBuildingId) && Objects.equals(ownerByOwnerId, apartment.ownerByOwnerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idApartment, floor, buildingByBuildingId, ownerByOwnerId);
    }
}
