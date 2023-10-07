package io.github.ad417.Notations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.Format.MantissaExponent;
import io.github.ad417.Notations.Format.Util;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("unused")
public class CustomNotation extends EngineeringNotation {
    public static final String name = "Custom";

    private final String[] letters;
    private final String mantissaExponentSeperator;
    private final String seperator;

    public CustomNotation(String[] letters) {
        this(letters, "", "");
    }
    public CustomNotation(String letters) {
        this(letters.split(""), "", "");
    }
    public CustomNotation(String letters, String mantissaExponentSeperator, String seperator) {
        this(letters.split(""), mantissaExponentSeperator, seperator);
    }
    public CustomNotation(String[] letters, String mantissaExponentSeperator, String seperator) {
        if (letters.length < 2) {
            throw new RuntimeException("The supplied letter sequence must contain at least 2 letters");
        }
        this.letters = letters;
        this.mantissaExponentSeperator = mantissaExponentSeperator;
        this.seperator = seperator;
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        MantissaExponent engineering = Util.toEngineering(value);
        String mantissa = String.format("%."+places+"f", engineering.getMantissa());
        return mantissa
                + mantissaExponentSeperator
                + String.join(seperator, transcribe(engineering.getExponent()));
    }

    private ArrayList<String> transcribe(long exponent) {
        // In engineering format, exponent has a step of 3
        // (i.e. the progression looks like this: 1e3 => 10e3 => 100e3 => 1e6 => ...)
        // With this notation we want to convert each step to a letter sequence
        // First, we get a normalizedExponent which is simply e3, e6, e9, ... => 1, 2, 3, ...
        // And next we do a modified base10 to baseN conversion
        // where N is the amount of characters in this notation
        long normalizedExponent = exponent / 3;
        int base = letters.length;
        ArrayList<String> letters = new ArrayList<>();

        if (normalizedExponent < base) {
            letters.add(this.letters[(int)normalizedExponent - 1]);
            return letters;
        }

        int remainder;
        int letterIndex;
        while (normalizedExponent > base) {
            remainder = (int) (normalizedExponent % base);
            letterIndex = (remainder == 0 ? base : remainder) - 1;
            letters.add(this.letters[letterIndex]);
            normalizedExponent /= base;
            if (remainder == 0) normalizedExponent--;
        }
        letters.add(this.letters[(int)normalizedExponent - 1]);
        Collections.reverse(letters);
        return letters;
    }

    /*private transcribe(exponent: number): string[] {
    const letters = [];
    while (normalizedExponent > base) {
      const remainder = normalizedExponent % base;
      const letterIndex = (remainder === 0 ? base : remainder) - 1;
      letters.push(this.letters[letterIndex]);
      normalizedExponent = (normalizedExponent - remainder) / base;
      if (remainder === 0) {
        normalizedExponent--;
      }
    }
    letters.push(this.letters[normalizedExponent - 1]);
    return letters.reverse();
  }*/
}
