package com.method5.licensing.service;

import com.method5.licensing.LicenseApplicationTest;
import com.method5.licensing.util.RandomUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LicenseApplicationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ExpiringCacheTest {
    private static final Logger LOG = LogManager.getLogger(ExpiringCacheTest.class);

    private static String KEY1 = RandomUtil.getRandomString(10);
    private static String KEY2 = RandomUtil.getRandomString(10);

    @Test
    public void caseCanGetCachedData() throws Exception
    {
        ExpiringCache<String> expiringCache = null;

        try
        {
            expiringCache = new ExpiringCache<String>(60);
            expiringCache.put(KEY1, "test");
            expiringCache.put(KEY2, "test2");

            Assert.assertEquals(expiringCache.get(KEY2), "test2");

            expiringCache.put(KEY2, "test2-updated");

            Assert.assertNotSame(expiringCache.get(KEY2), "test2");
            Assert.assertEquals(expiringCache.get(KEY2), "test2-updated");
            Assert.assertEquals(expiringCache.get(KEY1), "test");
        }
        finally
        {
            expiringCache.destroy();
        }
    }

    @Test
    public void caseCanExpireItems() throws Exception
    {
        ExpiringCache<Object> expiringCache = null;

        try
        {
            expiringCache = new ExpiringCache<Object>(1);
            expiringCache.put(KEY1, new Object());

            Thread.sleep(1100);

            Assert.assertEquals(expiringCache.get(KEY1), null);
        }
        finally
        {
            expiringCache.destroy();
        }
    }
}
