// NameFile inherits from BaseInputFile, so it gets file-reading logic for free

/*
For inheritance:
  --> BaseInputFile contains shared logic for reading files (reading lines, splitting data, and storing in the `content` list)
  --> Instead of rewriting that logic in both NameFile and CourseFile, we put it once in BaseInputFile
  --> Line: public class NameFile extends BaseInputFile { — this connects NameFile to that shared logic

  --> Because of inheritance:
      - NameFile doesn’t need to write its own readFile() method
      - CourseFile also doesn’t need its own readFile()
*/

// Define a class that represents the student name file
public class NameFile extends BaseInputFile {

    // Constructor that takes a file path and passes it to the parent class
    public NameFile(String filePath) {
        super(filePath);  // Call BaseInputFile's constructor to set the file path
    }

    // Method that returns the name of a student given their ID
    public String getStudentName(String id) {
        // Loop through each row in the content (each row is [ID, Name])
        for (String[] row : content) {
            if (row[0].equals(id)) {   // If the ID in the current row matches the one we’re looking for
                return row[1];         // Return the corresponding name
            }
        }
        return "Unknown";  // If no match is found, return "Unknown" as a fallback
    }
}
