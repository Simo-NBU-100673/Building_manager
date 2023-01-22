package menu.string.container;

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
        synchronized (MenuStringContainer.class) {
            if (instance == null) {
                instance = new MenuErrStringContainer();
            }
            return instance;
        }
    }

    public String getErrMessage(int errNumber) {
        return String.format(errMessage, errNumber);
    }

    public String convertToErrMessageBox(String errMessage) {
            //split the errMessage into Strings of max length 50
            String[] errMessageArray = errMessage.split("(?<=\\G.{50})");

            //create a StringBuilder to store the result
            StringBuilder result = new StringBuilder();

            //add the first line
            result.append(getLineBreaker());

            for(String errMessagePart : errMessageArray){
                result
                        .append("\n  | ")
                        .append(errMessagePart)
                        .append(" ".repeat(50 - errMessagePart.length()))
                        .append(" |");
            }

            //add the last line
            result.append(getLineBreaker());

            return result.toString();
    }

    private String getLineBreaker() {
        return "\n  |" + "=".repeat(52) + "|";
    }
}
