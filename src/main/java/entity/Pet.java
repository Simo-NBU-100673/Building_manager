package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPet", nullable = false)
    private long idPet;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @ManyToOne
    @JoinColumn(name = "Apartment_id", referencedColumnName = "idApartment", nullable = false)
    private Apartment apartmentByApartmentId;

    public Pet() {
    }

    public Pet(long idPet) {
        this.idPet = idPet;
    }

    public Pet(String name) {
        this.name = name;
    }

    public Pet(long idPet, String name) {
        this.idPet = idPet;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "idPet=" + idPet +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return idPet == pet.idPet && Objects.equals(name, pet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPet, name);
    }

    public long getIdPet() {
        return idPet;
    }

    public void setIdPet(long idPet) {
        this.idPet = idPet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Apartment getApartmentByApartmentId() {
        return apartmentByApartmentId;
    }

    public void setApartmentByApartmentId(Apartment apartmentByApartmentId) {
        this.apartmentByApartmentId = apartmentByApartmentId;
    }
}
