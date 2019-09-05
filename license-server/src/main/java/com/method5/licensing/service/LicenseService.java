package com.method5.licensing.service;

import com.method5.licensing.exceptions.CoreException;

public interface LicenseService {
    public String validateLicense(String applicationKey, String licenseNumber, String ipAddress, String hostname,
                                  String macAddress, String seed) throws CoreException;

    public String encryptResponse(int responseCode, String seed, String privateKey) throws CoreException;
}
