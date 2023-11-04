package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

/**
 * Roman Notation - formats values as Roman numerals. Large values are shown in
 * a quasi-scientific notation.
 */
public class RomanNotation extends Notation {
    public static final String name = "Roman";

    @Override
    protected String getInfinity() {
        return "Infinitus";
    }

    private static final int[] ROMAN_VALUES = new int[] {
            1000000, 900000, 500000, 400000, 100000,
            90000, 50000, 40000, 10000, 9000, 5000,
            4000, 1000, 900, 500, 400, 100, 90, 50,
            40, 10, 9, 5, 4, 1
    };
    private static final String[] ROMAN_CHARACTERS = new String[] {
            "M̄", "C̄M̄", "D̄",  "C̄D̄", "C̄",
            "X̄C̄", "L̄", "X̄L̄", "X̄", "ⅯX̄",
            "V̄", "ⅯV̄", "Ⅿ", "ⅭⅯ", "Ⅾ",
            "ⅭⅮ", "Ⅽ", "ⅩⅭ", "Ⅼ", "ⅩⅬ",
            "Ⅹ", "ⅠⅩ", "Ⅴ", "ⅠⅤ", "Ⅰ"
    };
    private static final String[] ROMAN_FRACTIONS = new String[] {
            "", "·", ":", "∴", "∷", "⁙"
    };
    private static final int MAXIMUM = 4_000_000;
    private static final double MAX_LOG_10 = Math.log10(MAXIMUM);

    @Override
    public String formatUnder1000(double value, int places) {
        return romanize(value);
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.lt(MAXIMUM)) return romanize(value.toDouble());

        double log10 = value.log10();
        double maximums = log10 / MAX_LOG_10;
        double current = Math.pow(MAXIMUM, maximums - Math.floor(maximums));

        return romanize(current) + "↑" + formatDecimal(new BigDouble(maximums), 0, 0);
    }

    private String romanize(double value) {
        StringBuilder sb = new StringBuilder();
        double decimal;
        String roman;
        for (int i = 0; i < ROMAN_VALUES.length; i++) {
            decimal = ROMAN_VALUES[i];
            roman = ROMAN_CHARACTERS[i];
            while (decimal <= value) {
                sb.append(roman);
                value -= decimal;
            }
        }

        int duodecimal = (int) Math.round(Math.floor(value * 10) * 1.2);
        if (duodecimal == 0) {
            if (sb.isEmpty()) return "nulla";
            return sb.toString();
        }
        if (duodecimal > 5) {
            duodecimal -= 6;
            sb.append("Ｓ");
        }
        sb.append(ROMAN_FRACTIONS[duodecimal]);
        return sb.toString();
    }
}
