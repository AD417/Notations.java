package io.github.ad417.Notations.Format;

import java.text.DecimalFormat;

import io.github.ad417.BreakInfinity.BigDouble;
import org.jetbrains.annotations.Nullable;

public class Util {
    private static String addCommas(String value) {
        try {
            long num = Long.parseLong(value);
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid number
            return value;
        }
    }

    public static String formatWithCommas(double value) {
        return formatWithCommas(Double.toString(value));
    }

    public static String formatWithCommas(String value) {
        String[] decimalPointSplit = value.split("\\.");
        decimalPointSplit[0] = addCommas(decimalPointSplit[0]);
        return String.join(".", decimalPointSplit);
    }

    /**
     * Fixes cases like (9.6e3, 0), which results in "10e3" (but we need "1e4" instead)
     * because toFixed rounds numbers to the closest integer
     */
    public static BigDouble fixMantissaOverflow(
            BigDouble value,
            int places,
            double threshold,
            int powerOffset
    ) {
        double pow10 = Math.pow(10, places);
        boolean isOverflowing = Math.round(value.getMantissa() * pow10) >= threshold * pow10;
        if (isOverflowing) {
            return new BigDouble(1, value.getExponent() + powerOffset);
        }
        return value;
    }

    // TODO: toEngineering, toLongScale. toFixedEngineering, toFixedLongScale.

    public static boolean noSpecialFormatting(long exponent) {
        return exponent < Settings.lowerValueForExponentCommas;
    }

    public static boolean showCommas(long exponent) {
        return Settings.showExponentCommas && exponent < Settings.higherValueForExponentCommas;
    }

    public static boolean isExponentFullyShown(long exponent) {
        return noSpecialFormatting(exponent) || showCommas(exponent);
    }

    public static FormatDecimalCallback formatMantissaWithExponent(
            FormatCallback mantissaFormatting,
            FormatCallback exponentFormatting,
            int base,
            int steps,
            @Nullable FormatCallback mantissaFormattingIfExponentIsFormatted
    ) {
        return formatMantissaWithExponent(
                mantissaFormatting, exponentFormatting,
                base, steps,
                mantissaFormattingIfExponentIsFormatted,
                "e", false
        );
    }

    public static FormatDecimalCallback formatMantissaWithExponent(
            FormatCallback mantissaFormatting,
            FormatCallback exponentFormatting,
            int base,
            int steps,
            @Nullable FormatCallback mantissaFormattingIfExponentIsFormatted,
            String seperator
    ) {
        return formatMantissaWithExponent(
                mantissaFormatting, exponentFormatting,
                base, steps,
                mantissaFormattingIfExponentIsFormatted,
                seperator,
                false
        );
    }

    public static FormatDecimalCallback formatMantissaWithExponent(
            FormatCallback mantissaFormatting,
            FormatCallback exponentFormatting,
            int base,
            int steps,
            @Nullable FormatCallback mantissaFormattingIfExponentIsFormatted,
            String seperator,
            boolean forcePositiveExponent
    ) {
        // Thanks, I really, really, REALLY hate it.
        return new FormatDecimalCallback() {
            @Override
            public String format(BigDouble value, int precision, int precisionExponent) {
                double realBase = Math.pow(base, steps);
                long exponent = (long) Math.floor(value.log(realBase)) * steps;
                if (forcePositiveExponent) exponent = Math.max(exponent, 0);
                double mantissa = value.div(new BigDouble(base).pow(exponent)).toDouble();

                // The conditional !(1 <= mantissa && mantissa < realBase)
                // should be true only rarely, due to precision bugs
                // e.g. 0.8e1e15 has log which rounds to 1e15, but exponent should be 1e15 - 1
                // Edge cases are possible, of two types:
                // mantissa ends up at 0.999..., it is formatted as 1 and it's OK.
                // mantissa ends up at realBase + 0.000...1, it is formatted as base and then
                // the thing checking for it being formatted in that way steps in.
                // I think this always ends up pretty close to accurate though, with
                // inaccuracy being something like (realBase^(1e-16 * Math.log10(mantissa))).
                // mantissa should be at most roughly 10 so this is pretty small.
                // IDK if using Math.log or Math.log10 is faster.

                if (mantissa < 1 || mantissa >= realBase) {
                    int adjust = (int) Math.floor(Math.log(mantissa) / Math.log(realBase));
                    mantissa /= Math.pow(realBase, adjust);
                    exponent += (long) steps * adjust;
                }

                String m = mantissaFormatting.format(mantissa, precision);
                if (m.equals(mantissaFormatting.format(realBase, precision))) {
                    m = mantissaFormatting.format(1, precision);
                    exponent += steps;
                }
                // This can happen in some cases with a high exponent
                // (either due to high real base or high steps).
                if (exponent == 0) return m;

                // Note that with typical exponentFormatting being this.formatExponent.bind(this),
                // this will use at least precision 2 on the exponent if relevant, due to the default
                // value of largeExponentPrecision: number = Math.max(2, precision) in formatExponent.
                String e = exponentFormatting.format(exponent, precisionExponent);
                if (mantissaFormattingIfExponentIsFormatted != null && !isExponentFullyShown(exponent)) {
                    // No need to do a second check for roll-over.
                    m = mantissaFormattingIfExponentIsFormatted.format(mantissa, precision);
                }
                return m + seperator + e;
            }
        };
    }

    public static String formatMantissaBaseTen(double number, int precision) {
        double p = Math.max(precision, 0);
        return String.format("%."+p+"f", number);
    }
}
