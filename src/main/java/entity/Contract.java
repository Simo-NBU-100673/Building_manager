package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Contract {
    @ManyToOne
    @JoinColumn(name = "Employee_id", referencedColumnName = "idEmployee", nullable = false)
    private Employee employeeId;
    @Id
    @OneToOne
    @JoinColumn(name = "Building_id", referencedColumnName = "idBuilding", nullable = false)
    private Building buildingByBuildingId;
    @ManyToOne
    @JoinColumn(name = "Company_id", referencedColumnName = "idCompany", nullable = false)
    private Company companyByCompanyId;


}
