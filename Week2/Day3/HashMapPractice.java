import java.util.*;

public class HashMapPractice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> studentMarks = new HashMap<>();
        int choice;

        do {
            System.out.println("\n=== HashMap Operations Menu ===");
            System.out.println("1. Add Student Marks");
            System.out.println("2. Get Marks by Name");
            System.out.println("3. Check if Student Exists");
            System.out.println("4. Count Element Occurrences");
            System.out.println("5. Filter Students with Marks > 50");
            System.out.println("6. Iterate Map");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter marks: ");
                    int marks = sc.nextInt();
                    studentMarks.put(name, marks);
                    System.out.println("Added: " + name + " => " + marks);
                    break;

                case 2:
                    System.out.print("Enter student name to get marks: ");
                    name = sc.nextLine();
                    if (studentMarks.containsKey(name)) {
                        System.out.println(name + "'s Marks: " + studentMarks.get(name));
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter student name to check: ");
                    name = sc.nextLine();
                    System.out.println("Exists? " + studentMarks.containsKey(name));
                    break;

                case 4:
                    System.out.println("Enter number of elements: ");
                    int n = sc.nextInt();
                    int[] arr = new int[n];
                    System.out.println("Enter elements: ");
                    for (int i = 0; i < n; i++)
                        arr[i] = sc.nextInt();

                    HashMap<Integer, Integer> freq = new HashMap<>();
                    for (int num : arr) {
                        freq.put(num, freq.getOrDefault(num, 0) + 1);
                    }

                    System.out.println("Element Frequencies: " + freq);
                    break;

                case 5:
                    System.out.println("Students with marks > 50:");
                    for (Map.Entry<String, Integer> entry : studentMarks.entrySet()) {
                        if (entry.getValue() > 50) {
                            System.out.println(entry.getKey() + " => " + entry.getValue());
                        }
                    }
                    break;

                case 6:
                    System.out.println("Iterating using entrySet:");
                    for (Map.Entry<String, Integer> e : studentMarks.entrySet()) {
                        System.out.println(e.getKey() + " => " + e.getValue());
                    }

                    System.out.println("\nIterating using keySet:");
                    for (String key : studentMarks.keySet()) {
                        System.out.println(key + " => " + studentMarks.get(key));
                    }
                    break;

                case 0:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
