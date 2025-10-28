import java.util.*;

public class ArrayListOps {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        int choice;

        do {
            System.out.println("\n--- ArrayList Operations ---");
            System.out.println("1. Add element");
            System.out.println("2. Remove element");
            System.out.println("3. Display elements (for-each loop)");
            System.out.println("4. Display elements (using Iterator)");
            System.out.println("5. Filter elements (starts with given letter)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter element to add: ");
                    String addItem = sc.nextLine();
                    list.add(addItem);
                    System.out.println(addItem + " added to list.");
                    break;

                case 2:
                    System.out.print("Enter element to remove: ");
                    String removeItem = sc.nextLine();
                    if (list.remove(removeItem)) {
                        System.out.println(removeItem + " removed.");
                    } else {
                        System.out.println(removeItem + " not found.");
                    }
                    break;

                case 3:
                    System.out.println("Displaying elements using for-each loop:");
                    for (String item : list) {
                        System.out.println(item);
                    }
                    break;

                case 4:
                    System.out.println("Displaying elements using Iterator:");
                    Iterator<String> it = list.iterator();
                    while (it.hasNext()) {
                        System.out.println(it.next());
                    }
                    break;

                case 5:
                    System.out.print("Enter starting letter to filter: ");
                    char letter = sc.nextLine().charAt(0);
                    System.out.println("Filtered elements:");
                    for (String item : list) {
                        if (item.toLowerCase().startsWith(String.valueOf(letter).toLowerCase())) {
                            System.out.println(item);
                        }
                    }
                    break;

                case 6:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
