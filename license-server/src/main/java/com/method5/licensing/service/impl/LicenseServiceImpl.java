package com.method5.licensing.service.impl;

import com.method5.licensing.constant.LicenseServerResponseEnum;
import com.method5.licensing.dao.LicenseRepository;
import com.method5.licensing.dao.LicenseValidationRequestRepository;
import com.method5.licensing.domain.License;
import com.method5.licensing.domain.LicenseValidationRequest;
import com.method5.licensing.exceptions.CoreException;
import com.method5.licensing.service.InvalidRequestManager;
import com.method5.licensing.service.LicenseService;
import com.method5.licensing.util.EncryptionUtil;
import com.method5.licensing.util.StringUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {
    private static final Logger LOG = LogManager.getLogger(LicenseService.class);

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseValidationRequestRepository licenseValidationRequestRepository;

    @Autowired
    private InvalidRequestManager invalidRequestManager;

    @Value("${licenseServer.privateKey}")
    private String defaultPrivateKeyLocation;

    @Value("${licenseServer.licenseDirectory}")
    private String licenseDirectoryLocation;

    public String validateLicense(String applicationKey, String licenseNumber, String ipAddress, String hostname,
                                  String macAddress, String seed) throws CoreException
    {
        LOG.info(StringUtil.concat("Validation request for applicationKey:", applicationKey,
                " licenseNumber:", licenseNumber,
                " ipAddress:", ipAddress,
                " hostname:", hostname,
                " macAddress:", macAddress,
                " seed:", seed));

        // Create/Populate ValidationRequest
        LicenseValidationRequest validationRequest = new LicenseValidationRequest();
        validationRequest.setApplicationKey(applicationKey);
        validationRequest.setLicenseNumber(licenseNumber);
        validationRequest.setIpAddress(ipAddress);
        validationRequest.setHostname(hostname);
        validationRequest.setMacAddress(macAddress);
        validationRequest.setDate(Calendar.getInstance().getTime());

        int responseCode;

        if(invalidRequestManager.isRequestBanned(ipAddress, hostname, macAddress))
        {
            LOG.warn("Ban is in effect");

            responseCode = LicenseServerResponseEnum.INVALID.getCode();
            validationRequest.setValid(false);
            licenseValidationRequestRepository.save(validationRequest);

            // Reject immediately and do not validate license
            return encryptResponse(responseCode, seed, null);
        }

        // verify license
        License license = null;

        try
        {
            List<License> licenses = licenseRepository.findByApplicationKeyAndLicenseNumber(applicationKey, licenseNumber);

            if(licenses != null && licenses.size() != 0 && validateLicense(licenses.get(0)))
            {
                LOG.info("license validated for LicenseID={" + licenses.get(0).getLicenseID() + "}");

                responseCode = LicenseServerResponseEnum.VALID.getCode();
                validationRequest.setValid(true);
            }
            else
            {
                LOG.warn("license not valid");

                responseCode = LicenseServerResponseEnum.INVALID.getCode();
                validationRequest.setValid(false);

                invalidRequestManager.cacheInvalidRequest(ipAddress, hostname, macAddress);
            }
        }
        catch (Exception e)
        {
            LOG.error("Encountered an error searching for the License", e);

            responseCode = LicenseServerResponseEnum.ERROR.getCode();
            validationRequest.setValid(false);
        }

        licenseValidationRequestRepository.save(validationRequest);
        String privateKey = license != null ? license.getPrivateKey() : null;
        return encryptResponse(responseCode, seed, privateKey);
    }

    public String encryptResponse(int responseCode, String seed, String privateKey) throws CoreException
    {
        String result = StringUtil.concat(responseCode, "|", seed);
        LOG.info("Result:" + result);

        String encoded;

        if(!StringUtils.isEmpty(privateKey))
            encoded = EncryptionUtil.encryptWithPrivateKey(licenseDirectoryLocation + privateKey, result);
        else
            encoded = EncryptionUtil.encryptWithPrivateKey(defaultPrivateKeyLocation, result);

        LOG.info("Encoded:" + encoded);

        return encoded;
    }

    private boolean validateLicense(License license)
    {
        if(license == null)
        {
            LOG.warn("License was not found");
            return false;
        }

        if(!license.isActive())
        {
            LOG.warn("License not active");
            return false;
        }

        Date currentDate = Calendar.getInstance().getTime();

        if(license.getEffectiveDate() == null || currentDate.before(license.getEffectiveDate()))
        {
            LOG.warn("License not yet effective");
            return false;
        }

        if(license.getExpirationDate() != null && currentDate.after(license.getExpirationDate()))
        {
            LOG.warn("License past expirationDate");
            return false;
        }

        return true;
    }
}
