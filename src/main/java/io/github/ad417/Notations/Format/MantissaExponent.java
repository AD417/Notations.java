package io.github.ad417.Notations.Format;

import io.github.ad417.BreakInfinity.BigDouble;

// TODO: make this an interface or similar in BreakInfinity.
//  What we're doing here is scuffed anyways.
public class MantissaExponent {
    private final double mantissa;
    private final long exponent;

    public MantissaExponent(double mantissa, long exponent) {
        this.mantissa = mantissa;
        this.exponent = exponent;
    }

    public MantissaExponent(BigDouble value) {
        this.mantissa = value.getMantissa();
        this.exponent = value.getExponent();
    }

    public double getMantissa() {
        return mantissa;
    }

    public long getExponent() {
        return exponent;
    }
}
