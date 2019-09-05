package com.method5.licensing.controller;

import com.method5.licensing.LicenseApplicationTest;
import com.method5.licensing.LicensingApplication;
import com.method5.licensing.constant.LicenseServerResponseEnum;
import com.method5.licensing.service.LicenseService;
import com.method5.licensing.util.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=LicenseApplicationTest.class)
@WebMvcTest(value = LicenseController.class,excludeAutoConfiguration = LicensingApplication.class, secure = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class LicenseControllerTest {
    private static final String VALID_APP_KEY = "mitchell-exporter";
    private static final String VALID_LICENSE_NUM = "E81FF58DE48FA315995E71134B1DA";

    private static final String EXPIRED_APP_KEY = "rbc-glass-service";
    private static final String EXPIRED_LICENSE_NUM = "401153DA68EB1ED22B61FD127C6R";

    @Autowired
    private LicenseController licenseController;

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void caseBasicConnectionOk() throws Exception
    {
        mockMvc.perform(get("/v1/validateLicense")
                .param("applicationKey", RandomUtil.getRandomString(10))
                .param("licenseNumber", RandomUtil.getRandomString(10))
                .param("ipAddress", RandomUtil.getRandomString(10))
                .param("hostname", RandomUtil.getRandomString(10))
                .param("macAddress", RandomUtil.getRandomString(10))
                .param("seed", RandomUtil.getRandomString(10)))
                .andExpect(status().isOk());
    }

    @Test
    public void caseValidLicense() throws Exception
    {
        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.VALID.getCode(), seed, null);

        mockMvc.perform(get("/v1/validateLicense")
                .param("applicationKey", VALID_APP_KEY)
                .param("licenseNumber", VALID_LICENSE_NUM)
                .param("ipAddress", RandomUtil.getRandomString(10))
                .param("hostname", RandomUtil.getRandomString(10))
                .param("macAddress", RandomUtil.getRandomString(10))
                .param("seed", seed))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void caseInvalidLicense() throws Exception
    {
        String seed = RandomUtil.getRandomString(10);
        String expectedResponse = licenseService.encryptResponse(LicenseServerResponseEnum.INVALID.getCode(), seed, null);

        mockMvc.perform(get("/v1/validateLicense")
                .param("applicationKey", EXPIRED_APP_KEY)
                .param("licenseNumber", EXPIRED_LICENSE_NUM)
                .param("ipAddress", RandomUtil.getRandomString(10))
                .param("hostname", RandomUtil.getRandomString(10))
                .param("macAddress", RandomUtil.getRandomString(10))
                .param("seed", seed))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}
