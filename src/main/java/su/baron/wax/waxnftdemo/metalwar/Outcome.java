package su.baron.wax.waxnftdemo.metalwar;

import org.jetbrains.annotations.NotNull;

public class Outcome {
    private final MetalwarToken shard;
    private final Integer amount;

    public Outcome(MetalwarToken shard, Integer amount) {
        this.shard = shard;
        this.amount = amount;
    }

    public MetalwarToken getShard() {
        return shard;
    }

    public Integer getAmount() {
        return amount;
    }

    public static @NotNull Outcome of(Integer amount, MetalwarToken shard) {
        return new Outcome(shard, amount);
    }
}
