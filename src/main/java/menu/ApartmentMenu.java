package menu;

import dao.ApartmentDAO;
import dao.BuildingDAO;
import dao.OwnerDAO;
import entity.Apartment;
import entity.Building;
import entity.Owner;
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
        Apartment apartment = getApartmentFromUserInput();
        ApartmentDAO.saveApartment(apartment);
    }

    private Apartment getApartmentFromUserInput(){
        System.out.println("Type for Apartment: {floor} (space) {building_id} (space) {owner_id} and press (ENTER)");
        String[] tokens = userInput.nextLine().split(" ");
        int floor = Integer.parseInt(tokens[0]);

        long idBuilding = Long.parseLong(tokens[1]);
        Building building = getBuildingById(idBuilding);

        long idOwner = Long.parseLong(tokens[2]);
        Owner owner = getOwnerById(idOwner);

        Apartment apartment = new Apartment(floor, building, owner);

        return apartment;
    }

    private Building getBuildingById(long id){
        if (!BuildingDAO.exists(id)) {
            throw new IllegalArgumentException("Building with this ID does NOT exist!");
        }

        Building building = BuildingDAO.getBuildingById(id);

        return building;
    }

    private Owner getOwnerById(long id){
        if (!OwnerDAO.exists(id)) {
            throw new IllegalArgumentException("Owner with this ID does NOT exist!");
        }

        Owner owner = OwnerDAO.getOwnerById(id);

        return owner;
    }

    private void editApartment() {
        Apartment apartment = getApartmentByIdFromUser();
        Apartment apartmentUserInput = getApartmentFromUserInput();

        int floor = apartmentUserInput.getFloor();
        Building building = apartmentUserInput.getBuildingByBuildingId();
        Owner owner = apartmentUserInput.getOwnerByOwnerId();

        apartment.setFloor(floor);
        apartment.setBuildingByBuildingId(building);
        apartment.setOwnerByOwnerId(owner);

        ApartmentDAO.updateApartment(apartment);
    }

    private Apartment getApartmentByIdFromUser() {
        System.out.print("Enter the apartment's ID and press (ENTER): ");
        String buildingId = userInput.nextLine();
        long id = Integer.parseInt(buildingId);

        if (!ApartmentDAO.exists(id)) {
            throw new IllegalArgumentException("Apartment with this ID does NOT exist!");
        }

        Apartment apartment = ApartmentDAO.getApartmentById(id);

        return apartment;
    }

    private void deleteApartment() {
        Apartment apartment = getApartmentByIdFromUser();
        ApartmentDAO.deleteApartment(apartment);
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
