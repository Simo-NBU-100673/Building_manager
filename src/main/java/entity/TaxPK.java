package entity;

import jakarta.persistence.*;
import tax.type.TaxType;

import java.io.Serializable;
import java.util.Objects;

public class TaxPK implements Serializable {
//    @Column(name = "Building_id", nullable = false)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long buildingId;

    @Id
    @ManyToOne
    @JoinColumn(name = "Building_id", referencedColumnName = "idBuilding", nullable = false)
    private Building buildingByBuildingId;

    @Id
    @Column(name = "Type", nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private TaxType type;

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
}
