package com.method5.licensing.service;

import com.method5.licensing.LicenseApplicationTest;
import com.method5.licensing.util.RandomUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LicenseApplicationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class InvalidRequestManagerTest {
    private static final Logger LOG = LogManager.getLogger(InvalidRequestManagerTest.class);

    private String ipAddress = RandomUtil.getRandomString(10);
    private String hostname = RandomUtil.getRandomString(10);
    private String macAddress = RandomUtil.getRandomString(10);

    @Autowired
    private InvalidRequestManager invalidManager;

    @Test
    public void caseCanBanByIpAddress() throws Exception
    {
        LOG.info(""+ (invalidManager.getNumberAllowedLicenseRequests() + 1));
        for(int i = 0; i < invalidManager.getNumberAllowedLicenseRequests() + 1; i++)
        {
            Assert.assertFalse(invalidManager.isRequestBanned(ipAddress, getRandomValue(), getRandomValue()));
            invalidManager.cacheInvalidRequest(ipAddress, getRandomValue(), getRandomValue());
        }

        Assert.assertTrue(invalidManager.isRequestBanned(ipAddress, getRandomValue(), getRandomValue()));
    }

    @Test
    public void caseCanBanByHostname() throws Exception
    {
        for(int i = 0; i < invalidManager.getNumberAllowedLicenseRequests() + 1; i++)
        {
            Assert.assertFalse(invalidManager.isRequestBanned(getRandomValue(), hostname, getRandomValue()));
            invalidManager.cacheInvalidRequest(getRandomValue(), hostname, getRandomValue());
        }

        Assert.assertTrue(invalidManager.isRequestBanned(getRandomValue(), hostname, getRandomValue()));
    }

    @Test
    public void caseCanBanByMacAddress() throws Exception
    {
        for(int i = 0; i < invalidManager.getNumberAllowedLicenseRequests() + 1; i++)
        {
            Assert.assertFalse(invalidManager.isRequestBanned(getRandomValue(), getRandomValue(), macAddress));
            invalidManager.cacheInvalidRequest(getRandomValue(), getRandomValue(), macAddress);
        }

        Assert.assertTrue(invalidManager.isRequestBanned(getRandomValue(), getRandomValue(), macAddress));
    }

    @Test
    public void caseCanBanByAll() throws Exception
    {
        for(int i = 0; i < invalidManager.getNumberAllowedLicenseRequests() + 1; i++)
        {
            Assert.assertFalse(invalidManager.isRequestBanned(ipAddress, hostname, macAddress));
            invalidManager.cacheInvalidRequest(ipAddress, hostname, macAddress);
        }

        Assert.assertTrue(invalidManager.isRequestBanned(ipAddress, hostname, macAddress));
    }

    private String getRandomValue()
    {
        return RandomUtil.getRandomString(10) + System.nanoTime();
    }
}
