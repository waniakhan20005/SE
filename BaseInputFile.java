// Import required classes for file and data handling 
import java.io.File;                     // Used to create a File object from a file path
import java.io.FileNotFoundException;   // Exception thrown if file is not found 
import java.util.ArrayList;             // allow usage of dynamic arrays 
import java.util.Arrays;               
import java.util.List;                  
import java.util.Scanner;              // Used for reading input from the file

/* Encapsulation in Action:
Protected Fields:
protected List<String[]> content;
protected String filePath;

These fields store the internal state of the class. 
Marking them protected: 
- They are hidden from outside classes like Main.java or DataMerger.java
- They are still accessible within subclasses
This is important because this protects internal data from changes  keeps file handling logic will be self contained. 
-------------------------------------------
Public Getter Method:
public List<String[]> getContent()
This method has read only access to
 the stored file data. Instead of exposing the content list directly, we expose it through a method.

Why it matters: This is a core principle of encapsulation — giving controlled access to internal data while preventing direct modification.

--------------------------------------
Public readFile() Method:
public void readFile() throws FileNotFoundException

This method reads the file and fills in the content field internally.
External code (like Main) doesn’t need to know how the file is read — they just call readFile(), and the class handles the details.

Why it matters: This encapsulates the logic of file reading, so it’s reusable and harder to misuse. You’re enforcing a consistent way to load data across different file types.

In summary, BaseInputFile demonstrates good encapsulation by:

Keeping internal data protected

Providing public methods to safely interact with that data

Hiding complexity behind reusable, well-defined behaviors
*/

// Base class for reading and storing content from an input file
public class BaseInputFile {
    protected List<String[]> content = new ArrayList<>();  // Will store each line of the file as an array of strings (split by comma)
    protected String filePath;                             // Holds the path to the file

    // Constructor takes a file path and ensures it's valid (not null or empty)
    public BaseInputFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            // Throws an error if the file path is missing — this is called "offensive programming" 
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        this.filePath = filePath;  // If valid, store the file path
    }

    // Reads the contents of the file and fills the 'content' list
    public void readFile() throws FileNotFoundException {
        File file = new File(filePath);           // Create a File object using the provided path
        Scanner scanner = new Scanner(file);      // Scanner is used to read the file line by line

        // Loop through each line in the file
        while (scanner.hasNextLine()) {
            String[] lineData = scanner.nextLine().split(",");    // Split the line at each comma
            // Trim spaces from each piece of data and add it to the content list
            content.add(Arrays.stream(lineData).map(String::trim).toArray(String[]::new));
        }

        scanner.close();  // Close the scanner to free resources
    }

    // Public method to access the stored content (read-only)
    public List<String[]> getContent() {
        return content;   // Returns the list of string arrays (each array represents a line in the file)
    }
}
