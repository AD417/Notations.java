package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImperialTest {
    public static Notation notation = new ImperialNotation();

    @BeforeEach
    public void setDefaultSettings() {
        Settings.lowerValueForExponentCommas = 100_000;
        Settings.higherValueForExponentCommas = 1_000_000_000;
        Settings.showExponentCommas = true;
    }

    @Test
    public void testNotationName() {
        assertEquals("Imperial", ImperialNotation.name);
    }

    @Test
    public void testInfinity() {
        assertEquals("Infinite", notation.format(BigDouble.POSITIVE_INFINITY));
        assertEquals("-Infinite", notation.format(BigDouble.NEGATIVE_INFINITY));
    }

    @Test
    public void testZeroConversion() {
        assertEquals("0.00pL", notation.format(BigDouble.ZERO, 0, 0));
        assertEquals("0.00pL", notation.format(BigDouble.ZERO, 1, 1));
        assertEquals("0.00pL", notation.format(BigDouble.ZERO, 2, 2));
        assertEquals("0.00pL", notation.format(BigDouble.ZERO, 10, 10));

        BigDouble negativeZero = new BigDouble(-0);
        assertEquals("0.00pL", notation.format(negativeZero, 0, 0));
        assertEquals("0.00pL", notation.format(negativeZero, 1, 1));
        assertEquals("0.00pL", notation.format(negativeZero, 2, 2));
        assertEquals("0.00pL", notation.format(negativeZero, 10, 10));

    }

    @Test
    public void testSmall() {
        assertEquals("1.00pL", notation.format(new BigDouble(1), 2, 2));
        assertEquals("2.00pL", notation.format(new BigDouble(2), 2, 2));
        assertEquals("3.00pL", notation.format(new BigDouble(3), 2, 2));
        assertEquals("4.00pL", notation.format(new BigDouble(4), 2, 2));
        assertEquals("5.00pL", notation.format(new BigDouble(5), 2, 2));
        assertEquals("6.00pL", notation.format(new BigDouble(6), 2, 2));
        assertEquals("7.00pL", notation.format(new BigDouble(7), 2, 2));
        assertEquals("8.00pL", notation.format(new BigDouble(8), 2, 2));
        assertEquals("9.00pL", notation.format(new BigDouble(9), 2, 2));
        assertEquals("10.00pL", notation.format(new BigDouble(10), 2, 2));
        assertEquals("100.00pL", notation.format(new BigDouble(100), 2, 2));
    }

    @Test
    public void testRounding() {
        BigDouble roundsUp = new BigDouble(0.6666666);
        BigDouble roundsDown = new BigDouble(0.4);
        BigDouble pointFive = new BigDouble(0.5);
        assertEquals("0.40pL", notation.format(roundsDown, 0, 0));

        assertEquals("0.67pL", notation.format(roundsUp, 0, 0));
        assertEquals("0.67pL", notation.format(roundsUp, 0, 1));
        assertEquals("0.67pL", notation.format(roundsUp, 0, 2));

        assertEquals("0.50pL", notation.format(pointFive, 0, 0));
    }

    @Test
    public void test1000() {
        BigDouble thousand = new BigDouble(1000);
        assertEquals("1.000nL", notation.format(thousand, 0, 0, 0));
        assertEquals("1.000nL", notation.format(thousand, 2, 0, 0));
        assertEquals("1.006nL", notation.format(thousand.add(6), 2, 0, 0));
    }

    @Test
    public void testExtremelySmall() {
        BigDouble small = new BigDouble("1e-4000");
        while (small.lte(1e-10)) {
            assertEquals("0.00pL", notation.format(small, 2, 2, 0));
            small = small.times(10);
        }
        // Should be 1e-9; 1e-9 * 1e9 = 1e0 = 1
        small = small.times(1e9);
        assertEquals("1.00pL", notation.format(small, 2, 2, 0));
    }

    @Test
    public void testUnitVolumes() {
        assertEquals("a minute minim", notation.format(new BigDouble("61611520")));
        assertEquals("a minute dram", notation.format(new BigDouble("3720000000")));
        assertEquals("a minute ounce", notation.format(new BigDouble("2.976e10")));
        assertEquals("a minute gill", notation.format(new BigDouble("1.1904e11")));
        assertEquals("a minute cup", notation.format(new BigDouble("2.3808e11")));
        assertEquals("a minute pint", notation.format(new BigDouble("4.7616e11")));
        assertEquals("a minute quart", notation.format(new BigDouble("9.5232e11")));
        assertEquals("a minute gallon", notation.format(new BigDouble("3.80928e12")));
        assertEquals("a minute pin", notation.format(new BigDouble("1.714176e13")));
        assertEquals("a minute firkin", notation.format(new BigDouble("3.428352e13")));
        assertEquals("a minute kilderkin", notation.format(new BigDouble("6.81374122e13")));
        // Require absurd precision to be reached EXACTLY. This is intended, per the JS version.
        assertEquals("a minute barrel", notation.format(new BigDouble("1.36274824397e14")));
        assertEquals("a minute hogshead", notation.format(new BigDouble("204412236595200")));
        assertEquals("a minute puncheon", notation.format(new BigDouble("272549648793600")));
        assertEquals("a minute butt", notation.format(new BigDouble("408824473190400")));
        assertEquals("a minute tun", notation.format(new BigDouble("817648946380800")));

        assertEquals("a tiny minim", notation.format(new BigDouble("8176489463808000")));
    }

    @Test
    public void testABunchOfSemiArbitraryValues() {
        assertEquals("10.00pL", notation.format(new BigDouble("1e1")));
        assertEquals("100.00pL", notation.format(new BigDouble("1e2")));
        assertEquals("1.000nL", notation.format(new BigDouble("1e3")));
        assertEquals("100.0nL", notation.format(new BigDouble("1e5")));
        assertEquals("1.6 minute minims", notation.format(new BigDouble("1e8")));
        assertEquals("a minute quart", notation.format(new BigDouble("1e12")));
        assertEquals("2 tiny drams and 2 minims", notation.format(new BigDouble("1e18")));
        assertEquals("23 petite minims", notation.format(new BigDouble("1e27")));
        assertEquals("2 petite ounces and 4 drams", notation.format(new BigDouble("1e41")));
        assertEquals("a petite pin and 3 gallons", notation.format(new BigDouble("1e62")));
        assertEquals("15 small minims", notation.format(new BigDouble("1e93")));
        assertEquals("a small dram and 16 minims", notation.format(new BigDouble("1e140")));
        assertEquals("a small ounce and 6 drams", notation.format(new BigDouble("1e210")));
        assertEquals("2 small quarts and a gill", notation.format(new BigDouble("1e315")));
        assertEquals("a small butt and 3 firkins", notation.format(new BigDouble("1e473")));
        assertEquals("20 modest minims", notation.format(new BigDouble("1e710")));
        assertEquals("a modest dram and 31 minims", notation.format(new BigDouble("1e1065")));
        assertEquals("a modest ounce and 6 drams", notation.format(new BigDouble("1e1598")));
        assertEquals("a modest quart and 3 cups", notation.format(new BigDouble("1e2397")));
        assertEquals("a modest puncheon and a pin", notation.format(new BigDouble("1e3596")));
        assertEquals("17 medium minims", notation.format(new BigDouble("1e5394")));
        assertEquals("a medium dram and 12 minims", notation.format(new BigDouble("1e8091")));
        assertEquals("a medium ounce and 2 drams", notation.format(new BigDouble("1e12137")));
        assertEquals("almost a medium quart", notation.format(new BigDouble("1e18206")));
        assertEquals("a medium kilderkin and a firkin", notation.format(new BigDouble("1e27309")));
        assertEquals("14 generous minims", notation.format(new BigDouble("1e40964")));
        assertEquals("5 minims short of a generous dram", notation.format(new BigDouble("1e61446")));
        assertEquals("6 generous drams and 44 minims", notation.format(new BigDouble("1e92169")));
        assertEquals("a generous pint", notation.format(new BigDouble("1e138254")));
        assertEquals("a generous firkin and 2 gallons", notation.format(new BigDouble("1e207381")));
        assertEquals("12 large minims", notation.format(new BigDouble("1e311072")));
        assertEquals("42 large minims", notation.format(new BigDouble("1e466608")));
        assertEquals("4 large drams and 33 minims", notation.format(new BigDouble("1e699912")));
        assertEquals("a large cup and an ounce", notation.format(new BigDouble("1e1049868")));
        assertEquals("a large pin and a quart", notation.format(new BigDouble("1e1574802")));
        assertEquals("10 great minims", notation.format(new BigDouble("1e2362203")));
        assertEquals("33 great minims", notation.format(new BigDouble("1e3543305")));
        assertEquals("3 great drams and 10 minims", notation.format(new BigDouble("1e5314958")));
        assertEquals("a great gill and an ounce", notation.format(new BigDouble("1e7972437")));
        assertEquals("2 great gallons and a pint", notation.format(new BigDouble("1e11958656")));
        assertEquals("3 great tuns and 2 puncheons", notation.format(new BigDouble("1e17937984")));
        assertEquals("26 grand minims", notation.format(new BigDouble("1e26906976")));
        assertEquals("2 grand drams and 15 minims", notation.format(new BigDouble("1e40360464")));
        assertEquals("an ounce short of a grand gill", notation.format(new BigDouble("1e60540696")));
        assertEquals("a grand gallon", notation.format(new BigDouble("1e90811044")));
        assertEquals("a grand tun and 3 firkins", notation.format(new BigDouble("1e136216566")));
        assertEquals("21 huge minims", notation.format(new BigDouble("1e204324849")));
        assertEquals("a huge dram and 38 minims", notation.format(new BigDouble("1e306487274")));
        assertEquals("2 huge ounces", notation.format(new BigDouble("1e459730911")));
        assertEquals("a gill short of 2 huge quarts", notation.format(new BigDouble("1e689596367")));
        assertEquals("a huge puncheon and a firkin", notation.format(new BigDouble("1e1034394551")));
        assertEquals("17 gigantic minims", notation.format(new BigDouble("1e1551591827")));
        assertEquals("a gigantic dram and 13 minims", notation.format(new BigDouble("1e2327387741")));
        assertEquals("a gigantic ounce and 2 drams", notation.format(new BigDouble("1e3491081612")));
        assertEquals("a gigantic quart", notation.format(new BigDouble("1e5236622418")));
        assertEquals("a gigantic kilderkin and a firkin", notation.format(new BigDouble("1e7854933627")));
        assertEquals("15 immense minims", notation.format(new BigDouble("1e11782400441")));
        assertEquals("5 minims short of an immense dram", notation.format(new BigDouble("1e17673600662")));
        assertEquals("6 immense drams and 47 minims", notation.format(new BigDouble("1e26510400993")));
        assertEquals("an immense pint and an ounce", notation.format(new BigDouble("1e39765601490")));
        assertEquals("an immense firkin and 3 gallons", notation.format(new BigDouble("1e59648402235")));
        assertEquals("12 colossal minims", notation.format(new BigDouble("1e89472603353")));
        assertEquals("42 colossal minims", notation.format(new BigDouble("1e134208905030")));
        assertEquals("4 colossal drams and 35 minims", notation.format(new BigDouble("1e201313357545")));
        assertEquals("a colossal cup and an ounce", notation.format(new BigDouble("1e301970036318")));
        assertEquals("a colossal pin and 2 quarts", notation.format(new BigDouble("1e452955054477")));
        assertEquals("10 vast minims", notation.format(new BigDouble("1e679432581716")));
        assertEquals("33 vast minims", notation.format(new BigDouble("1e1019148872574")));
        assertEquals("3 vast drams and 11 minims", notation.format(new BigDouble("1e1528723308861")));
        assertEquals("a vast gill and an ounce", notation.format(new BigDouble("1e2293084963292")));
        assertEquals("2 vast gallons and 3 cups", notation.format(new BigDouble("1e3439627444938")));
        assertEquals("3 vast tuns and 3 hogsheads", notation.format(new BigDouble("1e5159441167407")));
        assertEquals("26 galactic minims", notation.format(new BigDouble("1e7739161751111")));
        assertEquals("2 galactic drams and 16 minims", notation.format(new BigDouble("1e11608742626667")));
        assertEquals("an ounce short of a galactic gill", notation.format(new BigDouble("1e17413113940001")));
        assertEquals("a galactic gallon", notation.format(new BigDouble("1e26119670910002")));
        assertEquals("a galactic tun and a barrel", notation.format(new BigDouble("1e39179506365003")));
        assertEquals("21 cosmic minims", notation.format(new BigDouble("1e58769259547505")));
        assertEquals("a cosmic dram and 38 minims", notation.format(new BigDouble("1e88153889321258")));
        assertEquals("2 cosmic ounces", notation.format(new BigDouble("1e132230833981887")));
        assertEquals("almost 2 cosmic quarts", notation.format(new BigDouble("1e198346250972831")));
        assertEquals("a cosmic puncheon and 3 pins", notation.format(new BigDouble("1e297519376459247")));
        assertEquals("18 infinite minims", notation.format(new BigDouble("1e446279064688871")));
        assertEquals("an infinite dram and 13 minims", notation.format(new BigDouble("1e669418597033307")));
    }

    @Test
    public void testLargeWithCommas() {
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 0));
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 1));
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 2));
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 3));
    }

    @Test
    public void testLargeWithoutCommas() {
        Settings.showExponentCommas = false;
        BigDouble randomBigNum = new BigDouble("3.5826347327e289372384");
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 0));
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 1));
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 2));
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 3));
        assertEquals("a huge dram and 16 minims", notation.format(randomBigNum, 4));
    }

    @Test
    public void testNegative() {
        assertEquals("-1.00pL", notation.format(new BigDouble(-1), 2, 2));
        assertEquals("-2.00pL", notation.format(new BigDouble(-2), 2, 2));
        assertEquals("-3.00pL", notation.format(new BigDouble(-3), 2, 2));
        assertEquals("-4.00pL", notation.format(new BigDouble(-4), 2, 2));
        assertEquals("-5.00pL", notation.format(new BigDouble(-5), 2, 2));
        assertEquals("-6.00pL", notation.format(new BigDouble(-6), 2, 2));
        assertEquals("-7.00pL", notation.format(new BigDouble(-7), 2, 2));
        assertEquals("-8.00pL", notation.format(new BigDouble(-8), 2, 2));
        assertEquals("-9.00pL", notation.format(new BigDouble(-9), 2, 2));
        assertEquals("-10.00pL", notation.format(new BigDouble(-10), 2, 2));
        assertEquals("-100.00pL", notation.format(new BigDouble(-100), 2, 2));
    }

}
