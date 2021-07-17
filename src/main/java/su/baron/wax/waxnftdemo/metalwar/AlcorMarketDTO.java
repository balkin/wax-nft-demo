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
    private Integer frozen;
    @JsonProperty("fee")
    private Integer fee;
    @JsonProperty("last_price")
    private Double lastPrice;
    @JsonProperty("volume24")
    private Integer volume24;
    @JsonProperty("volumeWeek")
    private Integer volumeWeek;
    @JsonProperty("volumeMonth")
    private Integer volumeMonth;
    @JsonProperty("change24")
    private Integer change24;
    @JsonProperty("changeWeek")
    private Integer changeWeek;

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
    public Integer getFrozen() {
        return frozen;
    }

    @JsonProperty("frozen")
    public void setFrozen(Integer frozen) {
        this.frozen = frozen;
    }

    @JsonProperty("fee")
    public Integer getFee() {
        return fee;
    }

    @JsonProperty("fee")
    public void setFee(Integer fee) {
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
    public Integer getVolume24() {
        return volume24;
    }

    @JsonProperty("volume24")
    public void setVolume24(Integer volume24) {
        this.volume24 = volume24;
    }

    @JsonProperty("volumeWeek")
    public Integer getVolumeWeek() {
        return volumeWeek;
    }

    @JsonProperty("volumeWeek")
    public void setVolumeWeek(Integer volumeWeek) {
        this.volumeWeek = volumeWeek;
    }

    @JsonProperty("volumeMonth")
    public Integer getVolumeMonth() {
        return volumeMonth;
    }

    @JsonProperty("volumeMonth")
    public void setVolumeMonth(Integer volumeMonth) {
        this.volumeMonth = volumeMonth;
    }

    @JsonProperty("change24")
    public Integer getChange24() {
        return change24;
    }

    @JsonProperty("change24")
    public void setChange24(Integer change24) {
        this.change24 = change24;
    }

    @JsonProperty("changeWeek")
    public Integer getChangeWeek() {
        return changeWeek;
    }

    @JsonProperty("changeWeek")
    public void setChangeWeek(Integer changeWeek) {
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
