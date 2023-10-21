package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

/**
 * Consult  <a href="https://dan-simon.github.io/misc/what-the-hex/">Dan Simon's page</a>
 * for an appropriate summary of this notation.
 */
public class HexNotation extends Notation {
    public static final String name = "Hex";

    protected static final String INFINITE = "FFFFFFFF";
    protected static final String NEGATIVE_INFINITE = "00000000";

    @Override
    public String formatVerySmallNegativeDecimal(BigDouble value, int places) {
        return formatDecimal(value.negate());
    }

    @Override
    public String formatVerySmallDecimal(BigDouble value,  int places) {
        return formatDecimal(value);
    }

    @Override
    public String formatNegativeUnder1000(double value, int places) {
        return this.formatDecimal(new BigDouble(-value));
    }

    @Override
    public String formatUnder1000(double value, int places) {
        return this.formatDecimal(new BigDouble(value));
    }

    @Override
    public String formatNegativeDecimal(BigDouble value, int places, int placesExponent) {
        return this.formatDecimal(value.negate());
    }

    public String formatDecimal(BigDouble value) {
        // Java compiler compliance -- no default parameters.
        // Places are irrelevant in this notation! Come on!
        return formatDecimal(value, 0, 0);
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        String rawString = Integer.toHexString(rawHexValue(value, 32)).toUpperCase();
        rawString = "00000000" + rawString;
        return rawString.substring(rawString.length() - 8);
    }

    private int rawHexValue(BigDouble inputValue, int numberOfBits) {
        BigDouble value = inputValue;
        // The original uses an array. How about bit magic?
        int output = 0;

        int i;
        for (i = 0; i < numberOfBits; i++) {
            if (BigDouble.isInfinite(value)) break;
            output <<= 1;
            if (value.lessThan(0)) {
                output |= 0;
                value = modifiedLogarithm(value.neg()).neg();
            } else {
                output |= 1;
                value = modifiedLogarithm(value);
            }
        }
        output <<= numberOfBits - i;

        // This conditional is just here for correct rounding.
        if (output != Math.pow(2, numberOfBits) - 1 &&
                (value.gt(0) || (value.eq(0) && (output & 1) == 1))) {
            output += 1;
        }
        return output;
    }

    private BigDouble modifiedLogarithm(BigDouble x) {
        // This function implements a tweak to the usual logarithm.
        // It has the same value at powers of 2 but is linear between
        // powers of 2 (so for example, f(3) = 1.5).
        // Also, we can't use long because log2(x) > x.exponent.
        BigDouble floorOfLog = BigDouble.floor(x.log2());
        BigDouble previousPowerOfTwo = new BigDouble(2).pow(floorOfLog);
        BigDouble fractionToNextPowerOfTwo = x.div(previousPowerOfTwo).sub(1);
        return floorOfLog.add(fractionToNextPowerOfTwo);
    }
}
