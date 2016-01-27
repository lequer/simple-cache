/*
 * 
 * 
 */
package info.lequer.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.slf4j.Logger;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;
import java.util.Comparator;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michel
 * @param <K>
 * @param <V>
 */
public class Cache<K extends Object, V extends Object> {

    private final Logger logger = LoggerFactory.getLogger(Cache.class);

    private final ConcurrentHashMap<K, CacheObject<V>> cacheMap;
    private final String name;
    private final long timeIdle;
    private final long timeToLive;
    private final int maxElements;
    private File cacheFile;

    /**
     *
     * @param name
     * @param timeIdle time is given in seconds
     * @param ttl time is given in seconds
     * @param maxElements
     */
    public Cache(String name, long timeIdle, long ttl, int maxElements) {
        this.name = name;
        this.timeIdle = timeIdle * 1000;
        this.timeToLive = ttl * 1000;

        this.maxElements = maxElements;
        cacheMap = new ConcurrentHashMap<>();
        try {
            cacheFile = File.createTempFile(name, "cache");
        } catch (IOException ex) {
            throw new IllegalArgumentException("Cannot access cache file");
        }
      

        logger.info("initialiazing cache " + name + " with timeToLive: {}", this.timeToLive);
        if (this.timeIdle > 0 && this.timeToLive > 0) {

            Thread t;
            t = new Thread(() -> {
                while (true) {
                    
                    try {
                        sleep(timeToLive);
                    } catch (InterruptedException ex) {
                        logger.error(ex.getMessage());
                    }
                    sortLRU();
                    cleanup();
                    logger.debug("cleanup cache");
                    write();
                }
            });

            t.setDaemon(true);
            t.start();
        }

    }

    public Boolean exists(K key){
        return cacheMap.containsKey(key);
    }
    // PUT method
    public void put(K key, V value) {
        cacheMap.put(key, new CacheObject<>(value));
    }

    // GET method
    public V get(K key) {
        V c;
        if (cacheMap.containsKey(key)) {
            c = cacheMap.get(key).value;
            cacheMap.get(key).lastAccessed = currentTimeMillis();
        } else {
            c = null;
        }
        return c;
    }

    // REMOVE method
    public void remove(K key) {
        cacheMap.remove(key);
    }

    // Get Cache Objects Size()
    public int size() {
        return cacheMap.size();
    }

    // Empty the cache from all objects
    // and remove the cache file
    public void clear() {
        cacheMap.clear();
        if (cacheFile.exists()) {
            cacheFile.delete();
        }

    }

    // CLEANUP method
    public void cleanup() {

        long now = currentTimeMillis();
        Iterator<Entry<K, CacheObject<V>>> itr = cacheMap.entrySet().iterator();
        while (itr.hasNext()) {
            Entry<K, CacheObject<V>> currentElement = itr.next();

            if (currentElement.getValue().lastAccessed + timeIdle < now) {
                itr.remove();
            }

        }

    }

    /**
     * When loading a file, the default behavior is to clear the loaded one
     * already.
     */
    public void load() {
        cacheMap.clear();
        if (cacheFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cacheFile))) {

                cacheMap.putAll((ConcurrentHashMap<K, CacheObject<V>>) ois.readObject());
            } catch (IOException | ClassNotFoundException ex) {
                logger.error("Cannot load cache: {}", ex.getMessage());
            }
        }
    }

    public void write() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cacheFile))) {
            out.writeObject(cacheMap);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

    }

    public String getName() {
        return this.name;
    }

    public File getCacheFile(){
        return cacheFile;
    }
    public void sortLRU() {
        Comparator<Entry<K, CacheObject<V>>> byValue = (entry1, entry2) -> entry1.getValue().lastAccessed.compareTo(
                entry2.getValue().lastAccessed);
        cacheMap.entrySet().stream().sorted(byValue.reversed());
        Iterator<Entry<K, CacheObject<V>>> itr = cacheMap.entrySet().iterator();
        while (itr.hasNext()) {
            Entry<K, CacheObject<V>> currentElement = itr.next();
            System.out.println(currentElement.getValue().lastAccessed + " " + currentElement);
            //if (currentElement.getValue().lastAccessed + timeIdle < now) {
            //   itr.remove();
            // }

        }
    }
}
