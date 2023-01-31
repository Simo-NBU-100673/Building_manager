package dao;

import entity.*;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.SessionFactoryUtil;

import java.util.*;

public class BuildingDAO extends GenericDAO<Building>{

    @Override
    protected Class<Building> getEntityClass() {
        return Building.class;
    }

    private static final BuildingDAO BUILDING_DAO = new BuildingDAO();

    public static void saveBuilding(Building building){
        BUILDING_DAO.create(building);
    }

    public static void updateBuilding(Building building){
        BUILDING_DAO.update(building);
    }

    public static Building getBuildingById(long id){
        return BUILDING_DAO.getById(id);
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

    public static boolean addressExists(Building building){
        ensureNotNull(building);

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT b FROM Building b WHERE b.address = :address";
            session.beginTransaction();
            List<Building> buildings = session
                    .createQuery(hql, Building.class)
                    .setParameter("address", building.getAddress())
                    .getResultList();

            session.getTransaction().commit();

            return !buildings.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static boolean exists(long id){
        if(id < 1){
            throw new IllegalArgumentException("Negative value for id");
        }

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT b FROM Building b WHERE b.idBuilding = :id";
            session.beginTransaction();
            List<Building> buildings = session
                    .createQuery(hql, Building.class)
                    .setParameter("id", id)
                    .getResultList();

            session.getTransaction().commit();

            return !buildings.isEmpty();

        }catch (Exception e){
            return false;
        }
    }

    public static List<Apartment> getApartmentsInBuilding(Building building){
        ensureNotNull(building);

        List<Apartment> apartments;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT b.apartmentsByIdBuilding FROM Building b JOIN b.apartmentsByIdBuilding a WHERE b.idBuilding = :id";
            session.beginTransaction();
            apartments = session
                    .createQuery(hql, Apartment.class)
                    .setParameter("id", building.getIdBuilding())
                    .getResultList();

            session.getTransaction().commit();

            if(apartments.isEmpty()){
                throw new IllegalArgumentException("No apartments in building");
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        return apartments;
    }

    public static List<Apartment> getApartmentsInBuildingByFloor(Building building, int floor){
        ensureNotNull(building);

        if(floor < 1){
            throw new IllegalArgumentException("Negative floor number");
        }

        List<Apartment> apartments;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT b.apartmentsByIdBuilding FROM Building b JOIN b.apartmentsByIdBuilding a WHERE b.idBuilding = :id AND a.floor =:floor";
            session.beginTransaction();
            apartments = session
                    .createQuery(hql, Apartment.class)
                    .setParameter("floor", floor)
                    .setParameter("id", building.getIdBuilding())
                    .getResultList();

            session.getTransaction().commit();

            if(apartments.isEmpty()){
                throw new IllegalArgumentException("No apartments in building on this floor: "+floor);
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        return apartments;
    }

    public static Map<Building, List<Apartment>> getAllApartmentsByBuildings() {
        Map<Building, List<Apartment>> buildingApartments = new HashMap<>();

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String hql = "SELECT b, b.apartmentsByIdBuilding FROM Building b";
            List<Object[]> results = session.createQuery(hql).getResultList();
            for (Object[] result : results) {
                Building building = (Building) result[0];
                Apartment apartment = (Apartment) result[1];
                List<Apartment> apartments = buildingApartments.getOrDefault(building, new ArrayList<>());
                apartments.add(apartment);
                buildingApartments.put(building, apartments);
            }
        }

        return buildingApartments;

    }

    public static List<Tax> getAllTaxes(Building building){
        ensureNotNull(building);

        List<Tax> taxes;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()){
            String hql = "SELECT t FROM Tax t WHERE t.id.buildingByBuildingId.idBuilding = :id";
            session.beginTransaction();
            taxes = session
                    .createQuery(hql, Tax.class)
                    .setParameter("id", building.getIdBuilding())
                    .getResultList();

            session.getTransaction().commit();

            if(taxes.isEmpty()){
                throw new IllegalArgumentException("No Taxes for this building");
            }

        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        return taxes;
    }

    public static void deleteBuilding(Building building){
        BUILDING_DAO.delete(building);
    }

    public static <T> void ensureNotNull(T company) {
        if (company == null) {
            throw new IllegalArgumentException();
        }
    }
}
