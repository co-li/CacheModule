package cache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.List;

public class JCacheModule implements CacheModule {

    private volatile boolean isInit = false;

    private static JCacheModule instance;

    private CachingProvider provider;
    private CacheManager cacheMgr;
    private Cache<String, Object> cache;
    
    @Override
    public String getName() {
        return "JCache Module";
    }
    
    @Override
    public boolean isInit() {
        return this.isInit;
    }
    
    @Override
    public boolean start() {
        provider = Caching.getCachingProvider();
        cacheMgr = provider.getCacheManager();

        MutableConfiguration<String, Object> configuration =
                new MutableConfiguration<String, Object>()
                        .setTypes(String.class, Object.class)
                        .setStoreByValue(false)
                        .setManagementEnabled(true)
                        .setStatisticsEnabled(true)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES));
        cache = cacheMgr.createCache("jCache", configuration);

        isInit = true;
        instance = this;

        return isInit();
    }
    
    @Override
    public void stop() {
        //cacheMgr.destroyCache("jCache");
        provider.close();
        isInit = false;
    }

    @Override
    public void put(CacheType type, String key, Object value) {
        key = getKey(type, key);
        cache.put(key, value);
    }

    @Override
    public Object get(CacheType type, String key) {
        key = getKey(type, key);
        return cache.get(key);
    }

    @Override
    public void remove(CacheType type, String key) {
        key = getKey(type, key);
        cache.remove(key);
    }

    private String getKey(CacheType type, String key) {
        switch (type) {
            case NAME:
                key = "username_" + key;
            case NUMBER:
                key = "number_" + key;
            default:
            	break;
        }
        return key;
    }

    public static JCacheModule getInstance() {
        return instance;
    }

}
