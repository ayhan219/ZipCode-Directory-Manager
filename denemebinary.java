import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class denemebinary {

    public static void main(String[] args) {
        readBinaryFile();
    }

    public static void readBinaryFile() {
        String filename = "binary.dat";

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<?> list = (ArrayList<?>) reader.readObject();
            System.out.println("Read object: " + list);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Class cast error: " + e.getMessage());
        }
    }
}
