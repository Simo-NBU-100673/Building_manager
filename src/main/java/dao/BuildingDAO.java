package dao;

import entity.Building;
import entity.Company;
import entity.Contract;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.SessionFactoryUtil;

public class BuildingDAO extends GenericDAO<Building>{

    @Override
    protected Class<Building> getEntityClass() {
        return Building.class;
    }

    public static long getCountOfBuildingsOfCompany(Company company) {
        ensureNotNull(company);

        long countOfBuildings;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //gets all contracts where Employee works in the company which was given
            countOfBuildings = session
                    .createQuery("" +
                            "SELECT COUNT(b.idBuilding) " +
                            "FROM Building b " +
                            "INNER JOIN b.contractByIdBuilding c " +
                            "INNER JOIN c.employeeByEmployeeId e " +
                            "WHERE e.companyByCompanyId = :company " +
                            "", Long.class)
                    .setParameter("company", company)
                    .getSingleResult();
            transaction.commit();
        }catch (NoResultException | NonUniqueResultException e){
            throw new IllegalArgumentException(e);
        }catch (IllegalStateException e){
            throw new IllegalArgumentException("Not fully created object passed",e);
        }

        return countOfBuildings;
    }

    public static void ensureNotNull(Company company) {
        if (company == null) {
            throw new IllegalArgumentException();
        }
    }
}
