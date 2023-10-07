package io.github.ad417.Notations;

import io.github.ad417.Notations.Format.FormatCallback;
import io.github.ad417.Notations.Format.Util;
import io.github.ad417.BreakInfinity.BigDouble;

import java.text.Format;

@SuppressWarnings("unused")
public abstract class Notation {
    protected static final String INFINITE = "Infinite";
    protected static final String NEGATIVE_INFINITE = "-Infinite";
    protected static String name;

    public final String format(BigDouble value) {
        return format(value, 0, 0, 0);
    }

    public final String format(BigDouble value, int places) {
        return format(value, places, 0, places);
    }

    public final String format(BigDouble value, int places, int placesUnder1000) {
        return format(value, places, placesUnder1000, places);
    }

    public final String format(BigDouble value, int places, int placesUnder1000, int placesExponent) {
        if (BigDouble.isInfinite(value)) {
            return value.sign() < 0 ? NEGATIVE_INFINITE : INFINITE;
        }

        if (value.getExponent() < -300) {
            return value.sign() < 0
                    ? formatVerySmallNegativeDecimal(value.abs(), placesUnder1000)
                    : formatVerySmallDecimal(value, placesUnder1000);
        }

        if (value.getExponent() < 3) {
            double number = value.toDouble();
            return number < 0
                    ? formatNegativeUnder1000(Math.abs(number), placesUnder1000)
                    : formatUnder1000(number, placesUnder1000);
        }

        // TODO: Format.Settings.isInfinite()

        return value.sign() < 0
                ? formatNegativeDecimal(value.abs(), places, placesExponent)
                : this.formatDecimal(value.abs(), places, placesExponent);
    }

    public String formatVerySmallNegativeDecimal(BigDouble value, int places) {
        return "-" + formatVerySmallDecimal(value, places);
    }

    public String formatVerySmallDecimal(BigDouble value, int places) {
        // We switch to very small formatting below 1e-300 due to precision loss,
        // so value.toDouble() might not be zero.
        return formatUnder1000(value.toDouble(), places);
    }

    public String formatNegativeUnder1000(double value, int places) {
        return "-" + formatUnder1000(value, places);
    }

    public String formatUnder1000(double value, int places) {
        // TODO: toFixed(double value, int places)
        return String.format("%."+places+"f", value);
    }

    public String formatNegativeDecimal(BigDouble value, int places, int placesExponent) {
        return "-" + formatDecimal(value, places, placesExponent);
    }

    public abstract String formatDecimal(BigDouble value, int places, int placesExponent);

    protected final String formatExponent(long exponent) {
        // TODO: Format.Settings.exponentDefaultPlaces;
        return formatExponent(exponent, 0, (n, p) -> String.format("%.0f", n), 2);
    }

    protected final String formatExponent(long exponent, int precision) {
        return formatExponent(exponent, precision, (n, p) -> String.format("%.0f", n), Math.max(precision, 2));
    }

    protected final String formatExponent(
            long exponent, int precision,
            FormatCallback specialFormat
    ) {
        return formatExponent(exponent, precision, specialFormat, Math.max(precision, 2));
    }

    protected final String formatExponent(
            double exponent,
            int precision,
            FormatCallback specialFormat,
            int largeExponentPrecision
    ) {
        if (Util.noSpecialFormatting(exponent)) {
            return specialFormat.format(exponent, Math.max(precision, 1));
        }
        if (Util.showCommas(exponent)) {
            return Util.formatWithCommas(specialFormat.format(exponent, 0));
        }
        return formatDecimal(new BigDouble(exponent), largeExponentPrecision, largeExponentPrecision);
    }

}
