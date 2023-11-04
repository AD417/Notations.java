package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogarithmTest {
    public static Notation notation = new LogarithmNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Logarithm", LogarithmNotation.name);
    }

    @Test
    public void testInfinity() {
        assertEquals("Infinite", notation.format(BigDouble.POSITIVE_INFINITY));
        assertEquals("-Infinite", notation.format(BigDouble.NEGATIVE_INFINITY));
    }

    @Test
    public void testZeroConversion() {
        assertEquals("0", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals("0.0", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals("0.00", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals("0.0000000000", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals("0", notation.format(negativeZero, 0, 0));
        assertEquals("0.0", notation.format(negativeZero, 1, 1));
        assertEquals("0.00", notation.format(negativeZero, 2, 2));
        assertEquals("0.0000000000", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        assertEquals("1.00", notation.format(new BigDouble(1), 2, 2));
        assertEquals("2.00", notation.format(new BigDouble(2), 2, 2));
        assertEquals("3.00", notation.format(new BigDouble(3), 2, 2));
        assertEquals("4.00", notation.format(new BigDouble(4), 2, 2));
        assertEquals("5.00", notation.format(new BigDouble(5), 2, 2));
        assertEquals("6.00", notation.format(new BigDouble(6), 2, 2));
        assertEquals("7.00", notation.format(new BigDouble(7), 2, 2));
        assertEquals("8.00", notation.format(new BigDouble(8), 2, 2));
        assertEquals("9.00", notation.format(new BigDouble(9), 2, 2));
        assertEquals("10.00", notation.format(new BigDouble(10), 2, 2));
        assertEquals("100.00", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals("0", notation.format(roundsDown, 0, 0));

        assertEquals("1", notation.format(roundsUp, 0, 0));
        assertEquals("0.7", notation.format(roundsUp, 0, 1));
        assertEquals("0.67", notation.format(roundsUp, 0, 2));

        assertEquals("1", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals("e3.0", notation.format(thousand, 0, 0, 0));
        assertEquals("e3.00", notation.format(thousand, 2, 0, 0));
        assertEquals("e3.00", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        BigDouble small = new BigDouble("1e-4000");
        while (small.lte(1e-10)) {
            assertEquals("0.00", notation.format(small, 2, 2, 0));
            small = small.times(10);
        }
        // Should be 1e-9; 1e-9 * 1e9 = 1e0 = 1
        small = small.times(1e9);
        assertEquals("1.00", notation.format(small, 2, 2, 0));
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("e289,372,385", notation.format(randomBigNum, 0));
        assertEquals("e289,372,385", notation.format(randomBigNum, 1));
        assertEquals("e289,372,385", notation.format(randomBigNum, 2));
        assertEquals("e289,372,385", notation.format(randomBigNum, 3));
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("ee8.5", notation.format(randomBigNum, 0));
        assertEquals("ee8.5", notation.format(randomBigNum, 1));
        assertEquals("ee8.46", notation.format(randomBigNum, 2));
        assertEquals("ee8.461", notation.format(randomBigNum, 3));
        assertEquals("ee8.4615", notation.format(randomBigNum, 4));
    }

    @Test
    public void testNegative() {
        assertEquals("-1.00", notation.format(new BigDouble(-1), 2, 2));
        assertEquals("-2.00", notation.format(new BigDouble(-2), 2, 2));
        assertEquals("-3.00", notation.format(new BigDouble(-3), 2, 2));
        assertEquals("-4.00", notation.format(new BigDouble(-4), 2, 2));
        assertEquals("-5.00", notation.format(new BigDouble(-5), 2, 2));
        assertEquals("-6.00", notation.format(new BigDouble(-6), 2, 2));
        assertEquals("-7.00", notation.format(new BigDouble(-7), 2, 2));
        assertEquals("-8.00", notation.format(new BigDouble(-8), 2, 2));
        assertEquals("-9.00", notation.format(new BigDouble(-9), 2, 2));
        assertEquals("-10.00", notation.format(new BigDouble(-10), 2, 2));
        assertEquals("-100.00", notation.format(new BigDouble(-100), 2, 2));
    }

}
