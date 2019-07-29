import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;


/**
 * NOTE : Input Error Handling is performed in the Main class. Any unexpected input for class pow will fail the test case.
 */
class powTest {

    private pow pDummyObjTest = new pow(BigDecimal.ONE, BigDecimal.ONE);

    @Test
    void getSignToMultiply() {
        BigDecimal x,y;
        BigDecimal expected;
        BigDecimal actual;


        //Test Case 1: If x is negative and y is even
        x = new BigDecimal("-100");
        y = new BigDecimal("12");
        pDummyObjTest = new pow(x, y);
        expected = BigDecimal.ONE.negate();
        actual = pDummyObjTest.getSignToMultiply(x, y);
        assertEquals(expected, actual);

        //Test Case 2: If x is negative and y is odd
        x = new BigDecimal("-100");
        y = new BigDecimal("13");
        pDummyObjTest = new pow(x, y);
        expected = BigDecimal.ONE.negate();
        actual = pDummyObjTest.getSignToMultiply(x, y);
        assertEquals(expected, actual);

        //Test Case 3: If x is positive and y is even
        x = new BigDecimal("100");
        y = new BigDecimal("12");
        pDummyObjTest = new pow(x, y);
        expected = BigDecimal.ONE;
        actual = pDummyObjTest.getSignToMultiply(x, y);
        assertEquals(expected, actual);


        //Test Case 4: If x is positive and y is odd
        x = new BigDecimal("100");
        y = new BigDecimal("13");
        pDummyObjTest = new pow(x, y);
        expected = BigDecimal.ONE;
        actual = pDummyObjTest.getSignToMultiply(x, y);
        assertEquals(expected, actual);
    }

    @Test
    void getPowResult() {
        // NOTE : The Error checking for input is done in the Main class.
        //This class does no error checking. So, incorrect input will fail the test case.
        pow pObjTest;
        BigDecimal x,y;
        String expected;
        String actual;

        //Test Case 1 : When x,y is positive Integer.
        x = new BigDecimal("312");
        y = new BigDecimal("3");
        pObjTest = new pow(x, y);
        expected = "3.0371328E7";
        actual = pObjTest.getPowResult();
        assertEquals(expected, actual);

        //Test Case 2 : When x,y is negative Integer.
        x = new BigDecimal("-32");
        y = new BigDecimal("-3");
        pObjTest = new pow(x, y);
        expected = "-3.05175781E-5";
        actual = pObjTest.getPowResult();
        assertEquals(expected, actual);


        //Test Case 3 : When x,y is positive fractional numbers.
        x = new BigDecimal("312.221");
        y = new BigDecimal("3.2412");
        pObjTest = new pow(x, y);
        expected = "1.21632769E8";
        actual = pObjTest.getPowResult();
        assertEquals(expected, actual);

    }

    @Test
    @Timeout(value = 3)
    void getPowResultTimeTest()
    {
        // Test Case 1 : When x,y and large numbers. Base 10 digits.
        BigDecimal x = new BigDecimal("12121212121212");
        BigDecimal y = new BigDecimal("12");
        pow pObjTest = new pow(x, y, 200);
        String expected = "1.00589493E157";
        String actual = pObjTest.getPowResult();
        assertEquals(expected, actual);
    }

    @Test
    void getExponent() {
        BigDecimal x;
        BigDecimal expected;
        BigDecimal actual;
        //This method is to get value of e^x

        //Test case 1: When x is positive integer.
        x = new BigDecimal("34");
        expected = new BigDecimal("583461742527454.8814029027346103910190036592389411081057829421204316676742119505811471038583648840578915946798961083");
        actual = pDummyObjTest.getExponent(x);
        assertEquals(expected, actual);


        //Test case 2: When x is positive fraction.
        x = new BigDecimal("3.34");
        expected = new BigDecimal("28.2191267054086129265611051165996075258360161913169324678356545327177957994504014839685819866553057841");
        actual = pDummyObjTest.getExponent(x);
        assertEquals(expected, actual);

        //Test case 3: When x is negative integer.
        x = new BigDecimal("-3");
        expected = new BigDecimal("0.0497870683678639429793424156500617766316995921884232155676277276060606677301995501540542442366333447");
        actual = pDummyObjTest.getExponent(x);
        assertEquals(expected, actual);

        //Test case 4: When x is negative fraction.
        x = new BigDecimal("-2.5");
        expected = new BigDecimal("0.082084998623898795169528674467159807837804121015436648845758410515224756880410971309751571521236466");
        actual = pDummyObjTest.getExponent(x);
        assertEquals(expected, actual);
    }

