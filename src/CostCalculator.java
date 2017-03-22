import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;


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
    public CostCalculator(String basePrice, String numLabor, String materials) throws ParseException, NumberFormatException {
       
       this.basePrice = applyFlatMarkup(parseString(basePrice));
       this.numLabor = Integer.parseInt(numLabor);
       this.materials = materials.trim();
       String[] matArray = new String[0];
       if(materials.length() != 0) {
           matArray = materials.split(",");
       }
       double markupToAdd = calculateAdditionalMarkup(this.numLabor, matArray);
       BigDecimal markup = new BigDecimal(markupToAdd);
       BigDecimal result = markup.multiply(this.basePrice);
       result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
       System.out.println("RESULT: " + result);
       
    }

    /* --------------------------------------------------------- */
    /* ------------------------ METHODS ------------------------ */
    /* --------------------------------------------------------- */
   
    public BigDecimal getBasePrice() {
        return this.basePrice;
    }
    
    /* --------------------------------------------------------- */
    /* -------------------- HELPER METHODS --------------------- */
    /* --------------------------------------------------------- */
  
    // Pre:
    // Post:
    private double calculateAdditionalMarkup(int numLabor, String[] matArray) {
        double markup = numLabor * Constants.LABOR_MARKUP;
        System.out.println("MARKUP AFTER NUMLABOR: " + markup);
        for(int i=0; i<matArray.length; i++) {
            if(matArray[i].equals("food")) {
                System.out.println("EQUALS FOOD");
                markup += Constants.FOOD_MARKUP;
                System.out.println("MARKUP");
            }
            if(matArray[i].equals("drugs")) {
                markup += Constants.PHARM_MARKUP;
            }
            if(matArray[i].equals("electronics")) {
                markup += Constants.ELEC_MARKUP;
            }
        }
        System.out.println("MARKUP: " + markup);
        return ((markup/100) + 1);
    }
    
    // Pre:
    // Post:
    private BigDecimal applyFlatMarkup(BigDecimal b) {
        return b.multiply(Constants.FLAT_MARKUP, MathContext.DECIMAL64);
    }
  
    // Pre: 
    // Post:
    private BigDecimal parseString(String userInput) throws ParseException {
        
        String theAmount = userInput.replaceAll("^\\$","");

        // Check that it is a valid number
        Pattern p = Pattern.compile(this.costRegEx);
        Matcher m = p.matcher(theAmount);
        if(!m.matches()) {
            throw new IllegalArgumentException("Bad Input: " + userInput);
        } else {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.CANADA);
            BigDecimal b = new BigDecimal(format.parse(theAmount.replaceAll("^\\$","")).toString());
            return b;
        }

    }
  
   
    /* --------------------------------------------------------- */
    /* ------------------------ TESTING ------------------------ */
    /* --------------------------------------------------------- */
 
 
    // Pre:
    // Post:
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
        BigDecimal b = null;
        Exception ex = null;
        try {
            b = parseString(amount);
        } catch (Exception e) {
            ex = e;
        }
        if(expectedResult.equals("Pass")) {
            if(ex != null) {
                System.out.println("*******parseStringMethod Test Fail. Input: " + amount + "; Exception: " + ex.getMessage());
            } else {
                System.out.println("Test Pass. Input: " + amount + "; Result: " + b);
            }
        } else { // b should be null
            if( b != null ) {
                System.out.println("*******parseStringMethod Test Fail. Input: " + amount + "; Result: " + b);
            } else {
                System.out.println("Test Pass. Input: " + amount + "; Exception: " + ex.getMessage());
            }
        }
    }
    
    

    /* -------------------------- MAIN ------------------------- */
   
    public static void main(String[] args) throws ParseException {

        System.out.println("Starting Tests ...");
        
        CostCalculator tester = new CostCalculator("1299.99", "hello", "hello");
        
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
        
        // Testing Apply Markup
        tester.testApplyFlatMarkup( new BigDecimal(1299.99), 1364.9895);
        tester.testApplyFlatMarkup( new BigDecimal(0), 0);
        tester.testApplyFlatMarkup( new BigDecimal(1.0004), 1.0504);
    }
        
  
   
}
