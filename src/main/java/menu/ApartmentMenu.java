package menu;

import java.util.Scanner;

public class ApartmentMenu extends AbstractMenu {
    private static final int menuNumber = 3;

    @Override
    protected void handleInput(int num) {
        System.out.println("Hello from ApartmentMenu");
    }

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }
}
