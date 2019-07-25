import java.math.BigDecimal;
import java.math.RoundingMode;

public class pow {

    /*----------Class variable declaration-----------------------*/
    BigDecimal x;
    BigDecimal y;
    BigDecimal log10 = new BigDecimal("2.302585092994045684018");
    BigDecimal signToMultiply;
    int maxIterations = 10000;
    int scale = 200;
    int finalScale; //This is not fixed and changes as per input.
    Boolean isEven = false;


    /**
     * Constructor for pow class. This method instantiate the class variables.
     * @param x The base of the power function
     * @param y The exponent of power function
     */
    public pow(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
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
        if((x.scale() <= 0) & (y.scale() <= 0)) {
            return 0;
        }
        else
        {
            if(2*(x.scale() + y.scale()) < 8)
            {
                return 8;
            }
            else
                return 2*(x.scale() + y.scale()) ;
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
        if( (x.compareTo(BigDecimal.ZERO) == -1) & (!(isEven)) )
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
        System.out.println("Log value calculated is : " + logValue);
        System.out.println("Value of Exponent of E : " + exponentOfE);

        answer = getExponent(exponentOfE).multiply(signToMultiply).setScale(this.finalScale , RoundingMode.HALF_UP);
        return answer;
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
        BigDecimal tmp = new BigDecimal(String.format("%.8f", result));
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
        return temp.divide( getPower(BigDecimal.TEN, digit_count) , this.scale, RoundingMode.HALF_UP);
    }

    /**
     * This method return the number of significant digits in the number. E.g 123, the method will return 3.
     * @param val The input value.
     * @return The number of significant digits in the value.
     */
    public BigDecimal numSignificantDigits(BigDecimal val)
    {
        val = val.stripTrailingZeros();
        return new BigDecimal(val.precision() - val.scale());
    }

    /**
     * Formulae used is x = ( y - 1 ) / ( y + 1 )
     * @param y Input is the value whose log is to be calculated
     * @return
     */
    public BigDecimal getXValForLog(BigDecimal y)
    {
        BigDecimal return_val = y.subtract(BigDecimal.ONE).divide(y.add(BigDecimal.ONE), this.scale, RoundingMode.HALF_UP);
        return return_val;
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
        for(int i=1;i<power.intValueExact();i++)
        {
            result = result.multiply(number);
        }
        return result;
    }
}