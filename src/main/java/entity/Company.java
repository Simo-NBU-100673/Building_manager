package entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Company {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idCompany", nullable = false)
    private long idCompany;
    @Basic
    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;
    @OneToMany(mappedBy = "companyByCompanyId")
    private Collection<Contract> contractsByIdCompany;

    public Company() {
        this.name="";
    }

    public Company(long idCompany) {
        this.idCompany = idCompany;
        this.name="";
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(long idCompany, String name) {
        this.idCompany = idCompany;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "idCompany=" + idCompany +
                ", name='" + name + '\'' +
                '}';
    }
}
