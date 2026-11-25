package com.reandroid.dex.key;

import com.reandroid.utils.ObjectsUtil;

/* JADX INFO: loaded from: classes11.dex */
public class KeyUtil {
    public static final String ANY_NAME = ObjectsUtil.of("*");

    public static boolean matches(String str, String str2) {
        if (str == null) {
            if (str2 == null) {
                return true;
            }
            return str2.equals(ANY_NAME);
        }
        if (str2 == null) {
            return false;
        }
        if (str.equals(str2)) {
            return true;
        }
        String str3 = ANY_NAME;
        return str.equals(str3) || str2.equals(str3);
    }
}
