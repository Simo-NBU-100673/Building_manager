package dao;

import entity.Company;
import entity.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import session.SessionFactoryUtil;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {
    private static Session session;

    @BeforeAll
    public static void init() {
        //Make the session to be opened to the test environment DB
        System.setProperty("test.env", "true");
        //! speed up the tests by loading the session factory before the tests to map the entities
        SessionFactoryUtil.getSessionFactory();
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
        session.createNativeQuery("DELETE FROM company").executeUpdate();
        session.createNativeQuery("DELETE FROM employee").executeUpdate();
        //set CASCADE DELETE to OFF
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        session.getTransaction().commit();

        //Closing session
        session.close();
    }

    @Test
    void saveEmployee() {
//        fail("Not yet implemented");
    }

    @Test
    void testSaveEmployee() {
//        fail("Not yet implemented");
    }

    @Test
    void getEmployeeById() {
//        fail("Not yet implemented");
    }

    @Test
    void getAllEmployees() {
//        fail("Not yet implemented");
    }

    @Test
    void updateEmployee() {
//        fail("Not yet implemented");
    }

    @Test
    void deleteEmployee() {
//        fail("Not yet implemented");
    }

    @Test
    void deleteEmployeeById() {
//        fail("Not yet implemented");
    }


    //?         FROM HERE ARE THE USED NOT GENERIC METHODS
    @Test
    void getEmployeesByCompanyHaveHiredEmployees() {
        //1. Employees are at least 1 = List<Employee>
    }

    @Test
    void getEmployeesByCompanyNoHiredEmployees() {
        //2. Employees 0 = IllegalArgumentException
    }

    @Test
    void getEmployeesByCompanyNullCompany() {
        //3. Company is null = IllegalArgumentException
    }

    @Test
    void getCountOfEmployeesOfCompanyHaveHiredEmployees() {
        //1. Employees are at least 1 = long
    }

    @Test
    void getCountOfEmployeesOfCompanyNoHiredEmployees() {
        //2. Employees 0 = 0
    }

    @Test
    void getCountOfEmployeesOfCompanyNullCompany() {
        //3. Company is null = IllegalArgumentException
    }

    @Test
    void existsTrue() {
        //1. Employee exists = true
    }

    @Test
    void existsFalse() {
        //2. Employee doesn't exist = false
    }

    @Test
    void existsNullEmployee() {
        //3. Employee is null = IllegalArgumentException
    }

    @Test
    void isHiredTrue() {
        //! This method uses id of the employee and equals for the company
        //1. Employee is hired = true
    }

    @Test
    void isHiredFalse() {
        //! This method uses id of the employee and equals for the company
        //2. Employee is not hired = false
    }

    @Test
    void isHiredNullEmployee() {
        //! This method uses id of the employee and equals for the company
        //3. Employee is null = IllegalArgumentException
    }

    @Test
    void hireEmployeeSuccessfully() {
        //1. Employee is hired = employee.companyByCompanyId = company
    }

    @Test
    void hireEmployeeAlreadyHired() {
        //2. Employee is already hired = IllegalArgumentException
    }

    @Test
    void hireEmployeeNullEmployee() {
        //3. Employee is null = IllegalArgumentException
    }

    @Test
    void hireEmployeeNullCompany() {
        //4. Company is null = IllegalArgumentException

    }

    @Test
    void hireEmployeeNonExistingCompany() {
        //5. Company doesn't exist = IllegalArgumentException
    }

    @Test
    void hireEmployeeNonExistingEmployee() {
        //6. Employee doesn't exist = IllegalArgumentException
    }

    @Test
    void fireEmployeeSuccessfully() {
        //1. Employee is fired = employee.companyByCompanyId = null
    }

    @Test
    void fireEmployeeNotHired() {
        //2. Employee is not hired = IllegalArgumentException
    }

    @Test
    void fireEmployeeNullEmployee() {
        //3. Employee is null = IllegalArgumentException
    }

    @Test
    void fireEmployeeNullCompany() {
        //4. Company is null = IllegalArgumentException

    }

    @Test
    void fireEmployeeNonExistingCompany() {
        //5. Company doesn't exist = IllegalArgumentException
    }

    @Test
    void fireEmployeeNonExistingEmployee() {
        //6. Employee doesn't exist = IllegalArgumentException
    }

    @Test
    void fireAllEmployeesByCompany() {
        //1. Employees are fired = employee.companyByCompanyId = null
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        Employee employee2 = new Employee("Simeon2", "Popov2");
        saveToDataBase(session, employee2);
        linkEmployeeToCompany(session, employee2, company);

        session.beginTransaction();
        Employee employeeFromDB = session.get(Employee.class, employee.getIdEmployee());
        Employee employee2FromDB = session.get(Employee.class, employee2.getIdEmployee());
        session.getTransaction().commit();

        assertEquals(company, employeeFromDB.getCompanyByCompanyId());
        assertEquals(company, employee2FromDB.getCompanyByCompanyId());

        EmployeeDAO.fireAllEmployeesByCompany(company);

        refreshEntity(session, employeeFromDB);
        refreshEntity(session, employee2FromDB);

        assertNull(employeeFromDB.getCompanyByCompanyId());
        assertNull(employee2FromDB.getCompanyByCompanyId());
    }

    @Test
    void ensureNotNull() {
        //1. Object is null = IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> EmployeeDAO.ensureNotNull(null));
    }

    private <T> void refreshEntity(Session session, T entity) {
        session.beginTransaction();
        session.refresh(entity);
        session.getTransaction().commit();
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

    private void unlinkEmployeeFromCompany(Session session, Employee employee) {
        session.beginTransaction();
        employee.setCompanyByCompanyId(null);
        session.update(employee);
        session.getTransaction().commit();
    }
}