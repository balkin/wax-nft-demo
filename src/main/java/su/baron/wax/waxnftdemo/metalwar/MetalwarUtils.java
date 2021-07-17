package su.baron.wax.waxnftdemo.metalwar;

import java.util.Arrays;
import java.util.Locale;

public class MetalwarUtils {

    public static final RaidUnit WOLF = new RaidUnit(MetalwarToken.SHW, 250,
            Arrays.asList(Outcome.of(5, MetalwarToken.SHW), Outcome.of(5, MetalwarToken.SHA)));
    public static final RaidUnit ANT = new RaidUnit(MetalwarToken.SHA, 400,
            Arrays.asList(Outcome.of(5, MetalwarToken.SHA), Outcome.of(5, MetalwarToken.SHS)));
    public static final RaidUnit SKUNK = new RaidUnit(MetalwarToken.SHS, 400,
            Arrays.asList(Outcome.of(5, MetalwarToken.SHS), Outcome.of(5, MetalwarToken.SHR)));
    public static final RaidUnit RACCOON = new RaidUnit(MetalwarToken.SHR, 600,
            Arrays.asList(Outcome.of(5, MetalwarToken.SHR), Outcome.of(5, MetalwarToken.SHE)));
    public static final RaidUnit ELEPHANTOR = new RaidUnit(MetalwarToken.SHE, 900,
            Arrays.asList(Outcome.of(5, MetalwarToken.SHE), Outcome.of(5, MetalwarToken.SHH)));


    public static final RaidUnit[] UNITS = {WOLF, ANT, SKUNK, RACCOON, ELEPHANTOR};

    public static RaidUnit unitByName(String unit) {
        switch (unit.toUpperCase(Locale.US)) {
            case "WOLF":
                return WOLF;
            case "ANT":
                return ANT;
            case "SKUNK":
                return SKUNK;
            case "RACCOON":
                return RACCOON;
            case "ELEPHANTOR":
                return ELEPHANTOR;
        }
        throw new MetalwarException("Non-existent unit");
    }
}
