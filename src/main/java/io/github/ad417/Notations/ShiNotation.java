package io.github.ad417.Notations;

import io.github.ad417.BreakInfinity.BigDouble;

public class ShiNotation extends Notation {
    public static final String name = "Shi";

    // The joke is that all these characters are pronounced "Shi".
    private static final String[] SHI =
            "世使侍勢十史嗜士始室實屍市恃拭拾施是時氏濕獅矢石視試詩誓識逝適釋食".split("");

    @Override
    public String formatUnder1000(double value, int places) {
        return shi(new BigDouble(value));
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return shi(value);
    }

    private String getShiCharacter(double value) {
        return SHI[(int) (Math.floor(value) % SHI.length)];
    }

    private String shi(BigDouble value) {
        double scaled = Math.pow(value.plus(1).log10() * 1000, 0.08);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(getShiCharacter(scaled * Math.pow(SHI.length, i)));
        }
        return sb.toString();
    }
}
