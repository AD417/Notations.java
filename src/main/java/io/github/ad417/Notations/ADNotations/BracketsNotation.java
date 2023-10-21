package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

/**
 * Brackets Notation - uses the 6 brackets ([{}]) to represent values.
 * Effectively logarithm notation base 6 with the brackets as digits.
 */
public class BracketsNotation extends Notation {
    public static final String name = "Brackets";

    private static final String[] BRACKETS = new String[] {
            ")", "[", "{", "]", "(", "}"
    };

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        // Edge case avoidance: log6(value) might exceed long limit.
        double log6 = value.log(6);
        double wholePartOfLog = Math.floor(log6);
        double decimalPartOfLog = log6 - wholePartOfLog;
        // Easier to convert a number between 0-35 to base 6 than messing with fractions and shit
        int decimalPartTimes36 = (int)(decimalPartOfLog * 36);

        StringBuilder out = new StringBuilder();
        int remainder;
        while (wholePartOfLog >= 6) {
            remainder = (int) (wholePartOfLog % 6);
            wholePartOfLog -= remainder;
            wholePartOfLog /= 6;
            out.insert(0, BRACKETS[remainder]);
        }
        out.insert(0, BRACKETS[(int)wholePartOfLog]);
        out.insert(0, "e");
        out.append(".");
        out.append(BRACKETS[decimalPartTimes36 / 6]);
        out.append(BRACKETS[decimalPartTimes36 % 6]);
        return out.toString();
    }
}
