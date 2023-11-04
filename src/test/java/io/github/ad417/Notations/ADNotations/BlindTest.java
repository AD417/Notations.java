package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Lol
public class BlindTest {
    public static Notation notation = new BlindNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Blind", BlindNotation.name);
    }

    @Test
    public void testInfinity() {
        assertEquals(" ", notation.format(BigDouble.POSITIVE_INFINITY));
        assertEquals(" ", notation.format(BigDouble.NEGATIVE_INFINITY));
    }

    @Test
    public void testZeroConversion() {
        assertEquals(" ", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals(" ", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals(" ", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals(" ", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals(" ", notation.format(negativeZero, 0, 0));
        assertEquals(" ", notation.format(negativeZero, 1, 1));
        assertEquals(" ", notation.format(negativeZero, 2, 2));
        assertEquals(" ", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        assertEquals(" ", notation.format(new BigDouble(1), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(2), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(3), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(4), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(5), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(6), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(7), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(8), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(9), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(10), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals(" ", notation.format(roundsDown, 0, 0));

        assertEquals(" ", notation.format(roundsUp, 0, 0));
        assertEquals(" ", notation.format(roundsUp, 0, 1));
        assertEquals(" ", notation.format(roundsUp, 0, 2));

        assertEquals(" ", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals(" ", notation.format(thousand, 0, 0, 0));
        assertEquals(" ", notation.format(thousand, 2, 0, 0));
        assertEquals(" ", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        assertEquals(" ", notation.format(new BigDouble(1e-1)));
        assertEquals(" ", notation.format(new BigDouble(1e-2)));
        assertEquals(" ", notation.format(new BigDouble(1e-3)));
        assertEquals(" ", notation.format(new BigDouble(1e-5)));
        assertEquals(" ", notation.format(new BigDouble(1e-10)));
        assertEquals(" ", notation.format(new BigDouble(1e-100)));
        assertEquals(" ", notation.format(new BigDouble("1e-1000")));
        assertEquals(" ", notation.format(new BigDouble("1e-1000000000000")));
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals(" ", notation.format(randomBigNum, 0));
        assertEquals(" ", notation.format(randomBigNum, 1));
        assertEquals(" ", notation.format(randomBigNum, 2));
        assertEquals(" ", notation.format(randomBigNum, 3));
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals(" ", notation.format(randomBigNum, 0));
        assertEquals(" ", notation.format(randomBigNum, 1));
        assertEquals(" ", notation.format(randomBigNum, 2));
        assertEquals(" ", notation.format(randomBigNum, 3));
        assertEquals(" ", notation.format(randomBigNum, 4));
    }

    @Test
    public void testNegative() {
        assertEquals(" ", notation.format(new BigDouble(-1), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-2), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-3), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-4), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-5), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-6), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-7), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-8), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-9), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-10), 2, 2));
        assertEquals(" ", notation.format(new BigDouble(-100), 2, 2));
    }

}
