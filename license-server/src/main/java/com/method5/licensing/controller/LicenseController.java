package com.method5.licensing.controller;

import com.method5.licensing.constant.LicenseServerResponseEnum;
import com.method5.licensing.exceptions.CoreException;
import com.method5.licensing.service.LicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LicenseController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/v1/validateLicense" )
    public String getValidateLicense(@RequestParam("applicationKey") final String applicationKey,
                                     @RequestParam("licenseNumber") final String licenseNumber,
                                     @RequestParam("ipAddress") final String ipAddress,
                                     @RequestParam("hostname") final String hostname,
                                     @RequestParam("macAddress") final String macAddress,
                                     @RequestParam("seed") final String seed)
    {
        try
        {
            return licenseService.validateLicense(applicationKey, licenseNumber, ipAddress, hostname, macAddress, seed);
        }
        catch(CoreException e)
        {
            LOG.error("Error validating license", e);
            return String.valueOf(LicenseServerResponseEnum.ERROR.getCode());
        }
    }

    @ResponseBody
    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    @ResponseStatus(value = HttpStatus.OK)
    public String handleResourceNotFoundException(MissingServletRequestParameterException e) {

        LOG.error("Missing Request Parameter" + e.getMessage());

        return String.valueOf(LicenseServerResponseEnum.ERROR.getCode());
    }
}
