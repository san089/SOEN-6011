/*
 * This File contains pow class which does calculate the power function value.
 *
 * Let f(x, y) = x^y. The function can be written as e^(y*ln(x))
 * For calculation we need a method for calculating e^(some power) and also ln(x).
 * The calculation is carried out in 2.
 *
 * Part 1 : e^(some power x)
 * Formulae : e^x = 1 + x/1! + x^2/2! + x^3/3! ....................
 *
 * For ln(x) we need part 2.
 * Part 2 : ln(x)
 * Formulae : ln(1+x/1-x) = 2*( x + x^3/3 + x^5/5 + x^7/7 ..............)
 *
 * Part 2 calculation is carried out by method - getLn.
 * Part 1 calculation is carried out by method - getExponent.
 * Method getPowResult controls all the calculation and provides the final result.
 *
 *
 * Other utility methods are used to help the calculations.
 *
 *
 * For more details refer -
 * Taylor series expansion : https://www.efunda.com/math/taylor_series/exponential.cfm
 * Natural log Series : http://hyperphysics.phy-astr.gsu.edu/hbase/Math/lnseries.html
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

class pow {

    /*----------Class variable declaration-----------------------*/
    private BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal log10 = new BigDecimal("2.302585092994045684018");
    private final BigDecimal signToMultiply;
    private int maxIterations = 10000;
    private final int scale = 100; //Default scale to be used.
    private final int finalScale; //This is not fixed and changes as per input.


    /**
     * Constructor for pow class. This method instantiate the class variables.
     * @param base The base of the power function
     * @param exponent The exponent of power function
     */
    public pow(BigDecimal base, BigDecimal exponent) {
        this.x = base;
        this.y = exponent;
        this.signToMultiply = getSignToMultiply(this.x, this.y);
        this.finalScale = setFinalScale(this.x, this.y);
    }

    /**
     * Overloaded constructor. This is useful if user want to optimize results by setting number of iterations.
     * NOTE : It is always suggested to use a higher value for higher accuracy.
     * @param base The base of the power function
     * @param exponent The exponent of power function
     * @param maxIters Number of iterations user want to run to converge result. This is used to reduce the output time but may lack accuracy.
     */
    public pow(BigDecimal base, BigDecimal exponent, int maxIters) {
        this.x = base;
        this.y = exponent;
        this.maxIterations = maxIters;
        this.signToMultiply = getSignToMultiply(this.x, this.y);
        this.finalScale = setFinalScale(this.x, this.y);
    }



    /**
     * This method takes base and exponent and adjust the scale for final answer.
     * @param x The base value x
     * @param y The exponent value y
     * @return The number of scale to be maintained for the final answer
     */
    public int setFinalScale(BigDecimal x, BigDecimal y)
    {
        x = x.stripTrailingZeros();
        y = y.stripTrailingZeros();
        if((x.scale() <= 0) & (y.scale() <= 0)) {
            if (y.signum() < 0 ) {
                return numSignificantDigits(x).intValueExact() * y.negate().intValueExact(); }
            else {
                return 0; }
        }
        else
        {
            if((2 * (x.scale() + y.scale())) < 8)
            {
                return Math.max(x.scale() * y.setScale(0, RoundingMode.HALF_UP).scale(), 8);
            }
            else{
                return 100; }
        }
    }



    /**
     * This method adjusts the + or - sign based on the base and exponent
     * @param x Base of the power function
     * @return the value as -1 or 1, depending on the base sign and power.
     */
    public BigDecimal getSignToMultiply(BigDecimal x, BigDecimal y)
    {
        BigDecimal signToReturn = BigDecimal.ONE;
        boolean ifIntEven = y.setScale(0, RoundingMode.HALF_UP).toBigIntegerExact().mod(new BigInteger("2")).equals(BigInteger.ZERO);
        if( (x.signum() < 0) & (y.stripTrailingZeros().scale() <= 0) & (!ifIntEven))
        {
            this.x = x.negate();
            return signToReturn.negate();
        }
        return signToReturn;
    }


    /**
     * This method controls the calculation and does return the final result for x^y.
     * @return Return the result of x raised to power y.
     */
    public BigDecimal getPowResult()
    {
        BigDecimal answer;
        BigDecimal logValue = getLn(this.x);
        BigDecimal exponentOfE = this.y.multiply(logValue);
        //System.out.println("Log value calculated is : " + logValue);
        //System.out.println("Value of Exponent of E : " + exponentOfE);

        answer = getExponent(exponentOfE).multiply(signToMultiply).setScale(this.finalScale , RoundingMode.HALF_UP);
        return answer.stripTrailingZeros();
    }


    /**
     * This method calculate result of e^ln(n) which is the final result for x^y.
     * @param power_val The value of ln(n).
     * @return The final value of e^ln(n)
     */
    public BigDecimal getExponent(BigDecimal power_val)
    {
        BigDecimal result = BigDecimal.ONE.add(power_val);
        BigDecimal i = new BigDecimal("2");

        BigDecimal xRaisedToPowerN = power_val;
        BigDecimal factVal = BigDecimal.ONE;
        for( ;i.intValueExact() < this.maxIterations; )
        {
            xRaisedToPowerN = xRaisedToPowerN.multiply(power_val).setScale(this.scale, RoundingMode.HALF_UP);
            factVal = factVal.multiply(i).setScale(this.scale, RoundingMode.HALF_UP);

            result = result.add(xRaisedToPowerN.divide(factVal, this.scale, RoundingMode.HALF_UP));
            i = i.add(BigDecimal.ONE);
        }
        BigDecimal tmp = result.setScale(this.scale, RoundingMode.HALF_UP);
        return  tmp.stripTrailingZeros() ;
    }



    /**
     * Using the formulae ln(1+x)/(1-x) = 2(x  +  x**3/3  +   x**5/5 + ........) for calculation of log
     * @param val The input to the log function
     * @return The log value of input with base as e.
     */
    public BigDecimal getLn(BigDecimal val)
    {
        BigDecimal digit_count = numSignificantDigits(val);
        BigDecimal additional_value = digit_count.multiply(this.log10); // E.g -> log 123 = (3*ln10 + ln 0.123) To optimize the calculations for larger numbers.
        BigDecimal temp_val = getSmallerValue(val, digit_count);

        BigDecimal value_of_x = getXValForLog(temp_val); //This the value of x in the formulae y = (1+x)/(1-x) for log calculation.
        BigDecimal result = value_of_x;

        BigDecimal xPower = value_of_x;
        BigDecimal i = new BigDecimal("3");
        for(; i.intValueExact() <= this.maxIterations; )
        {
               xPower = xPower.multiply(value_of_x).multiply(value_of_x).setScale(500, RoundingMode.HALF_UP);
               result = result.add(xPower.divide(i, 500, RoundingMode.HALF_UP));
               i = i.add(new BigDecimal("2"));
        }
        return ((new BigDecimal("2")).multiply(result)).add(additional_value).stripTrailingZeros();

    }


    /**
     * This method gives the smaller value for which log needs to be calculated.
     * For E.g - log 123 = (3*ln10 + ln 0.123). This method will return 0.123.
     * @param temp The value for
     * @param digit_count The number of significant digits in the number.
     * @return Return the smalled value for log calculation based on the significant digits.
     */
    public BigDecimal getSmallerValue(BigDecimal temp, BigDecimal digit_count)
    {
        return temp.divide( getPower(BigDecimal.TEN, digit_count) , this.scale, RoundingMode.HALF_UP).stripTrailingZeros();
    }

    /**
     * This method return the number of significant digits in the number. E.g 123, the method will return 3.
     * @param val The input value.
     * @return The number of significant digits in the value.
     */
    public BigDecimal numSignificantDigits(BigDecimal val)
    {
        val = val.stripTrailingZeros();
        BigDecimal significantDigits = new BigDecimal(val.precision() - val.scale());
        if(significantDigits.signum() <0 ) {
            return BigDecimal.ZERO;
        }
        else{
            return significantDigits; }
    }

    /**
     * Formulae used is x = ( y - 1 ) / ( y + 1 )
     * @param y Input is the value whose log is to be calculated
     * @return This method returns the value of x based on above formulae.
     */
    public BigDecimal getXValForLog(BigDecimal y)
    {
        return y.subtract(BigDecimal.ONE).divide(y.add(BigDecimal.ONE), this.scale, RoundingMode.HALF_UP);
    }


    /**
     * This method raises a number to the power of input power. number^power. This function is Used in rare cases.
     * @param number The input number such that number^power.
     * @param power This input power such that number^power.
     * @return The final answer of number^power.
     */
    public BigDecimal getPower(BigDecimal number, BigDecimal power)
    {
        if(power.intValueExact() == 0)
            return BigDecimal.ONE;
        BigDecimal result = number;
        for(int i = 1 ; i < power.intValueExact() ; i++)
        {
            result = result.multiply(number);
        }
        return result;
    }
}
