package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

public class AllNotation extends Notation {
    public static final String name = "ALL";

    private static final double LOG2_FACTOR = 1 / Math.log10(2);
    private static final Notation[] NOTATIONS = new Notation[] {
            new ScientificNotation(),
            new LetterNotation(),
            new StandardNotation(),
            new LogarithmNotation(),
            new BracketsNotation(),
            new InfinityNotation(),
            new RomanNotation(),
            new DotsNotation(),
            new ZalgoNotation(),
            new HexNotation(),
            new ImperialNotation(),
            new ClockNotation(),
            new PrimeNotation(),
            new BarNotation(),
            new ShiNotation(),
            // new BlobNotation(), :blobsad:
            new BlindNotation()
    };

    // TODO: add configuration for


    @Override
    public String formatNegativeUnder1000(double value, int places) {
        return this.formatDecimal(new BigDouble(-value), places, places);
    }

    @Override
    public String formatUnder1000(double value, int places) {
        return this.formatDecimal(new BigDouble(value), places, places);
    }

    @Override
    public String formatNegativeDecimal(BigDouble value, int places, int placesUnder1000) {
        return this.formatDecimal(value.neg(), places, placesUnder1000);
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        int index = (int) (LOG2_FACTOR * Math.log10(value.abs().add(2).log2()));
        Notation notation = NOTATIONS[index % NOTATIONS.length];
        return notation.format(value, places, places, placesExponent);
    }
}
