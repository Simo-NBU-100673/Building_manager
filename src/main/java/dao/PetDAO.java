package dao;

import entity.Apartment;
import entity.Pet;
import org.hibernate.Session;
import session.SessionFactoryUtil;

import java.util.List;

public class PetDAO extends GenericDAO<Pet>{
    @Override
    protected Class<Pet> getEntityClass() {
        return Pet.class;
    }

    private static final PetDAO PET_DAO = new PetDAO();

    public static boolean exists(long id){
        if(id < 1){
            throw new IllegalArgumentException("Negative value for id");
        }

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT p FROM Pet p WHERE p.idPet = :id";
            session.beginTransaction();
            List<Pet> pets = session
                    .createQuery(hql, Pet.class)
                    .setParameter("id", id)
                    .getResultList();

            session.getTransaction().commit();

            return !pets.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static Pet getPetById(long id){
        return PET_DAO.getById(id);
    }

    public static void savePet(Pet pet){
        PET_DAO.create(pet);
    }

    public static void updatePet(Pet pet){
        PET_DAO.update(pet);
    }

    public static void deletePet(Pet pet){
        PET_DAO.delete(pet);
    }

    public static <T> void ensureNotNull(T pet) {
        if (pet == null) {
            throw new IllegalArgumentException();
        }
    }
}
