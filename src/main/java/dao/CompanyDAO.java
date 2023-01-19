package dao;

import entity.Company;

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
}
