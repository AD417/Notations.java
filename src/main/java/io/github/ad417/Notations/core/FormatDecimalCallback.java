package io.github.ad417.Notations.core;

import io.github.ad417.BreakInfinity.BigDouble;

public interface FormatDecimalCallback {
    public String format(BigDouble value, int precision, int precisionExponent);
}
