import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;


/**
 * NOTE : Input Error checking is performed in the Main class. Any unexpected input for class powTest will fail the test case.
 */
class powTest {

    pow pDummyObjTest = new pow(BigDecimal.ONE, BigDecimal.ONE);

    @Test
    void setFinalScale() {
        pow pObjTest;
        BigDecimal x,y;
        int expected;
        int actual;


        //Test Case 1: If both x and y are integers
        x = new BigDecimal("1000");
        y = new BigDecimal("3");
        pObjTest = new pow(x, y);
        expected = 0;
        actual = pObjTest.setFinalScale(x, y);
        assertEquals(expected, actual);


        //Test Case 2: If x is fractional and y is integer.
        x = new BigDecimal("123.523");
        y = new BigDecimal("3");
        pObjTest = new pow(x, y);
        expected = 8;
        actual = pObjTest.setFinalScale(x, y);
        assertEquals(expected, actual);

        //Test Case 3: If x is integer and y is fractional.
        x = new BigDecimal("123");
        y = new BigDecimal("3.523");
        pObjTest = new pow(x, y);
        expected = 8;
        actual = pObjTest.setFinalScale(x, y);
        assertEquals(expected, actual);

        //Test Case 4: If both x and y are fractional.
        x = new BigDecimal("123.523");
        y = new BigDecimal("3.523");
        pObjTest = new pow(x, y);
        expected = 100;
        actual = pObjTest.setFinalScale(x, y);
        assertEquals(expected, actual);

        //Test Case 5: If both are integer but with trailing zeros.
        x = new BigDecimal("123.000");
        y = new BigDecimal("3.0000");
        pObjTest = new pow(x, y);
        expected = 0;
        actual = pObjTest.setFinalScale(x, y);
        assertEquals(expected, actual);

        //Test Case 6: If both are fractional with large fractional values.
        x = new BigDecimal("123.2342343363464756868242353645768465363");
        y = new BigDecimal("34.53236478632453745866964633");
        pObjTest = new pow(x, y);
        expected = 100;
        actual = pObjTest.setFinalScale(x, y);
        assertEquals(expected, actual);
    }

    @Test
    void getSignToMultiply() {
        BigDecimal x,y;
        int expected;
        int actual;


        //Test Case 1: If x is negative and y is even


        //Test Case 1: If x is negative and y is odd


        //Test Case 1: If x is positive and y is even


        //Test Case 1: If x is positive and y is odd
    }

    @Test
    void getPowResult() {
        // NOTE : The Error checking for input is done in the Main class.
        //This class does no error checking. So, incorrect input will fail the test case.
        pow pObjTest;
        BigDecimal x,y;
        BigDecimal expected;
        BigDecimal actual;

        //Test Case 1 : When x,y is positive Integer.
        x = new BigDecimal("312");
        y = new BigDecimal("3");
        pObjTest = new pow(x, y);
        expected = new BigDecimal("30371328");
        actual = pObjTest.getPowResult();
        assertEquals(expected, actual);

        //Test Case 2 : When x,y is negative Integer.
        x = new BigDecimal("-32");
        y = new BigDecimal("-3");
        pObjTest = new pow(x, y);
        expected = new BigDecimal("-0.000031");
        actual = pObjTest.getPowResult();
        assertEquals(expected, actual);


        //Test Case 3 : When x,y is positive fractional numbers.
        x = new BigDecimal("312.221");
        y = new BigDecimal("3.2412");
        pObjTest = new pow(x, y);
        expected = new BigDecimal("121632768.5740190358955239148827278024969182021483763874856958039298772445638875460567957221728830584154337975");
        actual = pObjTest.getPowResult();
        assertEquals(expected, actual);

    }
    @Test
    @Timeout(value = 3)
    void getPowResultTimeTest()
    {
        // Test Case 1 : When x,y and large numbers. Base 10 digits.
        BigDecimal x = new BigDecimal("1212121212");
        BigDecimal y = new BigDecimal("1212");
        pow pObjTest = new pow(x, y, 100);
        BigDecimal expected = new BigDecimal("10594855269910269859662775030153844150266815456395262367695363090394925790910514850786900626730275279887807326250135454640166897436415180484163554956346218505248164107431943564799724435369631009557620057907689556561192430106470515861885859481908440852024026499327883582772507407109");
        BigDecimal actual = pObjTest.getPowResult();
        assertEquals(expected, actual);
    }

    @Test
    void getExponent() {
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

        //Test Case 1: If val is a very big number. Check for timeout.
        val = new BigDecimal("1212121212121212121212121212121212121212");
        expected = new BigDecimal("89.9931905194");
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
        param1 = new BigDecimal("0.1212");
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

        // Tese Case 1 : When there are more than 0 significant digits with 0 as well.
        param1 = new BigDecimal("10000.000000");
        expected = new BigDecimal("5");
        actual = pDummyObjTest.numSignificantDigits(param1);
        assertEquals(expected, actual);

        //Test Case 2 : When there are 0 significant digits.
        param1 = new BigDecimal("0.123000");
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