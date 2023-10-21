package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

public class BarNotation extends Notation {
    public static final String name = "Bar";
    protected static final String NEGATIVE_INFINITE = "";
    protected static final String INFINITE = "";

    // The reason these have to be these non-bar characters and not the actual bars
    // is beyond me. However, you can trust that these will render correctly, as they
    // are part of the MonospaceTypewriter.ttf font.
    private static final String BARS = "";
    private static final String NEGATIVE_BARS = "";
    // Speeds up log math, I guess?
    private static final double LOG8FACTOR = Math.log(10) / Math.log(8);

    // Yet another precision-agnostic class. Ugh.
    @Override
    public String formatVerySmallNegativeDecimal(BigDouble value, int places) {
        return formatVerySmallNegativeDecimal(value);
    }
    public String formatVerySmallNegativeDecimal(BigDouble value) {
        return flipBars(formatDecimal(value));
    }

    @Override
    public String formatVerySmallDecimal(BigDouble value, int places) {
        return formatVerySmallDecimal(value);
    }
    public String formatVerySmallDecimal(BigDouble value) {
        return formatDecimal(value);
    }

    @Override
    public String formatNegativeUnder1000(double value, int places) {
        return formatNegativeUnder1000(value);
    }
    public String formatNegativeUnder1000(double value) {
        return flipBars(formatDecimal(new BigDouble(value)));
    }

    @Override
    public String formatUnder1000(double value, int places) {
        return formatUnder1000(value);
    }
    public String formatUnder1000(double value) {
        return this.formatDecimal(new BigDouble(value));
    }

    @Override
    public String formatNegativeDecimal(BigDouble value, int places, int placesExponent) {
        return formatNegativeDecimal(value);
    }
    public String formatNegativeDecimal(BigDouble value) {
        return flipBars(formatDecimal(value));
    }




    public String formatDecimal(BigDouble value) {
        return formatDecimal(value, 0, 0);
    }
    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.eq(0)) return "0";
        if (value.lt(1) && value.gt(0)) {
            return "/" + formatDecimal(value.recip(), places, placesExponent);
        }

        // TODO: value might not fit in a long???
        double log8 = LOG8FACTOR * value.log10();
        long wholeLog = (long) Math.floor(log8);
        double decimalLog = log8 - wholeLog;
        int decimalLog64 = (int) (decimalLog * 64);

        StringBuilder sb = new StringBuilder();
        sb.append(BARS.charAt(decimalLog64 % 8));
        sb.append(BARS.charAt(decimalLog64 / 8));

        int remainder;
        while (wholeLog >= 8) {
            remainder = (int) (wholeLog % 8);
            sb.append(BARS.charAt(remainder));
            wholeLog /= 8;
        }
        sb.append(BARS.charAt((int)wholeLog));

        return sb.toString();
    }

    private String flipBars(String positiveBars) {
        StringBuilder sb = new StringBuilder();
        char[] chars = positiveBars.toCharArray();
        for (char c : chars) {
            sb.append(NEGATIVE_BARS.charAt(BARS.indexOf(c)));
        }
        return sb.toString();
    }
}
