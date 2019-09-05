package com.method5.licensing.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class EncryptionUtilTest {

    @Test
    public void hex2decimal()
    {
        Assert.assertEquals(EncryptionUtil.hex2decimal("a4f9e"), 675742);
        Assert.assertEquals(EncryptionUtil.hex2decimal("e375a"), 931674);
    }
}
