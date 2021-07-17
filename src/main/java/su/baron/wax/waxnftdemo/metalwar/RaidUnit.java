package su.baron.wax.waxnftdemo.metalwar;

import java.util.List;

public class RaidUnit {
    private final MetalwarToken shard;
    private final int hp;
    private final List<Outcome> outcomeList;

    public RaidUnit(MetalwarToken shard, int hp, List<Outcome> outcomeList) {
        this.shard = shard;
        this.hp = hp;
        this.outcomeList = outcomeList;
    }

    public MetalwarToken getShard() {
        return shard;
    }

    public int getHp() {
        return hp;
    }

    public List<Outcome> getOutcomeList() {
        return outcomeList;
    }
}
