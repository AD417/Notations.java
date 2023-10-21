package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiTest {
    public static Notation notation = new ShiNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Shi", ShiNotation.name);
    }

    @Test
    public void testZeroConversion() {
        assertEquals("世世世", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals("世世世", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals("世世世", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals("世世世", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals("世世世", notation.format(negativeZero, 0, 0));
        assertEquals("世世世", notation.format(negativeZero, 1, 1));
        assertEquals("世世世", notation.format(negativeZero, 2, 2));
        assertEquals("世世世", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        assertEquals("使氏勢", notation.format(new BigDouble(1), 2, 2));
        assertEquals("使獅使", notation.format(new BigDouble(2), 2, 2));
        assertEquals("使矢侍", notation.format(new BigDouble(3), 2, 2));
        assertEquals("使矢視", notation.format(new BigDouble(4), 2, 2));
        assertEquals("使石嗜", notation.format(new BigDouble(5), 2, 2));
        assertEquals("使石氏", notation.format(new BigDouble(6), 2, 2));
        assertEquals("使石逝", notation.format(new BigDouble(7), 2, 2));
        assertEquals("使視十", notation.format(new BigDouble(8), 2, 2));
        assertEquals("使視屍", notation.format(new BigDouble(9), 2, 2));
        assertEquals("使視是", notation.format(new BigDouble(10), 2, 2));
        assertEquals("使誓濕", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals("使施史", notation.format(roundsDown, 0, 0));

        assertEquals("使是誓", notation.format(roundsUp, 0, 0));
        assertEquals("使是誓", notation.format(roundsUp, 0, 1));
        assertEquals("使是誓", notation.format(roundsUp, 0, 2));

        assertEquals("使施逝", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals("使逝濕", notation.format(thousand, 0, 0, 0));
        assertEquals("使逝濕", notation.format(thousand, 2, 0, 0));
        assertEquals("使逝濕", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        assertEquals("使屍拭", notation.format(new BigDouble(1e-1)));
        assertEquals("使十勢", notation.format(new BigDouble(1e-2)));
        assertEquals("世適識", notation.format(new BigDouble(1e-3)));
        assertEquals("世獅屍", notation.format(new BigDouble(1e-5)));
        assertEquals("世始施", notation.format(new BigDouble(1e-10)));
        assertEquals("世世世", notation.format(new BigDouble(1e-100)));
        assertEquals("世世世", notation.format(new BigDouble("1e-1000")));
        assertEquals("世世世", notation.format(new BigDouble("1e-1000000000000")));
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("始始是", notation.format(randomBigNum, 0));
        assertEquals("始始是", notation.format(randomBigNum, 1));
        assertEquals("始始是", notation.format(randomBigNum, 2));
        assertEquals("始始是", notation.format(randomBigNum, 3));
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("始始是", notation.format(randomBigNum, 0));
        assertEquals("始始是", notation.format(randomBigNum, 1));
        assertEquals("始始是", notation.format(randomBigNum, 2));
        assertEquals("始始是", notation.format(randomBigNum, 3));
        assertEquals("始始是", notation.format(randomBigNum, 4));
    }

    @Test
    public void testNegative() {
        assertEquals("-使氏勢", notation.format(new BigDouble(-1), 2, 2));
        assertEquals("-使獅使", notation.format(new BigDouble(-2), 2, 2));
        assertEquals("-使矢侍", notation.format(new BigDouble(-3), 2, 2));
        assertEquals("-使矢視", notation.format(new BigDouble(-4), 2, 2));
        assertEquals("-使石嗜", notation.format(new BigDouble(-5), 2, 2));
        assertEquals("-使石氏", notation.format(new BigDouble(-6), 2, 2));
        assertEquals("-使石逝", notation.format(new BigDouble(-7), 2, 2));
        assertEquals("-使視十", notation.format(new BigDouble(-8), 2, 2));
        assertEquals("-使視屍", notation.format(new BigDouble(-9), 2, 2));
        assertEquals("-使視是", notation.format(new BigDouble(-10), 2, 2));
        assertEquals("-使誓濕", notation.format(new BigDouble(-100), 2, 2));
    }

}
