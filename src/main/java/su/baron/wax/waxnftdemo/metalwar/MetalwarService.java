package su.baron.wax.waxnftdemo.metalwar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import su.baron.wax.waxnftdemo.dto.ProfitabilityDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MetalwarService {

    public static final URI MARKETS_URI = URI.create("https://wax.alcor.exchange/api/markets");

    public List<ProfitabilityDTO> calculateProfitability() {
        if (this.tokenPrices.isEmpty()) throw new MetalwarException("Token prices are not loaded yet");
        return Arrays.stream(MetalwarUtils.UNITS).map(this::calculateProfitability).collect(Collectors.toList());
    }

    private final Map<MetalwarToken, Double> tokenPrices = new HashMap<>();

    public @NotNull ProfitabilityDTO calculateProfitability(@NotNull RaidUnit raidUnit) {
        if (this.tokenPrices.isEmpty()) {
            throw new MetalwarException("Token prices are not loaded yet");
        }
        if (!this.tokenPrices.containsKey(MetalwarToken.MWM)) {
            throw new MetalwarException("Metalwar metal token price is unknown");
        }
        if (!this.tokenPrices.containsKey(raidUnit.getShard())) {
            throw new MetalwarException("Metalwar unit token price is unknown");
        }
        double income = 0.0;
        for (Outcome outcome : raidUnit.getOutcomeList()) {
            if (!this.tokenPrices.containsKey(outcome.getShard())) {
                throw new MetalwarException(String.format("Metalwar outcome token %s price is unknown", outcome.getShard().name()));
            }
            income += tokenPrices.get(outcome.getShard()) * outcome.getAmount();
        }
        final double loss = raidUnit.getHp() * tokenPrices.get(MetalwarToken.MWM) / 2;
        return new ProfitabilityDTO(String.format("%s profitability", raidUnit.getShard().getReadableName()), loss, income);
    }

    @Autowired
    private ObjectMapper objectMapper;

    static class AlcorMarketListReference extends TypeReference<List<AlcorMarketDTO>> {

    }

//    curl 'https://wax.greymass.com/v1/chain/get_table_rows' \
//  -H 'authority: wax.greymass.com' \
//  -H 'pragma: no-cache' \
//  -H 'cache-control: no-cache' \
//  -H 'user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36' \
//  -H 'content-type: text/plain;charset=UTF-8' \
//  -H 'accept: */*' \
//            -H 'sec-gpc: 1' \
//            -H 'origin: https://wax.alcor.exchange' \
//            -H 'sec-fetch-site: cross-site' \
//            -H 'sec-fetch-mode: cors' \
//            -H 'sec-fetch-dest: empty' \
//            -H 'referer: https://wax.alcor.exchange/' \
//            -H 'accept-language: en-US,en;q=0.9' \
//            --data-raw '{"json":true,"code":"alcordexmain","scope":67,"table":"sellorder","table_key":"","lower_bound":"","upper_bound":"","index_position":2,"key_type":"i128","limit":1000,"reverse":false,"show_payer":false}' \
//            --compressed

    @Async
    public @NotNull CompletableFuture<Map<String, Double>> fetchTokenPrices() {
        final HttpRequest request = HttpRequest.newBuilder().GET().uri(MARKETS_URI).build();
        final Map<String, Double> markets;
        try {
            markets = HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(httpResponse -> {
                log.info("HTTP Response: {}", httpResponse);
                final Map<String, Double> m = new HashMap<>();
                try {
                    objectMapper.readValue(httpResponse.body(), new AlcorMarketListReference()).forEach(alcorMarketDTO -> {
                        if (alcorMarketDTO.getPairId().contains("_WAX")) {
                            m.put(alcorMarketDTO.getSymbolId(), alcorMarketDTO.getLastPrice());
                        }
                    });
                } catch (JsonProcessingException ex) {
                    log.warn("Failed to process", ex);
                }
                return m;
            }).get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return CompletableFuture.failedFuture(e);
        }
        return CompletableFuture.completedFuture(markets);
    }

    @Scheduled(fixedDelay = 30000L)
    public void fetchAndUpdateTokenPrices() {
        fetchTokenPrices().thenAccept(o -> {
            log.info("Fetched {} token prices: {}", o.size(), o);
            updateTokenPrices(o);
        });
    }

    public void updateTokenPrices(Map<String, Double> o) {
        o.forEach((key, price) -> {
            try {
                final MetalwarToken metalwarToken = MetalwarToken.valueOf(key);
                tokenPrices.put(metalwarToken, price);
            } catch (Throwable ignored) {
                // Ignore tokens
            }
        });
    }
}
