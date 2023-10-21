package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

/**
 * Blind notation: makes you blind, you can't see the number. Lol
 */
public class BlindNotation extends Notation {
    public static final String name = "Blind";
    protected static final String NEGATIVE_INFINITE = " ";
    protected static final String INFINITE = " ";

    @Override
    public String formatNegativeDecimal(BigDouble value, int places, int placesExponent) {
        return " ";
    }

    @Override
    public String formatNegativeUnder1000(double value, int places) {
        return " ";
    }

    @Override
    public String formatUnder1000(double value, int places) {
        return " ";
    }

    @Override
    public String formatVerySmallNegativeDecimal(BigDouble value, int places) {
        return " ";
    }

    @Override
    public String formatVerySmallDecimal(BigDouble value, int places) {
        return " ";
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return " ";
    }
}
