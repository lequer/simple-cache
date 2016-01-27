/*
 * Copyrigth Michel Le Quer
 * michel@lequer.info
 * 
 */
package info.lequer.cache;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author pryon
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({info.lequer.cache.CacheManagerTest.class, info.lequer.cache.CacheTest.class})
public class CacheSuite {

    public CacheSuite() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        PropertyConfigurator.configure(classLoader.getResource("log4j.properties").getFile());
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
