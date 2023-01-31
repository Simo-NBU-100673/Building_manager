package dao;

import entity.Apartment;
import org.hibernate.Session;
import session.SessionFactoryUtil;

import java.util.List;

public class ApartmentDAO extends GenericDAO<Apartment>{
    @Override
    protected Class<Apartment> getEntityClass() {
        return Apartment.class;
    }

    private static final ApartmentDAO APARTMENT_DAO = new ApartmentDAO();

    public static boolean exists(long id){
        if(id < 1){
            throw new IllegalArgumentException("Negative value for id");
        }

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT a FROM Apartment a WHERE a.idApartment = :id";
            session.beginTransaction();
            List<Apartment> apartments = session
                    .createQuery(hql, Apartment.class)
                    .setParameter("id", id)
                    .getResultList();

            session.getTransaction().commit();

            return !apartments.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static Apartment getApartmentById(long id){
        return APARTMENT_DAO.getById(id);
    }

    public static void saveApartment(Apartment apartment){
        APARTMENT_DAO.create(apartment);
    }

    public static void updateApartment(Apartment apartment){
        APARTMENT_DAO.update(apartment);
    }

    public static void deleteApartment(Apartment apartment){
        APARTMENT_DAO.delete(apartment);
    }
}
