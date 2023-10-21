package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

public class MixedLogarithmNotation extends Notation {
    public static final String name = "Mixed Logarithm";

    private final Notation logarithm = new LogarithmNotation();
    private final Notation standard = new StandardNotation();

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.getExponent() < 33) {
            return standard.formatDecimal(value, places, placesExponent);
        }
        return logarithm.formatDecimal(value, places, placesExponent);
    }
}
