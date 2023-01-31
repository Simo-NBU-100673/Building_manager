package menu;

import dao.*;
import entity.*;
import menu.string.container.MenuErrStringContainer;
import tax.type.TaxType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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



    //  |  1.  Create a new apartment                        | DONE
    //  |  2.  Edit an apartment                             | DONE
    //  |  3.  Delete apartment                              | DONE
    //  |  4.  List all residents in the apartment           | DONE
    //  |  5.  Add resident                                  | DONE
    //  |  6.  Remove resident                               | DONE
    //  |  7.  Print floor of the apartment                  | DONE
    //  |  8.  List all pets living in the apartment         | DONE
    //  |  9.  Print the taxes for the apartment             |
    //  |  10. Print the owner of the apartment              | DONE
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
        Apartment apartment = getApartmentByIdFromUser();
        List<Resident> residents = ApartmentDAO.getResidentsInApartment(apartment).stream().toList();
        System.out.println("Residents in apartment with id("+apartment.getIdApartment()+"):");
        residents.forEach(System.out::println);
    }

    private void addResident() {
        Resident resident = null;
        try {
            resident = getResidentFromUserInput();
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

        Apartment apartment = getApartmentByIdFromUser();
        resident.setApartmentByApartmentId(apartment);

        ResidentDAO.saveResident(resident);
    }

    private Resident getResidentFromUserInput() throws ParseException {
        System.out.println("Type for Resident: {first_name} {last_name} {yyyy-MM-dd} {isUsingElevator(true/false)} and press (ENTER)");
        System.out.println("The date of birth must be in this format!");
        String[] tokens = userInput.nextLine().split(" ");
        String firstName = tokens[0];
        String lastName = tokens[1];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(tokens[2]);
        boolean isUsingElevator = tokens[3].trim().equalsIgnoreCase("true");
        Resident resident = new Resident(new java.sql.Date(date.getTime()),firstName,isUsingElevator,lastName);

        return resident;
    }

    private void removeResident() {
        Resident resident = getResidentByIdFromUserInput();
        ResidentDAO.deleteResident(resident);
    }

    private Resident getResidentByIdFromUserInput(){
        System.out.println("Type the id of the resident you want to remove from its current apartment:");
        long id = Long.parseLong(userInput.nextLine().trim());
        if (!ResidentDAO.exists(id)) {
            throw new IllegalArgumentException("Apartment with this ID does NOT exist!");
        }

        Resident resident = ResidentDAO.getResidentById(id);

        return resident;
    }

    private void printFloorOfApartment() {
        Apartment apartment = getApartmentByIdFromUser();
        System.out.println("Floor of apartment: "+apartment.getFloor());
    }

    private void listAllPetsInApartment() {
        Apartment apartment = getApartmentByIdFromUser();
        List<Pet> pets = ApartmentDAO.getPetsInApartment(apartment);
        System.out.println("Pets in apartment with id: "+apartment.getIdApartment());
        pets.forEach(System.out::println);
    }

    private void printTaxesForApartment() {
        Apartment apartment = getApartmentByIdFromUser();
        Tax taxPeople = BuildingDAO.getTaxOfBuilding(apartment.getBuildingByBuildingId(), TaxType.PEOPLE);
        int countOfPeopleInApartment = ApartmentDAO
                .getResidentsInApartment(apartment)
                .stream()
                .filter(resident -> isBeforeSevenYears(resident.getDateOfBirth().toLocalDate()))
                .filter(Resident::isUsingElevator)
                .toList()
                .size();

        Tax taxPets = BuildingDAO.getTaxOfBuilding(apartment.getBuildingByBuildingId(), TaxType.PET);
        int countOfPetsInApartment = ApartmentDAO.getPetsInApartment(apartment).size();

        String message = String.format("Apartment with residents: %d and pets: %d",countOfPeopleInApartment, countOfPetsInApartment);
        System.out.println(message);

        String message2 = String.format("Taxes: "+taxPeople.getType().name()+"(%d), "+taxPets.getType().name()+"(%d)",taxPeople.getFee(), taxPets.getFee());
        System.out.println(message2);

        int sum = Math.toIntExact(((taxPeople.getFee() * countOfPeopleInApartment) + (taxPets.getFee() * countOfPetsInApartment)));

        String message3 = String.format("SUM: "+ sum);
        System.out.println(message3);
    }

    private int calculateTaxForApartment(Apartment apartment){
        Tax taxPeople = BuildingDAO.getTaxOfBuilding(apartment.getBuildingByBuildingId(), TaxType.PEOPLE);
        int countOfPeopleInApartment = ApartmentDAO
                .getResidentsInApartment(apartment)
                .stream()
                .filter(resident -> isBeforeSevenYears(resident.getDateOfBirth().toLocalDate()))
                .filter(Resident::isUsingElevator)
                .toList()
                .size();

        Tax taxPets = BuildingDAO.getTaxOfBuilding(apartment.getBuildingByBuildingId(), TaxType.PET);
        int countOfPetsInApartment = ApartmentDAO.getPetsInApartment(apartment).size();

        int sum = Math.toIntExact(((taxPeople.getFee() * countOfPeopleInApartment) + (taxPets.getFee() * countOfPetsInApartment)));

        return sum;
    }

    private static boolean isBeforeSevenYears(LocalDate date) {
        LocalDate now = LocalDate.now();
        Period period = Period.between(date, now);
        return period.getYears() >= 7;
    }

    private void printOwnerOfApartment() {
        Apartment apartment = getApartmentByIdFromUser();
        System.out.println("Owner of apartment: "+apartment.getOwnerByOwnerId());
    }

    private void printLastPaidDate() {
        Apartment apartment = getApartmentByIdFromUser();
        Paymentshistory payment = PaymentHistoryDAO.getLastPayment(apartment);

        System.out.println("Last payment: "+payment);
    }

    private void printAllPaymentsForApartment() {
        Apartment apartment = getApartmentByIdFromUser();
        List<Paymentshistory> payments = PaymentHistoryDAO.getAllPayments(apartment).stream().toList();

        System.out.println("All payments for this apartment:");
        payments.forEach(System.out::println);
    }

    private void payTaxes() {
        Apartment apartment = getApartmentByIdFromUser();
        int tax = calculateTaxForApartment(apartment);
        java.util.Date currentDate = new java.util.Date();

        Paymentshistory payment = new Paymentshistory(new java.sql.Date(currentDate.getTime()), tax);
        payment.setApartmentByApartmentsId(apartment);

        PaymentHistoryDAO.savePayment(payment);
    }

}
