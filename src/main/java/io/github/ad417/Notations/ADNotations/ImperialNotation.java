package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.DoubleFormat;
import io.github.ad417.Notations.core.Notation;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Imperial Notation - a very impractical notation that converts the formatted
 * value into imperial units, wrapping around on itself if the units get too
 * big.
 */
public class ImperialNotation extends Notation {
    public static final String name ="Imperial";
    // FML
    private static class VolumeUnit {
        /** The volume, measured in pL. */
        long volume;
        /** The name representation as used in the final output. */
        String name;
        /**
         * An offset index used in the VOLUME_UNITS list to determine that is
         * larger than a "rounding error" for the unit in question.
         */
        int almostThresholdIndex;

        public VolumeUnit(long volume, String name, int almostThresholdIndex) {
            this.volume = volume;
            this.name = name;
            this.almostThresholdIndex = almostThresholdIndex;
        }
    }

    /** An array of every single volume unit used by Imperial Notation. */
    private static final VolumeUnit[] VOLUME_UNITS = new VolumeUnit[] {
            new VolumeUnit(0, "pL", 0),
            new VolumeUnit(61611520L, "minim", 0),
            new VolumeUnit(61611520L * 60, "dram", 1),
            new VolumeUnit(61611520L * 60 * 8, "ounce", 2),
            new VolumeUnit(61611520L * 60 * 8 * 4, "gill", 2),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2, "cup", 3),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2, "pint", 4),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2, "quart", 4),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4, "gallon", 4),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 2 * 9, "pin", 3),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4 * 9, "firkin", 3),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4 * 18, "kilderkin", 4),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4 * 36, "barrel", 4),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4 * 54, "hogshead", 5),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4 * 72, "puncheon", 6),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4 * 108, "butt", 7),
            new VolumeUnit(61611520L * 60 * 8 * 4 * 2 * 2 * 2 * 4 * 216, "tun", 7),
    };

    private static final VolumeUnit MINIMS = VOLUME_UNITS[1];
    /**
     * A list of adjectives that get appended to the volume units based on
     * how many times we wrap around the volume unit list.
     */
    private static final String[] VOLUME_ADJECTIVES = new String[] {
            "minute ", "tiny ", "petite ", "small ", "modest ", "medium ", "generous ",
            "large ", "great ", "grand ", "huge ", "gigantic ", "immense ", "colossal ",
            "vast ", "galactic ", "cosmic ", "infinite ", "eternal ",
            // Possibly not necessary for comaptibility, but the increased limit of
            // Break_Infinity.java vs BI.js means these prevent indexing exceptions.
            "unreal ", "celestial ", "multiversal "
    };
    // Required for pluralization. I wish the instantiation was less ugly, but whatever.
    private static final HashSet<Character> VOWELS = new HashSet<>(Arrays.asList(
            'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u'
    ));
    /** The maximum representable volume for a given adjective. */
    private static final double MAX_VOLUME = VOLUME_UNITS[VOLUME_UNITS.length - 1].volume * 10;
    /** The log base 10 of the max representable volume for a given adjective. */
    private static final double LOG_MAX_VOLUME = Math.log10(MAX_VOLUME);
    private static final double REDUCE_RATIO = Math.log10(MAX_VOLUME / MINIMS.volume);

    @Override
    public String formatUnder1000(double value, int places) {
        return convertToVolume(value, VOLUME_ADJECTIVES[0]);
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        if (value.lt(MAX_VOLUME)) {
            return convertToVolume(value.toDouble(), VOLUME_ADJECTIVES[0]);
        }

        double logValue = value.log10() - LOG_MAX_VOLUME;
        int adjectiveIndex = 1;
        while (logValue > REDUCE_RATIO) {
            adjectiveIndex++;
            logValue /= REDUCE_RATIO;
        }
        return convertToVolume(
                Math.pow(10, logValue) * MINIMS.volume,
                VOLUME_ADJECTIVES[adjectiveIndex]
        );
    }

    private String convertToVolume(double volume, String adjective) {
        int volumeID = findVolumeUnit(volume);
        if (volumeID == 0) return formatMetric(volume);

        String smallStr = checkSmallUnits(adjective, volume, volumeID);
        if (smallStr != null) return smallStr;

        VolumeUnit big = VOLUME_UNITS[volumeID];
        int numBig = (int) (volume / big.volume);
        double remainder = volume - (numBig * big.volume);

        // If we are almost exactly a unit, return immediately.
        if (remainder < VOLUME_UNITS[volumeID - big.almostThresholdIndex].volume) {
            return pluralOrArticle(numBig, adjective + big.name);
        }

        // When we are within a specified rounding error, unit break:
        if (volumeID < VOLUME_UNITS.length - 1) {
            String nextBiggestStr = checkAlmost(adjective, volume, 0, volumeID + 1);
            if (nextBiggestStr != null) return nextBiggestStr;
        }

        // If we are near a multiple of units, ddo something with that.
        String nearMultiple = checkAlmost(adjective, remainder, numBig, volumeID);
        if (nearMultiple != null) return nearMultiple;

        // Search for the best unit to pair with.
        int numBest = (int) (remainder / VOLUME_UNITS[volumeID - 1].volume);
        int bestUnitIndex = volumeID - 1;
        double bestUnitError = remainder - numBest * VOLUME_UNITS[volumeID - 1].volume;

        for (int thirdUnitIndex = volumeID - 2; thirdUnitIndex > 0 && thirdUnitIndex > volumeID - big.almostThresholdIndex; thirdUnitIndex--) {
            VolumeUnit third = VOLUME_UNITS[thirdUnitIndex];
            int numThird = (int) (remainder / third.volume);
            // If we have a lot of the unit under consideration -- then stop. The exception is in
            // case of minims, where it may be normal to have a bunch of them; in that case, we print
            // drams if possible.
            if (numThird > 9 && thirdUnitIndex != 1) break;

            // Using floor, we can compare errors directly, without Abs.
            double thirdUnitError = remainder - numThird * third.volume;
            if (thirdUnitError < 0.99 * bestUnitError) {
                numBest = numThird;
                bestUnitIndex = thirdUnitIndex;
                bestUnitError = thirdUnitError;
            }
        }

        return bigAndSmall(adjective, numBig, big, numBest, VOLUME_UNITS[bestUnitIndex]);
    }

    /**
     * Handle cases where we might have almost some big unit.
     * @param adjective Adjective that will be appended to the unit.
     * @param volume value to be formatted.
     * @param numBig The number of the unit we have.
     * @param bigIndex the index of that unit in VOLUME_UNITS.
     * @return the formatted output, if within the capabilities of this function;
     * null otherwise.
     */
    private @Nullable String checkAlmost(String adjective, double volume, int numBig, int bigIndex) {
        VolumeUnit big = VOLUME_UNITS[bigIndex];
        if (volume + VOLUME_UNITS[bigIndex - big.almostThresholdIndex].volume > big.volume) {
            return almost(adjective, numBig + 1, big);
        }
        VolumeUnit small = VOLUME_UNITS[bigIndex + 1 - big.almostThresholdIndex];
        if (volume + small.volume > big.volume) {
            return shortOf(adjective, numBig + 1, big, 1, small);
        }
        return null;
    }

    /**
     * Handles cases involving everything up to ounces; in the case of ounces it may
     * return nothing, in which case, the normal code path should be used.
     * @param adjective adjective that will be attached to unit
     * @param volume value to be formatted
     * @param volumeID index into VOLUME_UNITS for x (largest unit smaller than x)
     * @return the formatted output, if within the capabilities of this function;
     * null otherwise.
     */
    private @Nullable String checkSmallUnits(String adjective, double volume, int volumeID) {
        // Check for some minims short of a small unit break:
        if (volumeID <= 3 && volume + 9.5 * MINIMS.volume > VOLUME_UNITS[volumeID+1].volume) {
            return almostOrShortOf(volume, adjective, 1, VOLUME_UNITS[volumeID+1], MINIMS);
        }
        // Minims to drams. This goes:
        // a minim
        // 1.5 minims                  <-- we don't do this with larger units
        // 10 minims ... 50 minims
        // 9 minims short of a dram
        // a minim short of a dram
        // almost a dram               <-- handled above
        VolumeUnit big = VOLUME_UNITS[volumeID];
        if (volumeID == 1) {
            int deciMinims = (int)Math.round(volume * 10 / big.volume);
            if (deciMinims == 10) {
                return this.addArticle(adjective + big.name);
            }
            int places = deciMinims < 100 ? 1 : 0;
            return DoubleFormat.toFixed((double) deciMinims / 10, places)
                    + " "
                    + adjective
                    + big.name
                    + "s";
        }
        if (volumeID == 2) {
            int numBig = (int) (volume / big.volume);
            double remainder = volume - numBig * big.volume;
            if (remainder > 50.5 * MINIMS.volume) {
                // 9 minims short of a dram!
                return almostOrShortOf(volume, adjective, numBig + 1, big, MINIMS);
            }
            // Eg: a dram and 15 minims
            int numSmall = (int) Math.round(remainder / MINIMS.volume);
            return bigAndSmall(adjective, numBig, big, numSmall, MINIMS);
        }
        return null;
    }

    /**
     * Format a value that contains some number of a bigger unit and smaller unit.
     * @param adjective An adjective added to the unit.
     * @param numBig The number of the big unit.
     * @param big The big unit.
     * @param numSmall The number of the small unit.
     * @param small The small unit.
     * @return A properly formatted string for this situation.
     */
    private String bigAndSmall(
            String adjective,
            int numBig, VolumeUnit big,
            int numSmall, VolumeUnit small
    ) {
        String bigStr = pluralOrArticle(numBig, adjective + big.name);
        if (numSmall == 0) return bigStr;
        return bigStr
                + " and "
                + pluralOrArticle(numSmall, small.name);
    }

    /**
     * Format a value that may be some number of small units short of a larger unit.
     * @param volume The volume to do math on.
     * @param adjective An adjective added to the unit.
     * @param numBig The number of the big unit.
     * @param bigger The big unit.
     * @param smaller The small unit.
     * @return A properly formatted string for this situation.
     */
    private String almostOrShortOf(
            double volume, String adjective,
            int numBig, VolumeUnit bigger,
            VolumeUnit smaller
    ) {
        int smallerShortOfBig = (int) Math.round((numBig * bigger.volume - volume) / smaller.volume);
        if (smallerShortOfBig == 0) {
            return almost(adjective, numBig, bigger);
        }
        return shortOf(adjective, numBig, bigger, smallerShortOfBig, smaller);
    }

    /**
     * Format a value that is less than a unit short of some unit.
     * @param adjective An adjective added to the unit.
     * @param numUnit The number of units that the input nearly is.
     * @param unit The unit in question.
     * @return A properly formatted string for this situation.
     */
    private String almost(String adjective, int numUnit, VolumeUnit unit) {
        return "almost " + pluralOrArticle(numUnit, adjective + unit.name);
    }

    /**
     * Format a value that is some small units short of some larger units.
     * @param adjective An adjective added to the bigger unit.
     * @param numBig The number of the big unit.
     * @param bigger The big unit.
     * @param numSmall The number of the small unit.
     * @param smaller The small unit.
     * @return A properly formatted string for this situation.
     */
    private String shortOf(
            String adjective, int numBig,
            VolumeUnit bigger, int numSmall,
            VolumeUnit smaller
    ) {
        return pluralOrArticle(numSmall, smaller.name) +
                " short of " +
                pluralOrArticle(numBig, adjective + bigger.name);
    }

    /**
     * Format a value to be either plural or with an article.
     * Eg: "a(n) X" vs "3 Xs"
     * @param numUnit the number of units.
     * @param unit the unit that will either be plural or prepended.
     * @return a properly formatted string.
     */
    private String pluralOrArticle(int numUnit, String unit) {
        if (numUnit == 1) return addArticle(unit);
        return String.format("%d %ss", numUnit, unit);
    }

    private String addArticle(String unit) {
        String article = VOWELS.contains(unit.charAt(0)) ? "an" : "a";
        return article + " " + unit;
    }

    /**
     * Format a value in Litres, when it's too small to format using actual imperial
     * units.
     * @param volume the amount to convert. Always less than 61,611,520 because of
     * @return the metric representation of this
     */
    private String formatMetric(double volume) {
        if (volume < 1000) {
            int places = volume < 10 || volume == Math.round(volume) ? 2 : 0;
            return DoubleFormat.toFixed(volume, places) + "pL";
        }
        if (volume < 1e6) {
            return DoubleFormat.toPrecision(volume / 1000, 4) + "nL";
        }
        return DoubleFormat.toPrecision(volume / 1e6, 4) + "μL";
    }

    /**
     * Find the largest volume unit smaller than x.
     * @param x The volume to find a unit for.
     * @return an index into {@link #VOLUME_UNITS} for the relevant value.
     */
    private int findVolumeUnit(double x) {
        int low = 0;
        int high = VOLUME_UNITS.length;
        int middle;
        while (high - low > 1) {
            middle = (low + high) / 2;
            if (VOLUME_UNITS[middle].volume > x) high = middle;
            else low = middle;
        }
        return low;
    }

    public static void main(String[] args) {
        Notation n = new ImperialNotation();
        System.out.println(n.format(new BigDouble("1e968")));
        System.out.println(n.format(new BigDouble("2e505")));
        System.out.println(n.format(new BigDouble("1.485e287334234")));
        System.out.println(n.format(new BigDouble("1e" + (92233720368547758L))));
        n = new ScientificNotation();
        System.out.println(n.format(new BigDouble("1e" + (92233720368547758L))));

        System.out.println(ImperialNotation.REDUCE_RATIO);
    }
}
