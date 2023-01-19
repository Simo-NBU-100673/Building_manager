package menu;

import java.util.Map;

public class EmployeeMenu extends AbstractMenu {
    private static final int menuNumber = 4;

    @Override
    protected void handleInput(int num) {
        System.out.println("Hello from EmployeeMenu");
    }

    @Override
    protected Map<Integer, Runnable> populateActionsMap() {
        return null;
    }

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }
}
