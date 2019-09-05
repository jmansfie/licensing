package com.method5.licensing.dao;

import com.method5.licensing.domain.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LicenseRepository extends CrudRepository<License, Integer> {
    List<License> findByApplicationKeyAndLicenseNumber(String applicationKey, String licenseNumber);
}
