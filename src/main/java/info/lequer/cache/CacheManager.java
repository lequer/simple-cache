/*
 * 
 * 
 */
package info.lequer.cache;

import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author Michel
 */
public enum CacheManager {

    INSTANCE;
    private final ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>();

    public Cache getCache(String cacheName) {
        return caches.get(cacheName);
    }

    public void addCache(Cache cache) {
        caches.put(cache.getName(), cache);
    }
    
    public void removeCache(Cache cache){
        caches.remove(cache.getName());
    }

    public boolean cacheExists(String candidates) {
        return (caches.containsKey(candidates) && caches.get(candidates) != null);
    }

    public <K extends Object, V extends Object> void create(String name, long timeToLive, long timeInterval, int maxElements, Class<K> keyType, Class<V> valueType) {
        if (!cacheExists(name)) {
            caches.put(name, new Cache<>(name, timeToLive, timeInterval, maxElements));
        }
    }
}
