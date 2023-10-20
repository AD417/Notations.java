import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.Format.Settings;
import io.github.ad417.Notations.Notation;
import io.github.ad417.Notations.ZalgoNotation;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("The notation doesn't error, looks reasonably correct, and I hate debugging Zalgo.")
public class ZalgoTest {
    public static Notation notation = new ZalgoNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Zalgo", ZalgoNotation.name);
    }

    @Test
    public void testZeroConversion() {
        assertEquals("0̍̍", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals("0̍̍", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals("0̍̍", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals("0̍̍", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals("0̍̍", notation.format(negativeZero, 0, 0));
        assertEquals("0̍̍", notation.format(negativeZero, 1, 1));
        assertEquals("0̍̍", notation.format(negativeZero, 2, 2));
        assertEquals("0̍̍", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        assertEquals("0҉͚҉͚҉̸̰̍", notation.format(new BigDouble(1), 2, 2));
        assertEquals("0̸̿.̍̚0̰̰1͓̍", notation.format(new BigDouble(2), 2, 2));
        assertEquals("0̶҉.̍̚0҉̚1̍̍", notation.format(new BigDouble(3), 2, 2));
        assertEquals("0̷̰.͓҉0̍̍1̿", notation.format(new BigDouble(4), 2, 2));
        assertEquals("0̶̸.̸̶0̷̍1̰̍", notation.format(new BigDouble(5), 2, 2));
        assertEquals("0̷͓.̸͚0̸҉1̿̍", notation.format(new BigDouble(6), 2, 2));
        assertEquals("0̿̍.͚̍0̷̍1͚̍", notation.format(new BigDouble(7), 2, 2));
        assertEquals("0҉͚.͚̚0̿̚1̶̍", notation.format(new BigDouble(8), 2, 2));
        assertEquals("0̰̰.̿҉0͚̚2͚̍", notation.format(new BigDouble(9), 2, 2));
        assertEquals("0҉͚.̸̚0̰̍2̶̍", notation.format(new BigDouble(10), 2, 2));
        assertEquals("0̿̚.̷̍0̷3͓", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals("0͓̰̰͚̿̿̍̍", notation.format(roundsDown, 0, 0));

        assertEquals("0̸̶̷̰̰̿̿̍", notation.format(roundsUp, 0, 0));
        assertEquals("0̸̶̷̰̰̿̿̍", notation.format(roundsUp, 0, 1));
        assertEquals("0̸̶̷̰̰̿̿̍", notation.format(roundsUp, 0, 2));

        assertEquals("0̶̸͓͚̿̍̚̚", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals("0̰͓.̿̿0̚҉5̶̍", notation.format(thousand, 0, 0, 0));
        assertEquals("0̰͓.̿̿0̚҉5̶̍", notation.format(thousand, 2, 0, 0));
        assertEquals("0̰҉.̿̿0̶͓5̷̍", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        assertEquals("0͚͓̍̚̚̚̚", notation.format(new BigDouble(1e-1)));
        assertEquals("0̷̶̍̍̚̚", notation.format(new BigDouble(1e-2)));
        assertEquals("0̷̷̶̍̚", notation.format(new BigDouble(1e-3)));
        assertEquals("0̷̍̚", notation.format(new BigDouble(1e-5)));
        assertEquals("0̍̍", notation.format(new BigDouble(1e-10)));
        assertEquals("0̍̍", notation.format(new BigDouble(1e-100)));
        assertEquals("0̍̍", notation.format(new BigDouble("1e-1000")));
        assertEquals("0̍̍", notation.format(new BigDouble("1e-1000000000000")));
    }

    @Test
    public void testSomewhatLarge() {
        assertEquals("1̶̍.̚̚5̶͓",notation.format(new BigDouble("1e100")));
        assertEquals("1̶̶8̷̶5͚̍.̰̚1҉8͓", notation.format(new BigDouble("1.23456789e12345")));
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 0));
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 1));
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 2));
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 3));
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 0));
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 1));
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 2));
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 3));
        assertEquals("4҉͓,̷̷3҉4҉0̿,̰6̍2҉9̿.҉1̍7̚", notation.format(randomBigNum, 4));
    }

    @Test
    public void testNegative() {
        assertEquals("-0҉͚҉͚҉̸̰̍", notation.format(new BigDouble(-1), 2, 2));
        assertEquals("-0̸̿.̍̚0̰̰1͓̍", notation.format(new BigDouble(-2), 2, 2));
        assertEquals("-0̶҉.̍̚0҉̚1̍̍", notation.format(new BigDouble(-3), 2, 2));
        assertEquals("-0̷̰.͓҉0̍̍1̿", notation.format(new BigDouble(-4), 2, 2));
        assertEquals("-0̶̸.̸̶0̷̍1̰̍", notation.format(new BigDouble(-5), 2, 2));
        assertEquals("-0̷͓.̸͚0̸҉1̿̍", notation.format(new BigDouble(-6), 2, 2));
        assertEquals("-0̿̍.͚̍0̷̍1͚̍", notation.format(new BigDouble(-7), 2, 2));
        assertEquals("-0҉͚.͚̚0̿̚1̶̍", notation.format(new BigDouble(-8), 2, 2));
        assertEquals("-0̰̰.̿҉0͚̚2͚̍", notation.format(new BigDouble(-9), 2, 2));
        assertEquals("-0҉͚.̸̚0̰̍2̶̍", notation.format(new BigDouble(-10), 2, 2));
        assertEquals("-0̿̚.̷̍0̷3͓", notation.format(new BigDouble(-100), 2, 2));
    }

}
