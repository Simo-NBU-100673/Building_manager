package entity;

import jakarta.persistence.*;

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
    @Column(name = "Fee", nullable = false)
    private long fee;

}
