package menu;

import java.util.*;

import dao.EmployeeDAO;
import entity.Building;
import entity.Employee;
import jakarta.persistence.NonUniqueResultException;
import menu.string.container.MenuErrStringContainer;
import org.hibernate.query.Query;

public class EmployeeMenu extends AbstractMenu {
    private static final int menuNumber = 4;

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }

    @Override
    protected void populateActionsMap(Map<Integer, Runnable> actions) {
        // Populate commands map
        actions.put(1, this::createNewEmployee);
        actions.put(2, this::editEmployee);
        actions.put(3, this::deleteEmployee);
        actions.put(4, this::listAllEmployeesManagingBuildings);
        actions.put(5, this::listAllEmployees);
    }

    @Override
    protected void handleInput(int num) {
        try {
            super.actions.get(num).run();

        } catch (NullPointerException | NoSuchElementException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            printErrMessage(e.getMessage());
        }
    }

    private void printErrMessage(String message) {
        String errMessage = MenuErrStringContainer
                .getInstance()
                .convertToErrMessageBox(message);

        System.out.println(errMessage);
    }

    //  |====================================================|
    //  |            [Operations with Employee]              |
    //  |====================================================|
    //  |  1.  Create a employee                             |
    //  |  2.  Edit an employee                              |
    //  |  3.  Delete employee                               |
    //  |  4.  List all employees with managed buildings     |
    //  |  5.  Print employee                                |
    //  |====================================================|
    //  |         (BACK)        [4/5]         (NEXT)         |
    //  |====================================================|

    private void createNewEmployee() {
        //!To make methods for ArrayIndexOutOfBoundsException and NoSuchElementException
        System.out.println("Enter employee's {first_name} (SPACE) {last_name} and press (ENTER)");

        String[] names = userInput.nextLine().split(" ");//NoSuchElementException
        String firstName = parseName(names[0]);//IndexOutOfBoundsException
        String lastName = parseName(names[1]);//IndexOutOfBoundsException

        Employee employee = new Employee(firstName, lastName);

        if(EmployeeDAO.checkIfEmployeeWithSameNamesExists(employee)) {
            System.out.println("Employee with same names already exists");
            System.out.println("Do you want to create a new employee with same names? (Y/N)");
            String answer = userInput.nextLine();

            if(checkAnswer(answer)) {
                EmployeeDAO.saveEmployee(employee);
            }

        }else {
            EmployeeDAO.saveEmployee(employee);
            System.out.println("Employee created successfully");
        }
    }

    private boolean checkAnswer(String answer) {
        //the answer must be 1 Yes y ignore case to be true
        return answer.equalsIgnoreCase("Y") ||
                answer.equalsIgnoreCase("Yes") ||
                answer.equalsIgnoreCase("1");
    }

    private void editEmployee() {
        //!To make methods for ArrayIndexOutOfBoundsException and NoSuchElementException
        Employee employee = getEmployeeFromUserInput();

        //check if there is not any employee with these name if that is the case throw new IllegalArgumentException
        if(!EmployeeDAO.checkIfEmployeeWithSameNamesExists(employee)) {
            throw new IllegalArgumentException("Employee with this names does not exist");
        }

        //Now check if there is more than one employee with these names
        List<Employee> employees = EmployeeDAO.getAllEmployeesWithSameNames(employee);

        if(employees.size() == 1) {
            Employee uniqueEmployee = employees.get(0);
            uniqueEmployee.setFirstName(employee.getFirstName());
            uniqueEmployee.setLastName(employee.getLastName());
            updateEmployee(uniqueEmployee);
            return;
        }

        //there is more than one employee with these names
        System.out.println("There is more than one employee with these names");
        System.out.println("Enter employee's id to edit it");
        int id = getEmployeeId();

        for (Employee emp : employees) {
            if(emp.getIdEmployee() == id) {
                employee = emp;
                break;
            }
        }

        //check if there is an employee with this id
        if(!EmployeeDAO.exists(employee)){
            throw new IllegalArgumentException("Employee with this id does not exist");
        }

        //update employee
        System.out.println("Employee found successfully");
        updateEmployee(employee);

    }

    private void updateEmployee(Employee employee) {
        System.out.println("To change this current employee:");
        Employee emp = getEmployeeFromUserInput();
        employee.setFirstName(emp.getFirstName());
        employee.setLastName(emp.getLastName());
        EmployeeDAO.updateEmployee(employee);
    }

    private Employee getEmployeeFromUserInput() {
        String[] names = getEmployeeNames();
        Employee employee = new Employee(names[0], names[1]);

        return employee;
    }

    private String[] getEmployeeNames() {
        System.out.println("Enter employee's {first_name} (SPACE) {last_name} and press (ENTER)");

        String[] names = userInput.nextLine().split(" ");//NoSuchElementException
        String firstName = parseName(names[0]);//IndexOutOfBoundsException
        String lastName = parseName(names[1]);//IndexOutOfBoundsException

        return new String[]{firstName, lastName};
    }

    private int getEmployeeId() {
        String id = userInput.nextLine();
        //check if id is a number
        if(!isNumber(id)) {
            throw new IllegalArgumentException("Invalid id, enter a number");
        }

        return Integer.parseInt(id);
    }

    private boolean isNumber(String id) {
        try {
            Integer.parseInt(id);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }

    private void deleteEmployee() {
        Employee employee = getEmployeeFromUserInput();

        //check if there is not any employee with these name if that is the case throw new IllegalArgumentException
        if(!EmployeeDAO.checkIfEmployeeWithSameNamesExists(employee)) {
            throw new IllegalArgumentException("Employee with this names does not exist");
        }

        //Now check if there is more than one employee with these names
        List<Employee> employees = EmployeeDAO.getAllEmployeesWithSameNames(employee);

        if(employees.size() == 1) {
            employee = employees.get(0);
            EmployeeDAO.deleteEmployee(employee);
            return;
        }

        //there is more than one employee with these names
        System.out.println("There is more than one employee with these names");
        System.out.println("Enter employee's id to edit it");
        int id = getEmployeeId();

        for (Employee emp : employees) {
            if(emp.getIdEmployee() == id) {
                employee = emp;
                break;
            }
        }

        //check if there is an employee with this id
        if(!EmployeeDAO.exists(employee)){
            throw new IllegalArgumentException("Employee with this id does not exist");
        }

        //update employee
        System.out.println("Employee found successfully");
        EmployeeDAO.deleteEmployee(employee);
    }

    private void listAllEmployeesManagingBuildings() {
        EmployeeDAO.getBuildingsForEmployee().forEach((employee, buildings) -> {
            System.out.println("\n"+employee);
            buildings.forEach(building -> System.out.println("\t" + building));
        });
    }

    private void listAllEmployees() {
        //create a list of employees with EmployeeDAO.getAllEmployees() and print it
        List<Employee> employees = (List<Employee>) EmployeeDAO.getAllEmployees();

        //check if the list is empty
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("There are no employees");
        }

        //print the list
        System.out.println("All employees:");
        employees.forEach(System.out::println);
    }

    private void ensureEmployeeNotExists(Employee employee) throws IllegalArgumentException {
        //!id must be exactly the same
        if (!EmployeeDAO.exists(employee)) {
            throw new IllegalArgumentException("Employee does not exist");
        }
    }

    private String parseName(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Invalid name, enter a non-empty value");
        }
        return input.trim();
    }

}
