package com.android.tools.r8;

import com.android.tools.r8.utils.L3;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Version {
    public static final String LABEL = "8.1.56";

    public static boolean a() {
        return false;
    }

    static int b(String str) {
        if (str.equals("main")) {
            return -1;
        }
        int iIndexOf = str.indexOf(46) + 1;
        return Integer.parseInt(str.substring(iIndexOf, str.indexOf(46, iIndexOf)));
    }

    static int c(String str) {
        if (str.equals("main")) {
            return -1;
        }
        int iIndexOf = str.indexOf(46, str.indexOf(46) + 1) + 1;
        int iIndexOf2 = str.indexOf(45, iIndexOf);
        if (iIndexOf2 == -1) {
            iIndexOf2 = str.length();
        }
        return Integer.parseInt(str.substring(iIndexOf, iIndexOf2));
    }

    static String d(String str) {
        if (str.equals("main")) {
            return null;
        }
        int iIndexOf = str.indexOf(45) + 1;
        return iIndexOf > 0 ? str.substring(iIndexOf) : "";
    }

    public static int getMajorVersion() {
        return a(LABEL);
    }

    public static int getMinorVersion() {
        return b(LABEL);
    }

    public static int getPatchVersion() {
        return c(LABEL);
    }

    public static String getPreReleaseString() {
        return d(LABEL);
    }

    public static String getVersionString() {
        return "8.1.56 (" + L3.c.a() + Constant.AFTER_QUTO;
    }

    public static boolean isDevelopmentVersion() {
        return a(LABEL, L3.c.c());
    }

    static int a(String str) {
        if (str.equals("main")) {
            return -1;
        }
        return Integer.parseInt(str.substring(0, str.indexOf(46)));
    }

    static boolean a(String str, boolean z) {
        return str.equals("main") || str.endsWith("-dev") || z;
    }
}
