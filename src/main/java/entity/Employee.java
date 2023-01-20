package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee", nullable = false)
    private long idEmployee;
    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "Company_name", referencedColumnName = "name")
    private Company companyByCompanyName;
    @OneToMany(mappedBy = "employeeByEmployeeId")
    private Collection<Contract> contractsByIdEmployee;

}
