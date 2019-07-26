/**
 * @Course : SOEN 6011 - SOFTWARE ENGINEERING PROCESSES
 * @Professor : Pankaj Kamthan
 * @author : Sanchit Kumar
 * @StudentId : 40081187
 *
 *
 *                              LICENSE
 *
 *                      GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *      Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 *          Everyone is permitted to copy and distribute verbatim copies
 *          of this license document, but changing it is not allowed.
 *
 *
 * This project is the implementation of Power function x^y without using any java in-build modules for calculation.
 * This file is the entry point for this project.
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.math.BigDecimal;


/**
 * This class controls the program execution.
 */
public class Main  {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char controllerChar = 'y';
        while(true) {
            try {
                System.out.println("___________________________POWER FUNCTION___________________________");
                System.out.println("====================================================================");
                System.out.print("Enter the base value : ");
                BigDecimal x = sc.nextBigDecimal();
                x = x.stripTrailingZeros();

                System.out.print("Enter the exponent value : ");
                BigDecimal y = sc.nextBigDecimal();
                y = y.stripTrailingZeros();

                if (!isSpecialCasesPassed(x, y)) {
                    throw new ValueNotDefined();
                }

                final long startTime = System.currentTimeMillis();
                pow p = new pow(x, y);
                BigDecimal answer = p.getPowResult();

                System.out.println("\nResult (" + x.toPlainString() + ")^(" + y.toPlainString() + ") : ");
                System.out.println( "With Scientific notation : " + answer.stripTrailingZeros());
                System.out.println("Without Scientific notation : " + answer.stripTrailingZeros().toPlainString());

                final long endTime = System.currentTimeMillis();
                System.out.println("\nExecution time of program is : " + (endTime - startTime) / 1000F);

            } catch (InputMismatchException ime) {
                System.out.println("\nError : Input mismatch exception occured.");
            } catch (ValueNotDefined vnd) {
                System.out.println("\nError : Value not defined for given input.");
                System.out.println("//*******\nDetails : This Error occurs in 3 cases.");
                System.out.println("Case 1 : 0 raised to power 0.\nCase 2 : 0 raised to power of negative number.");
                System.out.println("Case 3 : If x^y, when x is negative and y = a/b and b is even.\n//*******");
            }
            catch (Exception e){
                System.out.println("\nError : Some exception occured.");
            }

            System.out.print("\nPress n to exit, any other key to continue : ");
            controllerChar = sc.next().charAt(0);
            if(controllerChar == 'n' || controllerChar == 'N')
            {
                System.out.println("Thank you!!");
                break;
            }
            System.out.println("\n====================================================================");

        }//end of while loop
    }//end of main method


    /**
     * This method checks for special cases.
     * There are 3 special cases to handle for which the value of power function is not defined.
     * Case 1 : 0 raised to power 0.
     * Case 2 : 0 raised to power of negative number.
     * Case 3 : If x^y, when x is negative and y = a/b and b is even.
     * @param x
     * @param y
     * @return
     */
    public static boolean isSpecialCasesPassed(BigDecimal x, BigDecimal y)
    {
        if(x.equals(BigDecimal.ZERO) & (y.signum() <= 0))
            return false;
        if(x.signum() < 0)
        {
            if(y.stripTrailingZeros().scale() > 0)
            {
                FractionHandling f = new FractionHandling(y.stripTrailingZeros().doubleValue());
                if(f.isFractionDenominatorEven())
                    return false;
            }
        }
        return true;
    }

}//end of Main class

class ValueNotDefined extends Exception{

    public ValueNotDefined(){
        super("Value not defined. Check your input.");
    }
}


/**
 * This class is used to handle the special case number 3. See above for more details.
 * The exponent if decimal can be represented as exponent = a/b. If b is even number then the value of power function is undefined.
 * The method isFractionDenominatorEven() return true if b is even, false otherwise.
 */
class FractionHandling
{
    double Exponent;
    private int numerator, denominator;
    private long lowestNumerator, lowestDenominator;

    public FractionHandling(double exponent) {
        this.Exponent = exponent;
    }

    public long gcm(long a, long b) {
        return b == 0 ? a : gcm(b, a % b); // Not bad for one line of code :)
    }

    public void asFraction(long a, long b) {
        long gcm = gcm(a, b);
        this.lowestNumerator = (a / gcm);
        this.lowestNumerator = (b / gcm);

    }

    /**
     * @return true if the denominator is even, false otherwise.
     */
    public boolean isFractionDenominatorEven()
    {
        decimalToFraction(this.Exponent);
        asFraction(this.numerator, this.denominator);
        if(this.lowestDenominator %2 == 0)
            return true;
        else
            return false;
    }

    /**
     * This method converts decimal to fraction.
     * @param decimal Input decimal number.
     */
    public void decimalToFraction(double decimal) {
        String stringNumber = String.valueOf(decimal);
        int numberDigitsDecimals = stringNumber.length() - 1 - stringNumber.indexOf('.');
        int denominator = 1;
        for (int i = 0; i < numberDigitsDecimals; i++) {
            decimal *= 10;
            denominator *= 10;
        }

        int numerator = (int) Math.round(decimal);
        int greatestCommonFactor = greatestCommonFactor(numerator, denominator);
        this.numerator = numerator / greatestCommonFactor;
        this.denominator = denominator / greatestCommonFactor;
    }

    /**
     * @param num Number1
     * @param denom Number2
     * @return The Greatest common factor of the two number input.
     */
    public int greatestCommonFactor(int num, int denom) {
        if (denom == 0) {
            return num;
        }
        return greatestCommonFactor(denom, num % denom);
    }

}