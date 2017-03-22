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
   

    /* --------------------------------------------------------- */
    /* -------------------- HELPER METHODS --------------------- */
    /* --------------------------------------------------------- */
  
    private Number parseString(String userInput) throws ParseException {
        
        String theAmount = userInput.replaceAll("^\\$","");

        // Check that it is a valid number
        Pattern p = Pattern.compile(this.costRegEx);
        Matcher m = p.matcher(theAmount);
        if(!m.matches()) {
            throw new IllegalArgumentException("Bad Input: " + userInput);
        } else {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.CANADA);
            Number along = format.parse(theAmount.replaceAll("^\\$",""));
            return along;
        }

    }
  
   
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
                System.out.println("*******RegEx Test Fail: Did not match " + passTests[i]);
            } else {
                System.out.println("Test Pass. Input: " + passTests[i]);
            }
        }       
        String[] failTests = {"","aaaa","32.00.00","3,33,333", ".321", "333,?333"};
        for(int i=0; i<failTests.length; i++) {
            Matcher m = p.matcher(failTests[i]);
            if(m.matches()) {
                System.out.println("*******RegEx Test Fail: Matched " + passTests[i]);
            } else {
                System.out.println("Test Pass. Input: " + failTests[i]);
            }
        }
    }
    
    
    // Pre: amount is not null. expectedResult is one of "Pass", "Fail"
    // Post: Calls the method parsestring with amount as a parameter. Compares the returned
    //       result to expectedResult. If they match, the test is passed. Otherwise, fail. 
    private void testParseStringMethod(String amount, String expectedResult) {
        Number b = null;
        Exception ex = null;
        try {
            b = parseString(amount);
        } catch (Exception e) {
            ex = e;
        }
        if(expectedResult.equals("Pass")) {
            if(ex != null) {
                System.out.println("*******Test Fail. Input: " + amount + "; Exception: " + ex.getMessage());
            } else {
                System.out.println("Test Pass. Input: " + amount + "; Result: " + b);
            }
        } else { // b should be null
            if( b != null ) {
                System.out.println("*******Test Fail. Input: " + amount + "; Result: " + b);
            } else {
                System.out.println("Test Pass. Input: " + amount + "; Exception: " + ex.getMessage());
            }
        }
    }
    
    

    /* -------------------------- MAIN ------------------------- */
   
    public static void main(String[] args) {

        System.out.println("Starting Tests ...");
        
        CostCalculator tester = new CostCalculator("hello", "hello", "hello");
        
        // On all pass: RegEx can be used to catch bad initial base price input       
        tester.testRegularExpression(); 
        
        // Test Parse String Method
        tester.testParseStringMethod("", "Fail");
        tester.testParseStringMethod("$", "Fail");
        tester.testParseStringMethod("aaa", "Fail");
        tester.testParseStringMethod("?agfasg?", "Fail");           
        tester.testParseStringMethod("$100,00.00.00", "Fail");
        tester.testParseStringMethod("0", "Fail"); // Should we let this pass or fail ? Decide later
        tester.testParseStringMethod("033", "Fail"); // Should we let this pass or fail ? Decide later
        tester.testParseStringMethod("$100", "Pass");        
        tester.testParseStringMethod("$1", "Pass");     
        tester.testParseStringMethod("$1,323.234", "Pass");  
        tester.testParseStringMethod("1,333,333", "Pass");  
        tester.testParseStringMethod("1,333,333,333,333,333", "Pass");  
        tester.testParseStringMethod("$0.030", "Pass");  
    }
        
  
   
}
