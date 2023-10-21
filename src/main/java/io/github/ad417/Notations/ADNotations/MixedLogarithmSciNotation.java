package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;
/**
 * Mixed Engineering Notation - Uses {@link ScientificNotation} below 1e33,
 * and {@link LogarithmNotation} above it.
 */
public class MixedLogarithmSciNotation extends Notation {
    public static final String name = "Mixed Logarithm (Sci)";

    private final Notation logarithm = new LogarithmNotation();
    private final Notation scientific = new ScientificNotation();

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.getExponent() < 33) {
            return scientific.formatDecimal(value, places, placesExponent);
        }
        return logarithm.formatDecimal(value, places, placesExponent);
    }
}
