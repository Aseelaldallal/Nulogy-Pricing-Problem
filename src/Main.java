
public class Main {

    public static void main(String[] args) {
	      
	    InputParser parser = new InputParser(args);
        CostCalculator calculator = new CostCalculator();
        String finalPrice = calculator.calculate(parser.getInitialBaseCost(), parser.getNumLabor(), parser.getMaterials()); 
        System.out.println(finalPrice);
 
    }
    
    
}
