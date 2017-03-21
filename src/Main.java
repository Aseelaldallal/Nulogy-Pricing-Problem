import java.util.Scanner;

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
          
          //CostCalculator costCalc = new CostCalculator(basePrice, numLabor, materials);
          //System.out.println("Cost: " + costCalc.calculate());
          
    }
    
    
}
