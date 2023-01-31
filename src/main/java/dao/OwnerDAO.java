package dao;

import entity.Building;
import entity.Owner;
import org.hibernate.Session;
import session.SessionFactoryUtil;

import java.util.List;

public class OwnerDAO extends GenericDAO<Owner>{
    @Override
    protected Class<Owner> getEntityClass() {
        return Owner.class;
    }

    private static final OwnerDAO OWNER_DAO = new OwnerDAO();

    public static boolean exists(long id){
        if(id < 1){
            throw new IllegalArgumentException("Negative value for id");
        }

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT o FROM Owner o WHERE o.idOwner = :id";
            session.beginTransaction();
            List<Owner> owners = session
                    .createQuery(hql, Owner.class)
                    .setParameter("id", id)
                    .getResultList();

            session.getTransaction().commit();

            return !owners.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static Owner getOwnerById(long id){
        return OWNER_DAO.getById(id);
    }
}
