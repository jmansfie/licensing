package com.method5.licensing.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="License")
public class License
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="LicenseID")
    private Integer licenseID;

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
    @Column(name="EffectiveDate")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ExpirationDate")
    private Date expirationDate;

    @Column(name="Notes")
    private String notes;

    @Column(name="Active")
    private boolean active;

    @Column(name="PrivateKey")
    private String privateKey;

    @Column(name="Password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getLicenseID()
    {
        return licenseID;
    }

    public void setLicenseID(Integer licenseID)
    {
        this.licenseID = licenseID;
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

    public Date getEffectiveDate()
    {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
