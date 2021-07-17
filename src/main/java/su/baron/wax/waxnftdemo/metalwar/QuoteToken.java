package su.baron.wax.waxnftdemo.metalwar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contract",
        "symbol",
        "str"
})
public class QuoteToken {

    private static class SymbolDTO {
        private String name;
        private Integer precision;

        public String getName() {
            return name;
        }
    }

    @JsonProperty("contract")
    private String contract;
    @JsonProperty("symbol")
    private SymbolDTO symbol;
    @JsonProperty("str")
    private String str;

    @JsonProperty("contract")
    public String getContract() {
        return contract;
    }

    @JsonProperty("contract")
    public void setContract(String contract) {
        this.contract = contract;
    }

    @JsonProperty("str")
    public String getStr() {
        return str;
    }

    @JsonProperty("str")
    public void setStr(String str) {
        this.str = str;
    }

    public SymbolDTO getSymbol() {
        return symbol;
    }

    public void setSymbol(SymbolDTO symbol) {
        this.symbol = symbol;
    }

    public String getSymbolId() {
        return symbol.getName();
    }
}
