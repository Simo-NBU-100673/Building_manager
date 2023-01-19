package menu;

public interface Menu {
    String openMenu();
    public static void printNewLines(int count){
        System.out.println("\n".repeat(count));
    }

    public int calculateLengthOfMenu();
}
