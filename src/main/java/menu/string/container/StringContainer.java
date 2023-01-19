package menu.string.container;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface StringContainer {
    public static String getStringFromFile(String fileName) {
        String path = String.format("src/main/java/menu/string/files/%s", fileName);
        File file = new File(path);
        StringBuilder sb = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
