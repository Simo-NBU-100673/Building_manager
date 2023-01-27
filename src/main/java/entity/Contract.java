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

    public Contract() {
    }

    public Contract(Building buildingByBuildingId, Employee employeeByEmployeeId) {
        this.buildingByBuildingId = buildingByBuildingId;
        this.employeeByEmployeeId = employeeByEmployeeId;
    }

    public Building getBuildingByBuildingId() {
        return buildingByBuildingId;
    }

    public void setBuildingByBuildingId(Building buildingByBuildingId) {
        this.buildingByBuildingId = buildingByBuildingId;
    }

    public Employee getEmployeeByEmployeeId() {
        return employeeByEmployeeId;
    }

    public void setEmployeeByEmployeeId(Employee employeeByEmployeeId) {
        this.employeeByEmployeeId = employeeByEmployeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(buildingByBuildingId, contract.buildingByBuildingId) && Objects.equals(employeeByEmployeeId, contract.employeeByEmployeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingByBuildingId, employeeByEmployeeId);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "Building=" + buildingByBuildingId +
                ", Employee=" + employeeByEmployeeId +
                '}';
    }
}
