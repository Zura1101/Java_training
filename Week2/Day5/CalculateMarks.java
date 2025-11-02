import java.io.*;
import java.util.*;

public class CalculateMarks {

    public static void main(String[] args) {
        // You can change these paths as needed
        String inputFilePath = "C:\\Users\\ranga\\Desktop\\Java_training_local\\Week2\\Day5\\marks.csv";   // Input file path
        String outputFilePath = "C:\\Users\\ranga\\Desktop\\Java_training_local\\Week2\\Day5\\results.csv"; // Output file path

        List<String[]> data = new ArrayList<>();

        // Step 1: Read CSV file from input path
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    isHeader = false;
                    continue; // skip header row
                }
                data.add(values);
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Step 2: Calculate total and average
        List<String> results = new ArrayList<>();
        results.add("Name,Total,Average"); // header for output

        for (String[] row : data) {
            String name = row[0];
            int[] marks = new int[row.length - 1];

            // Convert marks to integers
            for (int i = 1; i < row.length; i++) {
                try {
                    marks[i - 1] = Integer.parseInt(row[i]);
                } catch (NumberFormatException e) {
                    marks[i - 1] = 0; // default to 0 for invalid values
                }
            }

            int total = 0;
            for (int mark : marks) {
                total += mark;
            }
            double average = (double) total / marks.length;

            results.add(name + "," + total + "," + String.format("%.2f", average));
        }

        // Step 3: Ensure output folder exists
        File outputFile = new File(outputFilePath);
        outputFile.getParentFile().mkdirs(); // create directories if missing

        // Step 4: Write results to output path
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String result : results) {
                bw.write(result);
                bw.newLine();
            }
            System.out.println("✅ Results written successfully to: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
        }
    }
}
