package dao;

import entity.Company;
import entity.Contract;
import jakarta.persistence.NoResultException;
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
import java.util.NoSuchElementException;

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

            if (companies.isEmpty()){
                throw new IllegalArgumentException("No companies found");
            }

            return companies;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //Working but PK must be the same
    public static void updateCompany(Company company) {
        COMPANY_DAO.update(company);
    }

    //Working but PK must be the same
    public static void deleteCompany(Company company) {
        COMPANY_DAO.delete(company);
    }

    //Working
    public static void deleteCompanyById(long id) {
        COMPANY_DAO.deleteById(id);
    }

    //throws NoSuchFileException when the file does not exist
    //throws NonUniqueResultException when there are multiple file that are adequate to the criteria
    public static Company getCompanyByName(String name) {
        CriteriaBuilder builder = SessionFactoryUtil.getSessionFactory().createEntityManager().getCriteriaBuilder();
        CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
        Root<Company> root = criteria.from(Company.class);
        criteria
                .select(root)
                .where(builder.equal(root.get("name"), name));

        Company company = SessionFactoryUtil
                .getSessionFactory()
                .createEntityManager()
                .createQuery(criteria)
                .getSingleResult();

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

    public static void ensureNotNull(Company company) {
        if (company == null) {
            throw new IllegalArgumentException();
        }
    }
}
