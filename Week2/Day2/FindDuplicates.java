import java.util.*;

public class FindDuplicates {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

        System.out.print("Enter how many elements you want to add: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            list.add(sc.nextLine());
        }

        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        for (String item : list) {
            if (!seen.add(item)) {
                duplicates.add(item);
            }
        }

        if (duplicates.isEmpty()) {
            System.out.println("No duplicates found.");
        } else {
            System.out.println("Duplicate elements: " + duplicates);
        }

        sc.close();
    }
}
