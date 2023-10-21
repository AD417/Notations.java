package io.github.ad417.Notations.ADNotations;

/**
 * Letter Notation - a Custom Notation that just uses all the letters of the
 * alphabet.
 */
public class LetterNotation extends CustomNotation {
    public static final String name = "Letters";

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public LetterNotation() {
        super(LETTERS);
    }
}
