// Import necessary classes for file writing and utility data structures
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataMerger {
    // Declare private variables for name and course files
    private NameFile nameFile;
    private CourseFile courseFile;

    // Constructor initializes the nameFile and courseFile objects
    public DataMerger(NameFile nameFile, CourseFile courseFile) {
        this.nameFile = nameFile;
        this.courseFile = courseFile;
    }

    // This method merges data and writes the output to a specified file
    public void mergeAndWriteOutput(String outputFileName) throws IOException {
        // Map to store student ID -> student name for quick lookup
        Map<String, String> studentNames = new HashMap<>();
        
        // Populate the map with data from the name file
        for (String[] row : nameFile.getContent()) {
            studentNames.put(row[0], row[1]); // row[0] = student ID, row[1] = name
        }

        // List to hold merged data rows (each with ID, name, course, final grade)
        List<String[]> mergedData = new ArrayList<>();
        
        // Process each row in the course file
        for (String[] row : courseFile.getContent()) {
            String id = row[0];                 // Student ID
            String course = row[1];             // Course code
            double finalGrade = courseFile.calculateFinalGrade(row); // Compute final grade
            String name = studentNames.getOrDefault(id, "Unknown");  // Get name from map or fallback
            
            // Add merged record to list
            mergedData.add(new String[]{id, name, course, String.format("%.2f", finalGrade)});
        }

        // Sort merged data by student ID in ascending order
        mergedData.sort(Comparator.comparing(arr -> arr[0]));

        // Write the sorted and merged data to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String[] row : mergedData) {
                writer.write(String.join(", ", row)); // Join elements with commas
                writer.newLine();                     // Move to the next line
            }
        }
    }
}
