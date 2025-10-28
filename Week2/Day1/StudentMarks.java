import java.util.Scanner;

class StudentMarks {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        int[] marks = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter mark of student " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
        }
        int max = marks[0], min = marks[0], sum = 0;
        for (int mark : marks) {
            if (mark > max)
                max = mark;
            if (mark < min)
                min = mark;
            sum += mark;
        }
        double avg = (double) sum / n;
        System.out.println("Max: " + max);
        System.out.println("Min: " + min);
        System.out.println("Average: " + avg);
        sc.close();
    }
}
