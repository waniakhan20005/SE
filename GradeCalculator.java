// GradeCalculator.java
// Java application to read NameFile.txt and CourseFile.txt,
// calculate weighted grades, and write results to GradesReport.txt.

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GradeCalculator {

    // Hardcoded base directory where input files are located
    private static final String BASE_DIR = "C:\\Users\\wania\\OneDrive - Wilfrid Laurier University\\school\\third year\\first semester spring\\CP317 - Software Engineering\\code\\";

    public static void main(String[] args) {
        String nameFile = BASE_DIR + "NameFile.txt";
        String courseFile = BASE_DIR + "CourseFile.txt";
        String outputFile = BASE_DIR + "GradesReport.txt";

        try {
            Map<String, String> studentNames = loadStudentNames(nameFile);
            List<String[]> gradesData = processCourses(courseFile, studentNames);
            writeGradesReport(outputFile, gradesData);
            System.out.println("Grades report successfully written to " + outputFile);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    // Loads student ID-name pairs from NameFile.txt
    public static Map<String, String> loadStudentNames(String filename) throws IOException {
        Map<String, String> names = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                names.put(parts[0].trim(), parts[1].trim());
            }
        }
        return names;
    }

    // Calculates grades and returns rows for output
    public static List<String[]> processCourses(String filename, Map<String, String> nameLookup) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<String[]> outputData = new ArrayList<>();

        // Add header row
        outputData.add(new String[]{
            "Student ID", "Student Name", "Course Code", "Final grade (Test1,2,3 @20% each; Final @40%)"
        });

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String studentId = parts[0].trim();
                String courseCode = parts[1].trim();
                double test1 = Double.parseDouble(parts[2].trim());
                double test2 = Double.parseDouble(parts[3].trim());
                double test3 = Double.parseDouble(parts[4].trim());
                double finalExam = Double.parseDouble(parts[5].trim());

                double finalGrade = computeFinalGrade(test1, test2, test3, finalExam);
                String studentName = nameLookup.getOrDefault(studentId, "Unknown");

                outputData.add(new String[]{
                    studentId, studentName, courseCode, String.format("%.1f", finalGrade)
                });
            }
        }
        return outputData;
    }

    // Writes the final grades to a new file
    public static void writeGradesReport(String filename, List<String[]> rows) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            for (String[] row : rows) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }

    // Computes the weighted final grade
    public static double computeFinalGrade(double t1, double t2, double t3, double finalExam) {
        return Math.round((t1 * 0.2 + t2 * 0.2 + t3 * 0.2 + finalExam * 0.4) * 10.0) / 10.0;
    }
}
