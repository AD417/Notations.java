package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Util;

/**
 * Standard Notation: the notation system you were probably taught in
 * middle school, taken to unreasonable extremes with large numbers.
 */
public class StandardNotation extends Notation {
    public static String name = "Standard";

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        // Since abbreviateStandard ignores places, there's no need for this notation
        // to either accept or not accept negative places values. It treats them as 0 either way.
        return Util.formatMantissaWithExponent(
                Util::formatMantissaBaseTen, (n, p) -> Util.abbreviateStandard((long)n),
                1000, 1, null,
                " ", true).format(value, places, placesExponent);
    }
}
