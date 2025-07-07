// Import necessary classes for handling file-related exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a NameFile object to read "NameFile.txt"
            NameFile names = new NameFile("NameFile.txt");

            // Create a CourseFile object to read "CourseFile.txt"
            CourseFile courses = new CourseFile("CourseFile.txt");

            // Read and store data from NameFile.txt into the names object
            names.readFile();

            // Read and store data from CourseFile.txt into the courses object
            courses.readFile();

            // Create a DataMerger object, passing the name and course data
            DataMerger merger = new DataMerger(names, courses);

            // Merge and write the formatted data to "MergedOutput.txt"
            merger.mergeAndWriteOutput("MergedOutput.txt");

            // Inform the user that the process completed successfully
            System.out.println("Data merged and written to MergedOutput.txt");

        // Handle case when input files are missing
        } catch (FileNotFoundException e) {
            System.err.println("Error: One of the input files was not found.");

        // Handle issues when writing the output file
        } catch (IOException e) {
            System.err.println("Error writing the output file.");

        // Catch any other unexpected exceptions
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