    @Test()
    @Timeout(value = 3)
    void getLn() {
        BigDecimal val;
        BigDecimal expected;
        BigDecimal actual;

        //Test Case 1: If val is positive number
        val = new BigDecimal("2123");
        expected = new BigDecimal("7.6605854617");
        actual = pDummyObjTest.getLn(val).setScale(10 , RoundingMode.HALF_UP);
        assertEquals(expected, actual);

        //Test Case 2: If val is a very big number. Check for timeout.
        val = new BigDecimal("1212121212121212121212121212121212121212");
        expected = new BigDecimal("89.9931905194");
        actual = pDummyObjTest.getLn(val).setScale(10 , RoundingMode.HALF_UP);
        assertEquals(expected, actual);

        //Test Case 3: If the val is a fractional number.
        val = new BigDecimal("0.0232");
        expected = new BigDecimal("-3.7636030003");
        actual = pDummyObjTest.getLn(val).setScale(10 , RoundingMode.HALF_UP);
        assertEquals(expected, actual);
    }

    @Test
    void getSmallerValue() {
        BigDecimal param1;
        BigDecimal param2;
        BigDecimal expected;
        BigDecimal actual;

        //Test 1: The input is very large.
        param1 = new BigDecimal("1236826381683681263868162836");
        param2 = new BigDecimal("28");
        expected = new BigDecimal("0.1236826381683681263868162836");
        actual = pDummyObjTest.getSmallerValue(param1, param2);
        assertEquals(expected, actual);


        //Test 2: The input is floating number with no significant digits.
        param1 = new BigDecimal("0.01212");
        param2 = BigDecimal.ZERO;
        expected = param1;
        actual = pDummyObjTest.getSmallerValue(param1, param2);
        assertEquals(expected, actual);

    }

    @Test
    void numSignificantDigits() {
        BigDecimal param1;
        BigDecimal expected;
        BigDecimal actual;

        // Test Case 1 : When there are more than 0 significant digits with 0 as well.
        param1 = new BigDecimal("10000.000000");
        expected = new BigDecimal("5");
        actual = pDummyObjTest.numSignificantDigits(param1);
        assertEquals(expected, actual);

        //Test Case 2 : When there are 0 significant digits.
        param1 = new BigDecimal("0.00123000");
        expected = BigDecimal.ZERO;
        actual = pDummyObjTest.numSignificantDigits(param1);
        assertEquals(expected, actual);


        //Test Case 3 : When the number has leading zeros
        param1 = new BigDecimal("000010000.123000");
        expected = new BigDecimal("5");
        actual = pDummyObjTest.numSignificantDigits(param1);
        assertEquals(expected, actual);
    }

    @Test
    void getXValForLog() {
        //This method is to test for the value of x, using formulae y = (1+x)/(1-x).
        BigDecimal param1;
        BigDecimal expected;
        BigDecimal actual;

        //Test Case 1 : When the input is positive
        param1 = new BigDecimal("0.1");
        expected = new BigDecimal("-0.8181818182");
        actual = pDummyObjTest.getXValForLog(param1).setScale(10, RoundingMode.HALF_UP);
        assertEquals(expected, actual);

        //Test Case 1 : When the input is negative
        param1 = new BigDecimal("-0.1");
        expected = new BigDecimal("-1.2222222222");
        actual = pDummyObjTest.getXValForLog(param1).setScale(10, RoundingMode.HALF_UP);
        assertEquals(expected, actual);
    }

    @Test
    void getPower() {
        //NOTE : This method is not for negative exponents.
        BigDecimal param1;
        BigDecimal param2;
        BigDecimal expected;
        BigDecimal actual;

        //Test Case 1 : Both numbers as integers.
        param1 = BigDecimal.TEN;
        param2 = new BigDecimal("2");
        expected = BigDecimal.TEN.pow(2);
        actual = pDummyObjTest.getPower(param1, param2);
        assertEquals(expected, actual);


        //Test Case 2 : base as fraction and power as integer.
        param1 = new BigDecimal("2.42");
        param2 = new BigDecimal("2");
        expected = new BigDecimal("5.8564");
        actual = pDummyObjTest.getPower(param1, param2);
        assertEquals(expected, actual);

    }
}