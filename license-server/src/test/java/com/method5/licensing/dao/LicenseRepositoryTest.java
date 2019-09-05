package com.method5.licensing.dao;

import com.method5.licensing.LicenseApplicationTest;
import com.method5.licensing.domain.License;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LicenseApplicationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class LicenseRepositoryTest {

    @Autowired
    private LicenseRepository licenseRepository;

    @Test
    public void caseFindLicenseByApplicationKeyAndLicenseNumber()
    {
        List<License> licenses = licenseRepository.findByApplicationKeyAndLicenseNumber("mitchell-exporter", "2A953B4A1B9A5686AEBA26CF79AE3");
        Assert.assertTrue(licenses.size() == 1);
    }

    @Test
    public void caseWrongLicenseByApplicationKeyAndLicenseNumber()
    {
        List<License> licenses = licenseRepository.findByApplicationKeyAndLicenseNumber("??????", "?????");
        Assert.assertTrue(licenses.size() == 0);
    }
}
