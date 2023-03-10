package menu;

import java.util.Map;

public class MergedMenu extends AbstractMenu {
    private static final int menuNumber = 5;

    @Override
    protected void handleInput(int num) {
        System.out.println("Hello from MergedMenu");
    }

    @Override
    protected void populateActionsMap(Map<Integer, Runnable> actions) {
    }

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }
}
