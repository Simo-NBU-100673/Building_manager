package menu;

import menu.string.container.MenuErrStringContainer;

import java.util.Map;
import java.util.Scanner;

public class ApartmentMenu extends AbstractMenu {
    private static final int menuNumber = 3;
    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }
    @Override
    protected void populateActionsMap(Map<Integer, Runnable> actions) {
        // Populate commands map
        actions.put(1, this::createNewApartment);
        actions.put(2, this::editApartment);
        actions.put(3, this::deleteApartment);
        actions.put(4, this::listAllResidentsInApartment);
        actions.put(5, this::addResident);
        actions.put(6, this::removeResident);
        actions.put(7, this::printFloorOfApartment);
        actions.put(8, this::listAllPetsInApartment);
        actions.put(9, this::printTaxesForApartment);
        actions.put(10, this::printOwnerOfApartment);
        actions.put(11, this::printLastPaidDate);
        actions.put(12, this::printAllPaymentsForApartment);
        actions.put(13, this::payTaxes);
    }



    //  |  1.  Create a new apartment                        |
    //  |  2.  Edit an apartment                             |
    //  |  3.  Delete apartment                              |
    //  |  4.  List all residents in the apartment           |
    //  |  5.  Add resident                                  |
    //  |  6.  Remove resident                               |
    //  |  7.  Print floor of the apartment                  |
    //  |  8.  List all pets living in the apartment         |
    //  |  9.  Print the taxes for the apartment             |
    //  |  10. Print the owner of the apartment              |
    //  |  11. Print last date when taxes were paid          |
    //  |  12. Print all payments from apartment             |
    //  |  13. Pay taxes                                     |

    @Override
    protected void handleInput(int num) {
        try {
            super.actions.get(num).run();
        } catch (Exception e) {
            printErrMessage(e.getMessage());
        }
    }

    private void printErrMessage(String message) {
        String errMessage = MenuErrStringContainer
                .getInstance()
                .convertToErrMessageBox(message);

        System.out.println(errMessage);
    }

    private void createNewApartment() {

    }

    private void editApartment() {

    }

    private void deleteApartment() {

    }

    private void listAllResidentsInApartment() {

    }

    private void addResident() {

    }

    private void removeResident() {

    }

    private void printFloorOfApartment() {

    }

    private void listAllPetsInApartment() {

    }

    private void printTaxesForApartment() {

    }

    private void printOwnerOfApartment() {

    }

    private void printLastPaidDate() {

    }

    private void printAllPaymentsForApartment() {

    }

    private void payTaxes() {

    }

}
