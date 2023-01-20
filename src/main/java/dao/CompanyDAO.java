package dao;

import entity.Company;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import session.SessionFactoryUtil;

import java.util.Collection;

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
    public static Collection<Company> getAllCompanies() {
        return COMPANY_DAO.getAllEntities();
    }

    //Working but PK must be the same
    public static void updateEmployee(Company company) {
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
    public static Company getCompanyByName(String name){
        CriteriaBuilder builder = SessionFactoryUtil.getSessionFactory().createEntityManager().getCriteriaBuilder();
        CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
        Root<Company> root = criteria.from(Company.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("name"), name));
        TypedQuery<Company> query = SessionFactoryUtil.getSessionFactory().createEntityManager().createQuery(criteria);

        return query.getSingleResult();
    }
    public static boolean containsCompany(Company company) throws IllegalArgumentException{
        ensureNotNull(company);
        return getAllCompanies().contains(company);
    }

    public static void ensureNotNull(Company company){
        if (company == null){
            throw new IllegalArgumentException();
        }
    }
}
