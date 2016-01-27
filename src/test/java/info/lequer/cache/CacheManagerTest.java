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
public class CacheManagerTest {
    
    public CacheManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

      /**
     * Test of getCache method, of class CacheManager.
     */
    @Test
    public void testGetCache() {
        System.out.println("getCache");
        CacheManager instance = CacheManager.INSTANCE;
        instance.create("testCache", 10, 10, 10, String.class, Long.class);
        assertTrue(instance.getCache("testCache") instanceof Cache);
    }

    /**
     * Test of addCache method, of class CacheManager.
     */
    @Test
    public void testAddCache() {
        System.out.println("addCache");
        Cache cache = new Cache("testCache", 10, 10, 10);
        CacheManager instance = CacheManager.INSTANCE;
        instance.addCache(cache);
        assertTrue(instance.cacheExists("testCache"));
        instance.removeCache(instance.getCache("testCache"));
    }

    /**
     * Test of cacheExists method, of class CacheManager.
     */
    @Test
    public void testCacheExists() {
        System.out.println("cacheExists");
        CacheManager instance = CacheManager.INSTANCE;
        instance.create("testCache", 10, 100, 100, String.class, Long.class);
        assertTrue(instance.cacheExists("testCache"));
        instance.removeCache(instance.getCache("testCache"));
        assertFalse(instance.cacheExists("testCache"));
    }

    /**
     * Test of create method, of class CacheManager.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        CacheManager instance = CacheManager.INSTANCE;
        instance.create("testCache", 10, 100, 100, String.class, Long.class);
        assertTrue(instance.cacheExists("testCache"));
        instance.removeCache(instance.getCache("testCache"));
    }

    /**
     * Test of removeCache method, of class CacheManager.
     */
    @Test
    public void testRemoveCache() {
        System.out.println("removeCache");
        CacheManager instance = CacheManager.INSTANCE;
        instance.create("testCache", 10, 100, 100, String.class, Long.class);
        assertTrue(instance.cacheExists("testCache"));
        instance.removeCache(instance.getCache("testCache"));
        assertFalse(instance.cacheExists("testCache"));
    }
    
}
