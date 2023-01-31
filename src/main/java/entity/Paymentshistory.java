package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Paymentshistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPayment", nullable = false)
    private long idPayment;
    @Basic
    @Column(name = "dateOfPayment", nullable = false)
    private Date dateOfPayment;

    @Basic
    @Column(name = "tax", nullable = false)
    private int tax;

    @ManyToOne
    @JoinColumn(name = "Apartments_id", referencedColumnName = "idApartment", nullable = false)
    private Apartment apartmentByApartmentsId;

    public Paymentshistory() {
    }

    public Paymentshistory(long idPayment, Date dateOfPayment, int tax) {
        this.idPayment = idPayment;
        this.dateOfPayment = dateOfPayment;
        this.tax = tax;
    }

    public Paymentshistory(Date dateOfPayment, int tax) {
        this.dateOfPayment = dateOfPayment;
        this.tax = tax;
    }

    public Paymentshistory(long idPayment) {
        this.idPayment = idPayment;
    }

    @Override
    public String toString() {
        return "Paymentshistory{" +
                "idPayment=" + idPayment +
                ", dateOfPayment=" + dateOfPayment +
                ", tax=" + tax +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paymentshistory that = (Paymentshistory) o;
        return idPayment == that.idPayment && tax == that.tax && Objects.equals(dateOfPayment, that.dateOfPayment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPayment, dateOfPayment, tax);
    }

    public long getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(long idPayment) {
        this.idPayment = idPayment;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public Apartment getApartmentByApartmentsId() {
        return apartmentByApartmentsId;
    }

    public void setApartmentByApartmentsId(Apartment apartmentByApartmentsId) {
        this.apartmentByApartmentsId = apartmentByApartmentsId;
    }
}
