package io.github.ad417.Notations;

import io.github.ad417.BreakInfinity.BigDouble;

public class LogarithmNotation extends Notation {
    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        double log10 = value.log10();
        return "e" + this.formatExponent(log10, places, (n, p) -> String.format("%."+p+"f", n), placesExponent);
    }
}
