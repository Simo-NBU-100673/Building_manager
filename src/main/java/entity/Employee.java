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

    @OneToMany(mappedBy = "employeeId")
    private Collection<Contract> employeeContracts;

    public Employee() {
        this.firstName="";
        this.lastName="";
    }

    public Employee(long idEmployee) {
        this.idEmployee = idEmployee;
        this.firstName="";
        this.lastName="";
    }

    public Employee(long idEmployee, String firstName, String lastName) {
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
