package io.github.ad417.Notations.core;

import io.github.ad417.BreakInfinity.BigDouble;

/**
 * Abstract notation class containing the basic framework for all notations
 * defined in the Notations library.
 * @see #format(BigDouble, int, int, int)
 */
@SuppressWarnings("unused")
public abstract class Notation {
    /**
     * Representation of Infinity in this notation -- any number deemed
     * too large to represent.
     */
    protected static final String INFINITE = "Infinite";
    /**
     * Representation of Negative Infinity in this notation -- any number
     * deemed too large in magnitude to represent, but negative.
     */
    protected static final String NEGATIVE_INFINITE = "-Infinite";
    /** The name of this notation */
    public static String name;

    /**
     * Format a BigDouble value in this notation.
     * @param value the value to format.
     * @return a String, formatted in this notation.
     * @see #format(BigDouble, int, int, int)
     */
    public final String format(BigDouble value) {
        return format(value, 0, 0, 0);
    }

    /**
     * Format a BigDouble value in this notation with the given places parameters.
     * @param value the value to format.
     * @param places the amount of precision the mantissa should have, in
     *               values past the decimal place.
     * @return a String, formatted in this notation with the given places
     * parameters.
     * @see #format(BigDouble)
     * @see #format(BigDouble, int, int)
     * @see #format(BigDouble, int, int, int)
     */
    public final String format(BigDouble value, int places) {
        return format(value, places, 0, places);
    }

    /**
     * Format a BigDouble value in this notation with the given places parameters.
     * @param value the value to format.
     * @param places the amount of precision the mantissa should have, in
     *               values past the decimal place.
     * @param placesUnder1000 the amount of precision the mantissa should
     *                        have if the value is less than 1000, also in
     *                        values past the decimal place.
     * @return a String, formatted in this notation with the given places
     * parameters.
     * @see #format(BigDouble)
     * @see #format(BigDouble, int)
     * @see #format(BigDouble, int, int, int)
     */
    public final String format(BigDouble value, int places, int placesUnder1000) {
        return format(value, places, placesUnder1000, places);
    }

    /**
     * Format a BigDouble value in this notation with the given places parameters.
     * @param value the value to format.
     * @param places the amount of precision the mantissa should have, in
     *               values past the decimal place.
     * @param placesUnder1000 the amount of precision the mantissa should
     *                        have if the value is less than 1000, also in
     *                        values past the decimal place.
     * @param placesExponent the amount of precision the exponent should have.
     * @return a String, formatted in this notation with the given places
     * parameters.
     */
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

    /**
     * Special case for formatting negative values that may be too small to
     * safely represent using Doubles.
     * @param value the value to format.
     * @param places the amount of precision the mantissa should have.
     * @return a String, formatted in this notation with the given places
     * parameters.
     */
    public String formatVerySmallNegativeDecimal(BigDouble value, int places) {
        return "-" + formatVerySmallDecimal(value, places);
    }

    /**
     * Special case for formatting values that may be too small to safely
     * represent using Doubles.
     * @param value the value to format.
     * @param places the amount of precision the mantissa should have.
     * @return a String, formatted in this notation with the given places
     * parameters.
     */
    public String formatVerySmallDecimal(BigDouble value, int places) {
        // We switch to very small formatting below 1e-300 due to precision loss,
        // so value.toDouble() might not be zero.
        return formatUnder1000(value.toDouble(), places);
    }

    /**
     * Format negative values that are above -1000. Typically, values
     * less in magnitude than 1000 are not formatted by many notations.
     * @param value the value to format. Note that it is a double.
     * @param places the amount of precision the mantissa should have.
     * @return a String, formatted in this notation with the given places
     * parameters.
     */
    public String formatNegativeUnder1000(double value, int places) {
        return "-" + formatUnder1000(value, places);
    }

    /**
     * Format values that are under 1000. Typically, values less than 1000 are
     * not formatted by many notations.
     * @param value the value to format. Note that it is a double.
     * @param places the amount of precision the mantissa should have.
     * @return a String, formatted in this notation with the given places
     * parameters.
     */
    public String formatUnder1000(double value, int places) {
        // TODO: toFixed(double value, int places)
        return String.format("%."+places+"f", value);
    }

    /**
     * Format values that are less than -1000.
     * @param value the value to format.
     * @param places the amount of precision the mantissa should have.
     * @param placesExponent the amount of precision the exponent should have.
     * @return a String, formatted in this notation with the given places
     * parameters.
     */
    public String formatNegativeDecimal(BigDouble value, int places, int placesExponent) {
        return "-" + formatDecimal(value, places, placesExponent);
    }

    /**
     * Format values that are greater than 1000. All notations usually defer
     * to this method, and must implement it in subclasses.
     * @param value the value to format.
     * @param places the amount of precision the mantissa should have.
     * @param placesExponent the amount of precision the exponent should have.
     * @return a String, formatted in this notation with the given places
     * parameters.
     */
    public abstract String formatDecimal(BigDouble value, int places, int placesExponent);

    /**
     * Convenience exponent formatting for certain notations (Log notation)
     * that need to format exponents in unusual ways.
     * @param exponent the exponent to format.
     * @return a formatted exponent.
     * @see #formatExponent(double, int, FormatCallback, int)
     */
    protected final String formatExponent(double exponent) {
        // TODO: Format.Settings.exponentDefaultPlaces;
        // TODO: String.format can be replaced with toFixed.
        return formatExponent(exponent, 0, (n, p) -> String.format("%.0f", n), 2);
    }

    /**
     * Convenience exponent formatting for certain notations (Log notation)
     * that need to format exponents in unusual ways.
     * @param exponent the exponent to format.
     * @param precision the number of digits of precision necessary.
     * @return a formatted exponent.
     * @see #formatExponent(double, int, FormatCallback, int)
     */
    protected final String formatExponent(double exponent, int precision) {
        return formatExponent(exponent, precision, (n, p) -> String.format("%.0f", n), Math.max(precision, 2));
    }

    /**
     * Convenience exponent formatting for certain notations (Log notation)
     * that need to format exponents in unusual ways.
     * @param exponent the exponent to format.
     * @param precision the number of digits of precision necessary.
     * @param specialFormat a formatting callback / lambda used in certain
     *                      circumstances.
     * @return a formatted exponent.
     * @see #formatExponent(double, int, FormatCallback, int)
     */
    protected final String formatExponent(
            double exponent, int precision,
            FormatCallback specialFormat
    ) {
        return formatExponent(exponent, precision, specialFormat, Math.max(precision, 2));
    }

    /**
     * Convenience exponent formatting for certain notations (Log notation)
     * that need to format exponents in unusual ways.
     * @param exponent the exponent to format.
     * @param precision the number of digits of precision necessary.
     * @param specialFormat a formatting callback / lambda used in certain
     *                      circumstances.
     * @param largeExponentPrecision the amount of precision for a very large
     *                               exponent.
     * @return a formatted exponent.
     */
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
