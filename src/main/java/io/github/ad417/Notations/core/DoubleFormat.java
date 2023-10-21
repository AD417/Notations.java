package io.github.ad417.Notations.core;

public final class DoubleFormat {
    private DoubleFormat() {}

    public static String toFixed(double x, int digits) {
        if (digits < 0 || digits > 16) digits = 16;
        return String.format("%."+digits+"f", x);
    }

    public static String toPrecision(double x, int digits) {
        if (digits < 0 || digits > 16) digits = 16;
        return String.format("%."+digits+"g", x)
                .replace("e+", "e")
                .replace("e-", "e")
                .replace("e0", "e");
    }
}
