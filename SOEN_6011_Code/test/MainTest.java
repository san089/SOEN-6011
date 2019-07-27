import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test //Checking for the case 0 raised to power 0.
    void ZeroRaisedToZero() {

        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            final InputStream original = System.in;
            final FileInputStream inputs = new FileInputStream(new File("test/InputSimulationCase1.txt"));
            System.setIn(inputs);
            Main.main(null);
            System.setIn(original);

            Scanner scanner = new Scanner( new File("test/OutputSimulation.txt") );
            String expected = scanner.useDelimiter("\\A").next();
            scanner.close(); // Put this call in a finally block
            assertEquals(removeWhiteSpaces(expected), removeWhiteSpaces(outContent.toString()));

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Input Simulation File not found.");
        }

    }



    @Test //Checking for the case 0 raised to negative power.
    void ZeroRaisedToNegative() {

        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            final InputStream original = System.in;
            final FileInputStream inputs = new FileInputStream(new File("test/InputSimulationCase2.txt"));
            System.setIn(inputs);
            Main.main(null);
            System.setIn(original);

            Scanner scanner = new Scanner( new File("test/OutputSimulation.txt") );
            String expected = scanner.useDelimiter("\\A").next();
            scanner.close(); // Put this call in a finally block
            assertEquals(removeWhiteSpaces(expected), removeWhiteSpaces(outContent.toString()));

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Input Simulation File not found.");
        }

    }


    @Test
    void negativeBaseRootError() {

        //Test Case 1 : Check for exception if the base is negative and exponent is the of the form y = a/b, where b is even number.
        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            final InputStream original = System.in;
            final FileInputStream inputs = new FileInputStream(new File("test/InputSimulationCase3.txt"));
            System.setIn(inputs);
            Main.main(null);
            System.setIn(original);

            Scanner scanner = new Scanner( new File("test/OutputSimulation.txt") );
            String expected = scanner.useDelimiter("\\A").next();
            scanner.close(); // Put this call in a finally block
            assertEquals(removeWhiteSpaces(expected), removeWhiteSpaces(outContent.toString()));

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Input Simulation File not found.");
        }


    }

    @Test
    void notANumberInput() {

        //Test Case 1 : Check for exception if the base is negative and exponent is the of the form y = a/b, where b is even number.
        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            final InputStream original = System.in;
            final FileInputStream inputs = new FileInputStream(new File("test/InputSimulationCase4.txt"));
            System.setIn(inputs);
            Main.main(null);
            System.setIn(original);

            Scanner scanner = new Scanner( new File("test/OutputNotANumber.txt") );
            String expected = scanner.useDelimiter("\\A").next();
            scanner.close(); // Put this call in a finally block
            assertEquals(removeWhiteSpaces(expected), removeWhiteSpaces(outContent.toString()));

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Input Simulation File not found.");
        }


    }

    /**
     * Utility method for removing whitespaces in the string.
     * @param input Input String
     * @return String after removing white spaces
     */
    String removeWhiteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }
}