import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TTest {

    @Test
    void amIPositive() {
        boolean errorPos = false;
        boolean errorNeg = false;

        // T should only accept 1 value

        // If T is positive
        T num1 = new T(3,0,0,0);
        boolean shouldBePos1 = num1.amIPositive(num1);

        T num2 = new T(0,4,0,0);
        boolean shouldBePos2 = num2.amIPositive(num2);

        T num3 = new T(0,0,5,0);
        boolean shouldBePos3 = num3.amIPositive(num3);

        T num4 = new T(0,0,0,1);
        boolean shouldBePos4 = num4.amIPositive(num4);

        // They should all be positive! Error if at least 1 of them returns negative
        if (!shouldBePos1 || !shouldBePos2 || !shouldBePos3 || !shouldBePos4)
            errorPos = true;

        System.out.println(errorPos);


        // If T is negative or 0
        T num11 = new T(0,0,0,0);
        boolean shouldBeNeg1 = num11.amIPositive(num11);

        T num12 = new T(0,-4,0,0);
        boolean shouldBeNeg2 = num12.amIPositive(num12);

        T num13 = new T(0,0,-5,0);
        boolean shouldBeNeg3 = num13.amIPositive(num13);

        T num14 = new T(0,0,0,-1);
        boolean shouldBeNeg4 = num14.amIPositive(num14);

        // They should all be negative! Error if at least 1 of them returns positive
        if (shouldBeNeg1 || shouldBeNeg2 || shouldBeNeg3 || shouldBeNeg4)
            errorNeg = true;

        System.out.println(errorNeg);

        System.out.println();
        System.out.print("Should be false, false.");
        System.out.println();
    }
}