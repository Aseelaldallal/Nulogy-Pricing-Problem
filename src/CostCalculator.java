import java.text.*;
import java.util.*;
import java.math.*;


public class CostCalculator {
    
    /* --------------------------------------------------------- */
    /* ---------------------- CONSTRUCTOR ---------------------- */
    /* --------------------------------------------------------- */
  
    // Constructor
    public CostCalculator() { }

    /* --------------------------------------------------------- */
    /* -------------------- HELPER METHODS --------------------- */
    /* --------------------------------------------------------- */
  
    // Pre: basePrice, numLabor, materials are not null
    // Post: calculates final price based on basePrice, numLabor and materials
    public String calculate(BigDecimal basePrice, int numLabor, ArrayList<String> materials) {       
        BigDecimal priceAfterFlat = applyFlatMarkup(basePrice);
        BigDecimal additionalMarkupP = new BigDecimal(1 + (calculateAdditionalMarkup(numLabor, materials)/100)); // Avoiding double, loss of precision
        BigDecimal finalPrice = priceAfterFlat.multiply(additionalMarkupP);
        finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(finalPrice.doubleValue());
    }
  
  
    // Pre: numLabor, matArray are not null.
    // Post: Returns the total percentage of markup needed based on numLabor and materials
    //       in matArray
    private double calculateAdditionalMarkup(int numLabor, ArrayList<String> matArray) {
        double markup = numLabor * Constants.LABOR_MARKUP;
        for(int i=0; i<matArray.size(); i++) {
            if(matArray.get(i).toLowerCase().equals("food")) {
                markup += Constants.FOOD_MARKUP;
            }
            if(matArray.get(i).toLowerCase().equals("drugs")) {
                markup += Constants.PHARM_MARKUP;
            }
            if(matArray.get(i).toLowerCase().equals("electronics")) {
                markup += Constants.ELEC_MARKUP;
            }
        }
        return markup;
    }
    
    // Pre: b is not null
    // Post: Applys flat markup to b
    private BigDecimal applyFlatMarkup(BigDecimal b) {
        return b.multiply(Constants.FLAT_MARKUP, MathContext.DECIMAL64);
    }
  
    /* --------------------------------------------------------- */
    /* ------------------------ TESTING ------------------------ */
    /* --------------------------------------------------------- */
 
 
    // Pre: aNum, expected not null
    // Post: Returns pass if aNum equivelant to expected. Returns fail otherwise
    private void testApplyFlatMarkup(BigDecimal aNum, double expected) {
        BigDecimal exp = new BigDecimal(expected, MathContext.DECIMAL64);
        BigDecimal newNum = this.applyFlatMarkup(aNum);
        newNum = newNum.setScale(4, BigDecimal.ROUND_HALF_UP);
        if(newNum.compareTo(exp) == 0) {
            System.out.println("Test Pass");
        } else {
            System.out.println("*******applyFlatMarkup Test Fail - Input: " + aNum + "; Expected: " + expected + "; Result: " + newNum);
        }
    }
    
    // Pre: numLabor, materials, expected not null
    // Post: If result of calculateAdditionalMarkup is expected, test pass. Otherwise, fail
    private void testCalculateAdditionalMarkup(int numLabor, ArrayList<String> materials, double expected) {
        double result = calculateAdditionalMarkup(numLabor, materials);
        if(result == expected) {
            System.out.println("Test Pass"); 
        } else {
            System.out.println("*******calculateAdditionalMarkup Test Fail - Num Labor: " + numLabor + "; Materials: " + materials.toString() + "; Expected: " + expected + "; Result: " + result);
        }
    }
    
    
   
    /* -------------------------- MAIN ------------------------- */
   
     public static void main(String[] args) throws ParseException {
        
        System.out.println("Starting tests....");
        
        CostCalculator tester = new CostCalculator();
        
        // Test applyFlatMarkup
        tester.testApplyFlatMarkup(new BigDecimal(0), 0);
        tester.testApplyFlatMarkup(new BigDecimal(1), 1.05);
        tester.testApplyFlatMarkup(new BigDecimal(1299.99), 1364.9895);

        // Test applyAdditionalMarkup
        tester.testCalculateAdditionalMarkup(0, new ArrayList<String>(), 0);
        String[] array = {"books", "magazines", "letters"};
        tester.testCalculateAdditionalMarkup(0, new ArrayList<String>(Arrays.asList(array)), 0);
        tester.testCalculateAdditionalMarkup(1, new ArrayList<String>(Arrays.asList(array)), 1.2);
        String[] array2 = {"Electronics"};
        tester.testCalculateAdditionalMarkup(1, new ArrayList<String>(Arrays.asList(array2)), 3.2);
        String[] array3 = {"drugs"};
        tester.testCalculateAdditionalMarkup(1, new ArrayList<String>(Arrays.asList(array3)), 8.7);
        String[] array4 = {"food"};
        tester.testCalculateAdditionalMarkup(1, new ArrayList<String>(Arrays.asList(array4)), 14.2);
        String[] array5 = {"electronics", "drugs", "food"};
        tester.testCalculateAdditionalMarkup(1, new ArrayList<String>(Arrays.asList(array5)), 23.7);
        String[] array6 = {"electronics", "drugs", "food", "books"};
        tester.testCalculateAdditionalMarkup(3, new ArrayList<String>(Arrays.asList(array6)), 26.1);
        tester.testCalculateAdditionalMarkup(5, new ArrayList<String>(), 6);
        String[] array7 = {"books", "drugs"};
        tester.testCalculateAdditionalMarkup(6, new ArrayList<String>(Arrays.asList(array7)), 14.7);
     }
  
   
   
  
   
}
