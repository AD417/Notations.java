package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HexTest {
    public static Notation notation = new HexNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Hex", HexNotation.name);
    }

    @Test
    public void testZeroConversion() {
        assertEquals("80000000", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals("80000000", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals("80000000", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals("80000000", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals("80000000", notation.format(negativeZero, 0, 0));
        assertEquals("80000000", notation.format(negativeZero, 1, 1));
        assertEquals("80000000", notation.format(negativeZero, 2, 2));
        assertEquals("80000000", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        assertEquals("C0000000", notation.format(new BigDouble(1), 2, 2));
        assertEquals("E0000000", notation.format(new BigDouble(2), 2, 2));
        assertEquals("E8000000", notation.format(new BigDouble(3), 2, 2));
        assertEquals("F0000000", notation.format(new BigDouble(4), 2, 2));
        assertEquals("F1800000", notation.format(new BigDouble(5), 2, 2));
        assertEquals("F2000000", notation.format(new BigDouble(6), 2, 2));
        assertEquals("F3000000", notation.format(new BigDouble(7), 2, 2));
        assertEquals("F4000000", notation.format(new BigDouble(8), 2, 2));
        assertEquals("F4800000", notation.format(new BigDouble(9), 2, 2));
        assertEquals("F5000000", notation.format(new BigDouble(10), 2, 2));
        assertEquals("F9480000", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals("9999999A", notation.format(roundsDown, 0, 0));

        assertEquals("AAAAAA64", notation.format(roundsUp, 0, 0));
        assertEquals("AAAAAA64", notation.format(roundsUp, 0, 1));
        assertEquals("AAAAAA64", notation.format(roundsUp, 0, 2));

        assertEquals("A0000000", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals("FA788000", notation.format(thousand, 0, 0, 0));
        assertEquals("FA788000", notation.format(thousand, 2, 0, 0));
        assertEquals("FA78B000", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        assertEquals("8A666666", notation.format(new BigDouble(1e-1)));
        assertEquals("86A3C4E2", notation.format(new BigDouble(1e-2)));
        assertEquals("85872327", notation.format(new BigDouble(1e-3)));
        assertEquals("83CE3C66", notation.format(new BigDouble(1e-5)));
        assertEquals("839DB788", notation.format(new BigDouble(1e-10)));
        assertEquals("82EE3BD5", notation.format(new BigDouble(1e-100)));
        assertEquals("828E1C88", notation.format(new BigDouble("1e-1000")));
        assertEquals("81CB3C49", notation.format(new BigDouble("1e-1000000000000")));
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 0));
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 1));
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 2));
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 3));
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 0));
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 1));
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 2));
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 3));
        assertEquals("FE2B8D2F", notation.format(randomBigNum, 4));
    }

    @Test
    public void testNegative() {
        assertEquals("40000000", notation.format(new BigDouble(-1), 2, 2));
        assertEquals("20000000", notation.format(new BigDouble(-2), 2, 2));
        assertEquals("18000000", notation.format(new BigDouble(-3), 2, 2));
        assertEquals("10000000", notation.format(new BigDouble(-4), 2, 2));
        assertEquals("0E800000", notation.format(new BigDouble(-5), 2, 2));
        assertEquals("0E000000", notation.format(new BigDouble(-6), 2, 2));
        assertEquals("0D000000", notation.format(new BigDouble(-7), 2, 2));
        assertEquals("0C000000", notation.format(new BigDouble(-8), 2, 2));
        assertEquals("0B800000", notation.format(new BigDouble(-9), 2, 2));
        assertEquals("0B000000", notation.format(new BigDouble(-10), 2, 2));
        assertEquals("06B80000", notation.format(new BigDouble(-100), 2, 2));
    }

}
