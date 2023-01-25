package menu;

import menu.string.container.MenuErrStringContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class BuildingMenu extends AbstractMenu {
    private static final int menuNumber = 2;

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }

    @Override
    protected void populateActionsMap(Map<Integer, Runnable> actions) {
        // Populate commands map
        actions.put(1, this::createNewBuilding);
        actions.put(2, this::editBuildingName);
        actions.put(3, this::editBuildingAddress);
        actions.put(4, this::deleteBuilding);
        actions.put(5, this::listAllApartmentsInBuilding);
        actions.put(6, this::listApartmentsOfBuildingByFloor);
        actions.put(7, this::listAllApartmentsByBuildingsByFloor);
        actions.put(8, this::printTotalTaxOfBuilding);
        actions.put(9, this::printTotalTaxOfBuildingsOfCompany);
        actions.put(10, this::printIdAndNamesOfManagingEmployeeOfBuilding);
        actions.put(11, this::printAddressOfBuilding);
        actions.put(12, this::printNameOfBuilding);
        actions.put(13, this::assignNewTaxForBuilding);
        actions.put(14, this::removeTaxForBuilding);
        actions.put(15, this::printAllTaxesOfBuilding);
        actions.put(16, this::editTaxForBuilding);
    }

    //  |  1.  Create a new building                         |
    //  |  2.  Edit a building name                          |
    //  |  3.  Edit a building address                       |
    //  |  4.  Delete a building                             |
    //  |  5.  List all apartments in the building           |
    //  |  6.  List all apartments by floor in building      |
    //  |  7.  List all apartments by building and floor     |
    //  |  8.  Print all fees combined for whole building    |
    //  |  9.  Print all fees per every building by every    |
    //  |      company                                       |
    //  |  10.  Print names and id of employee which manages |
    //  |      building by ****                              |
    //  |  11. Print address of building                     |
    //  |  12. Print name of building                        |
    //  |  13. Assign new tax                                |
    //  |  14. Remove a tax from building                    |
    //  |  15. Print all taxes of building                   |
    //  |  16. Edit a tax of building                        |

    @Override
    protected void handleInput(int num) {
        try {
            super.actions.get(num).run();
            //FIXME for now we don't know which exact exceptions will be passed here
        } catch (NullPointerException | NoSuchElementException | IllegalArgumentException e) {
            printErrMessage(e.getMessage());
        }
    }

    private void createNewBuilding() {
        System.out.println("Create new building");
    }

    private void editBuildingName() {
        System.out.println("Edit building name");
    }

    private void editBuildingAddress() {
        System.out.println("Edit building address");
    }

    private void deleteBuilding() {
        System.out.println("Delete building");
    }

    private void listAllApartmentsInBuilding() {
        System.out.println("List all apartments in building");
    }

    private void listApartmentsOfBuildingByFloor() {
        System.out.println("List apartments of building by floor");
    }

    private void listAllApartmentsByBuildingsByFloor() {
        System.out.println("List all apartments by buildings by floor");
    }

    private void printTotalTaxOfBuilding() {
        System.out.println("Print total tax of building");
    }

    private void printTotalTaxOfBuildingsOfCompany() {
        System.out.println("Print total tax of buildings of company");
    }

    private void printIdAndNamesOfManagingEmployeeOfBuilding() {
        System.out.println("Print id and names of managing employee of building");
    }

    private void printAddressOfBuilding() {
        System.out.println("Print address of building");
    }

    private void printNameOfBuilding() {
        System.out.println("Print name of building");
    }

    private void assignNewTaxForBuilding() {
        System.out.println("Assign new tax for building");
    }

    private void removeTaxForBuilding() {
        System.out.println("Remove tax for building");
    }

    private void printAllTaxesOfBuilding() {
        System.out.println("Print all taxes of building");
    }

    private void editTaxForBuilding() {
        System.out.println("Edit tax for building");
    }

    private void printErrMessage(String message) {
        String errMessage = MenuErrStringContainer
                .getInstance()
                .convertToErrMessageBox(message);

        System.out.println(errMessage);
    }


}
