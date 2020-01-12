package cache;

public interface CacheModule {

    String getName();
    boolean isInit();
    boolean start();
    void stop();
    void put(CacheType type, String key, Object value);
    Object get(CacheType type, String key);
    void remove(CacheType type, String key);

}
