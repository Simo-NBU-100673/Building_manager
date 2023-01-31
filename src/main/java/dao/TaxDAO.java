package dao;

import entity.Building;
import entity.Tax;
import org.hibernate.Session;
import session.SessionFactoryUtil;

import java.util.List;

public class TaxDAO extends GenericDAO<Tax>{
    @Override
    protected Class<Tax> getEntityClass() {
        return Tax.class;
    }

    private static final TaxDAO TAX_DAO = new TaxDAO();

    public static void saveTax(Tax tax){
        TAX_DAO.create(tax);
    }

    public static void updateTax(Tax tax){
        TAX_DAO.update(tax);
    }

    public static boolean exists(Tax tax){
        ensureNotNull(tax);

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT t FROM Tax t WHERE t.id.buildingByBuildingId =: building AND t.id.type =: type";
            session.beginTransaction();
            List<Tax> taxes = session
                    .createQuery(hql, Tax.class)
                    .setParameter("building", tax.getBuildingByBuildingId())
                    .setParameter("type", tax.getType())
                    .getResultList();

            session.getTransaction().commit();

            return !taxes.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static void deleteTax(Tax tax){
        TAX_DAO.delete(tax);
    }

    public static <T> void ensureNotNull(T tax) {
        if (tax == null) {
            throw new IllegalArgumentException();
        }
    }
}
