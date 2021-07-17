package su.baron.wax.waxnftdemo.metalwar;

import org.junit.jupiter.api.Test;
import su.baron.wax.waxnftdemo.dto.ProfitabilityDTO;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetalwarServiceTest {

    @Test
    void testProfitabilityWithoutTokenPricesShouldFail() {
        final MetalwarService metalwarService = new MetalwarService();
        assertThrows(MetalwarException.class, () -> metalwarService.calculateProfitability(MetalwarUtils.unitByName("WOLF")));
        assertThrows(MetalwarException.class, metalwarService::calculateProfitability);
        // Even if one token price is not present, profitability should fail
        metalwarService.updateTokenPrices(Map.of("SHW", 0.1, "SHA", 0.2));
        assertThrows(MetalwarException.class, metalwarService::calculateProfitability);
        assertThrows(IllegalArgumentException.class, () -> metalwarService.calculateProfitability(null));
        metalwarService.updateTokenPrices(Map.of("MWM", 1.0));
        assertThrows(MetalwarException.class, metalwarService::calculateProfitability);
    }

    @Test
    void testProfitabilityCalculation() {
        final MetalwarService metalwarService = new MetalwarService();
        // Should not be profitable: income is 5*0.1 + 5*0.2, loss is 125
        metalwarService.updateTokenPrices(Map.of("SHW", 0.1, "SHA", 0.2, "MWM", 1.0));
        final ProfitabilityDTO profitabilityDTO = metalwarService.calculateProfitability(MetalwarUtils.WOLF);
        assertEquals(1.5, profitabilityDTO.getProfit(), "Profit is 1.5 (5*0.1 + 5*0.2)");
        assertFalse(profitabilityDTO.isProfitable(), "With ridiculously expensive metal, can't be profitable");
        metalwarService.updateTokenPrices(Map.of("SHW", 0.5, "SHA", 1.0, "MWM", 0.05));
        final ProfitabilityDTO newProfitabilityDTO = metalwarService.calculateProfitability(MetalwarUtils.WOLF);
        assertTrue(newProfitabilityDTO.isProfitable(), "With realistic metal price and good shard prices, it's profitable");
    }
}
