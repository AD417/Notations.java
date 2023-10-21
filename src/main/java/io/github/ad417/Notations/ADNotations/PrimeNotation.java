package io.github.ad417.Notations.ADNotations;

import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.core.Notation;

import java.util.ArrayList;

/**
 * Prime Notation - formats large numbers into approximate prime
 * factorizations. Large enough values are shown as factorizations
 * raised to the power of other factorizations, recursively.
 */
public class PrimeNotation extends Notation {
    public static final String name = "Prime";
    protected static final String INFINITE = "Primefinity?";
    protected static final String NEGATIVE_INFINITE = "-Primefinity?";

    // Max int we can reliably get primes for.
    private static final int MAX_INT = 10006;
    private static final BigDouble MAX_INT_BIGDOUBLE = new BigDouble(MAX_INT);
    private static final double MAX_INT_LOG_10 = Math.log10(MAX_INT);

    private static final int[] PRIMES = cachePrimes();
    private static int[] cachePrimes() {
        ArrayList<Integer> primes = new ArrayList<>();
        boolean[] sieve = new boolean[MAX_INT];
        int sieveLimit = (int) Math.ceil(Math.sqrt(MAX_INT));
        for (int i = 2; i < sieveLimit; i++) {
            if (sieve[i]) continue;
            primes.add(i);
            for (int k = i * i; k < MAX_INT; k += i) {
                sieve[k] = true;
            }
        }
        for (int i = sieveLimit; i < MAX_INT; i++) {
            if (sieve[i]) continue;
            primes.add(i);
        }
        // Convert list of Integer objects to int primitive array.
        int[] out = new int[primes.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = primes.get(i);
        }
        return out;
    }

    private static final int LAST_PRIME_INDEX = PRIMES.length - 1;
    private static final int MAX_PRIME = PRIMES[LAST_PRIME_INDEX];

    // Goes up to 13. 2 ^ 13 = 8192 < 10006.
    private static final String[] EXPONENT = new String[] {
            "\u2070", "\u00B9", "\u00B2", "\u00B3", "\u2074",
            "\u2075", "\u2076", "\u2077", "\u2078", "\u2079",
            "\u00B9\u2070", "\u00B9\u00B9", "\u00B9\u00B2", "\u00B9\u00B3"
    };

    @Override
    public String formatUnder1000(double value, int places) {
        return primify(new BigDouble(value));
    }

    @Override
    public String formatDecimal(BigDouble value, int places, int placesExponent) {
        return primify(value);
    }

    public String primify(BigDouble value) {
        // We take the number and do 1 of 4 things depending on how big it is.
        // If the number is less than 0, we primify its negation and add a minus sign
        // to the start.
        // If the number is smaller than maxInt, 10006, then we just find the primes and
        // format them.
        // If not we need a way of representing the number, using only primes of course.
        // So we derive an exponent that will keep the base under the maxInt, then
        // we derive prime factors for both and format them as (base)^(exponent).
        // If the number is greater than 1e10006, we need to again format it differently.
        // So we increase our stack size to three, and repeat the process above from
        // top down.
        if (value.lte(MAX_INT_BIGDOUBLE)) {
            int floored = (int) value.toDouble();
            if (floored == 0) return "0";
            if (floored == 1) return "1";
            return formatFromList(factorize(floored));
        }

        double exp = value.log10() / MAX_INT_LOG_10;
        double base = Math.pow(MAX_INT, exp / Math.ceil(exp));
        if (exp < MAX_INT) {
            return formatBaseExp(base, exp);
        }

        double exp2 = Math.log10(exp) / MAX_INT_LOG_10;
        int exp2Ceil = (int) Math.ceil(exp2);
        exp = Math.pow(MAX_INT, exp2 / exp2Ceil);
        base = Math.pow(MAX_INT, exp / Math.ceil(exp));

        ArrayList<Integer> exp2List = factorize(exp2Ceil);
        String formattedExp2 = exp2List.size() == 1
                ? EXPONENT[exp2List.get(0)]
                : String.format("^(%s)", formatFromList(exp2List));

        return formatBaseExp(base, exp) + formattedExp2;
    }

    private String formatBaseExp(double base, double exp) {
        return String.format(
                "(%s)^(%s)",
                formatFromList(factorize((int) base)),
                formatFromList(factorize((int) Math.ceil(exp)))
        );
    }

    private String formatFromList(ArrayList<Integer> factors) {
        int lastPrime = factors.get(0);
        int count = 1;
        StringBuilder sb = new StringBuilder();

        int prime;
        for (int i = 1; i < factors.size(); i++) {
            prime = factors.get(i);

            if (prime == lastPrime) {
                count++;
                continue;
            }
            if (!sb.isEmpty()) sb.append("×");
            sb.append(lastPrime);
            if (count != 1) sb.append(EXPONENT[count]);

            lastPrime = prime;
            count = 1;
        }
        if (!sb.isEmpty()) sb.append("×");
        sb.append(lastPrime);
        if (count != 1) sb.append(EXPONENT[count]);
        return sb.toString();
    }

    // TODO: Might be useless?
    private int primesFuzzyIndexOf(int value) {
        if (value >= MAX_PRIME) return LAST_PRIME_INDEX;
        // Binary search!
        int min = 0;
        int max = LAST_PRIME_INDEX;
        int middle, prime;
        while (max != min + 1) {
            middle = (max + min) / 2;
            prime = PRIMES[middle];
            if (prime == value) return middle;
            if (prime > value) min = middle;
            if (prime < value) max = middle;
        }
        return min;
    }

    private ArrayList<Integer> factorize(int value) {
        ArrayList<Integer> factors = new ArrayList<>();
        int rootValue = (int) Math.sqrt(value);
        int divValue = value;

        int prime;
        for (int i = 0; PRIMES[i] <= rootValue; i++) {
            prime = PRIMES[i];
            while (divValue % prime == 0) {
                factors.add(prime);
                divValue /= prime;
            }
            if (divValue == 1) break;
        }
        if (divValue != 1) factors.add(divValue);

        return factors;
    }
}
