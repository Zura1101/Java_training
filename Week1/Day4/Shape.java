import java.util.Scanner;

public class Shape {

    double length;
    double width;
    double radius;

    public Shape() {
        System.out.println("Default Shape created.");
    }

    public Shape(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public Shape(double radius) {
        this.radius = radius;
    }

    public double area(double radius) {
        return Math.PI * radius * radius;
    }

    public double area(double length, double width) {
        return length * width;
    }

    public double perimeter(double radius) {
        return 2 * Math.PI * radius;
    }

    public double perimeter(double length, double width) {
        return 2 * (length + width);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Shape: 1 for Circle, 2 for Rectangle");
        int choice = sc.nextInt();

        Shape shape = new Shape();

        if (choice == 1) {
            System.out.print("Enter radius: ");
            double r = sc.nextDouble();

            System.out.println("\n--- Circle ---");
            System.out.println("Area: " + shape.area(r));
            System.out.println("Perimeter: " + shape.perimeter(r));
        } else if (choice == 2) {
            System.out.print("Enter length: ");
            double l = sc.nextDouble();

            System.out.print("Enter width: ");
            double w = sc.nextDouble();

            System.out.println("\n--- Rectangle ---");
            System.out.println("Area: " + shape.area(l, w));
            System.out.println("Perimeter: " + shape.perimeter(l, w));
        } else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}
