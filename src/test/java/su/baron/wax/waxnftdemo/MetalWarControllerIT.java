package su.baron.wax.waxnftdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import su.baron.wax.waxnftdemo.dto.ProfitabilityDTO;
import su.baron.wax.waxnftdemo.metalwar.MetalwarService;
import su.baron.wax.waxnftdemo.metalwar.MetalwarUtils;

import javax.annotation.PostConstruct;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class MetalWarControllerIT {

    public static final ProfitabilityDTO WOLF_PROFITABILITY = new ProfitabilityDTO("Wolf test response", 0.5, 0.25);
    public static final ProfitabilityDTO ANT_PROFITABILITY = new ProfitabilityDTO("Ant test response", 1.0, 2.0);
    public static final ProfitabilityDTO SKUNK_PROFITABILITY = new ProfitabilityDTO("Skunk test response", 2.0, 2.5);
    public static final ProfitabilityDTO RACCOON_PROFITABILITY = new ProfitabilityDTO("Raccoon test response", 5.0, 8.0);
    public static final ProfitabilityDTO ELEPHANTOR_PROFITABILITY = new ProfitabilityDTO("Elephantor test response", 10.0, 12.0);

    @LocalServerPort
    protected int localPort;

    @Autowired
    protected RestTemplateBuilder restTemplateBuilder;

    @MockBean
    protected MetalwarService metalwarService;

    protected RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        Mockito.when(metalwarService.calculateProfitability(MetalwarUtils.ANT)).thenReturn(ANT_PROFITABILITY);
        Mockito.when(metalwarService.calculateProfitability()).thenReturn(Arrays.asList(WOLF_PROFITABILITY, ANT_PROFITABILITY, SKUNK_PROFITABILITY, RACCOON_PROFITABILITY, ELEPHANTOR_PROFITABILITY));

        restTemplate = restTemplateBuilder
                .rootUri("http://localhost:"+localPort)
                .errorHandler(new DefaultResponseErrorHandler()).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void raidProfitsList() {
        final ResponseEntity<ProfitabilityDTO[]> response = restTemplate.getForEntity("/api/wax/metalwar/raid", ProfitabilityDTO[].class);
        assertNotNull(response.getBody(), "Response should be non-null");
        log.debug("Got response {}", response);
        log.debug("Got {} entries: {}", response.getBody().length, response.getBody());
        assertEquals(5, response.getBody().length, "5 mock entries should be returned");
    }

    @Test
    void testRaidProfitsAnt() {
        final ResponseEntity<ProfitabilityDTO> response = restTemplate.getForEntity("/api/wax/metalwar/raid/ant", ProfitabilityDTO.class);
        assertNotNull(response.getBody(), "Response should be non-null");
        log.debug("Got response {}", response);
        assertTrue(response.getBody().isProfitable(), "Raiding with ant should be profitable with the existing mocks");
    }
}
