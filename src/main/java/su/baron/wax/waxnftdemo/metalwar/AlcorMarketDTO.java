package su.baron.wax.waxnftdemo.metalwar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "base_token",
        "quote_token",
        "min_buy",
        "min_sell",
        "frozen",
        "fee",
        "last_price",
        "volume24",
        "volumeWeek",
        "volumeMonth",
        "change24",
        "changeWeek"
})
public class AlcorMarketDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("quote_token")
    private QuoteToken quoteToken;
    @JsonProperty("base_token")
    private QuoteToken baseToken;
    @JsonProperty("min_buy")
    private String minBuy;
    @JsonProperty("min_sell")
    private String minSell;
    @JsonProperty("frozen")
    private Boolean frozen;
    @JsonProperty("fee")
    private Double fee;
    @JsonProperty("last_price")
    private Double lastPrice;
    @JsonProperty("volume24")
    private Double volume24;
    @JsonProperty("volumeWeek")
    private Double volumeWeek;
    @JsonProperty("volumeMonth")
    private Double volumeMonth;
    @JsonProperty("change24")
    private Double change24;
    @JsonProperty("changeWeek")
    private Double changeWeek;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("quote_token")
    public QuoteToken getQuoteToken() {
        return quoteToken;
    }

    @JsonProperty("quote_token")
    public void setQuoteToken(QuoteToken quoteToken) {
        this.quoteToken = quoteToken;
    }

    @JsonProperty("min_buy")
    public String getMinBuy() {
        return minBuy;
    }

    @JsonProperty("min_buy")
    public void setMinBuy(String minBuy) {
        this.minBuy = minBuy;
    }

    @JsonProperty("min_sell")
    public String getMinSell() {
        return minSell;
    }

    @JsonProperty("min_sell")
    public void setMinSell(String minSell) {
        this.minSell = minSell;
    }

    @JsonProperty("frozen")
    public Boolean getFrozen() {
        return frozen;
    }

    @JsonProperty("frozen")
    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    @JsonProperty("fee")
    public Double getFee() {
        return fee;
    }

    @JsonProperty("fee")
    public void setFee(Double fee) {
        this.fee = fee;
    }

    @JsonProperty("last_price")
    public Double getLastPrice() {
        return lastPrice;
    }

    @JsonProperty("last_price")
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @JsonProperty("volume24")
    public Double getVolume24() {
        return volume24;
    }

    @JsonProperty("volume24")
    public void setVolume24(Double volume24) {
        this.volume24 = volume24;
    }

    @JsonProperty("volumeWeek")
    public Double getVolumeWeek() {
        return volumeWeek;
    }

    @JsonProperty("volumeWeek")
    public void setVolumeWeek(Double volumeWeek) {
        this.volumeWeek = volumeWeek;
    }

    @JsonProperty("volumeMonth")
    public Double getVolumeMonth() {
        return volumeMonth;
    }

    @JsonProperty("volumeMonth")
    public void setVolumeMonth(Double volumeMonth) {
        this.volumeMonth = volumeMonth;
    }

    @JsonProperty("change24")
    public Double getChange24() {
        return change24;
    }

    @JsonProperty("change24")
    public void setChange24(Double change24) {
        this.change24 = change24;
    }

    @JsonProperty("changeWeek")
    public Double getChangeWeek() {
        return changeWeek;
    }

    @JsonProperty("changeWeek")
    public void setChangeWeek(Double changeWeek) {
        this.changeWeek = changeWeek;
    }

    public QuoteToken getBaseToken() {
        return baseToken;
    }

    public void setBaseToken(QuoteToken baseToken) {
        this.baseToken = baseToken;
    }

    public String getPairId() {
        return quoteToken.getSymbolId() + "_" + baseToken.getSymbolId();
    }

    public String getSymbolId() {
        return quoteToken.getSymbolId();
    }
}
