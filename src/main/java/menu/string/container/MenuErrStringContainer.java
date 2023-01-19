package menu.string.container;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MenuErrStringContainer {
    private static volatile MenuErrStringContainer instance;
    private final String errMessage;
    private MenuErrStringContainer() {
        errMessage = StringContainer.getStringFromFile("ErrMessage.txt");
    }

    public static MenuErrStringContainer getInstance() {
        MenuErrStringContainer result = instance;
        if (result != null) {
            return result;
        }
        synchronized(MenuStringContainer.class) {
            if (instance == null) {
                instance = new MenuErrStringContainer();
            }
            return instance;
        }
    }

    public String getErrMessage(int errNumber) {
        return String.format(errMessage, errNumber);
    }
}
