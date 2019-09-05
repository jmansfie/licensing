package com.method5.licensing.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="LicenseValidationRequest")
public class LicenseValidationRequest
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="LicenseValidationRequestID")
    private Integer licenseValidationRequestID;

    @Column(name="ApplicationKey")
    private String applicationKey;

    @Column(name="LicenseNumber")
    private String licenseNumber;

    @Column(name="IPAddress")
    private String ipAddress;

    @Column(name="MACAddress")
    private String macAddress;

    @Column(name="Hostname")
    private String hostname;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Date")
    private Date date;

    @Column(name="Valid")
    private boolean valid;

    public Integer getLicenseValidationRequestID()
    {
        return licenseValidationRequestID;
    }

    public void setLicenseValidationRequestID(Integer licenseValidationRequestID)
    {
        this.licenseValidationRequestID = licenseValidationRequestID;
    }

    public String getApplicationKey()
    {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey)
    {
        this.applicationKey = applicationKey;
    }

    public String getLicenseNumber()
    {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber)
    {
        this.licenseNumber = licenseNumber;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress()
    {
        return macAddress;
    }

    public void setMacAddress(String macAddress)
    {
        this.macAddress = macAddress;
    }

    public String getHostname()
    {
        return hostname;
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public boolean isValid()
    {
        return valid;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
}