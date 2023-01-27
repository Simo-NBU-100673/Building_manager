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
    @JoinColumn(name = "Company_id", referencedColumnName = "idCompany")
    private Company companyByCompanyId;
    @OneToMany(mappedBy = "employeeByEmployeeId")
    private Collection<Contract> contractsByIdEmployee;

    public Employee() {
        this.firstName = "No name";
        this.lastName = "No name";
    }

    public Employee(long idEmployee) {
        this.idEmployee = idEmployee;
        this.firstName = "No name";
        this.lastName = "No name";
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(long idEmployee, String firstName, String lastName) {
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(Employee employee) {
        this.idEmployee = employee.idEmployee;
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.companyByCompanyId = employee.getCompanyByCompanyId();
        this.contractsByIdEmployee = employee.getContractsByIdEmployee();
    }

    public long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Company getCompanyByCompanyId() {
        return companyByCompanyId;
    }

    public void setCompanyByCompanyId(Company companyByCompanyId) {
        this.companyByCompanyId = companyByCompanyId;
    }

    public Collection<Contract> getContractsByIdEmployee() {
        return contractsByIdEmployee;
    }

    public void setContractsByIdEmployee(Collection<Contract> contractsByIdEmployee) {
        this.contractsByIdEmployee = contractsByIdEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return idEmployee == employee.idEmployee && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmployee, firstName, lastName);
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
