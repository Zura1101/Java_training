import java.util.*;

public class SortArrayList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.print("Enter how many numbers you want to add: ");
        int count = sc.nextInt();

        System.out.println("Enter " + count + " numbers:");
        for (int i = 0; i < count; i++) {
            System.out.print("Number " + (i + 1) + ": ");
            int num = sc.nextInt();
            numbers.add(num);
        }

        System.out.println("\nBefore sort: " + numbers);

        Collections.sort(numbers);
        System.out.println("Ascending order: " + numbers);

        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("Descending order: " + numbers);

        sc.close();
    }
}
