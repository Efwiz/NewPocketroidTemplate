package com.reandroid.common;

import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;

/* JADX INFO: loaded from: classes10.dex */
public interface Namespace {
    public static final String URI_ANDROID = ObjectsUtil.of("http://schemas.android.com/apk/res/android");
    public static final String URI_RES_AUTO = ObjectsUtil.of("http://schemas.android.com/apk/res-auto");
    public static final String PREFIX_ANDROID = ObjectsUtil.of("android");
    public static final String PREFIX_APP = ObjectsUtil.of("app");

    static boolean isValidPrefixChar(char c) {
        return (c <= 'Z' && c >= 'A') || (c <= 'z' && c >= 'a');
    }

    static boolean isValidPrefixSymbol(char c) {
        return (c <= '9' && c >= '0') || c == '_';
    }

    String getPrefix();

    String getUri();

    static boolean isValidUri(String str, int i) {
        int i2 = (i >> 24) & 255;
        if (i2 == 0) {
            if (StringsUtil.isEmpty(str)) {
                return true;
            }
            return isExternalUri(str);
        }
        if (i2 == 1) {
            return URI_ANDROID.equals(str);
        }
        if (URI_ANDROID.equals(str) || !isValidUri(str)) {
            return false;
        }
        return !isExternalUri(str);
    }

    static boolean isValidUri(String str, String str2) {
        if (PREFIX_ANDROID.equals(str2)) {
            return URI_ANDROID.equals(str);
        }
        if (URI_ANDROID.equals(str)) {
            return false;
        }
        return isValidUri(str);
    }

    static boolean isValidUri(String str) {
        if (str == null || str.length() < 3) {
            return false;
        }
        return str.contains("://");
    }

    static boolean isExternalUri(String str) {
        return (str == null || str.contains("schemas.android.com")) ? false : true;
    }

    static boolean isValidPrefix(String str, String str2) {
        String str3 = PREFIX_ANDROID;
        if (str3.equals(str2)) {
            return str3.equals(str);
        }
        if (str3.equals(str)) {
            return false;
        }
        return isValidPrefix(str);
    }

    static boolean isValidPrefix(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (!isValidPrefixChar(charArray[0])) {
            return false;
        }
        for (int i = 1; i < charArray.length; i++) {
            char c = charArray[i];
            if (!isValidPrefixChar(c) && !isValidPrefixSymbol(c)) {
                return false;
            }
        }
        return true;
    }

    static String prefixForResourceId(int i) {
        if (i == 0) {
            return null;
        }
        int i2 = (i >> 24) & 255;
        if (i2 == 1) {
            return PREFIX_ANDROID;
        }
        if (i2 != 0) {
            return PREFIX_APP;
        }
        return null;
    }

    static String uriForResourceId(int i) {
        if (i == 0) {
            return null;
        }
        int i2 = (i >> 24) & 255;
        if (i2 == 1) {
            return URI_ANDROID;
        }
        if (i2 != 0) {
            return URI_RES_AUTO;
        }
        return null;
    }
}
