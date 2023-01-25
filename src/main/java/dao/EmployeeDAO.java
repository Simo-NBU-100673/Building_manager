package dao;

import entity.Company;
import entity.Employee;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
    public static Employee getEmployeeById(long id) {
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
            throw new IllegalArgumentException("No employees found hired by this company");
        }

        return employees;
    }

    public static long getCountOfEmployeesOfCompany(Company company) {
        ensureNotNull(company);

        long numberOfEmployees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //gets all contracts where Employee works in the company which was given
            numberOfEmployees = session
                    .createQuery("" +
                            "SELECT COUNT(e.companyByCompanyId) " +
                            "FROM Employee e " +
                            "WHERE e.companyByCompanyId = :company " +
                            "", Long.class)
                    .setParameter("company", company)
                    .getSingleResult();
            transaction.commit();
        }catch (NonUniqueResultException e){
            throw new IllegalArgumentException(e);
        }catch (NoResultException e){
            return 0;
        }

        return numberOfEmployees;
    }

    public static boolean exists(Employee employee) throws IllegalArgumentException {
        ensureNotNull(employee);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            TypedQuery<Employee> query = session.createQuery("SELECT e.idEmployee FROM Employee e WHERE e.idEmployee =:id", Employee.class);
            query.setParameter("id", employee.getIdEmployee());
            return query.getSingleResult() != null;
        } catch (NoResultException | NonUniqueObjectException e) {
            return false;
        }
    }

    public static boolean isHired(long idEmployee, Company company) {
        ensureNotNull(company);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Company> query = session.createQuery("SELECT e.companyByCompanyId FROM Employee e WHERE e.idEmployee =:id", Company.class);
            query.setParameter("id", idEmployee);

            Company result = query.getSingleResult();
            if (result == null){
                return false;
            }

            return result.equals(company);

        } catch (NoResultException | NonUniqueObjectException e) {
            return false;
        }
    }

    public static void hireEmployee(Company tmpCompany, Employee employee) {
        ensureNotNull(tmpCompany);
        ensureNotNull(employee);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("SELECT e FROM Employee e WHERE e.idEmployee =:id", Employee.class);
            query.setParameter("id", employee.getIdEmployee());
            session.beginTransaction();

            Employee result = query.getSingleResult();
            if (result.getCompanyByCompanyId() != null){
                throw new IllegalArgumentException("Employee already hired");
            }

            result.setCompanyByCompanyId(tmpCompany);
            session.update(result);
            session.getTransaction().commit();

        } catch (NoResultException | NonUniqueObjectException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void fireEmployee(Company tmpCompany, Employee employee) {
        ensureNotNull(tmpCompany);
        ensureNotNull(employee);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("SELECT e FROM Employee e WHERE e.idEmployee =:id", Employee.class);
            query.setParameter("id", employee.getIdEmployee());
            session.beginTransaction();

            Employee result = query.getSingleResult();

            //if null he does not work anywhere, if not null but other company we can not fire him from this company
            if (result.getCompanyByCompanyId() == null || !result.getCompanyByCompanyId().equals(tmpCompany)){
                throw new IllegalArgumentException("Can not fire employee who does not work in this company");
            }

            result.setCompanyByCompanyId(null);
            session.update(result);
            session.getTransaction().commit();

        } catch (NoResultException | NonUniqueObjectException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void fireAllEmployeesByCompany(Company company) {
        ensureNotNull(company);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("SELECT e FROM Employee e WHERE e.companyByCompanyId =:company", Employee.class);
            query.setParameter("company", company);
            session.beginTransaction();

            List<Employee> result = query.getResultList();

            for (Employee employee : result) {
                employee.setCompanyByCompanyId(null);
                session.update(employee);
            }

            session.getTransaction().commit();

        } catch (NoResultException | NonUniqueObjectException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> void ensureNotNull(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
    }
}
