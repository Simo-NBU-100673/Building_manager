package gui;

import menu.*;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

//TODO singleton
public class UserInterface {

    private static volatile UserInterface instance;

    private final Map<Integer,AbstractMenu> menuMap;

    private UserInterface() {
        menuMap = new TreeMap<>();
        menuMap.put(1, new CompanyMenu());
        menuMap.put(2, new BuildingMenu());
        menuMap.put(3, new ApartmentMenu());
        menuMap.put(4, new EmployeeMenu());
        menuMap.put(5, new MergedMenu());
    }

    public static UserInterface getInstance() {
        UserInterface result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserInterface.class) {
            if (instance == null) {
                instance = new UserInterface();
            }
            return instance;
        }
    }
    public void start(){
        String input="";
        int menuNumber = 1;

        while (!input.equals("exit")){
            input = menuMap.get(menuNumber).openMenu();

            if(input.equals("next")) {
                menuNumber++;
                continue;
            }

            if(input.equals("back")) {
                menuNumber--;
            }
        }
    }

}
