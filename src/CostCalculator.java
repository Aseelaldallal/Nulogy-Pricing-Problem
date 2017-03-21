import java.text.*;
import java.util.*;
import java.math.*;
import java.util.regex.*;



public class CostCalculator {
    
    /* --------------------------------------------------------- */
    /* --------------------- INSTANCE VARS --------------------- */
    /* --------------------------------------------------------- */
    private BigDecimal basePrice;
    private int numLabor;
    private String materials;
       
    // RegEx to match cost - allows for consistent commas
    private String costRegEx = "^(\\d+|\\d{1,3}(,\\d{3})*)(\\.\\d+)?$";
    
    /* --------------------------------------------------------- */
    /* ---------------------- CONSTRUCTOR ---------------------- */
    /* --------------------------------------------------------- */
  
    // Constructor
    public CostCalculator(String basePrice, String numLabor, String materials) {
         

    }

    /* --------------------------------------------------------- */
    /* ------------------------ METHODS ------------------------ */
    /* --------------------------------------------------------- */
   
    public String calculate() {
        return "";
    }

   /* --------------------------------------------------------- */
   /* -------------------- HELPER METHODS --------------------- */
   /* --------------------------------------------------------- */
  
  
   
   /* --------------------------------------------------------- */
   /* ------------------------ TESTING ------------------------ */
   /* --------------------------------------------------------- */
 
 
    // Pre: None
    // Post: Tests that our regular expression correctly identifies valid costs
    private void testRegularExpression() {        
        Pattern p = Pattern.compile(costRegEx);
        
        String[] passTests = {"0","1","15","27.0", "27.889", "1,123", "21,123,333,222", "1,123.000", "333,333.1"};
        for(int i=0; i<passTests.length; i++) {
            Matcher m = p.matcher(passTests[i]);
            if(!m.matches()) {
                System.out.println("RegEx Test Fail: Did not match " + passTests[i]);
            }
        }       
        String[] failTests = {"","aaaa","32.00.00","3,33,333", ".321", "333,?333"};
        for(int i=0; i<failTests.length; i++) {
            Matcher m = p.matcher(failTests[i]);
            if(m.matches()) {
                System.out.println("RegEx Test Fail: Matched " + passTests[i]);
            }
        }
    }
    
    
    public static void main(String[] args) throws ParseException{


        // On pass: RegEx can be used to catch user input errors
        CostCalculator regExTester = new CostCalculator("hello", "hello", "hello");
        regExTester.testRegularExpression(); 
        
     
    }
        
  
   
}
