package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

/**
 * Clock Notation - Uses unicode clocks to represent large values. Of note is
 * that all BigDouble-sized values can be represented using at most 4 clocks.
 */
public class ClockNotation extends Notation {
    public static final String name = "Clock";
    protected static final String INFINITE = "🕛🕡";
    protected static final String NEGATIVE_INFINITE = "-🕛🕡";

    private static final String[] HOURS = new String[] {
            "🕛", "🕐", "🕑", "🕒", "🕓", "🕔", "🕕", "🕖", "🕗", "🕘", "🕙", "🕚"
    };
    private static final double LOG12 = Math.log10(12);


    @Override
    public String formatUnder1000(double value, int places) {
        return clockwise(new BigDouble(value));
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return clockwise(value);
    }

    private String clockwise(BigDouble value) {
        if (value.lt(12)) return hour(value.toDouble());
        // Say the clock goes 0123456789ab. A single digit covers 0 through 11.
        // Two digits (clocks) go 00, 01, 02... 0b, 10, 11, ... bb
        // The leading digit is distinct from the absence of a clock. So, if we make
        // 00 be 12, and go up by multiples of 12, then 01 is 24, 02 is 36, etc; 0b is then
        // 144.
        // This, of course, is confusing, because we'd expect 10 to be 144. So, we go up
        // by multiples of 11 instead. Thus, we have 00 = 12, 01 = 23, 02 = 34, ... 0b = 133
        // Then we get to 10, 11, 12, etc. Same issue here -- so we go up by 132's (11*12).
        // Eventually, b0 is 12^12 == 8916100448256, so the limit of two digits is 12^13
        // With 3 clocks, we continue a scientific-like notation, with 2 clocks for the
        // exponent. The smallest exponent we need to show is 13; that's 00; so bb (143)
        // will be 156
        // 4 clocks, in general, is hyper scientific. However, when the fourth clock shows
        // up (showing 0), we do another cycle of scientific (so, mantissa + 2 exponent digits)
        // This gives another 144 possible values for the exponent, which gets to 300.

        // Did you get any of that?
        // Me neither.

        double log = value.log10() / LOG12;
        long exponent = (long) Math.floor(log);
        if (log < 301) {
            double clockLow = Math.pow(12, log - exponent + 1) - 12;
            clockLow /= 11;
            if (exponent < 13) {
                return hour(exponent - 1) + hour(clockLow);
            }
            exponent -= 13;
            String prefix = "";
            if (exponent >= 144) {
                prefix = hour(0);
                exponent -= 144;
            }
            return prefix + hour(exponent / 12.0) + hour(exponent % 12) + hour(clockLow);
        }
        // With the first clock of 4 showing 1, we do 3 digits of exponent. So, 000 is 301, and bbb
        // is 2028. Past that, we go by 12's, and so on.
        exponent -= 301;
        int clockHigh = 1;
        while (exponent >= 1728) {
            exponent = (exponent - 1728) / 12;
            clockHigh++;
        }
        return this.hour(clockHigh) + this.hour(exponent / 144.0) +
                this.hour(exponent % 144 / 12.0) + this.hour(exponent % 12);


    }

    private String hour(double number) {
        int index = (int) Math.max(Math.min(Math.floor(number), 11), 0);
        return HOURS[index];
    }
}
