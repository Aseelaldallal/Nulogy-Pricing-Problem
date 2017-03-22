import java.util.Scanner;
import java.text.*;

public class Main {

    public static void main(String[] args) {
	      
	      Scanner scanner = new Scanner(System.in);
	      
	      // Base Price
          System.out.print("Enter Base Price: ");
          String basePrice = scanner.nextLine();
          
          // Number of Laborers
          System.out.print("Number of Laborers: ");
          String numLabor = scanner.nextLine();
          
          // Materials
          System.out.print("Type in the type of materials involved. Seperate each with a comma: ");
          String materials = scanner.nextLine();

          scanner.close();
          
          try {
            CostCalculator myCalc = new CostCalculator("$12,456.95", "4", "books");
          } catch (Exception e) {
              System.out.println(e.getMessage());
          } 


          
    }
    
    
}
