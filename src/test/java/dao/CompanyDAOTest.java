package dao;

import entity.Company;
import entity.Employee;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.Session;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import session.SessionFactoryUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDAOTest {
    private static Session session;

    @BeforeAll
    public static void init() {
        //Make the session to be opened to the test environment DB
        System.setProperty("test.env", "true");
        //! speed up the tests by loading the session factory before the tests to map the entities
        SessionFactoryUtil.getSessionFactory();
    }

    @BeforeEach
    public void clearDB() {
        //Opening session
        session = SessionFactoryUtil.getSessionFactory().openSession();
    }

    @AfterEach
    public void close() {
        //Clearing the DB from all changes
        session.beginTransaction();
        session.createQuery("DELETE FROM Company").executeUpdate();
        session.getTransaction().commit();

        //Closing session
        session.close();
    }

    @Disabled
    @Test
    void saveCompanyThatAlreadyExistsSameId() {
        //create a company with the id 1 and put it in the DB
        Company companyDB = new Company(1, "SAP");
        session.beginTransaction();
        session.save(companyDB);
        session.getTransaction().commit();

        //They are compared by id if they are equal even that the names are different
        //?THIS MUST THROW EXCEPTION BUT IT DOESN'T FOR NOW (BUG) because the method exists compares them by id this is a very nice exception
        Company company = new Company(1, "Diff name");
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.saveCompany(company));
    }

    @Test
    void saveCompanyThatAlreadyExistsSameName() {
        //create a company with the id 1 and put it in the DB
        Company companyDB = new Company(1, "SAP");
        session.beginTransaction();
        session.save(companyDB);
        session.getTransaction().commit();

        //They are compared by id if they are equal even that the names are the same
        Company company = new Company(1, "SAP");
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.saveCompany(company));
    }

    @Test
    void saveCompanyNull() {
        Company company = null;
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.saveCompany(company));
    }

    @Test
    void saveCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        CompanyDAO.saveCompany(company);

        session.beginTransaction();
        Company companyFromDB = session.get(Company.class, company.getIdCompany());

        //delete the company from the DB after we got it from the DB
        session.remove(companyFromDB);
        session.getTransaction().commit();

        assertEquals(company, companyFromDB);
    }

    @Test
    void saveCompanies() {
        List<Company> companies = List.of(
                new Company("TEST NO EXISTING NAME 1"),
                new Company("TEST NO EXISTING NAME 2")
        );

        //save the companies in the DB
        CompanyDAO.saveCompanies(companies);

        //get the companies from the DB
        session.beginTransaction();
        List<Company> companiesFromDB = session
                .createQuery("SELECT a FROM Company a ORDER BY a.idCompany ASC ", Company.class)
                .getResultList();

        session.getTransaction().commit();

        assertEquals(companies, companiesFromDB);
    }

    @Test
    void getCompanyById() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        Company companyFromDB = CompanyDAO.getCompanyById(company.getIdCompany());

        assertEquals(company, companyFromDB);
    }

    @Test
    void getCompanyByNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.getCompanyById(-1));
    }

    @Test
    void getCompanyByIdNonExistingId() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        long id = company.getIdCompany();

        session.beginTransaction();
        session.remove(company);
        session.getTransaction().commit();

        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.getCompanyById(id));
    }

    @Test
    void getAllCompanies() {
        List<Company> companies = List.of(
                new Company("TEST NO EXISTING NAME 1"),
                new Company("TEST NO EXISTING NAME 2")
        );

        session.beginTransaction();

        //save the companies in the DB
        companies.forEach(session::save);
        session.getTransaction().commit();

        //get the companies from the DB
        List<Company> companiesFromDB = CompanyDAO.getAllCompanies();

        assertEquals(companies, companiesFromDB);
    }

    @Test
    void getAllCompaniesNoCompanies() {
        assertThrows(IllegalArgumentException.class, CompanyDAO::getAllCompanies);
    }

    @Test
    void updateCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        company.setName("NEW NAME");
        CompanyDAO.updateCompany(company);

        session.beginTransaction();
        Company companyFromDB = session.get(Company.class, company.getIdCompany());
        session.getTransaction().commit();

        assertEquals(company, companyFromDB);
    }

    @Test
    void updateCompanyNull() {
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.updateCompany(null));
    }

    @Test
    void updateCompanyNotExisting() {
        Company company = new Company(1, "TEST NO EXISTING NAME");
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.updateCompany(company));
    }

    @Test
    void deleteCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        //check if the company is in the DB
        session.beginTransaction();
        Company companyFromDB = session.get(Company.class, company.getIdCompany());
        session.getTransaction().commit();
        assertNotNull(companyFromDB);

        CompanyDAO.deleteCompany(company);

        session.beginTransaction();
        //get all the companies from the DB
        List<Company> companiesFromDB = session
                .createQuery("SELECT a FROM Company a ORDER BY a.idCompany ASC ", Company.class)
                .getResultList();
        session.getTransaction().commit();

        assertTrue(companiesFromDB.isEmpty());
    }

    @Test
    void deleteNonExistingCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        //check if the company is in the DB
        session.beginTransaction();
        Company companyFromDB = session.get(Company.class, company.getIdCompany());
        session.getTransaction().commit();
        assertNotNull(companyFromDB);

        Company companyNotInDB = new Company(2,"TEST NO EXISTING NAME 2");
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.deleteCompany(companyNotInDB));
    }

    @Test
    void deleteCompanyNull() {
        Company company = null;
        //This is side effect of trying to fire first all employees
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.deleteCompany(company));
    }

    @Test
    void deleteCompanyWithEmployees() {
        //create company and put it in the DB then create employees and put them in the DB so that the company has employees then delete the company and check if the employees are deleted as well
        session.beginTransaction();
        Company company = new Company("NEW COMPANY");
        Employee employee = new Employee(1,"Mihael","Petrov");
        session.save(company);
        session.save(employee);
        session.getTransaction().commit();

        employee.setCompanyByCompanyId(company);

        //update the employee in the DB
        session.beginTransaction();
        session.update(employee);
        session.getTransaction().commit();

        //check if the company and employee are in the DB
        session.beginTransaction();
        //get all the companies from the DB
        Company companyFromDB = session.get(Company.class, company.getIdCompany());
        Employee employeeFromDB = session.get(Employee.class, employee.getIdEmployee());
        session.getTransaction().commit();

        //check if the company and employee are already exist
        assertEquals(company, companyFromDB);
        assertEquals(employee, employeeFromDB);
        assertNotNull(employeeFromDB.getCompanyByCompanyId());

        //delete the company
        CompanyDAO.deleteCompany(companyFromDB);

        //check if the company id deleted and employee have null company
        session.beginTransaction();
        assertThrows(NoResultException.class, () -> {session
                .createQuery("SELECT c FROM Company c WHERE c.idCompany=:idCompany", Company.class)
                .setParameter("idCompany", company.getIdCompany())
                .getSingleResult();
        });
        session.getTransaction().commit();

        //check if the employee is deleted
        session.beginTransaction();
        session.refresh(employeeFromDB);
        session.getTransaction().commit();

        assertNull(employeeFromDB.getCompanyByCompanyId());
    }

    @Test
    void deleteCompanyByIdExistingCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        //check if the company is in the DB
        session.beginTransaction();
        Company companyFromDB = session.get(Company.class, company.getIdCompany());
        session.getTransaction().commit();
        assertEquals(company, companyFromDB);

        CompanyDAO.deleteCompanyById(company.getIdCompany());

        session.beginTransaction();
        //get all the companies from the DB
        assertThrows(NoResultException.class, () -> {session
                .createQuery("SELECT c FROM Company c WHERE c.idCompany=:idCompany", Company.class)
                .setParameter("idCompany", company.getIdCompany())
                .getSingleResult();
        });
        session.getTransaction().commit();
    }

    @ParameterizedTest
    @ValueSource(ints = {0,Integer.MAX_VALUE, Integer.MIN_VALUE})
    void deleteCompanyById(int id) {
        Company company = new Company(id,"TEST NO EXISTING NAME");
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.deleteCompanyById(company.getIdCompany()));
    }

    @Test
    void getCompanyByNameExistingCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        Company companyFromDB = CompanyDAO.getCompanyByName(company.getName());

        assertEquals(company, companyFromDB);
    }

    @Test
    void getCompanyByNameNonExistingCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.getCompanyByName(company.getName()));
    }

    @Test
    void getCompanyByNameNullCompany() {
        String name = null;
        assertThrows(IllegalArgumentException.class, () -> CompanyDAO.getCompanyByName(name));
    }

    @Test
    void existsWithExistingCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();

        assertTrue(CompanyDAO.exists(company));
    }

    @Test
    void existsWithNotExistingCompany() {
        Company company = new Company("TEST NO EXISTING NAME");
        assertFalse(CompanyDAO.exists(company));
    }

    @Test
    void existsWithCompanyNull() {
        Company company = null;
        assertThrows(IllegalArgumentException.class,()->CompanyDAO.exists(company));
    }
}