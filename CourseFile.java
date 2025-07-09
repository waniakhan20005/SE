// Import required classes to work with lists
import java.util.ArrayList;  // Allows us to create dynamic lists
import java.util.List;       // General List interface used to define list types

/* For inheritance:
   --> BaseInputFile contains shared logic for reading files (like reading lines, splitting them, and storing the data)
   --> Instead of repeating readFile() in both NameFile and CourseFile, we put it once in BaseInputFile and reuse it
   This is the line that enables that reuse: public class CourseFile extends BaseInputFile
*/

// CourseFile inherits functionality from BaseInputFile (like reading the file and storing its contents)
public class CourseFile extends BaseInputFile {

    // Constructor for CourseFile that takes a file path as input
    public CourseFile(String filePath) {
        super(filePath);  // Call the constructor from BaseInputFile to set the file path
    }

    // Method that returns all course records associated with a specific student ID
    public List<String[]> getCoursesForStudent(String id) {
        List<String[]> results = new ArrayList<>();  // List to store matching course records

        // Loop through each row in the file content
        for (String[] row : content) {
            // If the first column (student ID) matches the one we're looking for
            if (row[0].equals(id)) {
                results.add(row);  // Add that row to the results list
            }
        }

        return results;  // Return the list of matched course records
    }

    // Method to calculate the final grade from a row of course data
    public double calculateFinalGrade(String[] row) {
        // Each course row is expected to have 6 values: ID, courseCode, test1, test2, test3, finalExam
        if (row.length != 6) {
            // If the format is wrong (e.g., missing a grade), throw an error
            // This is "offensive programming" — we crash early if data is invalid
            throw new IllegalStateException("Course row format is invalid.");
        }

        try {
            // Convert strings to numbers for grade calculation
            double t1 = Double.parseDouble(row[2]);        // Test 1
            double t2 = Double.parseDouble(row[3]);        // Test 2
            double t3 = Double.parseDouble(row[4]);        // Test 3
            double finalExam = Double.parseDouble(row[5]); // Final exam

            // Calculate the final grade using weighted average:
            // Tests are each worth 20%, and the final exam is worth 40%
            return 0.2 * t1 + 0.2 * t2 + 0.2 * t3 + 0.4 * finalExam;

        } catch (NumberFormatException e) {
            // If any of the grade values can't be converted to numbers (e.g., garbage text), throw an error
            // This also follows "offensive programming" — crash if the data can't be trusted
            throw new IllegalArgumentException("Invalid number format in course row.");
        }
    }
}
