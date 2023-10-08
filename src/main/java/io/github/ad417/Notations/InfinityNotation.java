package io.github.ad417.Notations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.Format.Settings;
import io.github.ad417.Notations.Format.Util;

public class InfinityNotation extends Notation {
    public static final String name = "Infinity";

    private static final double LOG10_MAX_VALUE = Math.log10(Double.MAX_VALUE);

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        double log10 = value.log10();
        double infinities = log10 / LOG10_MAX_VALUE;
        int infPlaces = infinities < 1000 ? 4 : 3;
        infPlaces = Math.max(infPlaces, places);
        String formatted = String.format("%."+infPlaces+"f", infinities);
        if (Settings.showExponentCommas) {
            return Util.formatWithCommas(formatted) + "∞";
        }
        return formatted + "∞";
    }
}
