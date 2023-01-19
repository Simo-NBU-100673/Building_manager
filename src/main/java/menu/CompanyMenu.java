package menu;

public class CompanyMenu extends AbstractMenu {
    private static final int menuNumber = 1;

    @Override
    protected void handleInput(int num) {
        System.out.println("Hello from CompanyMenu");
    }

    @Override
    protected int getMenuNumber() {
        return menuNumber;
    }
}
