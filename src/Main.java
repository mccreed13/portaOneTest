import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    static Properties properties = new Properties();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            FileInputStream inputStream = new FileInputStream("file.properties");
            properties.load(inputStream);
            String pathFile = properties.getProperty("file.path");
            Exercise exercise = new Exercise(pathFile);
            System.out.println(exercise);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Program time: " + (end - start) / 1000d + "s");
    }
}
