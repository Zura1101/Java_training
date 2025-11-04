import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class week2test{
    public static void main(String[] args) {
        String inputpath = "C:\\Users\\ranga\\desktop\\Java_training_local\\Week2\\marks.csv";
        String outputpath = "C:\\Users\\ranga\\desktop\\Java_training_local\\Week2\\results.csv";
        List<String[]> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputpath))){
            String line;
            boolean isHeader = true;

            System.out.println("Og File Data");

            while ((line = reader.readLine()) != null) {
                System.out.println(line); 

                if (isHeader) { 
                    isHeader = false;
                    continue;
                }

                String[] row = line.split(",");
                data.add(row);
            }
        }catch (Exception e){
            System.out.print(e);
        }
        List<String> results = new ArrayList<>();
        results.add("Name,Total,Average");

        System.out.println("\n Calculated Results");

        for (String[] row : data) {
            if (row.length == 0) continue;

            String name = row[0].trim();
            int total = 0;
            int count = 0;

            for (int i = 1; i < row.length; i++) {
                String token = row[i].trim();
                if (!token.isEmpty()) {
                    try {
                        total += Integer.parseInt(token);
                        count++;
                    } catch (NumberFormatException e) {
                        System.out.print(e);
                    }
                }
            }

            

            double average = (count > 0) ? (double) total / count : 0.0;
            String resultLine = name + "," + total + "," + String.format("%.2f", average);
            results.add(resultLine);
            System.out.println(resultLine); 
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputpath))) {
            for (String outLine : results) {
                writer.write(outLine);
                writer.newLine();
            }
            System.out.println("\n Results written to: " + outputpath);
        } catch (Exception e){
            System.out.print(e);
        }
        
        
    }
}