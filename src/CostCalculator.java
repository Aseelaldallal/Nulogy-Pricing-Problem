import java.text.*;
import java.util.*;

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
    
   
    

   
   
  
   
}
