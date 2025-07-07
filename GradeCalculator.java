/**
 * GradeCalculator - A program that calculates student grades
 * 
 * This application reads student names from NameFile.txt and course grades from CourseFile.txt,
 * calculates weighted final grades, and generates a report in GradesReport.txt.
 * 
 * Grading Scheme:
 * - 3 Tests (20% each)
 * - Final Exam (40%)
 */

 import java.io.*;
 import java.nio.file.*;
 import java.util.*;
 
 public class GradeCalculator {
     // File locations - adjust this path to match your system
     private static final String BASE_DIR = "C:\\Users\\wania\\OneDrive - Wilfrid Laurier University\\school\\third year\\first semester spring\\CP317 - Software Engineering\\code\\";
     
     // Grading weights (3 tests @ 20% each, final exam @ 40%)
     private static final double TEST_WEIGHT = 0.2;    // Each test weight
     private static final double FINAL_EXAM_WEIGHT = 0.4;  // Final exam weight
 
     public static void main(String[] args) {
         // Set up file paths
         String nameFile = BASE_DIR + "NameFile.txt";       // Student ID/name data
         String courseFile = BASE_DIR + "CourseFile.txt";  // Course grade data
         String reportFile = BASE_DIR + "GradesReport.txt"; // Output file
 
         try {
             // 1. Load student names
             Map<String, String> studentNames = loadStudentNames(nameFile);
             
             // 2. Process grades and calculate final scores
             List<String[]> gradeReport = processCourseGrades(courseFile, studentNames);
             
             // 3. Generate the report
             writeGradeReport(reportFile, gradeReport);
             
             System.out.println("Success! Report generated at: " + reportFile);
         } catch (IOException e) {
             System.err.println("Error processing files: " + e.getMessage());
         }
     }
 
     // Loads student names from file and creates ID-Name mapping
     private static Map<String, String> loadStudentNames(String filename) throws IOException {
         Map<String, String> students = new HashMap<>();
         
         for (String line : Files.readAllLines(Paths.get(filename))) {
             line = line.trim();
             if (line.isEmpty()) continue;  // Skip blank lines
             
             String[] parts = line.split(",");
             if (parts.length >= 2) {
                 students.put(parts[0].trim(), parts[1].trim());
             }
         }
         
         return students;
     }
 
     // Processes course grades and calculates final weighted scores
     private static List<String[]> processCourseGrades(String filename, Map<String, String> nameLookup) throws IOException {
         List<String[]> report = new ArrayList<>();
         
         // Add report header
         report.add(new String[]{
             "Student ID", 
             "Student Name", 
             "Course Code", 
             "Final Grade (Tests: 20% each, Final: 40%)"
         });
 
         // Process each student's grades
         for (String line : Files.readAllLines(Paths.get(filename))) {
             line = line.trim();
             if (line.isEmpty()) continue;  // Skip blank lines
             
             String[] parts = line.split(",");
             if (parts.length >= 6) {
                 String studentId = parts[0].trim();
                 String courseCode = parts[1].trim();
                 
                 // Get test scores
                 double[] scores = {
                     Double.parseDouble(parts[2].trim()),  // Test 1
                     Double.parseDouble(parts[3].trim()),  // Test 2
                     Double.parseDouble(parts[4].trim()),  // Test 3
                     Double.parseDouble(parts[5].trim())   // Final Exam
                 };
                 
                 // Calculate final grade
                 double finalGrade = calculateFinalGrade(scores[0], scores[1], scores[2], scores[3]);
                 String studentName = nameLookup.getOrDefault(studentId, "Unknown");
                 
                 // Add to report
                 report.add(new String[]{
                     studentId, 
                     studentName, 
                     courseCode, 
                     String.format("%.1f", finalGrade)
                 });
             }
         }
         
         return report;
     }
 
     // Writes the grade report to a CSV file
     private static void writeGradeReport(String filename, List<String[]> data) throws IOException {
         try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
             for (String[] row : data) {
                 writer.write(String.join(",", row));
                 writer.newLine();
             }
         }
     }
 
     // Calculates the weighted final grade
     private static double calculateFinalGrade(double test1, double test2, double test3, double finalExam) {
         double weightedGrade = (test1 * TEST_WEIGHT) + 
                              (test2 * TEST_WEIGHT) + 
                              (test3 * TEST_WEIGHT) + 
                              (finalExam * FINAL_EXAM_WEIGHT);
         
         // Round to 1 decimal place
         return Math.round(weightedGrade * 10.0) / 10.0;
     }
 }