package su.baron.wax.waxnftdemo.dto;

public class ProfitabilityDTO {
    private final String description;
    private final Double expenses;
    private final Double profit;

    public ProfitabilityDTO(String description, Double expenses, Double profit) {
        this.description = description;
        this.expenses = expenses;
        this.profit = profit;
    }

    public String getDescription() {
        return description;
    }

    public Double getExpenses() {
        return expenses;
    }

    public Double getProfit() {
        return profit;
    }

    public Boolean isProfitable() {
        return profit > expenses;
    }
}
