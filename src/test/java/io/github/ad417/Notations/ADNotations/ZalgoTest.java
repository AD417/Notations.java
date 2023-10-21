package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// @Disabled("The notation doesn't error, looks reasonably correct, and I hate debugging Zalgo.")
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
    public void testABunchOfSemiArbitraryValues() {
        assertEquals("0҉͚.̸̚0̰̍2̶̍", notation.format(new BigDouble("1e1")));
        assertEquals("0̿̚.̷̍0̷3͓", notation.format(new BigDouble("1e2")));
        assertEquals("0̰͓.̿̿0̚҉5̶̍", notation.format(new BigDouble("1e3")));
        assertEquals("0̰͚.̿̿0̿̚8̸̍", notation.format(new BigDouble("1e5")));
        assertEquals("0̶̍.͓1͚2͚", notation.format(new BigDouble("1e8")));
        assertEquals("0̶̍.̷1̿8͓", notation.format(new BigDouble("1e12")));
        assertEquals("0͓̍.͚2̷7̷", notation.format(new BigDouble("1e18")));
        assertEquals("0̰̿.̿̚4̍̚1҉̍", notation.format(new BigDouble("1e27")));
        assertEquals("0̶̰.̿̍6̰̚2̶͓", notation.format(new BigDouble("1e41")));
        assertEquals("0̷̶.̷9͚3̰", notation.format(new BigDouble("1e62")));
        assertEquals("1̰̿̍.̸̶̿4̰̿", notation.format(new BigDouble("1e93")));
        assertEquals("2͓҉.͓͚1̰͓", notation.format(new BigDouble("1e140")));
        assertEquals("3̿̿.̿̿1͚5͓", notation.format(new BigDouble("1e210")));
        assertEquals("4̷̰.̸̿7̶҉3̸̰", notation.format(new BigDouble("1e315")));
        assertEquals("7̰͓̚.̸͓̰1̷͓", notation.format(new BigDouble("1e473")));
        assertEquals("1̶҉0̿.̶̶6̰5҉̶", notation.format(new BigDouble("1e710")));
        assertEquals("1̸̰5̸̚.̶̸9̶8̷̶", notation.format(new BigDouble("1e1065")));
        assertEquals("2͓͚3̿.̰͓9̸7̸҉", notation.format(new BigDouble("1e1598")));
        assertEquals("3҉҉5͓̚.̷̶9̚6͚̿", notation.format(new BigDouble("1e2397")));
        assertEquals("5̰͓3̶.̸̰9͚4̷҉", notation.format(new BigDouble("1e3596")));
        assertEquals("8͚̿0̸.͚̚9̸1̶͚", notation.format(new BigDouble("1e5394")));
        assertEquals("1҉̷2̶̍1͓̚.̶̰3̰7҉", notation.format(new BigDouble("1e8091")));
        assertEquals("1͚̿8҉̶2̶͚.͓̿0͚6͚", notation.format(new BigDouble("1e12137")));
        assertEquals("2͓̍7̷͓3̸̿.͓̿0̿9̶", notation.format(new BigDouble("1e18206")));
        assertEquals("4̷҉0̸̶9̍̍.͓6҉4͓", notation.format(new BigDouble("1e27309")));
        assertEquals("6҉͚1̶̚4̶̿.̷҉4̚7̿", notation.format(new BigDouble("1e40964")));
        assertEquals("9͚͚2̸͓1҉̷.҉7͓̍", notation.format(new BigDouble("1e61446")));
        assertEquals("1̶̿,͓3̿̚8̶2̶.͓͚5͓5̶", notation.format(new BigDouble("1e92169")));
        assertEquals("2̸̸,̶0͓7̍3̰.̷҉8͓3̸", notation.format(new BigDouble("1e138254")));
        assertEquals("3҉̶,͚1̸̶1̿0̰.̶̶7̶5̷", notation.format(new BigDouble("1e207381")));
        assertEquals("4̿̚,̿6͚̚6҉6̷.̰̚1̸3҉", notation.format(new BigDouble("1e311072")));
        assertEquals("6͚,̷9̷9̷9̚.̚1̷9̸", notation.format(new BigDouble("1e466608")));
        assertEquals("1̰҉0̷̿,̷̰4̰9̚8̚.̿7̶8̍", notation.format(new BigDouble("1e699912")));
        assertEquals("1̸͓5̸҉,͚̍7̿4̷8͓.̿1̶8̰", notation.format(new BigDouble("1e1049868")));
        assertEquals("2҉̚3͓̍,̰͓6̰2͚2͚.̰2͓7̿", notation.format(new BigDouble("1e1574802")));
        assertEquals("3̸҉5̷,̰̿4̰3̶3̶̿.̿4̶", notation.format(new BigDouble("1e2362203")));
        assertEquals("5̶̿3̸̰,̰̍1̚5̷0̰.͓1̰1̿", notation.format(new BigDouble("1e3543305")));
        assertEquals("7̸͓9̷͓,̰̰7̍2̷5̷.҉1̸7̷", notation.format(new BigDouble("1e5314958")));
        assertEquals("1̷͚1͓9̶,̰5͓8̸7̷.҉͚7̶5̶", notation.format(new BigDouble("1e7972437")));
        assertEquals("1҉̿7̸9̶,̷3̷͓8̚1̸.͚̍6̸3̶", notation.format(new BigDouble("1e11958656")));
        assertEquals("2̸̸6̷9͓,̍0̸7̿2̷.̸͓4̿5̚", notation.format(new BigDouble("1e17937984")));
        assertEquals("4҉̚0̶3͚,̿6̷̍0̍8̍.͓̍6͚8҉", notation.format(new BigDouble("1e26906976")));
        assertEquals("6҉҉0҉5̰,̰4̿̿1̍3̍.҉̶0̸1̚", notation.format(new BigDouble("1e40360464")));
        assertEquals("9̶̶0̿8̷,͚1͚̍1҉9̍.͓͓5̿2̷", notation.format(new BigDouble("1e60540696")));
        assertEquals("1̶̸,̷̷3͓6̰2̍,͓1̿7̶9̿.̚2͓8̶", notation.format(new BigDouble("1e90811044")));
        assertEquals("2͓̚,̷͚0͚4̸3̰,̿2̰6͓8̍.҉9̿2͓", notation.format(new BigDouble("1e136216566")));
        assertEquals("3҉̍,̿̿0̿6̶4̿,̍9̿0̿3̍.̚3҉8̷", notation.format(new BigDouble("1e204324849")));
        assertEquals("4̰̿,͚̰5̶9͓7̚,͚3̶5҉5̰.̷0̸8̿", notation.format(new BigDouble("1e306487274")));
        assertEquals("6̰̿,̍̿8̶9͚6̿,̷0͚3̚2͚.̷6̚3̍", notation.format(new BigDouble("1e459730911")));
        assertEquals("1҉҉0̍,̍3̚4҉4̶,҉0̿4̿8҉.̍9̷͚5̶", notation.format(new BigDouble("1e689596367")));
        assertEquals("1҉̸5̚,̍5̍1̚6̿,̶0̍7̰3͚.̰4̰̿3̶", notation.format(new BigDouble("1e1034394551")));
        assertEquals("2҉̶3̍,̶2͓7҉4̍,̸1̚1͓0͚.̿1̶̍5͓", notation.format(new BigDouble("1e1551591827")));
        assertEquals("3̿̚4̍,̶9̍1̶1̚,̶1҉6̷5̷.҉2̰̰3̿", notation.format(new BigDouble("1e2327387741")));
        assertEquals("5͓҉2͓,̸3̸6̚6҉,̚7̸4̿7̍.͓8̸͚5̰", notation.format(new BigDouble("1e3491081612")));
        assertEquals("7̶͓8̍,̶5͓5̍0̰,̰1̸2̰1̍.͚7͓̿7̸", notation.format(new BigDouble("1e5236622418")));
        assertEquals("1͚̿1͓7̶,҉8̍2̶5͚,̶1҉8҉̿2̰.̸6̍6̸", notation.format(new BigDouble("1e7854933627")));
        assertEquals("1̸͓7̿6̸,͚7҉3̸7̸,̶7̷7̷҉3̸.̚9҉9̶", notation.format(new BigDouble("1e11782400441")));
        assertEquals("2̿̍6̍̚5͓,̰1̚̚0̶̍6͓,̶6҉̚6̿1̚", notation.format(new BigDouble("1e17673600662")));
        assertEquals("3̷̰9̰7̷,̸6̸5̷9̚,̿9̿9̶͓1̷.̸4̚9̸", notation.format(new BigDouble("1e26510400993")));
        assertEquals("5̶̸9̷6̚,͓4̚8͚9͚,̷9̿8̿7̷.҉2̰5͚", notation.format(new BigDouble("1e39765601490")));
        assertEquals("8̰͚9̿4̷,̷7̚3̷4̸,͚9̿8̶̶0҉.҉8͓7̿", notation.format(new BigDouble("1e59648402235")));
        assertEquals("1̿,҉3̶4̿2̚,̍1̸0̍2҉,͚4̸7̶1͓.͓3̶2̷", notation.format(new BigDouble("1e89472603353")));
        assertEquals("2̸̿,̿0̿1͓3͓,̶1̸5̶3̍,҉7҉0̍6̰.̶9̍9̚", notation.format(new BigDouble("1e134208905030")));
        assertEquals("3̚,̰0̸1̍9̶,҉7̚3̿0̶,̚5̷6̍0̍.̷4̿8͓", notation.format(new BigDouble("1e201313357545")));
        assertEquals("4̶̶,̰5̷2҉9̍,̸5͚9̰5̰,̰8̍4҉0̷.͓7̚3̷", notation.format(new BigDouble("1e301970036318")));
        assertEquals("6̶͓,̿7҉9̚4̚,͚3̸9̿3̸,͚7̍6̚1̷.̷0͚9͓", notation.format(new BigDouble("1e452955054477")));
        assertEquals("1͓̿0҉,̰1͚9̶1̷,҉5̍9̍0̚,̶6̚4̶1̷.҉6҉5̶", notation.format(new BigDouble("1e679432581716")));
        assertEquals("1҉5̶,͚2̍8̰7̰,̿3͓8̷5҉,͚9̚6̰2̸.͓4̚7̿", notation.format(new BigDouble("1e1019148872574")));
        assertEquals("2҉҉2̸,̿9͓3̚1̸҉,͚0̶7͓8҉,̚9̷4̍3̷.͚7̿", notation.format(new BigDouble("1e1528723308861")));
        assertEquals("3҉̚4̍,̚3҉9̿6̶,̚6҉1͚8҉,̿4҉1̚5̷.̶5҉6̚", notation.format(new BigDouble("1e2293084963292")));
        assertEquals("5҉̿1͚,̷5̍9̰4͓,҉9̶2̸7̚,̰6̚2͚3̷.͓3҉5҉", notation.format(new BigDouble("1e3439627444938")));
        assertEquals("7̸7̸,̷3̍9̸2̶,҉3̿9҉1̰,̷4̿3̶5͓.̰0͓2̿", notation.format(new BigDouble("1e5159441167407")));
        assertEquals("1̿̿1̸6̶,̷0̶8̚8͚,̶5̶8̚7̍,͚1͚5̰2͚.̸5̶4̰", notation.format(new BigDouble("1e7739161751111")));
        assertEquals("1̷͓7҉4̰,̸1̚3̍2̍,̶8̸8҉0̶,̿7͓2͚8͚.̍8̸1͓", notation.format(new BigDouble("1e11608742626667")));
        assertEquals("2҉̿6̿1̷,̍1̷9̶9͓,͓3̚2̶1̶,̷0̷9̿3͓.̶2̍3̷", notation.format(new BigDouble("1e17413113940001")));
        assertEquals("3̿̍9͚1̚,̚7̚9͓8҉,̿9̷8̶1̸,̷6͚3̷9͚.̶8̚5҉", notation.format(new BigDouble("1e26119670910002")));
        assertEquals("5̿8̷7̿,͓6̶9̚8̰,͚4̸7̚2̷,͚4҉5̸9͓.҉7̚7̍", notation.format(new BigDouble("1e39179506365003")));
        assertEquals("8̶̍8̷1̚,̚5̍4͚7̍,͚7͚0̶8̰,҉6̸8̸9̍.͚6̚6̷", notation.format(new BigDouble("1e58769259547505")));
        assertEquals("1͓,̰3̿2̍2̿,̚3̰2̶1͓,̿5͓6͓3̿,̶0҉3҉4̶.͓5̚", notation.format(new BigDouble("1e88153889321258")));
        assertEquals("1̰,̿9̶83͓,͚4͓8͚2͚,̰3҉4͓4̰,̿5҉5҉1͚.͓7҉5̷", notation.format(new BigDouble("1e132230833981887")));
        assertEquals("2̿,̿9̚7͚5͓,̶2͓2͓3̍,͚5̰1̍6̚,̸8̸2̶7̷.̚6̰3͓", notation.format(new BigDouble("1e198346250972831")));
        assertEquals("4̿,̰4͚6̶2̿,͓8҉3͚5̸,͓2͓7̰5̰,̚2҉4̸1҉.̶4͓6҉", notation.format(new BigDouble("1e297519376459247")));
        assertEquals("6҉,͓6҉9͓4̰,̍2̚5͓2̿,͚9҉1҉2͓,̷8̷6̶2̚.̶1̷9̚", notation.format(new BigDouble("1e446279064688871")));
        assertEquals("1͓̿0̸,͓0̷4̿1̰,͓3̶7̶9̷,҉3̸6̚9̍,̷2̿9̍3̍.̷3̶", notation.format(new BigDouble("1e669418597033307")));
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
