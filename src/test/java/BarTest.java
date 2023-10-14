import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.BarNotation;
import io.github.ad417.Notations.Format.Settings;
import io.github.ad417.Notations.Notation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Thanks, I hate this one. 
public class BarTest {
    public static Notation notation = new BarNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Bar", BarNotation.name);
    }

    @Test
    public void testZeroConversion() {
        // Not sure why this is the case, but it is. I'll just roll with it...
        assertEquals("0", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals("0", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals("0", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals("0", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals("0", notation.format(negativeZero, 0, 0));
        assertEquals("0", notation.format(negativeZero, 1, 1));
        assertEquals("0", notation.format(negativeZero, 2, 2));
        assertEquals("0", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        // TODO: determine if being an escape character sequence is correct behaviour.
        assertEquals("\uF421\uF421\uF421", notation.format(new BigDouble(1), 2, 2));
        assertEquals("\uF426\uF423\uF421", notation.format(new BigDouble(2), 2, 2));
        assertEquals("\uF422\uF425\uF421", notation.format(new BigDouble(3), 2, 2));
        assertEquals("\uF423\uF426\uF421", notation.format(new BigDouble(4), 2, 2));
        assertEquals("\uF422\uF427\uF421", notation.format(new BigDouble(5), 2, 2));
        assertEquals("\uF428\uF427\uF421", notation.format(new BigDouble(6), 2, 2));
        assertEquals("\uF424\uF428\uF421", notation.format(new BigDouble(7), 2, 2));
        assertEquals("\uF421\uF421\uF422", notation.format(new BigDouble(8), 2, 2));
        assertEquals("\uF424\uF421\uF422", notation.format(new BigDouble(9), 2, 2));
        assertEquals("\uF427\uF421\uF422", notation.format(new BigDouble(10), 2, 2));
        assertEquals("\uF426\uF422\uF423", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals("/\uF425\uF424\uF421", notation.format(roundsDown, 0, 0));

        assertEquals("/\uF425\uF422\uF421", notation.format(roundsUp, 0, 0));
        assertEquals("/\uF425\uF422\uF421", notation.format(roundsUp, 0, 1));
        assertEquals("/\uF425\uF422\uF421", notation.format(roundsUp, 0, 2));

        assertEquals("/\uF426\uF423\uF421", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals("\uF425\uF423\uF424", notation.format(thousand, 0, 0, 0));
        assertEquals("\uF425\uF423\uF424", notation.format(thousand, 2, 0, 0));
        assertEquals("\uF425\uF423\uF424", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        assertEquals("/\uF427\uF421\uF422", notation.format(new BigDouble(1e-1)));
        assertEquals("/\uF426\uF422\uF423", notation.format(new BigDouble(1e-2)));
        assertEquals("/\uF425\uF423\uF424", notation.format(new BigDouble(1e-3)));
        assertEquals("/\uF423\uF425\uF426", notation.format(new BigDouble(1e-5)));
        assertEquals("/\uF425\uF421\uF424\uF422", notation.format(new BigDouble(1e-10)));
        assertEquals("/\uF427\uF426\uF427\uF426\uF422", notation.format(new BigDouble(1e-100)));
        assertEquals("/\uF424\uF423\uF424\uF423\uF422\uF423", notation.format(new BigDouble("1e-1000")));
        assertEquals(
                "/\uF426\uF424\uF423\uF425\uF424\uF424\uF421\uF421\uF423\uF427\uF421\uF423\uF428\uF421\uF421\uF423",
                notation.format(new BigDouble("1e-1000000000000"))
        );
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 0)
        );
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 1)
        );
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 2)
        );
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 3)
        );
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 0)
        );
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 1)
        );
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 2)
        );
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 3)
        );
        assertEquals(
                "\uF421\uF424\uF428\uF426\uF425\uF426\uF425\uF423\uF427\uF421\uF424\uF423",
                notation.format(randomBigNum, 4)
        );
    }

    @Test
    public void testNegative() {
        assertEquals("\uF431\uF431\uF431", notation.format(new BigDouble(-1), 2, 2));
        assertEquals("\uF436\uF433\uF431", notation.format(new BigDouble(-2), 2, 2));
        assertEquals("\uF432\uF435\uF431", notation.format(new BigDouble(-3), 2, 2));
        assertEquals("\uF433\uF436\uF431", notation.format(new BigDouble(-4), 2, 2));
        assertEquals("\uF432\uF437\uF431", notation.format(new BigDouble(-5), 2, 2));
        assertEquals("\uF438\uF437\uF431", notation.format(new BigDouble(-6), 2, 2));
        assertEquals("\uF434\uF438\uF431", notation.format(new BigDouble(-7), 2, 2));
        assertEquals("\uF431\uF431\uF432", notation.format(new BigDouble(-8), 2, 2));
        assertEquals("\uF434\uF431\uF432", notation.format(new BigDouble(-9), 2, 2));
        assertEquals("\uF437\uF431\uF432", notation.format(new BigDouble(-10), 2, 2));
        assertEquals("\uF436\uF432\uF433", notation.format(new BigDouble(-100), 2, 2));
    }

}
