package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {

    @BeforeAll
    public static void init() {
        System.setProperty("test.env", "true");
    }

    @AfterAll
    public static void close() {
//        System.setProperty("test.env", "false");
    }

    @Test
    void saveEmployee() {
        fail("Not yet implemented");
    }

    @Test
    void testSaveEmployee() {
        fail("Not yet implemented");
    }

    @Test
    void getEmployeeById() {
        fail("Not yet implemented");
    }

    @Test
    void getAllEmployees() {
        fail("Not yet implemented");
    }

    @Test
    void updateEmployee() {
        fail("Not yet implemented");
    }

    @Test
    void deleteEmployee() {
        fail("Not yet implemented");
    }

    @Test
    void deleteEmployeeById() {
        fail("Not yet implemented");
    }

    @Test
    void getEmployeesByCompany() {
        fail("Not yet implemented");
    }

    @Test
    void getCountOfEmployeesOfCompany() {
        fail("Not yet implemented");
    }

    @Test
    void exists() {
        fail("Not yet implemented");
    }

    @Test
    void isHired() {
        fail("Not yet implemented");
    }

    @Test
    void hireEmployee() {
        fail("Not yet implemented");
    }

    @Test
    void fireEmployee() {
        fail("Not yet implemented");
    }

    @Test
    void fireAllEmployeesByCompany() {
        fail("Not yet implemented");
    }

    @Test
    void ensureNotNull() {
        fail("Not yet implemented");
    }
}