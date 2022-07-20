package cat.kmruiz.mdiag.overview.topology.sharded;

public record Settings(
        LoadBalancerSetting loadBalancer,
        BooleanSetting autosplit
) {
    public static Settings defaultSettings() {
        return new Settings(
                new LoadBalancerSetting(false, "off"),
                new BooleanSetting(false)
        );
    }

    public record BooleanSetting(boolean status) {}
    public record LoadBalancerSetting(boolean status, String mode) {}

}