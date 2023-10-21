# Notations.java
All the notation that are included in the Reality Update for Antimatter Dimensions, including most
that are included in the Android version (complete list coming soon(tm)). 

See all the notations in action [here](https://antimatter-dimensions.github.io/notations/). 

## Setup
TBA

## Use
All the notation classes are included at `io.github.ad417.Notations.ADNotations`.

You can instantiate any of them by importing and instantiating the relevant notation:

```java
import io.github.ad417.BreakInfinity.BigDouble;
import io.github.ad417.Notations.ADNotations.ScientificNotation;
import io.github.ad417.Notations.core.Notation;

class Main {
    public static void main(String[] args) {
        Notation scientific = new ScientificNotation();
        System.out.println(scientific.format(new BigDouble("1000"), 2, 2));
    }
}
```

The main method that notations provide is `format(value, places, placesUnder1000)`.

- `value` must be a BigDouble -- for now. 
- `places` is used to format the mantissa if the value is at least 1000.
- `placesUnder1000` formats the mantissa if the value is under 1000. 

Configuration is also possible, through the `...core.Settings` class. 

## Extending
Creating your own notations is simple! Just extend the base class `Notation`
and implement the `name` parameter and `formatDecimal` function:
```java
class SimpleNotation extends Notation {
    public static final String name = "Simple";
    
    @Override
    public String formatDecimal(BigDouble value, int places) {
        return String.format(
                "Mantissa: %s, Exponent: %d",
                DoubleFormat.toFixed(value.getMantissa(), places),
                value.getExponent
        );
    }
}
```

You can also extend existing notations (like `EmojiNotation` does) and override other 
methods, but this is a more advanced case which you can figure out by looking at the 
source code of existing notations.