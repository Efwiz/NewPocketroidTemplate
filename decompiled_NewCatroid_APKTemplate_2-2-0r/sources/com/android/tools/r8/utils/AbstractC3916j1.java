package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.j1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3916j1 {
    public static boolean a(int i) {
        return e(i);
    }

    public static boolean b(int i) {
        return e(i);
    }

    public static boolean c(int i) {
        return i == 63;
    }

    public static boolean d(int i) {
        return e(i) || i == 32 || i == 160 || i == 5760 || (8192 <= i && i <= 8202) || i == 8239 || i == 8287 || i == 12288;
    }

    private static boolean e(int i) {
        return (65 <= i && i <= 90) || (97 <= i && i <= 122) || ((48 <= i && i <= 57) || i == 36 || i == 45 || i == 95 || ((161 <= i && i <= 8191) || ((8208 <= i && i <= 8231) || ((8240 <= i && i <= 55295) || ((57344 <= i && i < 65279) || ((65279 < i && i <= 65519) || (65536 <= i && i <= 1114111)))))));
    }
}
