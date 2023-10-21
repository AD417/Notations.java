package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

public class DotsNotation extends Notation {
    public static final String name = "Dots";

    private static final String[] DOTS = (
            "⠀⠁⠂⠃⠄⠅⠆⠇⠈⠉⠊⠋⠌⠍⠎⠏⠐⠑⠒⠓⠔⠕⠖⠗⠘⠙⠚⠛⠜⠝⠞⠟⠠⠡⠢⠣⠤⠥⠦⠧⠨⠩⠪⠫⠬⠭⠮⠯⠰⠱⠲⠳⠴⠵⠶⠷⠸⠹⠺⠻⠼⠽⠾⠿" +
            "⡀⡁⡂⡃⡄⡅⡆⡇⡈⡉⡊⡋⡌⡍⡎⡏⡐⡑⡒⡓⡔⡕⡖⡗⡘⡙⡚⡛⡜⡝⡞⡟⡠⡡⡢⡣⡤⡥⡦⡧⡨⡩⡪⡫⡬⡭⡮⡯⡰⡱⡲⡳⡴⡵⡶⡷⡸⡹⡺⡻⡼⡽⡾⡿" +
            "⢀⢁⢂⢃⢄⢅⢆⢇⢈⢉⢊⢋⢌⢍⢎⢏⢐⢑⢒⢓⢔⢕⢖⢗⢘⢙⢚⢛⢜⢝⢞⢟⢠⢡⢢⢣⢤⢥⢦⢧⢨⢩⢪⢫⢬⢭⢮⢯⢰⢱⢲⢳⢴⢵⢶⢷⢸⢹⢺⢻⢼⢽⢾⢿" +
            "⣀⣁⣂⣃⣄⣅⣆⣇⣈⣉⣊⣋⣌⣍⣎⣏⣐⣑⣒⣓⣔⣕⣖⣗⣘⣙⣚⣛⣜⣝⣞⣟⣠⣡⣢⣣⣤⣥⣦⣧⣨⣩⣪⣫⣬⣭⣮⣯⣰⣱⣲⣳⣴⣵⣶⣷⣸⣹⣺⣻⣼⣽⣾⣿"
    ).split("");

    protected static final String INFINITE = "⣿⠀⣿";

    @Override
    public String formatUnder1000(double value, int places) {
        return dotify(value * 254);
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.lt(16387063.9980315)) {
            return this.dotify(value.toDouble() * 254);
        }
        double log = value.log(254);
        double exponent = (long) log - 2;
        double mantissa = Math.pow(254, log - exponent);
        return dotify(exponent) + "⣿" + dotify(mantissa * 254);
    }

    private String dotify(double rawValue) {
        return dotify(rawValue, false);
    }

    private String dotify(double rawValue, boolean pad) {
        double value = Math.round(rawValue);
        if (!pad && value < 254) {
            return DOTS[(int)value + 1];
        }
        if (value < 64516) {
            return DOTS[(int)value / 254 + 1] + DOTS[(int)value % 254 + 1];
        }
        return this.dotify(Math.floor(value / 64516)) + this.dotify(value % 64516, true);
    }
}
