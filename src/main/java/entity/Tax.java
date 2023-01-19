package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(TaxPK.class)
public class Tax {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "Building_id", nullable = false)
//    private long buildingId;
    @Id
    @ManyToOne
    @JoinColumn(name = "Building_id", referencedColumnName = "idBuilding", nullable = false)
    private Building buildingByBuildingId;
    @Id
    @Column(name = "Fee", nullable = false)
    private long fee;
    @Basic
    @Column(name = "Type", nullable = false, length = 45)
    private String type;


}
