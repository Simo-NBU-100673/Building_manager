package dao;

import entity.Building;
import entity.Company;
import entity.Contract;
import entity.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import session.SessionFactoryUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContractDAOTest {
    private static Session session;

    @BeforeAll
    public static void init() {
        //Make the session to be opened to the test environment DB
        System.setProperty("test.env", "true");
    }

    @BeforeEach
    public void setUp() {
        //Opening session
        session = SessionFactoryUtil.getSessionFactory().openSession();
    }

    @AfterEach
    public void tearDown() {
        //Clearing the DB from all changes
        session.beginTransaction();
        //set CASCADE DELETE to ON
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createNativeQuery("DELETE FROM building").executeUpdate();
        session.createNativeQuery("DELETE FROM company").executeUpdate();
        session.createNativeQuery("DELETE FROM contract").executeUpdate();
        session.createNativeQuery("DELETE FROM employee").executeUpdate();
        //set CASCADE DELETE to OFF
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        session.getTransaction().commit();

        //Closing session
        session.close();
    }

    @Test
    void getContractsByCompanyHavingContracts() {
        //1. Contracts list with at least 1 contract
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        Building building = new Building("24th street", "Test building");
        saveToDataBase(session, building);

        Contract contract = createContract(session, employee, building);

        List<Contract> contracts = ContractDAO.getContractsByCompany(company);
        assertEquals(1, contracts.size());

        assertEquals(contract, contracts.get(0));

    }

    @Test
    void getContractsByCompanyNotHavingContracts() {
        //2. Empty list = IllegalArgumentException
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        assertThrows(IllegalArgumentException.class, () -> ContractDAO.getContractsByCompany(company));
    }

    @Test
    void getContractsByCompanyNullCompany() {
        //3. Null company = IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> ContractDAO.getContractsByCompany(null));
    }


    @Test
    void getBuildingsByCompanyHavingBuildings() {
        //1. Buildings list with at least 1 building
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        Building building = new Building("24th street", "Test building");
        saveToDataBase(session, building);

        Contract contract = createContract(session, employee, building);

        List<Building> buildings = ContractDAO.getBuildingsByCompany(company);
        assertEquals(1, buildings.size());

        assertEquals(building, buildings.get(0));
    }

    @Test
    void getBuildingsByCompanyNotHavingBuildings() {
        //2. Empty list = IllegalArgumentException
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        assertThrows(IllegalArgumentException.class, () -> ContractDAO.getBuildingsByCompany(company));
    }

    @Test
    void getBuildingsByCompanyNullCompany() {
        //3. Null company = IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> ContractDAO.getBuildingsByCompany(null));
    }

    @Test
    void getCountOfContractsOfCompanyHavingContracts() {
        //1. Contracts count at least 1
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        Building building = new Building("24th street", "Test building");
        saveToDataBase(session, building);

        Contract contract = createContract(session, employee, building);

        assertEquals(1, ContractDAO.getCountOfContractsOfCompany(company));
    }

    @Test
    void getCountOfContractsOfCompanyNotHavingContracts() {
        //2. No contracts of this company = 0
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        assertEquals(0, ContractDAO.getCountOfContractsOfCompany(company));
    }

    @Test
    void getCountOfContractsOfCompanyNullCompany() {
        //3. Null company = IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> ContractDAO.getCountOfContractsOfCompany(null));
    }

    @Test
    void ensureNotNull() {
        assertThrows(IllegalArgumentException.class, () -> ContractDAO.ensureNotNull(null));
    }

    private <T> void saveToDataBase(Session session, T entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    private void linkEmployeeToCompany(Session session, Employee employee, Company company) {
        session.beginTransaction();
        employee.setCompanyByCompanyId(company);
        session.update(employee);
        session.getTransaction().commit();
    }

    private Contract createContract(Session session, Employee employee, Building building){
        Contract contract = new Contract(building, employee);

        session.beginTransaction();
        session.save(contract);
        session.getTransaction().commit();
        return contract;
    }
}