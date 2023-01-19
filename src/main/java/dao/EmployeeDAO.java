package dao;

import entity.Employee;

import java.util.Collection;

//TODO make singleton with DLC
public final class EmployeeDAO extends GenericDAO<Employee> {

    private static final EmployeeDAO EMPLOYEE_DAO = new EmployeeDAO();

    @Override
    protected Class<Employee> getEntityClass() {
        return Employee.class;
    }

    //Working
    public static void saveEmployee(Employee employee) {
        EMPLOYEE_DAO.create(employee);
    }

    //Working
    public static void saveEmployee(Collection<Employee> Employee) {
        EMPLOYEE_DAO.create(Employee);
    }

    //Working
    public static Employee getEmployeeById(int id) {
        return EMPLOYEE_DAO.getById(id);
    }

    //Working
    public static Collection<Employee> getAllEmployees() {
        return EMPLOYEE_DAO.getAllEntities();
    }

    //Working but PK must be the same
    public static void updateEmployee(Employee employee) {
        EMPLOYEE_DAO.update(employee);
    }

    //Working but PK must be the same
    public static void deleteEmployee(Employee employee) {
        EMPLOYEE_DAO.delete(employee);
    }

    //Working
    public static void deleteEmployeeById(int id) {
        EMPLOYEE_DAO.deleteById(id);
    }
}
