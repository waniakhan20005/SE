// Import classes for file handling, scanning, and lists
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Base class for reading input files and storing content
public class BaseInputFile {
    protected List<String[]> content = new ArrayList<>(); // Stores file data line by line
    protected String filePath; // Path to the file

    // Constructor that sets the file path and checks it's valid
    public BaseInputFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        this.filePath = filePath;
    }

    // Reads the file and stores its lines into the content list
    public void readFile() throws FileNotFoundException {
        File file = new File(filePath); // Create a File object
        Scanner scanner = new Scanner(file); // Scanner to read file

        // Read each line, split by comma, and trim whitespace
        while (scanner.hasNextLine()) {
            String[] lineData = scanner.nextLine().split(",");
            content.add(Arrays.stream(lineData).map(String::trim).toArray(String[]::new));
        }

        scanner.close(); // Close scanner after reading
    }

    // Getter to access file content
    public List<String[]> getContent() {
        return content;
    }
}
