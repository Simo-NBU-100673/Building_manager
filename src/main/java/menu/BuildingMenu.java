package menu;

public class BuildingMenu extends AbstractMenu {
    private static final int menuNumber = 2;

    @Override
    protected void handleInput(int num) {
        System.out.println("Hello from BuildingMenu");
    }

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }
}
