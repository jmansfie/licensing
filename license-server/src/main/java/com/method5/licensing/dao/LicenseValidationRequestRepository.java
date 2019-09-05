package com.method5.licensing.dao;

import com.method5.licensing.domain.LicenseValidationRequest;
import org.springframework.data.repository.CrudRepository;

public interface LicenseValidationRequestRepository  extends CrudRepository<LicenseValidationRequest, Integer> {

}
