/*
 * Copyrigth Michel Le Quer
 * michel@lequer.info
 * 
 */
package info.lequer.cache;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pryon
 */
public class CacheTest {

    private Cache<String, Integer> cache1;
    private Cache<Long, String> cache2;

    public CacheTest() {
       
    }

    @BeforeClass
    public static void setUpClass() {
       
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
     //    PropertyConfigurator.configure(System.getProperty("user.home") + "/Documents\\NetBeansProjects\\EACreator/log4j.properties");
        // Create 2 caches with different content
        cache1 = new Cache<>("integer", 10, 10, 10);
        cache2 = new Cache<>("string", 10, 10, 10);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class Cache.
     */
    @Test
    public void testPutAndGet() {
        System.out.println("Put an Get");
        cache1.put("1", 2);
        cache2.put(1000L, "long");
        Integer res = cache1.get("1");
        Integer exp = 2;
        assertEquals(res, exp);
        String res2 = cache2.get(1000L);
        String exp2 = "long";
        assertEquals(res2, exp2);
    }

    /**
     * Test of remove method, of class Cache.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        cache1.put("1", 2);
        cache2.put(1000L, "long");
        Integer res = cache1.get("1");
        Integer exp = 2;
        assertEquals(res, exp);
        String res2 = cache2.get(1000L);
        String exp2 = "long";
        assertEquals(res2, exp2);
        cache1.remove("1");
        cache2.remove(1000L);
        assertNull(cache1.get("1"));
        assertNull(cache2.get(1000L));
    }

    /**
     * Test of size method, of class Cache.
     */
    @Test
    public void testSize() {
        System.out.println("size" + cache1.size());
        cache1.clear();
        int expResult = 0;
        int result = cache1.size();
        assertEquals(expResult, result);
        for (int i = 0; i < 10; i++) {
            cache1.put("" + i, i);
        }
        assertEquals(10, cache1.size());
        cache1.clear();

    }

    /**
     * Test of cleanup method, of class Cache.
     */
    @Test
    public void testCleanup() throws InterruptedException {
        System.out.println("cleanup");
        /**
         * cache1 is suppose to have a 10 seconds ttl and 10 seconds idle
         */
        cache1.clear();
        cache1.put("clear", 1);
        int res = cache1.get("clear");
        assertEquals(1, res);
        Thread.sleep(5000); // the value should still be there in idle/2
        assertEquals(1, (int) cache1.get("clear")); // just accessed the value so it shouldn't still be there in 'idle' time
        Thread.sleep(10010);
        cache1.cleanup();
        assertNull(cache1.get("clear"));
        cache1.clear();
    }

    /**
     * Test of load method, of class Cache.
     * To load we need to write it first .... mostly the cache is in memory.
     * ... but if we load the file before ttl, the cache should be empty.
     */
    @Test
    public void testLoad() {
        cache1.clear();
        cache1.put("load", 1);
        assertEquals(1, (int) cache1.get("load"));
        /// no time for the value to be written in file cache, so "load" value should be null
        cache1.load();
        assertNull(cache1.get("load"));
        // put the value again and write to file this time
        cache1.put("load", 1);
        cache1.write();
        cache1.load();
        // this time the value should be here
        assertEquals(1, (int) cache1.get("load"));
        cache1.clear();
    }

    /**
     * Test of write method, of class Cache.
     */
    @Test
    public void testWrite() {
        System.out.println("write");
        cache1.clear();
        // 
        
        cache1.put("load", 1);
        assertEquals(1, (int) cache1.get("load"));
        /// no time for the value to be written in file cache, so "load" value should be null
        cache1.load();
        assertNull(cache1.get("load"));
        // put the value again and write to file this time
        cache1.put("load", 1);
        cache1.write();
        cache1.load();
        // this time the value should be here
        assertEquals(1, (int) cache1.get("load"));
        cache1.clear();
    }

    /**
     * Test of getName method, of class Cache.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "integer";
        String result = cache1.getName();
        assertEquals(expResult, result);
    }

  

    /**
     * Test of clear method, of class Cache.
     * Once cleared, a cache should be empty and file should be deleted if written
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        cache1.put("clear", 1);
        cache1.write();
        assertEquals(1, cache1.size());
        assertTrue(cache1.getCacheFile().exists());
        assertEquals(1,(int) cache1.get("clear"));
        cache1.clear();
        assertNull(cache1.get("clear"));
        assertEquals(0, cache1.size());
        assertFalse(cache1.getCacheFile().exists());
    }
 @Test
 public void testExists(){
     assertFalse(cache1.exists("test"));
     cache1.put("test", 1);
     assertTrue(cache1.exists("test"));
 }
}
