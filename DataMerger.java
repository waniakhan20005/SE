// Import necessary classes for writing to files and using common data structures
import java.io.BufferedWriter;  // Used to efficiently write text to files
import java.io.FileWriter;      // Used to create and write to a file
import java.io.IOException;     // Handles file-related errors
import java.util.*;             // Imports utility classes like List, Map, HashMap, etc.

public class DataMerger {
    // Variables to hold the name and course file objects
    private NameFile nameFile;
    private CourseFile courseFile;

    // Constructor receives and stores the NameFile and CourseFile objects
    public DataMerger(NameFile nameFile, CourseFile courseFile) {
        this.nameFile = nameFile;
        this.courseFile = courseFile;
    }

    // Method that merges student names with their courses and grades, then writes to a file
    public void mergeAndWriteOutput(String outputFileName) throws IOException {
        // Create a map to store student ID → name, making it easy to find a name using the ID
        Map<String, String> studentNames = new HashMap<>();

        // Fill the map using the content from the name file
        for (String[] row : nameFile.getContent()) {
            studentNames.put(row[0], row[1]);  // row[0] is the student ID, row[1] is the student's name
        }

        // Prepare a list to store the merged results: ID, name, course code, and final grade
        List<String[]> mergedData = new ArrayList<>();

        // Go through each course record
        for (String[] row : courseFile.getContent()) {
            String id = row[0];                             // Student ID
            String course = row[1];                         // Course code
            double finalGrade = courseFile.calculateFinalGrade(row); // Calculate the student's final grade

            // Look up the student name using their ID, or use "Unknown" if not found
            String name = studentNames.getOrDefault(id, "Unknown");

            // Format the final grade to 2 decimal places and add the merged row to the list
            mergedData.add(new String[]{id, name, course, String.format("%.2f", finalGrade)});
        }

        // Sort the merged list by student ID so the output is nicely ordered
        mergedData.sort(Comparator.comparing(arr -> arr[0]));

        // Write the sorted results to the output file
        // try-with-resources ensures the writer is automatically closed afterward
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String[] row : mergedData) {
                // Join the row values with commas and write to the file
                writer.write(String.join(", ", row));
                writer.newLine();  // Add a new line after each entry
            }
        }
    }
}
