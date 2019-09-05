package com.method5.licensing.util;

public class StringUtil {
    public static String concat(Object... objs)
    {
        StringBuilder sb = new StringBuilder();

        for (Object obj : objs)
        {
            sb.append(obj);
        }

        return sb.toString();
    }
}
