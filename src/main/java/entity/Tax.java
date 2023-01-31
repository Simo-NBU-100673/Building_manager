package entity;

import jakarta.persistence.*;
import tax.type.TaxType;

import java.util.Objects;

@Entity
@IdClass(TaxPK.class)
public class Tax {
    @Id
    @ManyToOne
    @JoinColumn(name = "Building_id", referencedColumnName = "idBuilding", nullable = false)
    private Building buildingByBuildingId;
    @Id
    @Column(name = "Type", nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private TaxType type;

    @Basic
    @Column(name = "Fee", nullable = false)
    private long fee;

    public Tax() {
    }

    public Tax(Building buildingByBuildingId, TaxType type) {
        this.buildingByBuildingId = buildingByBuildingId;
        this.type = type;
    }

    public Tax(Building buildingByBuildingId, TaxType type, long fee) {
        this.buildingByBuildingId = buildingByBuildingId;
        this.type = type;
        this.fee = fee;
    }

    public Building getBuildingByBuildingId() {
        return buildingByBuildingId;
    }

    public void setBuildingByBuildingId(Building buildingByBuildingId) {
        this.buildingByBuildingId = buildingByBuildingId;
    }

    public TaxType getType() {
        return type;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return fee == tax.fee && Objects.equals(buildingByBuildingId, tax.buildingByBuildingId) && type == tax.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingByBuildingId, type, fee);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "buildingByBuildingId=" + buildingByBuildingId +
                ", type=" + type +
                ", fee=" + fee +
                '}';
    }
}
