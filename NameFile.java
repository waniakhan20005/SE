// NameFile inherits from BaseInputFile
public class NameFile extends BaseInputFile {
    public NameFile(String filePath) {
        super(filePath); // Call the constructor of the base class
    }

    // Returns the student name for a given student ID
    public String getStudentName(String id) {
        for (String[] row : content) {
            if (row[0].equals(id)) {
                return row[1]; // Return name if ID matches
            }
        }
        return "Unknown"; // Fallback if ID not found
    }
}
