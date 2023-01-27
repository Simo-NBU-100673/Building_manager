package dao;

import entity.Building;
import entity.Company;
import entity.Contract;
import entity.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import session.SessionFactoryUtil;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingDAOTest {
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
    public void testGetCountOfBuildingsOfCompanyNullCompany() {
        Company company = null;
        assertThrows(IllegalArgumentException.class, () -> BuildingDAO.getCountOfBuildingsOfCompany(company));
    }

    @Test
    public void testGetCountOfBuildingsOfCompanyNotExistingId() {
        //TODO add other companies with other ids but one must have the same name as the test one
        Company company = new Company(5,"Not existing company");
        assertEquals(0,BuildingDAO.getCountOfBuildingsOfCompany(company));
    }

    @Test
    public void testGetCountOfBuildingsOfCompanyNotCorrectName() {
        Company company = new Company(1,"SAP");
        Employee employee = new Employee(1,"Simo","Popov");
        Building building = new Building(1,"Kazbek 41","Building 1");


        session.beginTransaction();
        session.save(company);
        session.save(employee);
        session.save(building);
        session.getTransaction().commit();

        session.beginTransaction();
        employee.setCompanyByCompanyId(company);
        session.update(employee);
        session.getTransaction().commit();

        Contract contract = new Contract(building, employee);
        session.beginTransaction();
        session.save(contract);
        session.getTransaction().commit();

        //!Here the company is associated with the company in the DB
        //!ID must be the same as the one in the DB
        Company companyTest = new Company(company.getIdCompany(),"SAPA");

        assertEquals(1,BuildingDAO.getCountOfBuildingsOfCompany(companyTest));
    }

    @Test
    public void testGetCountOfBuildingsOfCompanyNoIdCompany() {
        Company company = new Company("SAP");
        assertThrows(IllegalArgumentException.class, ()-> BuildingDAO.getCountOfBuildingsOfCompany(company));
    }

    @Test
    public void testGetCountOfBuildingsOfNonExistingCompany() {
        Company company = new Company(1,"SAP");
        assertEquals(0, BuildingDAO.getCountOfBuildingsOfCompany(company));
    }

    @Test
    public void testGetCountOfBuildingsOfCompanyDefaultCompany() {
        Company company = new Company();
        assertThrows(IllegalArgumentException.class, ()-> BuildingDAO.getCountOfBuildingsOfCompany(company));
    }

    @Test
    public void testGetCountOfBuildingsOfCompanyCompanyWithTwoBuildings() {
        Company company = new Company(1,"SAP");
        Employee employee = new Employee(1,"Simo","Popov");
        Building buildingFirst = new Building(1,"Kazbek 41","Building 1");
        Building buildingSecond = new Building(2,"Pomelo 2","Building 2");


        session.beginTransaction();
        session.save(company);
        session.save(employee);
        session.save(buildingFirst);
        session.save(buildingSecond);
        session.getTransaction().commit();

        session.beginTransaction();
        employee.setCompanyByCompanyId(company);
        session.update(employee);
        session.getTransaction().commit();

        Contract contractFirst = new Contract(buildingFirst, employee);
        Contract contractSecond = new Contract(buildingSecond, employee);
        session.beginTransaction();
        session.save(contractFirst);
        session.save(contractSecond);
        session.getTransaction().commit();

        //!Here the company is associated with the company in the DB
        //!Name and ID must be the same as the one in the DB
        Company companyTest = new Company(company.getIdCompany(),"SAP");

        assertEquals(2,BuildingDAO.getCountOfBuildingsOfCompany(companyTest));
    }
}