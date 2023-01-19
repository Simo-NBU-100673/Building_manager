package menu;

public class EmployeeMenu extends AbstractMenu {
    private static final int menuNumber = 4;

    @Override
    protected void handleInput(int num) {
        System.out.println("Hello from EmployeeMenu");
    }

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }
}
