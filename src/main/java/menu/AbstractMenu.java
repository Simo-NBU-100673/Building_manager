package menu;

import menu.string.container.MenuErrStringContainer;
import menu.string.container.MenuStringContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class AbstractMenu implements Menu, Comparable<AbstractMenu> {
    private final int lengthOfMenu;

    private final String menuString;

    protected Map<Integer, Runnable> actions;

    protected Scanner userInput;

    public AbstractMenu() {
        this.lengthOfMenu = calculateLengthOfMenu();
        this.menuString = MenuStringContainer.getInstance().getMenu(getMenuNumber());
        actions = populateActionsMap();
        userInput = new Scanner(System.in);
    }

    public int calculateLengthOfMenu() {
        int length = 0;
        String menu = MenuStringContainer.getInstance().getMenu(getMenuNumber());

        try(Scanner scanner = new Scanner(menu)){
            while (scanner.hasNextLine()) {
                length++;
                scanner.nextLine();
            }
        }

        return length - 6;
    }

    public String openMenu() {
        String input = "";

        while (true) {

            printMenu();

            //listen for input from user and set menuNumber with Scanner
            input = userInput.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                userInput.close();
                return "exit";
            }

            if (input.equals("next") && getMenuNumber() < 5) {
                Menu.printNewLines(20);
                return "next";
            }

            if (input.equals("back") && getMenuNumber() > 1) {
                Menu.printNewLines(20);
                return "back";
            }

            if(validateInput(input)){
                int num = Integer.parseInt(input);
                handleInput(num);
            }

        }
    }

    private boolean validateInput(String input) {
        try {
            int num = Integer.parseInt(input);

            if (num < 1 || num > lengthOfMenu) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            Menu.printNewLines(20);
            printErrorMessage();
            return false;
        }

        return true;
    }

    private void printMenu() {
        System.out.println(menuString);
        System.out.print("\t>> ");
    }

    private void printErrorMessage() {
        String errorMessage = MenuErrStringContainer.getInstance().getErrMessage(lengthOfMenu);
        System.out.println(errorMessage);
    }

    protected abstract void handleInput(int num);

    protected abstract int getMenuNumber();

    protected abstract Map<Integer, Runnable> populateActionsMap();

    @Override
    public int compareTo(AbstractMenu o) {
        return Integer.compare(getMenuNumber(), o.getMenuNumber());
    }
}
