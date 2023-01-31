package dao;

import entity.Apartment;
import entity.Building;
import entity.Pet;
import entity.Resident;
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

    public static List<Resident> getResidentsInApartment(Apartment apartment){
        ensureNotNull(apartment);

        List<Resident> residents;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT r FROM Resident r WHERE r.apartmentByApartmentId.idApartment = :id";
            session.beginTransaction();
            residents = session
                    .createQuery(hql, Resident.class)
                    .setParameter("id", apartment.getIdApartment())
                    .getResultList();

            session.getTransaction().commit();

            if(residents.isEmpty()){
                throw new IllegalArgumentException("No apartments in building");
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        return residents;
    }

    public static List<Pet> getPetsInApartment(Apartment apartment){
        ensureNotNull(apartment);

        List<Pet> pets;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT p FROM Pet p WHERE p.apartmentByApartmentId.idApartment = :id";
            session.beginTransaction();
            pets = session
                    .createQuery(hql, Pet.class)
                    .setParameter("id", apartment.getIdApartment())
                    .getResultList();

            session.getTransaction().commit();

            if(pets.isEmpty()){
                throw new IllegalArgumentException("No apartments in building");
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        return pets;
    }

    public static <T> void ensureNotNull(T company) {
        if (company == null) {
            throw new IllegalArgumentException();
        }
    }
}
