package su.baron.wax.waxnftdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import su.baron.wax.waxnftdemo.dto.ProfitabilityDTO;
import su.baron.wax.waxnftdemo.metalwar.MetalwarService;

import java.util.List;

@Controller
public class MetalWarController {

    @Autowired
    private MetalwarService metalwarService;

    @GetMapping("/api/wax/metalwar/raid")
    public ResponseEntity<List<ProfitabilityDTO>> raidProfits() {
        return ResponseEntity.ok(metalwarService.calculateProfitability());
    }
}
