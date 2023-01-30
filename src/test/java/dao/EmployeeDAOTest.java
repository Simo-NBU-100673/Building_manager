package dao;

import entity.Company;
import entity.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import session.SessionFactoryUtil;

import java.util.List;

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
        Employee employee = new Employee("Simeon", "Popov");
        EmployeeDAO.saveEmployee(employee);

        refreshEntity(session, employee);

        assertEquals(employee, EmployeeDAO.getEmployeeById(employee.getIdEmployee()));
    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);

        session.beginTransaction();
        Employee fromDB = session.get(Employee.class, employee.getIdEmployee());
        session.getTransaction().commit();

        assertEquals(employee, fromDB);
    }

    @Test
    void getAllEmployees() {
        Employee employee1 = new Employee("Simeon", "Popov");
        Employee employee2 = new Employee("Simeon2", "Popov2");
        saveToDataBase(session, employee1);
        saveToDataBase(session, employee2);

        List<Employee> employees = List.of(employee1, employee2);
        List<Employee> employeesFromDB = EmployeeDAO.getAllEmployees().stream().toList();

        assertEquals(employees, employeesFromDB);
    }

    @Test
    void updateEmployee() {
        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);

        employee.setFirstName("Simeon2");
        employee.setLastName("Popov2");
        EmployeeDAO.updateEmployee(employee);

        refreshEntity(session, employee);

        session.beginTransaction();
        Employee fromDB = session.get(Employee.class, employee.getIdEmployee());
        session.getTransaction().commit();

        assertEquals(employee, fromDB);
    }

    @Test
    void deleteEmployee() {
        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);

        EmployeeDAO.deleteEmployee(employee);

        session.beginTransaction();
        List <Employee> fromDB = session.createQuery("FROM Employee WHERE idEmployee = :id", Employee.class)
                .setParameter("id", employee.getIdEmployee())
                .getResultList();
        session.getTransaction().commit();

        assertTrue(fromDB.isEmpty());
    }


    //?         FROM HERE ARE THE USED NOT GENERIC METHODS
    @Test
    void getEmployeesByCompanyHaveHiredEmployees() {
        //1. Employees are at least 1 = List<Employee>
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        assertEquals(1, EmployeeDAO.getEmployeesByCompany(company).size());
        assertEquals(employee, EmployeeDAO.getEmployeesByCompany(company).get(0));
    }

    @Test
    void getEmployeesByCompanyNoHiredEmployees() {
        //2. Employees 0 = IllegalArgumentException
        Company company = new Company(1, "SAP");

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.getEmployeesByCompany(company));
    }

    @Test
    void getEmployeesByCompanyNullCompany() {
        //3. Company is null = IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.getEmployeesByCompany(null));
    }

    @Test
    void getCountOfEmployeesOfCompanyHaveHiredEmployees() {
        //1. Employees are at least 1 = long
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        assertEquals(1, EmployeeDAO.getCountOfEmployeesOfCompany(company));
    }

    @Test
    void getCountOfEmployeesOfCompanyNoHiredEmployees() {
        //2. Employees 0 = 0
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        assertEquals(0, EmployeeDAO.getCountOfEmployeesOfCompany(company));
    }

    @Test
    void getCountOfEmployeesOfCompanyNullCompany() {
        //3. Company is null = IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.getCountOfEmployeesOfCompany(null));
    }

    @Test
    void existsTrue() {
        //1. Employee exists = true
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        assertTrue(EmployeeDAO.exists(employee));
    }

    @Test
    void existsFalse() {
        //2. Employee doesn't exist = false
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");

        assertFalse(EmployeeDAO.exists(employee));
    }

    @Test
    void existsNullEmployee() {
        //3. Employee is null = IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.exists(null));
    }

    @Test
    void isHiredTrue() {
        //! This method uses id of the employee and equals for the company
        //1. Employee is hired = true
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        assertTrue(EmployeeDAO.isHired(employee.getIdEmployee(), company));
    }

    @Test
    void isHiredFalse() {
        //! This method uses id of the employee and equals for the company
        //2. Employee is not hired = false
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        assertFalse(EmployeeDAO.isHired(employee.getIdEmployee()+1, company));
    }

    @Test
    void hireEmployeeSuccessfully() {
        //1. Employee is hired = employee.companyByCompanyId = company
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);

        //check if employee.companyByCompanyId is null which means that the employee is not hired
        assertNull(employee.getCompanyByCompanyId());

        EmployeeDAO.hireEmployee(company, employee);

        refreshEntity(session, employee);

        assertEquals(company, employee.getCompanyByCompanyId());
    }

    @Test
    void hireEmployeeAlreadyHired() {
        //2. Employee is already hired = IllegalArgumentException
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.hireEmployee(company, employee));
    }

    @Test
    void hireEmployeeNullEmployee() {
        //3. Employee is null = IllegalArgumentException
        Company company = new Company(1, "SAP");
        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.hireEmployee(company, null));
    }

    @Test
    void hireEmployeeNullCompany() {
        //4. Company is null = IllegalArgumentException
        Employee employee = new Employee("Simeon", "Popov");
        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.hireEmployee(null, employee));

    }

    @Test
    void hireEmployeeNonExistingCompany() {
        //5. Company doesn't exist = IllegalArgumentException
        Company company = new Company(1, "SAP");

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.hireEmployee(company, employee));
    }

    @Test
    void hireEmployeeNonExistingEmployee() {
        //6. Employee doesn't exist = IllegalArgumentException
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.hireEmployee(company, employee));
    }

    @Test
    void fireEmployeeSuccessfully() {
        //1. Employee is fired = employee.companyByCompanyId = null
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);
        linkEmployeeToCompany(session, employee, company);

        //check if employee.companyByCompanyId is null which means that the employee is not hired
        assertNotNull(employee.getCompanyByCompanyId());

        EmployeeDAO.fireEmployee(company, employee);

        refreshEntity(session, employee);

        assertNull(employee.getCompanyByCompanyId());
    }

    @Test
    void fireEmployeeNotHired() {
        //2. Employee is not hired = IllegalArgumentException
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);

        assertNull(employee.getCompanyByCompanyId());
        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.fireEmployee(company, employee));
    }

    @Test
    void fireEmployeeNullEmployee() {
        //3. Employee is null = IllegalArgumentException
        Employee employee = new Employee("Simeon", "Popov");

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.fireEmployee(null, employee));
    }

    @Test
    void fireEmployeeNullCompany() {
        //4. Company is null = IllegalArgumentException
        Company company = new Company(1, "SAP");

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.fireEmployee(company, null));

    }

    @Test
    void fireEmployeeNonExistingCompany() {
        //5. Company doesn't exist = IllegalArgumentException
        Company company = new Company(1, "SAP");

        Employee employee = new Employee("Simeon", "Popov");
        saveToDataBase(session, employee);

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.fireEmployee(company, employee));
    }

    @Test
    void fireEmployeeNonExistingEmployee() {
        //6. Employee doesn't exist = IllegalArgumentException
        Company company = new Company(1, "SAP");
        saveToDataBase(session, company);

        Employee employee = new Employee("Simeon", "Popov");

        assertThrows(IllegalArgumentException.class,
                () -> EmployeeDAO.fireEmployee(company, employee));
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