package com.method5.licensing.service;

import com.method5.licensing.ClientApplicationTest;
import com.method5.licensing.exceptions.CoreException;
import com.method5.licensing.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClientApplicationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class LicenseValidatorServiceTest {

    @Value("${method5.license.serverCertificate}")
    private String licenseServerCertificate;

    @Test
    public void caseValidateResponse() throws CoreException {
        Assert.assertTrue(LicenseValidatorService.validateResponse(
                licenseServerCertificate,
                "MvDAVHkSdgdOL6ZVy7aahycGI2tt+YgIOdlsradSRLfK+pjumsjI4DXJxjPAZW6LBcfzuOq4TEdPGOWncEp9rS6FXWIRBERrJdj5C5cjBZ1+v6EtxWpvS4K/Mo6s+oqSt0u7W2RtB2Oa1WzxEkAmbv3NZrCuuJx5syk2eCraW5g=",
                "dNCGPtedOA"));
    }

    @Test(expected=CoreException.class)
    public void caseValidateInvalidResponse() throws CoreException {
        Assert.assertTrue(LicenseValidatorService.validateResponse(
                licenseServerCertificate,
                RandomUtil.getRandomString(25),
                RandomUtil.getRandomString(25)));
    }
}
