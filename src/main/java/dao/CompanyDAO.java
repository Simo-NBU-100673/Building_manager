package dao;

import entity.Company;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.SessionFactoryUtil;

import java.util.Collection;
import java.util.List;

public final class CompanyDAO extends GenericDAO<Company> {

    private static final CompanyDAO COMPANY_DAO = new CompanyDAO();

    @Override
    protected Class<Company> getEntityClass() {
        return Company.class;
    }

    //Working
    public static void saveCompany(Company company) {
        COMPANY_DAO.create(company);
    }

    //Working
    public static void saveCompanies(Collection<Company> companies) {
        COMPANY_DAO.create(companies);
    }

    //Working
    public static Company getCompanyById(long id) {
        return COMPANY_DAO.getById(id);
    }

    //Working
    public static List<Company> getAllCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            List<Company> companies = session
                    .createQuery("SELECT a FROM Company a ORDER BY a.idCompany ASC ", Company.class)
                    .getResultList();

            if (companies.isEmpty()) {
                throw new IllegalArgumentException("No companies found");
            }

            return companies;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //Working but PK must be the same
    public static void updateCompany(Company company) {
        try {
            COMPANY_DAO.update(company);

        } catch (OptimisticLockException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //Working but PK must be the same
    public static void deleteCompany(Company company) {
        //fire all employees of this company and then delete the company
        EmployeeDAO.fireAllEmployeesByCompany(company);

        try {
            //delete company
            COMPANY_DAO.delete(company);

        }catch (OptimisticLockException e){
            throw new IllegalArgumentException(e);
        }
    }

    //Working
    public static void deleteCompanyById(long id) {
        COMPANY_DAO.deleteById(id);
    }

    //throws NoSuchFileException when the file does not exist
    //throws NonUniqueResultException when there are multiple file that are adequate to the criteria
    public static Company getCompanyByName(String name) {
        ensureNotNull(name);

        Company company;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session
                    .createQuery("SELECT a FROM Company a WHERE a.name = :name", Company.class)
                    .setParameter("name", name)
                    .getSingleResult();

            transaction.commit();
//            session.evict(company);
        } catch (NoResultException | NonUniqueResultException e) {
            throw new IllegalArgumentException(e);
        }

        return company;
    }

    public static boolean exists(Company company) throws IllegalArgumentException {
        ensureNotNull(company);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            TypedQuery<Company> query = session.createQuery("SELECT name FROM Company WHERE name=:name", Company.class);
            query.setParameter("name", company.getName());
            return query.getSingleResult() != null;
        } catch (NoResultException | NonUniqueObjectException e) {
            return false;
        }
    }

    public static <T> void ensureNotNull(T company) {
        if (company == null) {
            throw new IllegalArgumentException();
        }
    }
}
