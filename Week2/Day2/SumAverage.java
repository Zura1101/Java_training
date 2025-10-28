import java.util.*;

public class SumAverage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.print("Enter how many numbers you want to add: ");
        int n = sc.nextInt();

        System.out.println("Enter " + n + " numbers:");
        for (int i = 0; i < n; i++) {
            System.out.print("Number " + (i + 1) + ": ");
            numbers.add(sc.nextInt());
        }

        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }

        double average = (numbers.isEmpty()) ? 0 : (double) sum / numbers.size();

        System.out.println("\nNumbers: " + numbers);
        System.out.println("Sum = " + sum);
        System.out.println("Average = " + average);

        sc.close();
    }
}
