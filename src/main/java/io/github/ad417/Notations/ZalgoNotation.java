package io.github.ad417.Notations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.Format.DoubleFormat;
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
        double scaled = value.add(1).log10() / 66666 * 1000;

        // Absolute String bullshit is what's going on here.

        // In the original code, we convert scaled to a fixed value with 2dp, and then convert
        // it immediately back into a Number. how useless. The display part needs to be a
        // String anyways because it gets formatted with Commas.
        // We prune trailing zeroes because that's what should happen in the final representation.
        String displayPart = DoubleFormat.toFixed(scaled, 2).replaceAll("\\.?0*$", "");

        // The Zalgo part is fairly straightforward, compared to the original code.
        int zalgoPart = (int) (Math.abs(Math.pow(2, 30) * (scaled - Double.parseDouble(displayPart))));

        // TODO: make formatWithCommas use a more JS-compliant toString method.
        //  I think it converts the int part, if possible, and then adds on
        //  the decimal part if possible afterwards.

        // "displayPart" is kept as a String so this bit is less painless.
        String[] displayChars = Util.formatWithCommas(displayPart).split("");
        String[] zalgoIndices = (zalgoPart + DoubleFormat.toFixed(scaled, 0)).split("");

        int zalgoIndex;
        int displayIndex;
        for (int i = 0; i < zalgoIndices.length; i++) {
            zalgoIndex = Integer.parseInt(zalgoIndices[i]);
            displayIndex = (37 * i) % displayChars.length;
            displayChars[displayIndex] += ZALGO_CHARS[zalgoIndex];
        }

        StringBuilder sb = new StringBuilder();
        for (String s : displayChars) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ZalgoNotation n = new ZalgoNotation();
        System.out.println(DoubleFormat.toFixed(10344048.945489455, 2));
        System.out.println(n.heComes(new BigDouble("1e689596367")));
    }
}
