package com.android.tools.r8.utils;

import com.android.tools.r8.Version;
import com.android.tools.r8.internal.CM;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.utils.b3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3874b3 {
    public static boolean b(String str) {
        return a(str, System.getProperty(str), false);
    }

    public static int b() {
        String strA = a("com.android.tools.r8.inliningInstructionLimit");
        if (strA != null) {
            return Integer.parseInt(strA);
        }
        return -1;
    }

    public static Object a(Function function, Supplier supplier) {
        if (a()) {
            return function.apply(System.getProperty("com.android.tools.r8.startup.profile"));
        }
        return supplier.get();
    }

    public static String a(String str) {
        if (Version.isDevelopmentVersion()) {
            return System.getProperty(str);
        }
        return null;
    }

    public static boolean a() {
        CM.b();
        return System.getProperty("com.android.tools.r8.startup.profile") != null;
    }

    public static boolean a(String str, boolean z) {
        return a(str, a(str), z);
    }

    private static boolean a(String str, String str2, boolean z) {
        if (str2 == null) {
            return z;
        }
        if (Y2.c(str2)) {
            return false;
        }
        if (Y2.d(str2)) {
            return true;
        }
        throw new IllegalArgumentException("Expected value of " + str + " to be a boolean, but was: " + str2);
    }
}
