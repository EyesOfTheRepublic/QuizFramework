import java.util.HashMap;

public class SeedList {
    private final HashMap<String, CalcData> seedList = new HashMap<>();

    public SeedList addSeedItem(final String key, final CalcData val) {
        seedList.putIfAbsent(key, val);
        return this;
    }

    public CalcData getSeedItem(final String key) {
        return seedList.getOrDefault(key, null);
    }

    public boolean containsSeedKey(final String key) {
        return seedList.containsKey(key);
    }
}
