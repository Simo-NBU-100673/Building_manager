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
    @ManyToOne
    @JoinColumn(name = "Apartments_id", referencedColumnName = "idApartment", nullable = false)
    private Apartment apartmentByApartmentsId;

}
