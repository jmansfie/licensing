package com.method5.licensing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Scope("singleton")
public class InvalidRequestManager
{
    @Autowired
    private ExpiringCache<List<Long>> requestCache;

    @Value("${licenseServer.number.invalid.license.requests}")
    private long numberAllowedLicenseRequests;

    @Value("${licenseServer.invalid.license.requests.time.period}")
    private long invalidLicenseRequestPeriodInSeconds;

    @Value("${licenseServer.invalid.license.request.ban.length}")
    private long banLengthInSeconds;

    private enum CacheType
    {
        IP_ADDRESS, HOSTNAME, MAC_ADDRESS, BANNED_IP_ADDRESS, BANNED_HOSTNAME, BANNED_MAC_ADDRESS
    }

    private String getCacheKey(CacheType cacheType, String value)
    {
        return cacheType + "|~" + value;
    }

    public boolean isRequestBanned(String ipAddress, String hostname, String macAddress)
    {
        return isIdentificationBanned(CacheType.BANNED_IP_ADDRESS, ipAddress) ||
                isIdentificationBanned(CacheType.BANNED_HOSTNAME, hostname) ||
                isIdentificationBanned(CacheType.BANNED_MAC_ADDRESS, macAddress);
    }


    public void initiateBan(String ipAddress, String hostname, String macAddress)
    {
        // Ban ipAddress
        {
            String key = getCacheKey(CacheType.BANNED_IP_ADDRESS, ipAddress);
            requestCache.put(key, new ArrayList<Long>(), banLengthInSeconds);
        }

        // Ban hostname
        {
            String key = getCacheKey(CacheType.BANNED_HOSTNAME, hostname);
            requestCache.put(key, new ArrayList<Long>(), banLengthInSeconds);
        }

        // Ban macAddress
        {
            String key = getCacheKey(CacheType.BANNED_MAC_ADDRESS, macAddress);
            requestCache.put(key, new ArrayList<Long>(), banLengthInSeconds);
        }
    }

    private boolean isIdentificationBanned(CacheType cacheType, String value)
    {
        String banKey = getCacheKey(cacheType, value);
        List<Long> banValue = requestCache.get(banKey);

        return banValue != null;
    }

    public void cacheInvalidRequest(String ipAddress, String hostname, String macAddress)
    {
        {
            List<Long> cachedValue = logForIdentification(CacheType.IP_ADDRESS, ipAddress);

            if(cachedValue.size() > numberAllowedLicenseRequests)
            {
                initiateBan(ipAddress, hostname, macAddress);
                return;
            }
        }
        {
            List<Long> cachedValue = logForIdentification(CacheType.HOSTNAME, hostname);

            if(cachedValue.size() > numberAllowedLicenseRequests)
            {
                initiateBan(ipAddress, hostname, macAddress);
                return;
            }
        }
        {
            List<Long> cachedValue = logForIdentification(CacheType.MAC_ADDRESS, macAddress);

            if(cachedValue.size() > numberAllowedLicenseRequests)
            {
                initiateBan(ipAddress, hostname, macAddress);
                return;
            }
        }
    }

    private List<Long> logForIdentification(CacheType type, String value)
    {
        String key = getCacheKey(type, value);
        List<Long> cachedValue = requestCache.get(key);

        if(cachedValue == null)
        {
            cachedValue = new ArrayList<Long>();
        }

        long currMills = System.currentTimeMillis();

        cleanIdentificationList(cachedValue, currMills);
        cachedValue.add(currMills);

        requestCache.put(key, cachedValue, invalidLicenseRequestPeriodInSeconds);

        return cachedValue;
    }

    private void cleanIdentificationList(List<Long> cachedValue, long currMills)
    {
        long earliestTime = currMills - invalidLicenseRequestPeriodInSeconds * 1000;

        Set<Long> toRemove = new HashSet<Long>();

        for(Long time : cachedValue)
        {
            if(earliestTime > time)
            {
                toRemove.add(time);
            }
        }

        for(Long time : toRemove)
        {
            cachedValue.remove(time);
        }
    }

    public long getNumberAllowedLicenseRequests()
    {
        return numberAllowedLicenseRequests;
    }

    public void setNumberAllowedLicenseRequests(long numberAllowedLicenseRequests)
    {
        this.numberAllowedLicenseRequests = numberAllowedLicenseRequests;
    }

    public long getInvalidLicenseRequestPeriodInSeconds()
    {
        return invalidLicenseRequestPeriodInSeconds;
    }

    public void setInvalidLicenseRequestPeriodInSeconds(long invalidLicenseRequestPeriodInSeconds)
    {
        this.invalidLicenseRequestPeriodInSeconds = invalidLicenseRequestPeriodInSeconds;
    }

    public long getBanLengthInSeconds()
    {
        return banLengthInSeconds;
    }

    public void setBanLengthInSeconds(long banLengthInSeconds)
    {
        this.banLengthInSeconds = banLengthInSeconds;
    }
}