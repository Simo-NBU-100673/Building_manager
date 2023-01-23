package menu;

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
        actions.put(11, this::printNameOfCompany);
        actions.put(12, this::hireEmployee);
        actions.put(13, this::fireEmployee);

        return actions;
    }

    //  |  1.  Create a new company                          | DONE
    //  |  2.  Edit a company                                | DONE
    //  |  3.  Delete a company                              | DONE
    //  |  4.  List all companies                            | DONE
    //  |  5.  List all contracts of a company               |
    //  |  6.  List all employees of a company               |
    //  |  7.  List all buildings of a company               |
    //  |  8.  List the count of all contracts of a company  |
    //  |  9.  List the count of all employees of a company  |
    //  |  10. List the count of all buildings of a company  |
    //  |  11. Print name of company                         |
    //  |  12. Hire employee                                 |
    //  |  13. Fire employee                                 |

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
            String errMessage = MenuErrStringContainer
                    .getInstance()
                    .convertToErrMessageBox(e.getMessage());

            System.out.println(errMessage);
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
            String errMessage = MenuErrStringContainer
                    .getInstance()
                    .convertToErrMessageBox(e.getMessage());

            System.out.println(errMessage);
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
            String errMessage = MenuErrStringContainer
                    .getInstance()
                    .convertToErrMessageBox(e.getMessage());

            System.out.println(errMessage);
        }
    }

    private void listAllCompanies() {
        try {
            List<Company> companies = CompanyDAO.getAllCompanies();

            System.out.println("\nList of all companies: ");
            companies.forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            String errMessage = MenuErrStringContainer
                    .getInstance()
                    .convertToErrMessageBox(e.getMessage());

            System.out.println(errMessage);
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

                List<Contract>contracts = ContractDAO.getContractsByCompany(tmpCompany);
                contracts.forEach(System.out::println);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            String errMessage = MenuErrStringContainer
                    .getInstance()
                    .convertToErrMessageBox(e.getMessage());

            System.out.println(errMessage);
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

                List<Employee>employees = EmployeeDAO.getEmployeesByCompany(tmpCompany);
                System.out.println("\nList of all employees: ");
                employees.forEach(System.out::println);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            String errMessage = MenuErrStringContainer
                    .getInstance()
                    .convertToErrMessageBox(e.getMessage());

            System.out.println(errMessage);
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

                List<Building>employees = ContractDAO.getBuildingsByCompany(tmpCompany);
                System.out.println("\nList of all buildings: ");
                employees.forEach(System.out::println);

            } else {
                throw new IllegalArgumentException("Company does not exist");
            }

        } catch (NoSuchElementException | IllegalArgumentException e) {
            String errMessage = MenuErrStringContainer
                    .getInstance()
                    .convertToErrMessageBox(e.getMessage());

            System.out.println(errMessage);
        }
    }

    private void listCountOfAllContractsOfCompany() {
        System.out.println("listCountOfAllContractsOfCompany");
    }

    private void listCountOfAllEmployeesOfCompany() {
        System.out.println("listCountOfAllEmployeesOfCompany");
    }

    private void listCountOfAllBuildingsOfCompany() {
        System.out.println("listCountOfAllBuildingsOfCompany");
    }

    private void printNameOfCompany() {
        System.out.println("printNameOfCompany");
    }

    private void hireEmployee() {
        System.out.println("hireEmployee");
    }

    private void fireEmployee() {
        System.out.println("fireEmployee");
    }

}
