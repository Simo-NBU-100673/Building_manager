package dao;

import entity.Apartment;
import entity.Resident;
import org.hibernate.Session;
import session.SessionFactoryUtil;

import java.util.List;

public class ResidentDAO extends GenericDAO<Resident>{
    @Override
    protected Class<Resident> getEntityClass() {
        return Resident.class;
    }

    private static final ResidentDAO RESIDENT_DAO = new ResidentDAO();

    public static boolean exists(long id){
        if(id < 1){
            throw new IllegalArgumentException("Negative value for id");
        }

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT r FROM Resident r WHERE r.idResident = :id";
            session.beginTransaction();
            List<Resident> residents = session
                    .createQuery(hql, Resident.class)
                    .setParameter("id", id)
                    .getResultList();

            session.getTransaction().commit();

            return !residents.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static Resident getResidentById(long id){
        return RESIDENT_DAO.getById(id);
    }

    public static void saveResident(Resident resident){
        RESIDENT_DAO.create(resident);
    }

    public static void updateResident(Resident resident){
        RESIDENT_DAO.update(resident);
    }

    public static void deleteResident(Resident resident){
        RESIDENT_DAO.delete(resident);
    }

    public static <T> void ensureNotNull(T resident) {
        if (resident == null) {
            throw new IllegalArgumentException();
        }
    }
}
