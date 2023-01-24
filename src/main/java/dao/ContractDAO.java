package dao;

import entity.Building;
import entity.Company;
import entity.Contract;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.SessionFactoryUtil;

import java.util.List;

public class ContractDAO extends GenericDAO<Contract>{
    @Override
    protected Class<Contract> getEntityClass() {
        return Contract.class;
    }

    public static List<Contract> getContractsByCompany(Company company) {
        ensureNotNull(company);

        List<Contract> contracts;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //gets all contracts where Employee works in the company which was given
            contracts = session
                    .createQuery("" +
                            "SELECT c FROM Contract c " +
                            "JOIN FETCH c.employeeByEmployeeId e " +
                            "WHERE e.companyByCompanyId = :company " +
                            "", Contract.class)
                    .setParameter("company", company)
                    .getResultList();
            transaction.commit();
        }

        if(contracts.isEmpty()){
            throw new IllegalArgumentException("No contracts found");
        }

        return contracts;
    }

    public static List<Building> getBuildingsByCompany(Company company) {
        ensureNotNull(company);

        List<Building> buildings;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //gets all contracts where Employee works in the company which was given
            buildings = session
                    .createQuery("" +
                            "SELECT c.buildingByBuildingId " +
                            "FROM Contract c " +
                            "JOIN c.employeeByEmployeeId e " +
                            "WHERE e.companyByCompanyId = :company " +
                            "", Building.class)
                    .setParameter("company", company)
                    .getResultList();
            transaction.commit();
        }

        if(buildings.isEmpty()){
            throw new IllegalArgumentException("No contracts found");
        }

        return buildings;
    }

    public static long getCountOfBuildingsPerCompany(Company company) {
        ensureNotNull(company);

        long numberOfBuildings;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //gets all contracts where Employee works in the company which was given
            numberOfBuildings = session
                    .createQuery("" +
                            "SELECT COUNT(c.buildingByBuildingId) " +
                            "FROM Contract c " +
                            "JOIN c.employeeByEmployeeId e " +
                            "WHERE e.companyByCompanyId = :company " +
                            "", Long.class)
                    .setParameter("company", company)
                    .getSingleResult();
            transaction.commit();
        }catch (NoResultException | NonUniqueResultException e){
            throw new IllegalArgumentException(e);
        }

        return numberOfBuildings;
    }

    public static void ensureNotNull(Company company) {
        if (company == null) {
            throw new IllegalArgumentException();
        }
    }
}
