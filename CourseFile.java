// Import required classes for list and array list
import java.util.ArrayList;
import java.util.List;

// CourseFile inherits from BaseInputFile
public class CourseFile extends BaseInputFile {
    public CourseFile(String filePath) {
        super(filePath); // Call the constructor of the base class
    }

    // Retrieves all course records for a specific student ID
    public List<String[]> getCoursesForStudent(String id) {
        List<String[]> results = new ArrayList<>();
        for (String[] row : content) {
            if (row[0].equals(id)) {
                results.add(row); // Add matching row to results
            }
        }
        return results;
    }

    // Calculates the final grade for a course record (row)
    public double calculateFinalGrade(String[] row) {
        // Ensure the row has exactly 6 elements (ID, course, test1–3, final exam)
        if (row.length != 6) {
            throw new IllegalStateException("Course row format is invalid.");
        }
        try {
            // Parse test scores and final exam
            double t1 = Double.parseDouble(row[2]);
            double t2 = Double.parseDouble(row[3]);
            double t3 = Double.parseDouble(row[4]);
            double finalExam = Double.parseDouble(row[5]);

            // Calculate weighted average: 20% each test, 40% final exam
            return 0.2 * t1 + 0.2 * t2 + 0.2 * t3 + 0.4 * finalExam;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in course row.");
        }
    }
}
