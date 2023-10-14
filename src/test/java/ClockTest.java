import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.ClockNotation;
import io.github.ad417.Notations.Format.Settings;
import io.github.ad417.Notations.HexNotation;
import io.github.ad417.Notations.Notation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClockTest {
    public static Notation notation = new ClockNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Clock", ClockNotation.name);
    }

    @Test
    public void testZeroConversion() {
        assertEquals("\uD83D\uDD5B", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals("\uD83D\uDD5B", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals("\uD83D\uDD5B", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals("\uD83D\uDD5B", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals("\uD83D\uDD5B", notation.format(negativeZero, 0, 0));
        assertEquals("\uD83D\uDD5B", notation.format(negativeZero, 1, 1));
        assertEquals("\uD83D\uDD5B", notation.format(negativeZero, 2, 2));
        assertEquals("\uD83D\uDD5B", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        assertEquals("\uD83D\uDD50", notation.format(new BigDouble(1), 2, 2));
        assertEquals("\uD83D\uDD51", notation.format(new BigDouble(2), 2, 2));
        assertEquals("\uD83D\uDD52", notation.format(new BigDouble(3), 2, 2));
        assertEquals("\uD83D\uDD53", notation.format(new BigDouble(4), 2, 2));
        assertEquals("\uD83D\uDD54", notation.format(new BigDouble(5), 2, 2));
        assertEquals("\uD83D\uDD55", notation.format(new BigDouble(6), 2, 2));
        assertEquals("\uD83D\uDD56", notation.format(new BigDouble(7), 2, 2));
        assertEquals("\uD83D\uDD57", notation.format(new BigDouble(8), 2, 2));
        assertEquals("\uD83D\uDD58", notation.format(new BigDouble(9), 2, 2));
        assertEquals("\uD83D\uDD59", notation.format(new BigDouble(10), 2, 2));
        assertEquals("\uD83D\uDD5B\uD83D\uDD56", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals("\uD83D\uDD5B", notation.format(roundsDown, 0, 0));

        assertEquals("\uD83D\uDD5B", notation.format(roundsUp, 0, 0));
        assertEquals("\uD83D\uDD5B", notation.format(roundsUp, 0, 1));
        assertEquals("\uD83D\uDD5B", notation.format(roundsUp, 0, 2));

        assertEquals("\uD83D\uDD5B", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals("\uD83D\uDD50\uD83D\uDD55", notation.format(thousand, 0, 0, 0));
        assertEquals("\uD83D\uDD50\uD83D\uDD55", notation.format(thousand, 2, 0, 0));
        assertEquals("\uD83D\uDD50\uD83D\uDD55", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble(1e-1)));
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble(1e-2)));
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble(1e-3)));
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble(1e-5)));
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble(1e-10)));
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble(1e-100)));
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble("1e-1000")));
        assertEquals("\uD83D\uDD5B", notation.format(new BigDouble("1e-1000000000000")));
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 0)
        );
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 1)
        );
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 2)
        );
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 3)
        );
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 0)
        );
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 1)
        );
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 2)
        );
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 3)
        );
        assertEquals(
                "\uD83D\uDD55\uD83D\uDD55\uD83D\uDD53\uD83D\uDD57",
                notation.format(randomBigNum, 4)
        );
    }

    @Test
    public void testNegative() {
        assertEquals("-\uD83D\uDD50", notation.format(new BigDouble(-1), 2, 2));
        assertEquals("-\uD83D\uDD51", notation.format(new BigDouble(-2), 2, 2));
        assertEquals("-\uD83D\uDD52", notation.format(new BigDouble(-3), 2, 2));
        assertEquals("-\uD83D\uDD53", notation.format(new BigDouble(-4), 2, 2));
        assertEquals("-\uD83D\uDD54", notation.format(new BigDouble(-5), 2, 2));
        assertEquals("-\uD83D\uDD55", notation.format(new BigDouble(-6), 2, 2));
        assertEquals("-\uD83D\uDD56", notation.format(new BigDouble(-7), 2, 2));
        assertEquals("-\uD83D\uDD57", notation.format(new BigDouble(-8), 2, 2));
        assertEquals("-\uD83D\uDD58", notation.format(new BigDouble(-9), 2, 2));
        assertEquals("-\uD83D\uDD59", notation.format(new BigDouble(-10), 2, 2));
        assertEquals("-\uD83D\uDD5B\uD83D\uDD56", notation.format(new BigDouble(-100), 2, 2));
    }

}
