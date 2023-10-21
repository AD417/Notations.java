package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

/**
 * Mixed Engineering Notation - Uses {@link StandardNotation} below 1e33,
 * and {@link EngineeringNotation} above it.
 */
public class MixedEngineeringNotation extends Notation {
    public static final String name = "Mixed Engineering";

    private final Notation engineering = new EngineeringNotation();
    private final Notation standard = new StandardNotation();

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.getExponent() < 33) {
            return standard.formatDecimal(value, places, placesExponent);
        }
        return engineering.formatDecimal(value, places, placesExponent);
    }
}
