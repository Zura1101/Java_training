import java.util.Scanner;

public class ReverseString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        // Option 1: using StringBuilder (simple)
        String reversed = new StringBuilder(input).reverse().toString();

        // Option 2: manual reverse (uncomment to use)
        /*
         * char[] chars = input.toCharArray();
         * for (int i = 0, j = chars.length - 1; i < j; i++, j--) {
         * char tmp = chars[i];
         * chars[i] = chars[j];
         * chars[j] = tmp;
         * }
         * String reversed = new String(chars);
         */

        System.out.println("Reversed: " + reversed);
        sc.close();
    }
}
