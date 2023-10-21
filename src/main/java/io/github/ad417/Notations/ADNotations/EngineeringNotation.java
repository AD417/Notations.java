package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
import io.github.ad417.Notations.core.Util;

public class EngineeringNotation extends Notation {
    public static String name = "Engineering";

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return Util.formatMantissaWithExponent(
                Util::formatMantissaBaseTen, (n, p) -> this.formatExponent((long)n, p),
                10, 3, (x, p) -> Util.formatMantissaBaseTen(x, 0)
    ).format(value, places, placesExponent);
    }
}
