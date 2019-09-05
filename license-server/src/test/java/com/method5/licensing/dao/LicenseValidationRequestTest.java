package com.method5.licensing.dao;

import com.method5.licensing.LicenseApplicationTest;
import com.method5.licensing.domain.LicenseValidationRequest;
import com.method5.licensing.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LicenseApplicationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class LicenseValidationRequestTest {

    @Autowired
    private LicenseValidationRequestRepository licenseValidationRequestRepository;

    @Test
    public void caseAddValidationRequest()
    {
        LicenseValidationRequest validationRequest = new LicenseValidationRequest();
        validationRequest.setApplicationKey(RandomUtil.getRandomString(10));
        validationRequest.setLicenseNumber(RandomUtil.getRandomString(10));
        validationRequest.setIpAddress(RandomUtil.getRandomString(10));
        validationRequest.setHostname(RandomUtil.getRandomString(10));
        validationRequest.setMacAddress(RandomUtil.getRandomString(10));
        validationRequest.setDate(Calendar.getInstance().getTime());

        LicenseValidationRequest insertedObject = licenseValidationRequestRepository.save(validationRequest);

        LicenseValidationRequest dbObject = licenseValidationRequestRepository.findOne(insertedObject.getLicenseValidationRequestID());

        Assert.assertNotNull(dbObject);

        Assert.assertEquals(dbObject.getApplicationKey(), validationRequest.getApplicationKey());
        Assert.assertEquals(dbObject.getLicenseNumber(), validationRequest.getLicenseNumber());
        Assert.assertEquals(dbObject.getIpAddress(), validationRequest.getIpAddress());
        Assert.assertEquals(dbObject.getHostname(), validationRequest.getHostname());
        Assert.assertEquals(dbObject.getMacAddress(), validationRequest.getMacAddress());
    }
}
