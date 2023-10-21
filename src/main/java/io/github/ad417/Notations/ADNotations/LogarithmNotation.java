package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

public class LogarithmNotation extends Notation {
    public static String name = "Logarithm";

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        double log10 = value.log10();
        return "e" + this.formatExponent(log10, places, (n, p) -> String.format("%."+p+"f", n), placesExponent);
    }
}
