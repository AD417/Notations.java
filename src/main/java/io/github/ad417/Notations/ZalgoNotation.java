package io.github.ad417.Notations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.Format.Util;

public class ZalgoNotation extends Notation {
    public static final String name = "Zalgo";

    private static final String[] ZALGO_CHARS = new String[] {
            "\u030D", "\u0336", "\u0353", "\u033F", "\u0489",
            "\u0330", "\u031A", "\u0338", "\u035A", "\u0337"
    };
    private static String makeInfinity() {
        String[] heComes = "HE COMES".split("");
        StringBuilder sb = new StringBuilder();
        for (String s : heComes) {
            sb.append(s);
            // TODO: What dereference?
            // Also just adds to the spookiness of this code.
            for (String z : ZalgoNotation.ZALGO_CHARS) sb.append(z);
        }
        return sb.toString();
    }


    protected static final String INFINITE = makeInfinity();
    protected static final String NEGATIVE_INFINITE = "-" + INFINITE;

    @Override
    public String formatUnder1000(double value, int places) {
        return heComes(new BigDouble(value));
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return heComes(new BigDouble(value));
    }

    private String heComes(BigDouble value) {
        double scaled = value.add(1).log10() / 66.666;
        double displayPart = Math.round(scaled * 100.0) / 100.0;
        // 1073741824 = 2 ^ 30
        int zalgoPart = (int) Math.abs(1073741824 * scaled - displayPart);

        String[] displayChars = Util.formatWithCommas(displayPart).split("");
        String[] zalgoIndices = String.format("%s%.0f", zalgoPart, scaled).split("");

        int zalgoIndex;
        int displayIndex;
        for (int i = 0; i < zalgoIndices.length; i++) {
            zalgoIndex = Integer.parseInt(zalgoIndices[i]);
            displayIndex = 37 * i % displayChars.length;
            displayChars[displayIndex] += ZALGO_CHARS[zalgoIndex];
        }

        StringBuilder sb = new StringBuilder();
        for (String s : displayChars) {
            sb.append(s);
        }
        return sb.toString();
    }
}
