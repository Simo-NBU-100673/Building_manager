package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Company {
    @Id
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @OneToMany(mappedBy = "companyByCompanyName")
    private Collection<Employee> employeesByCompanyName;

    public Company() {
        this.name = "No name";
    }

    public Company(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Employee> getEmployeesByCompanyName() {
        return employeesByCompanyName;
    }

    public void setEmployeesByCompanyName(Collection<Employee> employeesByCompanyName) {
        this.employeesByCompanyName = employeesByCompanyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
