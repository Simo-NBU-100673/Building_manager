package dao;

import entity.Company;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;
import org.junit.jupiter.api.*;
import session.SessionFactoryUtil;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingDAOTest {
    private static Session session;
    @Disabled
    @BeforeAll
    public static void init() {
        //Make the session to be opened to the test environment DB
        System.setProperty("test.env", "true");

        //Opening session
        session = SessionFactoryUtil.getSessionFactory().openSession();
    }
    @Disabled
    @AfterAll
    public static void close() {
//        //Make the session to be opened op the normal environment DB
//        System.setProperty("test.env", "false");

        //Closing session
        session.close();
    }

    @Disabled
    @Test
    @DisplayName("Test if the passed company is null")
    public void testGetCountOfBuildingsOfCompanyNullCompany() {
        assertThrows(IllegalArgumentException.class, () -> BuildingDAO.getCountOfBuildingsOfCompany(null));
    }
    @Disabled
    @Test
    @DisplayName("Test if the passed company is with ID which\nis not associated with any company in the DB")
    public void testGetCountOfBuildingsOfCompanyNotExistingId() {
        Company company = new Company(5,"Not existing company");
        assertEquals(0,BuildingDAO.getCountOfBuildingsOfCompany(company));
    }
    @Disabled
    @Test
    @DisplayName("Test if the passed company is with NAME which\nis not associated with any company in the DB, but ID is correct")
    public void testGetCountOfBuildingsOfCompanyNotCorrectName() {
        Company company = new Company(2,"Not existing company");
        assertEquals(1,BuildingDAO.getCountOfBuildingsOfCompany(company));
    }
    @Disabled
    @Test
    @DisplayName("Test if the passed company is created with only name and\nthere is no company with such name in the DB and ID is not set")
    public void testGetCountOfBuildingsOfCompanyNoIdCompany() {
        Company company = new Company("Not existing company");
        assertThrows(IllegalArgumentException.class, ()-> BuildingDAO.getCountOfBuildingsOfCompany(company));
    }
    @Disabled
    @Test
    @DisplayName("Test if the passed company is created with\ndefault constructor so it has no ID and NAME='No name'")
    public void testGetCountOfBuildingsOfCompanyDefaultCompany() {
        Company company = new Company();
        assertThrows(IllegalArgumentException.class, ()-> BuildingDAO.getCountOfBuildingsOfCompany(company));
    }
    @Disabled
    @Test
    @DisplayName("Test if the passed company is with ID and NAME which\nare associated with company in the DB")
    public void testGetCountOfBuildingsOfCompanyCompanyWithTwoBuildings() {
        Company company = new Company(1,"SAP");
        assertEquals(2,BuildingDAO.getCountOfBuildingsOfCompany(company));
    }
}