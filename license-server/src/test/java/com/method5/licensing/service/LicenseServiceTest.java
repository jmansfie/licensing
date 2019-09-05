package com.method5.licensing.service;

import com.method5.licensing.LicenseApplicationTest;
import com.method5.licensing.constant.LicenseServerResponseEnum;
import com.method5.licensing.exceptions.CoreException;
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
public class LicenseServiceTest {
    private static final Logger LOG = LogManager.getLogger(LicenseServiceTest.class);

    private static final String VALID_APP_KEY = "mitchell-exporter";
    private static final String VALID_LICENSE_NUM = "E81FF58DE48FA315995E71134B1DA";

    private static final String INACTIVE_APP_KEY = "mitchell-exporter";
    private static final String INACTIVE_LICENSE_NUM = "DA1016C6946B56C8F7E8E9107EA4";

    private static final String EXPIRED_APP_KEY = "rbc-glass-service";
    private static final String EXPIRED_LICENSE_NUM = "401153DA68EB1ED22B61FD127C6R";

    private static final String FUTURE_APP_KEY = "mitchell-exporter";
    private static final String FUTURE_LICENSE_NUM = "2A953B4A1B9A5686AEBA26CF79AE3";

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private InvalidRequestManager invalidRequestManager;

    @Test
    public void caseValidateValidLicense() throws CoreException
    {
        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.VALID.getCode(), seed, null);

        Assert.assertEquals(licenseService.validateLicense(
                VALID_APP_KEY,
                VALID_LICENSE_NUM,
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                seed),
                expectedResponse);
    }


    @Test
    public void caseBannedIpAddress() throws CoreException
    {
        String host = RandomUtil.getRandomString(10);
        String ipAddress = RandomUtil.getRandomString(10);
        String macAddress = RandomUtil.getRandomString(10);

        invalidRequestManager.initiateBan(ipAddress, host, macAddress);

        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.INVALID.getCode(), seed, null);

        Assert.assertEquals(licenseService.validateLicense(
                VALID_APP_KEY,
                VALID_LICENSE_NUM,
                ipAddress,
                host,
                macAddress,
                seed),
                expectedResponse);
    }

    @Test
    public void caseValidateInactiveLicense() throws CoreException
    {
        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.INVALID.getCode(), seed, null);

        Assert.assertEquals(licenseService.validateLicense(
                INACTIVE_APP_KEY,
                INACTIVE_LICENSE_NUM,
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                seed),
                expectedResponse);
    }

    @Test
    public void caseValidateExpiredLicense() throws CoreException
    {
        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.INVALID.getCode(), seed, null);

        Assert.assertEquals(licenseService.validateLicense(
                EXPIRED_APP_KEY,
                EXPIRED_LICENSE_NUM,
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                seed),
                expectedResponse);
    }

    @Test
    public void caseValidateFutureLicense() throws CoreException
    {
        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.INVALID.getCode(), seed, null);

        Assert.assertEquals(licenseService.validateLicense(
                FUTURE_APP_KEY,
                FUTURE_LICENSE_NUM,
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                seed),
                expectedResponse);
    }

    @Test
    public void caseValidateMissingLicense() throws CoreException
    {
        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.INVALID.getCode(), seed, null);

        Assert.assertEquals(licenseService.validateLicense(
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                RandomUtil.getRandomString(10),
                seed),
                expectedResponse);
    }
}
