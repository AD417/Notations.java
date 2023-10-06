package io.github.ad417.Notations.Format;

import java.text.DecimalFormat;
import io.github.ad417.BreakInfinity.BigDouble;

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
}
