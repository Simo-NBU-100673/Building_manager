package dao;

import entity.Building;
import entity.Company;
import entity.Contract;
import entity.Employee;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import session.SessionFactoryUtil;

import java.util.*;

//TODO make singleton with DLC
public final class EmployeeDAO extends GenericDAO<Employee> {

    public static final EmployeeDAO EMPLOYEE_DAO = new EmployeeDAO();
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

    //Working so far
    public static void deleteEmployee(Employee employee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

            //get all contracts of the employee which will be deleted
            session.beginTransaction();
            List<Contract> contracts = session.createQuery("" +
                            "select c " +
                            "from Contract c " +
                            "where c.employeeByEmployeeId = :employee",
                            Contract.class)
                    .setParameter("employee", employee)
                    .getResultList();

            session.getTransaction().commit();

            for (Contract contract : contracts) {
                //get employee with the least amount of contracts in the same company
                session.beginTransaction();
                Employee newEmployee = (Employee) session.createQuery("select e " +
                                "from Employee e " +
                                "where e.idEmployee != :employeeId " +
                                "and e.companyByCompanyId = :company " +
                                "order by (select count(c) from Contract c where c.employeeByEmployeeId = e) asc")
                        .setParameter("employeeId", employee.getIdEmployee())
                        .setParameter("company", employee.getCompanyByCompanyId())
                        .setMaxResults(1)
                        .uniqueResult();

                session.getTransaction().commit();

                //if there is no employee with the least amount of contracts in the same company, then delete the contract
                if (newEmployee != null) {
                    //update contract
                    session.beginTransaction();
                    contract.setEmployeeByEmployeeId(newEmployee);
                    session.merge(contract);
                    session.getTransaction().commit();

                } else {
                    //delete contract
                    session.beginTransaction();
                    session.remove(contract);
                    session.getTransaction().commit();
                }

            }
        }


        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(employee);
            session.getTransaction().commit();

        }
    }

    public static void deleteEmployeeById(long id) {
        Employee employee = getEmployeeById(id);
        deleteEmployee(employee);
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
        } catch (PersistenceException e){
            throw new IllegalArgumentException("No such company found!",e);
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

    public static boolean checkIfEmployeeWithSameNamesExists(Employee employee) {
        ensureNotNull(employee);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("SELECT e FROM Employee e WHERE e.firstName =:firstName AND e.lastName =:lastName", Employee.class);
            query.setParameter("firstName", employee.getFirstName());
            query.setParameter("lastName", employee.getLastName());
            session.beginTransaction();

            Employee result = query.getSingleResult();
//            session.evict(result);
            return result != null;

        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e){
            return true;
        }
    }

    public static List<Employee> getAllEmployeesWithSameNames(Employee employee){
        ensureNotNull(employee);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("SELECT e FROM Employee e WHERE e.firstName =:firstName AND e.lastName =:lastName", Employee.class);
            query.setParameter("firstName", employee.getFirstName());
            query.setParameter("lastName", employee.getLastName());
            session.beginTransaction();

            List<Employee> result = query.getResultList();
            if(result.isEmpty()){
                throw new IllegalArgumentException("No such employee found");
            }

//            session.evict(result);

            return result;

        } catch (NoResultException | IllegalArgumentException e) {
            throw new IllegalArgumentException("No such employee found");
        }
    }

    public Employee getEmployeeByNames(Employee employee){
        ensureNotNull(employee);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("SELECT e FROM Employee e WHERE e.firstName =:firstName AND e.lastName =:lastName", Employee.class);
            query.setParameter("firstName", employee.getFirstName());
            query.setParameter("lastName", employee.getLastName());
            session.beginTransaction();

            Employee result = query.getSingleResult();
            return result;

        } catch (NoResultException e) {
            throw new IllegalArgumentException("No such employee found");
        } catch (NonUniqueResultException e){
            throw new NonUniqueResultException("There are more employees with same name");
        }
    }

    public static Map<Employee, Collection<Building>> getBuildingsForEmployee() {
        Map<Employee, Collection<Building>> employeeBuildings  = new HashMap<>();

        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c.employeeByEmployeeId, c.buildingByBuildingId FROM Contract c";
            List<Object[]> results = session.createQuery(hql).getResultList();
            for (Object[] result : results) {
                Employee employee = (Employee) result[0];
                Building building = (Building) result[1];
                Collection<Building> buildings = employeeBuildings.getOrDefault(employee, new ArrayList<>());
                buildings.add(building);
                employeeBuildings.put(employee, buildings);
            }
        }

        return employeeBuildings ;
    }

    public static <T> void ensureNotNull(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
    }
}
