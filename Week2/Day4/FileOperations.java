import java.io.*;
import java.util.Scanner;

public class FileOperations {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * @param args
     */
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== File I/O Menu ===");
            System.out.println("1. Read and write text file (overwrite)");
            System.out.println("2. Append to file");
            System.out.println("3. Count words in a text file");
            System.out.println("4. Copy file content");
            System.out.println("5. Exit");
            System.out.print("Choose (1-5): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> readAndWrite();
                case "2" -> appendToFile();
                case "3" -> countWordsInFile();
                case "4" -> copyFileContent();
                case "5" -> {
                    try (sc) {
                        System.out.println("Goodbye!");
                    }
                    return;
                }

                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // 1) Read and write (overwrite) using BufferedReader and BufferedWriter
    private static void readAndWrite() {
        System.out.print("Enter source filename to read (e.g., input.txt): ");
        String source = sc.nextLine().trim();
        System.out.print("Enter destination filename to write (e.g., output.txt): ");
        String dest = sc.nextLine().trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Successfully read from '" + source + "' and wrote to '" + dest + "'.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }

    // 2) Append new data to existing file
    private static void appendToFile() {
        System.out.print("Enter filename to append to (will create if not exists): ");
        String filename = sc.nextLine().trim();
        System.out.println("Enter lines to append. Enter a single '.' on a line to finish.");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            while (true) {
                String line = sc.nextLine();
                if (line.equals(".")) break;
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Appended text to '" + filename + "'.");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }

    // 3) Count words in a text file
    private static void countWordsInFile() {
        System.out.print("Enter filename to count words (e.g., input.txt): ");
        String filename = sc.nextLine().trim();
        long wordCount = 0;
        long lineCount = 0;
        long charCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length() + System.lineSeparator().length();
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    // split on whitespace (one or more)
                    String[] words = trimmed.split("\\s+");
                    wordCount += words.length;
                }
            }
            System.out.println("File: " + filename);
            System.out.println("Lines: " + lineCount);
            System.out.println("Words: " + wordCount);
            System.out.println("Characters (approx, incl newlines): " + charCount);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }

    // 4) Copy file content (preserves overwrite semantics)
    private static void copyFileContent() {
        System.out.print("Enter source filename to copy from: ");
        String source = sc.nextLine().trim();
        System.out.print("Enter destination filename to copy to: ");
        String dest = sc.nextLine().trim();

        // Use buffered reader/writer copy for demonstrative purposes
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Copied content from '" + source + "' to '" + dest + "'.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
