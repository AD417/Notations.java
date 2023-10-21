package io.github.ad417.Notations.ADNotations;

/**
 * Emoji Notation - uses Emojis as symbols.
 */
public class EmojiNotation extends CustomNotation {
    public static final String name = "Emoji";

    private static final String[] EMOJI = new String[] {
            "😠", "🎂", "🎄", "💀", "🍆", "👪", "🌈", "💯", "🍦", "🎃", "💋", "😂", "🌙",
            "⛔", "🐙", "💩", "❓", "☢", "🙈", "👍", "☂", "✌", "⚠", "❌", "😋", "⚡"
    };

    public EmojiNotation() {
        super(EMOJI);
    }
}
