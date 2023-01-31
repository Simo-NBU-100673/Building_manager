package menu;

import dao.BuildingDAO;
import dao.TaxDAO;
import entity.Apartment;
import entity.Building;
import entity.Tax;
import menu.string.container.MenuErrStringContainer;
import org.hibernate.Session;
import session.SessionFactoryUtil;
import tax.type.TaxType;

import java.util.HashMap;
import java.util.List;
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
        actions.put(7, this::listAllApartmentsByBuildings);
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

    //  |  1.  Create a new building                         | DONE
    //  |  2.  Edit a building name                          | DONE
    //  |  3.  Edit a building address                       | DONE
    //  |  4.  Delete a building                             | DONE
    //  |  5.  List all apartments in the building           | DONE
    //  |  6.  List all apartments by floor in building      | DONE
    //  |  7.  List all apartments by buildings              | DONE
    //  |  8.  Print all fees combined for whole building    | DONE
    //  |  9.  Print all fees per every building by every    | NO
    //  |      company                                       | -
    //  |  10.  Print names and id of employee which manages | NO
    //  |      building by ****                              | -
    //  |  11. Print address of building                     | DONE
    //  |  12. Print name of building                        | DONE
    //  |  13. Assign new tax                                | DONE
    //  |  14. Remove a tax from building                    | DONE
    //  |  15. Print all taxes of building                   | DONE
    //  |  16. Edit a tax of building                        | DONE

    @Override
    protected void handleInput(int num) {
        try {
            super.actions.get(num).run();
        } catch (Exception e) {
            printErrMessage(e.getMessage());
        }
    }

    private void createNewBuilding() {
        Building building = getBuildingFromUserInput();
        BuildingDAO.saveBuilding(building);
    }

    private Building getBuildingFromUserInput() throws NoSuchElementException, IllegalArgumentException, ArrayIndexOutOfBoundsException {
        System.out.print("Enter the building's NAME (space) building's ADDRESS and press (ENTER): ");
        String[] buildingInfo = userInput.nextLine().split(" ");
        Building building = new Building(sanitizeString(buildingInfo[1]), sanitizeString(buildingInfo[0]));

        if (BuildingDAO.addressExists(building)) {
            throw new IllegalArgumentException("Building with this address already exists!");
        }

        return building;
    }

    private String sanitizeString(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Invalid name, enter a non-empty value");
        }

        return input.trim();
    }

    private void editBuildingName() {
        Building building = getBuildingByIdFromUser();
        System.out.println("Type new name for the building");
        String newName = userInput.nextLine();
        building.setName(newName);
        BuildingDAO.updateBuilding(building);
    }

    private Building getBuildingByIdFromUser() {
        System.out.print("Enter the building's ID and press (ENTER): ");
        String buildingId = userInput.nextLine();
        long id = Integer.parseInt(buildingId);

        if (!BuildingDAO.exists(id)) {
            throw new IllegalArgumentException("Building with this ID does NOT exist!");
        }

        Building building = BuildingDAO.getBuildingById(id);

        return building;
    }

    private void editBuildingAddress() {
        Building building = getBuildingByIdFromUser();
        System.out.println("Type new address for the building");
        String newAddress = userInput.nextLine();
        building.setAddress(newAddress);
        BuildingDAO.updateBuilding(building);
    }

    //Maybe here must use cascade when removing
    private void deleteBuilding() {
        Building building = getBuildingByIdFromUser();
        Building buildingFromDB = BuildingDAO.getBuildingById(building.getIdBuilding());
        BuildingDAO.deleteBuilding(buildingFromDB);
    }

    private void listAllApartmentsInBuilding() {
        Building building = getBuildingByIdFromUser();
        System.out.println("\nAll apartments:");
        BuildingDAO.getApartmentsInBuilding(building).forEach(System.out::println);
    }

    private void listApartmentsOfBuildingByFloor() {
        Building building = getBuildingByIdFromUser();

        System.out.println("Type the desired floor:");
        int floor = Integer.parseInt(userInput.nextLine());

        List<Apartment> apartmentsOfFloor = BuildingDAO.getApartmentsInBuildingByFloor(building, floor).stream().toList();
        System.out.println("\nAll apartments on floor [" + floor + "]:");
        apartmentsOfFloor.forEach(System.out::println);
    }

    private void listAllApartmentsByBuildings() {
        BuildingDAO.getAllApartmentsByBuildings().forEach((building, apartments) -> {
            System.out.println("\n[" + building.getIdBuilding() + "] " + building.getName() + " has apartments:");
            apartments.forEach(apartment -> System.out.println("\t" + apartment));
        });
    }

    private void printTotalTaxOfBuilding() {
        Building building = getBuildingByIdFromUser();

        long countOfPeople = BuildingDAO.getCountOfPeopleInBuilding(building);
        Tax taxPeople = BuildingDAO.getTaxOfBuilding(building, TaxType.PEOPLE);

        long countOfPets = BuildingDAO.getCountOfPetsInBuilding(building);
        Tax taxPets = BuildingDAO.getTaxOfBuilding(building, TaxType.PET);

        System.out.println("Count of pets: " + countOfPets + " * fee: " + taxPets.getFee() + " = " + (countOfPets * taxPets.getFee()));
        System.out.println("Count of people: " + countOfPeople + " * fee: " + taxPeople.getFee() + " = " + (countOfPeople * taxPeople.getFee()));
        System.out.println("\tSUM: " + ((countOfPets * taxPets.getFee()) + (countOfPeople * taxPeople.getFee())));
    }

    private void printTotalTaxOfBuildingsOfCompany() {
        System.out.println("Print total tax of buildings of company");
    }

    private void printIdAndNamesOfManagingEmployeeOfBuilding() {
        System.out.println("Print id and names of managing employee of building");
    }

    private void printAddressOfBuilding() {
        Building building = getBuildingByIdFromUser();
        Building buildingFromDB = BuildingDAO.getBuildingById(building.getIdBuilding());
        String message = String.format("Address of building with id[%d] %s", buildingFromDB.getIdBuilding(), buildingFromDB.getAddress());
        System.out.println(message);
    }

    private void printNameOfBuilding() {
        Building building = getBuildingByIdFromUser();
        Building buildingFromDB = BuildingDAO.getBuildingById(building.getIdBuilding());
        String message = String.format("Name of building with id[%d] %s", buildingFromDB.getIdBuilding(), buildingFromDB.getName());
        System.out.println(message);
    }

    private void assignNewTaxForBuilding() {
        Building building = getBuildingByIdFromUser();
        TaxType taxType = getTaxTypeFromUser();

        System.out.println("Type the fee for the tax (INT): ");
        int taxFee = Integer.parseInt(userInput.nextLine().trim());
        checkIfNegative(taxFee);

        Tax tax = new Tax(building, taxType, taxFee);

        if (TaxDAO.exists(tax)) {
            throw new IllegalArgumentException("Tax already set for this building");
        }

        TaxDAO.saveTax(tax);
        System.out.println("Successfully assigned NEW tax");
    }

    private TaxType getTaxTypeFromUser() {
        System.out.println("Type the type number of the tax");
        System.out.println("1 - People");
        System.out.println("2 - Pets");

        int taxNumber = Integer.parseInt(userInput.nextLine().trim());

        if (taxNumber == 1) {
            return TaxType.PEOPLE;
        } else if (taxNumber == 2) {
            return TaxType.PET;
        }

        throw new IllegalArgumentException("No valid input");
    }

    public void checkIfNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative value passed");
        }
    }

    private void removeTaxForBuilding() {
        Building building = getBuildingByIdFromUser();
        TaxType taxType = getTaxTypeFromUser();

        Tax tax = new Tax(building, taxType);

        if (!TaxDAO.exists(tax)) {
            throw new IllegalArgumentException("Tax NOT set for this building");
        }

        TaxDAO.deleteTax(tax);
        System.out.println("Successfully deleted tax");
    }

    private void printAllTaxesOfBuilding() {
        Building building = getBuildingByIdFromUser();
        Building buildingFromDB = BuildingDAO.getBuildingById(building.getIdBuilding());
        String message = String.format("Taxes of building %s with id[%d]:", buildingFromDB.getName(), buildingFromDB.getIdBuilding());
        System.out.println(message);
        List<Tax> taxes = BuildingDAO.getAllTaxes(building).stream().toList();
        System.out.println("\t" + "-".repeat(35));
        System.out.println("\t|  Building  |  TaxType  |  Fee  |");
        System.out.println("\t" + "-".repeat(35));
        taxes.forEach(tax -> {
            System.out.println("\t" + tax.getBuildingByBuildingId().getName() + " | " + tax.getType() + " | " + tax.getFee());
        });
    }

    private void editTaxForBuilding() {
        Building building = getBuildingByIdFromUser();
        TaxType taxType = getTaxTypeFromUser();

        Tax tax = new Tax(building, taxType);

        if (!TaxDAO.exists(tax)) {
            throw new IllegalArgumentException("Tax NOT set for this building");
        }

        System.out.println("Type the fee for NEW tax (INT): ");
        int taxFee = Integer.parseInt(userInput.nextLine().trim());
        checkIfNegative(taxFee);
        tax.setFee(taxFee);

        TaxDAO.updateTax(tax);
        System.out.println("Successfully changed tax");
    }

    private void printErrMessage(String message) {
        String errMessage = MenuErrStringContainer
                .getInstance()
                .convertToErrMessageBox(message);

        System.out.println(errMessage);
    }


}
