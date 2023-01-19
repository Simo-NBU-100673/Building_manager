package menu.string.container;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuStringContainer {

    private static volatile MenuStringContainer instance;
    public Map<Integer, String> menus;
    private final String menuCompany;
    private final String menuBuilding;
    private final String menuApartment;
    private final String menuEmployee;
    private final String menuMerged;


    private MenuStringContainer() {
        this.menus = new HashMap<>();
        this.menuCompany = StringContainer.getStringFromFile("Company.txt");
        this.menuBuilding = StringContainer.getStringFromFile("Building.txt");
        this.menuApartment = StringContainer.getStringFromFile("Apartment.txt");
        this.menuEmployee = StringContainer.getStringFromFile("Employee.txt");
        this.menuMerged = StringContainer.getStringFromFile("MergedOperations.txt");

        initMap();
    }


    public static MenuStringContainer getInstance() {
        MenuStringContainer result = instance;
        if (result != null) {
            return result;
        }
        synchronized(MenuStringContainer.class) {
            if (instance == null) {
                instance = new MenuStringContainer();
            }
            return instance;
        }
    }
    private void initMap() {
        this.menus.put(1, menuCompany);
        this.menus.put(2, menuBuilding);
        this.menus.put(3, menuApartment);
        this.menus.put(4, menuEmployee);
        this.menus.put(5, menuMerged);
    }

    public String getMenu(int menuNumber) {
        if (menuNumber < 1 || menuNumber > 5) {
            throw new IllegalArgumentException("Menu number must be between 1 and 5");
        }
        return menus.get(menuNumber);
    }
}
