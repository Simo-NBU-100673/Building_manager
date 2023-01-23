package dao;

import entity.Company;
import entity.Contract;
import entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.SessionFactoryUtil;

import java.util.Collection;
import java.util.List;

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

    public static List<Employee> getEmployeesByCompany(Company company) {
        ensureNotNull(company);

        List<Employee> employees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //gets all contracts where Employee works in the company which was given
            employees = session
                    .createQuery("" +
                            "SELECT e FROM Employee e " +
                            "WHERE e.companyByCompanyId = :company " +
                            "", Employee.class)
                    .setParameter("company", company)
                    .getResultList();
            transaction.commit();
        }

        if(employees.isEmpty()){
            throw new IllegalArgumentException("No contracts found");
        }

        return employees;
    }

    public static void ensureNotNull(Company company) {
        if (company == null) {
            throw new IllegalArgumentException();
        }
    }
}
