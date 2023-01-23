package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompany", nullable = false)
    private long idCompany;

    @Basic
    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;
    @OneToMany(mappedBy = "companyByCompanyId")
    private Collection<Employee> employeesByCompanyId;

    public Company() {
        this.name = "No name";
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(long idCompany, String name) {
        this.idCompany = idCompany;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
    }

    public Collection<Employee> getEmployeesByCompanyId() {
        return employeesByCompanyId;
    }

    public void setEmployeesByCompanyId(Collection<Employee> employeesByCompanyId) {
        this.employeesByCompanyId = employeesByCompanyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return idCompany == company.idCompany && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompany, name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "idCompany=" + idCompany +
                ", name='" + name + '\'' +
                '}';
    }
}
