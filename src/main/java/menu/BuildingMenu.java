package menu;

import java.util.Map;

public class BuildingMenu extends AbstractMenu {
    private static final int menuNumber = 2;

    @Override
    protected void handleInput(int num) {
        System.out.println("Hello from BuildingMenu");
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
