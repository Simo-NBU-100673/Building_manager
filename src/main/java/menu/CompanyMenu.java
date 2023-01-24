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
        super.actions.get(num).run();
    }

    private void createNewCompany() {
        try {
            System.out.print("Enter the company's NAME and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);
            CompanyDAO.saveCompany(company);

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private String parseName(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Invalid name, enter a non-empty value");
        }
        return input;
    }

    private void editCompany() {
        try {
            System.out.print("Enter the company's NAME that you want to edit and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                System.out.println("Company exists");
                System.out.println("Enter the NEW name of the company and press (ENTER): ");
                String newName = userInput.nextLine();

                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), newName);

                CompanyDAO.updateCompany(tmpCompany);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void deleteCompany() {
        try {
            System.out.print("Enter the company's NAME that you want to DELETE and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                System.out.println("Company successfully deleted!");

                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                CompanyDAO.deleteCompany(tmpCompany);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void listAllCompanies() {
        try {
            List<Company> companies = CompanyDAO.getAllCompanies();

            System.out.println("\nList of all companies: ");
            companies.forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void listAllContractsOfCompany() {
        try {
            System.out.print("Enter the company's NAME for which you want\nto see the contracts and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                List<Contract> contractsByCompany = ContractDAO.getContractsByCompany(tmpCompany);
                contractsByCompany.forEach(System.out::println);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void listAllEmployeesOfCompany() {
        try {
            System.out.print("Enter the company's NAME for which you want\nto see the hired employees and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                List<Employee> employeesByCompany = EmployeeDAO.getEmployeesByCompany(tmpCompany);
                System.out.println("\nList of all employees: ");
                employeesByCompany.forEach(System.out::println);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void listAllBuildingsOfCompany() {
        try {
            System.out.print("Enter the company's NAME for which you want\nto see the hired employees and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                List<Building> buildingsByCompany = ContractDAO.getBuildingsByCompany(tmpCompany);
                System.out.println("\nList of all buildings: ");
                buildingsByCompany.forEach(System.out::println);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void listCountOfAllContractsOfCompany() {
        try {
            System.out.print("Enter the company's NAME for which you want\nto see the contracts COUNT and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                long countOfContractsOfCompany = ContractDAO.getCountOfContractsOfCompany(tmpCompany);
                System.out.println("\nCount of contracts of Company (" + name + "): "+ countOfContractsOfCompany);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void listCountOfAllEmployeesOfCompany() {
        try {
            System.out.print("Enter the company's NAME for which you want\nto see the hired employees COUNT and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                long countOfEmployeesOfCompany = EmployeeDAO.getCountOfEmployeesOfCompany(tmpCompany);
                System.out.println("\nCount of employees of Company (" + name + "): "+ countOfEmployeesOfCompany);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void listCountOfAllBuildingsOfCompany() {
        try {
            System.out.print("Enter the company's NAME for which you want\nto see the COUNT of theirs buildings and press (ENTER): ");

            String input = userInput.nextLine();
            String name = parseName(input);

            Company company = new Company(name);

            if (CompanyDAO.exists(company)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                long countOfBuildingsOfCompany = BuildingDAO.getCountOfBuildingsOfCompany(tmpCompany);
                System.out.println("\nCount of buildings of Company (" + name + "): "+ countOfBuildingsOfCompany);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e);
        }
    }

    private void hireEmployee() {
        try {
            System.out.print("Enter the company's {NAME} (space) {EMPLOYEE_ID} and then press (ENTER): ");

            String[] input = userInput.nextLine().split(" ");
            String name = parseName(input[0]);
            long idEmployee = parseId(input[1]);

            Company company = new Company(name);
            Employee employee = new Employee(idEmployee);
            if (CompanyDAO.exists(company) && EmployeeDAO.exists(employee)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                Employee employeeDB = EmployeeDAO.getEmployeeById(idEmployee);
                Employee tmpEmployee = new Employee(employeeDB);

                EmployeeDAO.hireEmployee(tmpCompany, tmpEmployee);
                System.out.println("\nEmployee (" + tmpEmployee.getFirstName() +" " + tmpEmployee.getLastName()+ ") hired successfully");

            } else {
                throw new IllegalArgumentException("Company or Employee does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException | IndexOutOfBoundsException e) {
            printErrMessage(e);
        }
    }

    private long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be a number");
        }
    }

    private void fireEmployee() {
        try {
            System.out.print("Enter the company's {NAME} (space) {EMPLOYEE_ID} and then press (ENTER): ");

            String[] input = userInput.nextLine().split(" ");
            String name = parseName(input[0]);
            long idEmployee = parseId(input[1]);

            Company company = new Company(name);
            Employee employee = new Employee(idEmployee);
            if (CompanyDAO.exists(company) && EmployeeDAO.exists(employee)) {
                Company companyDB = CompanyDAO.getCompanyByName(name);
                Company tmpCompany = new Company(companyDB.getIdCompany(), name);

                Employee employeeDB = EmployeeDAO.getEmployeeById(idEmployee);
                Employee tmpEmployee = new Employee(employeeDB);

                EmployeeDAO.fireEmployee(tmpCompany, tmpEmployee);
                System.out.println("\nEmployee (" + tmpEmployee.getFirstName() +" " + tmpEmployee.getLastName()+ ") fired successfully");

            } else {
                throw new IllegalArgumentException("Company or Employee does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException | IndexOutOfBoundsException e) {
            printErrMessage(e);
        }
    }

    private void printErrMessage(RuntimeException e){
        String errMessage = MenuErrStringContainer
                .getInstance()
                .convertToErrMessageBox(e.getMessage());

        System.out.println(errMessage);
    }

}
