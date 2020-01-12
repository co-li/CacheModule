package cache;

public class Main {

    private static CacheModule cache;

    public static void main(String[] args) {
        cache = new JCacheModule();
        cache.start();
        cache.put(CacheType.NAME, "1", "Billy");
        cache.put(CacheType.NUMBER, "1", 12345);
        
        // This should print 12345 
        System.out.println(cache.get(CacheType.NUMBER, "1"));
        
        // This should print Billy
        System.out.println(cache.get(CacheType.NAME, "1"));

        // This should return null
        System.out.println(cache.get(CacheType.NAME, "2"));
    }
}
