package su.baron.wax.waxnftdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import su.baron.wax.waxnftdemo.dto.ProfitabilityDTO;
import su.baron.wax.waxnftdemo.metalwar.MetalwarService;
import su.baron.wax.waxnftdemo.metalwar.MetalwarUtils;
import su.baron.wax.waxnftdemo.metalwar.RaidUnit;

import java.util.List;
import java.util.Locale;

@Controller
public class MetalWarController {

    @Autowired
    private MetalwarService metalwarService;

    @GetMapping("/api/wax/metalwar/raid")
    public ResponseEntity<List<ProfitabilityDTO>> raidProfits() {
        return ResponseEntity.ok(metalwarService.calculateProfitability());
    }

    @GetMapping("/api/wax/metalwar/raid/{unit}")
    public ResponseEntity<ProfitabilityDTO> raidProfits(@PathVariable String unit) {
        final RaidUnit raidUnit = MetalwarUtils.unitByName(unit);
        return ResponseEntity.ok(metalwarService.calculateProfitability(raidUnit));
    }
}
