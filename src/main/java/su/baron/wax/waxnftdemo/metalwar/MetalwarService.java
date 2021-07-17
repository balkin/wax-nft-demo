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
        if (this.tokenPrices.isEmpty()) throw new MetalwarException("Token prices are not loaded yet");
        final double income = raidUnit.getOutcomeList().stream().mapToDouble(value -> tokenPrices.get(value.getShard()) * value.getAmount()).sum();
        final double loss = raidUnit.getHp() * tokenPrices.get(MetalwarToken.MWM) / 2;
        return new ProfitabilityDTO(String.format("%s profitability", raidUnit.getShard().getReadableName()), loss, income);
    }

    @Autowired
    private ObjectMapper objectMapper;

    static class AlcorMarketListReference extends TypeReference<List<AlcorMarketDTO>> {

    }

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
    public void updateTokenPrices() {
        fetchTokenPrices().thenApply(o -> {
            log.info("Fetched {} token prices: {}", o.size(), o);
            o.forEach((key, price) -> {
                try {
                    final MetalwarToken metalwarToken = MetalwarToken.valueOf(key);
                    tokenPrices.put(metalwarToken, price);
                } catch (Throwable ignored) {
                    // Ignore tokens
                }
            });
            return null;
        });
    }
}
