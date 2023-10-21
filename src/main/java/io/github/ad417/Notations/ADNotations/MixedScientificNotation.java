package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
/**
 * Mixed Engineering Notation - Uses {@link StandardNotation} below 1e33,
 * and {@link ScientificNotation} above it.
 */
public class MixedScientificNotation extends Notation {
    public static final String name = "Mixed Scientific";

    private final Notation scientific = new ScientificNotation();
    private final Notation standard = new StandardNotation();

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.getExponent() < 33) {
            return standard.formatDecimal(value, places, placesExponent);
        }
        return scientific.formatDecimal(value, places, placesExponent);
    }
}
