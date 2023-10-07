package io.github.ad417.Notations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.Format.FormatDecimalCallback;
import io.github.ad417.Notations.Format.Settings;
import io.github.ad417.Notations.Format.Util;

public class ScientificNotation extends Notation{
    protected static String name = "Scientific";

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return Util.formatMantissaWithExponent(
                Util::formatMantissaBaseTen,
                (n, p) -> this.formatExponent((long)n, p),
                10,
                1,
                (x, __) -> Util.formatMantissaBaseTen(x, 0)
        ).format(value, places, placesExponent);
    }


    public static void main(String[] args) {
        Settings.showExponentCommas = false;
        Notation scientific = new ScientificNotation();
        BigDouble bigSNum = new BigDouble("3.5826347327e289372384");
        System.out.println(scientific.format(bigSNum, 0));
    }
}
