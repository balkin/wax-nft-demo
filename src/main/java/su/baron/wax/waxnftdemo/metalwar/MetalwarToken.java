package su.baron.wax.waxnftdemo.metalwar;

public enum MetalwarToken {
    SHW("Wolf"), SHA("Ant"), SHS("Skunk"), SHR("Raccoon"), SHE("Elephantor"), SHH("Hamster"), MWM("Metal");

    private final String readableName;

    MetalwarToken(String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName() {
        return readableName;
    }
}
