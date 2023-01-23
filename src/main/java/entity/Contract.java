package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Contract {
    @Id
    @OneToOne
    @JoinColumn(name = "Building_id", referencedColumnName = "idBuilding", nullable = false)
    private Building buildingByBuildingId;
    @ManyToOne
    @JoinColumn(name = "Employee_id", referencedColumnName = "idEmployee", nullable = false)
    private Employee employeeByEmployeeId;

    @Override
    public String toString() {
        return "Contract{" +
                "Building=" + buildingByBuildingId +
                ", Employee=" + employeeByEmployeeId +
                '}';
    }
}
