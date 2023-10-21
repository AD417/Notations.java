package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Util;

/**
 * Scientific Notation - the simplest notation, formatting a value as
 * MMMeXXX, where M is the mantissa (between 1 and 10) and X is the
 * exponent (some integer). The value is therefore MMM * 10<sup>XXX</sup>.
 */
public class ScientificNotation extends Notation {
    public static String name = "Scientific";

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return Util.formatMantissaWithExponent(
                Util::formatMantissaBaseTen,
                (n, p) -> this.formatExponent((long)n, p),
                10,
                1,
                (x, __) -> Util.formatMantissaBaseTen(x, 0)
        ).format(value, places, placesExponent);
    }
}
