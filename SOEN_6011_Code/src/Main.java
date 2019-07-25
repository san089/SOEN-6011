import java.util.InputMismatchException;
import java.util.Scanner;
import java.math.BigDecimal;

/**
 *
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("\nEnter the base value : ");
            BigDecimal x = sc.nextBigDecimal();
            x = x.stripTrailingZeros();

            System.out.print("\nEnter the exponent value : ");
            BigDecimal y = sc.nextBigDecimal();
            y = y.stripTrailingZeros();

            final long startTime = System.currentTimeMillis();
            pow p = new pow(x,y);
            BigDecimal answer = p.getPowResult();

            System.out.print("Final Answer is  : " + answer.toPlainString());

            final long endTime = System.currentTimeMillis();
            System.out.println("\nExecution time of program is : " + (endTime - startTime)/ 1000F);

        }
        catch(InputMismatchException ime) {
            System.out.println("Input mismatch exception occured.");
        }

    }

}
