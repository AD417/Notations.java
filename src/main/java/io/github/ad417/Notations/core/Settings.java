package io.github.ad417.Notations.core;

import io.github.ad417.BreakInfinity.BigDouble;

public final class Settings {
    public static boolean isInfinite(BigDouble value) {
        // TODO: might be redundant?
        return value.gte(BigDouble.POSITIVE_INFINITY);
    }

    public static boolean showExponentCommas = true;
    public static long lowerValueForExponentCommas = 100_000;
    public static long higherValueForExponentCommas = 1_000_000_000;
}
