package su.baron.wax.waxnftdemo.metalwar;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MetalwarUtilsTest {

    @Test
    void unitByNameUnsuccessful() {
        assertThrows(MetalwarException.class, () -> MetalwarUtils.unitByName("CHEBURASHKA"));
    }

    @Test
    void unitByNameSuccessful() {
        assertNotNull( MetalwarUtils.unitByName("WOLF"), "`WOLF` unit should be found");
        assertNotNull( MetalwarUtils.unitByName("wolf"), "`wolf` unit should be found");
        assertNotNull( MetalwarUtils.unitByName("wOlF"), "`wOlF` unit should be found");

        // Make sure all existing names return some unit
        final List<RaidUnit> allUnits = Arrays.stream(MetalwarUtils.UNITS)
                .map(raidUnit -> raidUnit.getShard().getReadableName())
                .map(MetalwarUtils::unitByName)
                .collect(Collectors.toList());
        assertTrue(allUnits.stream().filter(Objects::isNull).findAny().isEmpty());
    }
}
