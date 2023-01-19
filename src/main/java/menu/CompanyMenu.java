package menu;

import java.util.HashMap;
import java.util.Map;

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

    //  |  1.  Create a new company                          |
    //  |  2.  Edit a company                                |
    //  |  3.  Delete a company                              |
    //  |  4.  List all companies                            |
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

    private void createNewCompany(){
        System.out.println("createNewCompany");
    }

    private void editCompany(){
        System.out.println("editCompany");
    }

    private void deleteCompany(){
        System.out.println("deleteCompany");
    }

    private void listAllCompanies(){
        System.out.println("listAllCompanies");
    }

    private void listAllContractsOfCompany(){
        System.out.println("listAllContractsOfCompany");
    }

    private void listAllEmployeesOfCompany(){
        System.out.println("listAllEmployeesOfCompany");
    }

    private void listAllBuildingsOfCompany(){
        System.out.println("listAllBuildingsOfCompany");
    }

    private void listCountOfAllContractsOfCompany(){
        System.out.println("listCountOfAllContractsOfCompany");
    }

    private void listCountOfAllEmployeesOfCompany(){
        System.out.println("listCountOfAllEmployeesOfCompany");
    }

    private void listCountOfAllBuildingsOfCompany(){
        System.out.println("listCountOfAllBuildingsOfCompany");
    }

    private void printNameOfCompany(){
        System.out.println("printNameOfCompany");
    }

    private void hireEmployee(){
        System.out.println("hireEmployee");
    }

    private void fireEmployee(){
        System.out.println("fireEmployee");
    }

}
