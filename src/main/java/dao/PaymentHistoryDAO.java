package dao;

import entity.Apartment;
import entity.Building;
import entity.Paymentshistory;
import entity.Tax;
import org.hibernate.Session;
import session.SessionFactoryUtil;

import java.util.List;

public class PaymentHistoryDAO extends GenericDAO<Paymentshistory>{
    @Override
    protected Class<Paymentshistory> getEntityClass() {
        return Paymentshistory.class;
    }

    private static final PaymentHistoryDAO PAYMENT_HISTORY_DAO_DAO = new PaymentHistoryDAO();

    public static boolean exists(long id){
        if(id < 1){
            throw new IllegalArgumentException("Negative value for id");
        }

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT p FROM Paymentshistory p WHERE p.idPayment = :id";
            session.beginTransaction();
            List<Paymentshistory> paymnets = session
                    .createQuery(hql, Paymentshistory.class)
                    .setParameter("id", id)
                    .getResultList();

            session.getTransaction().commit();

            return !paymnets.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static Paymentshistory getPaymentById(long id){
        return PAYMENT_HISTORY_DAO_DAO.getById(id);
    }

    public static void savePayment(Paymentshistory payment){
        PAYMENT_HISTORY_DAO_DAO.create(payment);
    }

    public static void updatePayment(Paymentshistory payment){
        PAYMENT_HISTORY_DAO_DAO.update(payment);
    }

    public static void deletePayment(Paymentshistory payment) {
        PAYMENT_HISTORY_DAO_DAO.delete(payment);
    }

    public static List<Paymentshistory> getAllPayments(Apartment apartment){
        ensureNotNull(apartment);

        List<Paymentshistory> payments;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT p FROM Paymentshistory p WHERE p.apartmentByApartmentsId.idApartment = :id";
            session.beginTransaction();
            payments = session
                    .createQuery(hql, Paymentshistory.class)
                    .setParameter("id", apartment.getIdApartment())
                    .getResultList();

            session.getTransaction().commit();

            if(payments.isEmpty()){
                throw new IllegalArgumentException("No payments recorded for this apartment");
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        return payments;
    }

    public static Paymentshistory getLastPayment(Apartment apartment){
        ensureNotNull(apartment);

        Paymentshistory payment;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT p FROM Paymentshistory p WHERE p.apartmentByApartmentsId.idApartment = :id ORDER BY p.dateOfPayment DESC";
            session.beginTransaction();
            payment = session
                    .createQuery(hql, Paymentshistory.class)
                    .setParameter("id", apartment.getIdApartment())
                    .setMaxResults(1)
                    .getSingleResult();

            session.getTransaction().commit();

            if(payment == null){
                throw new IllegalArgumentException("No payments recorded for this apartment");
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        return payment;
    }

    public static <T> void ensureNotNull(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }
}
