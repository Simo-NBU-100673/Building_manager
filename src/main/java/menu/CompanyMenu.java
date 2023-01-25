package menu;

import dao.BuildingDAO;
import dao.CompanyDAO;
import dao.ContractDAO;
import dao.EmployeeDAO;
import entity.Building;
import entity.Company;
import entity.Contract;
import entity.Employee;
import menu.string.container.MenuErrStringContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CompanyMenu extends AbstractMenu {
    private static final int menuNumber = 1;

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }

    @Override
    protected Map<Integer, Runnable> populateActionsMap() {
        Map<Integer, Runnable> actions = new HashMap<>();

        // Populate commands map
        actions.put(1, this::createNewCompany);
        actions.put(2, this::editCompany);
        actions.put(3, this::deleteCompany);
        actions.put(4, this::listAllCompanies);
        actions.put(5, this::listAllContractsOfCompany);
        actions.put(6, this::listAllEmployeesOfCompany);
        actions.put(7, this::listAllBuildingsOfCompany);
        actions.put(8, this::listCountOfAllContractsOfCompany);
        actions.put(9, this::listCountOfAllEmployeesOfCompany);
        actions.put(10, this::listCountOfAllBuildingsOfCompany);
        actions.put(11, this::hireEmployee);
        actions.put(12, this::fireEmployee);

        return actions;
    }

    @Override
    protected void handleInput(int num) {
        try {
            super.actions.get(num).run();

        } catch (NullPointerException | NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e.getMessage());
        }
    }

    private void createNewCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyDoesNotExist(company);
        CompanyDAO.saveCompany(company);
    }

    private void editCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME that you want to edit and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        System.out.print("Enter the NEW name of the company and press (ENTER): ");
        String newName = userInput.nextLine();

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());
        companyDB.setName(newName);

        CompanyDAO.updateCompany(companyDB);
    }

    private void deleteCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME that you want to DELETE and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());
        CompanyDAO.deleteCompany(companyDB);
        System.out.println("Company successfully deleted!");
    }

    private void listAllCompanies() throws IllegalArgumentException {
        List<Company> companies = CompanyDAO.getAllCompanies();

        System.out.println("\nList of all companies: ");
        companies.forEach(System.out::println);
    }

    private void listAllContractsOfCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME for which you want\nto see the CONTRACTS and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());

        List<Contract> contractsByCompany = ContractDAO.getContractsByCompany(companyDB);
        contractsByCompany.forEach(System.out::println);
    }

    private void listAllEmployeesOfCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME for which you want\nto see the hired EMPLOYEES and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());

        List<Employee> employeesByCompany = EmployeeDAO.getEmployeesByCompany(companyDB);
        System.out.println("\nList of all employees: ");
        employeesByCompany.forEach(System.out::println);
    }

    private void listAllBuildingsOfCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME for which you want\nto see managed BUILDINGS and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());

        List<Building> buildingsByCompany = ContractDAO.getBuildingsByCompany(companyDB);
        System.out.println("\nList of all buildings: ");
        buildingsByCompany.forEach(System.out::println);
    }

    private void listCountOfAllContractsOfCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME for which you want\nto see the contracts COUNT and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());

        long countOfContractsOfCompany = ContractDAO.getCountOfContractsOfCompany(companyDB);
        System.out.println("\nCount of contracts of Company (" + company.getName() + "): " + countOfContractsOfCompany);
    }

    private void listCountOfAllEmployeesOfCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME for which you want\nto see the hired employees COUNT and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());

        long countOfEmployeesOfCompany = EmployeeDAO.getCountOfEmployeesOfCompany(companyDB);
        System.out.println("\nCount of employees of Company (" + company.getName() + "): " + countOfEmployeesOfCompany);
    }

    private void listCountOfAllBuildingsOfCompany() throws IllegalArgumentException, NoSuchElementException {
        System.out.print("Enter the company's NAME for which you want\nto see the COUNT of theirs buildings and press (ENTER): ");
        Company company = getCompanyFromUserInput();
        ensureCompanyExists(company);

        Company companyDB = CompanyDAO.getCompanyByName(company.getName());

        long countOfBuildingsOfCompany = BuildingDAO.getCountOfBuildingsOfCompany(companyDB);
        System.out.println("\nCount of buildings of Company (" + company.getName() + "): " + countOfBuildingsOfCompany);
    }

    private void hireEmployee() {
        System.out.print("Enter the company's {NAME} (space) {EMPLOYEE_ID} and then press (ENTER): ");

        String[] input = userInput.nextLine().split(" ");
        String name = getCompanyNameFromUserInput(input);
        long idEmployee = getEmployeeIdFromUserInput(input);

        Company company = new Company(name);
        ensureCompanyExists(company);

        Employee employee = new Employee(idEmployee);
        ensureEmployeeExists(employee);

        Company companyDB = CompanyDAO.getCompanyByName(name);
        Employee employeeDB = EmployeeDAO.getEmployeeById(idEmployee);

        EmployeeDAO.hireEmployee(companyDB, employeeDB);
        System.out.println("\nEmployee (" + employeeDB.getFirstName() + " " + employeeDB.getLastName() + ") hired successfully");
    }

    private void fireEmployee() {
        System.out.print("Enter the company's {NAME} (space) {EMPLOYEE_ID} and then press (ENTER): ");

        String[] input = userInput.nextLine().split(" ");
        String name = getCompanyNameFromUserInput(input);
        long idEmployee = getEmployeeIdFromUserInput(input);

        Company company = new Company(name);
        ensureCompanyExists(company);

        Employee employee = new Employee(idEmployee);
        ensureEmployeeExists(employee);

        Company companyDB = CompanyDAO.getCompanyByName(name);
        Employee employeeDB = EmployeeDAO.getEmployeeById(idEmployee);

        EmployeeDAO.fireEmployee(companyDB, employeeDB);
        System.out.println("\nEmployee (" + employeeDB.getFirstName() + " " + employeeDB.getLastName() + ") fired successfully");
    }

    private String getCompanyNameFromUserInput(String[] input) throws IllegalArgumentException {
        String name;

        try {
            name = parseName(input[0]);

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No name of company passed", e);
        }

        return name;
    }

    private String parseName(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Invalid name, enter a non-empty value");
        }
        return input;
    }

    private long getEmployeeIdFromUserInput(String[] input) throws IllegalArgumentException {
        long idEmployee;

        try {
            idEmployee = parseId(input[1]);

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No id of employee passed", e);
        }

        return idEmployee;
    }

    private long parseId(String id) {
        try {
            return Long.parseLong(id);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a number");
        }
    }

    private void printErrMessage(String message) {
        String errMessage = MenuErrStringContainer
                .getInstance()
                .convertToErrMessageBox(message);

        System.out.println(errMessage);
    }

    private Company getCompanyFromUserInput() throws NoSuchElementException, IllegalArgumentException {
        String input = userInput.nextLine();
        String name = parseName(input);
        Company company = new Company(name);

        return company;
    }

    private void ensureCompanyExists(Company company) throws IllegalArgumentException {
        if (!CompanyDAO.exists(company)) {
            throw new IllegalArgumentException("Company does not exist");
        }
    }

    private void ensureCompanyDoesNotExist(Company company) throws IllegalArgumentException {
        if (CompanyDAO.exists(company)) {
            throw new IllegalArgumentException("Company already exists");
        }
    }

    private void ensureEmployeeExists(Employee employee) throws IllegalArgumentException {
        if (!EmployeeDAO.exists(employee)) {
            throw new IllegalArgumentException("Employee does not exist");
        }
    }


}
