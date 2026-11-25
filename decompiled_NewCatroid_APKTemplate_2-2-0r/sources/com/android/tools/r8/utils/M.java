package com.android.tools.r8.utils;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class M {
    private static final Boolean[] a = {Boolean.TRUE, Boolean.FALSE};
    public static final /* synthetic */ int b = 0;

    public static int a(boolean z) {
        return z ? 1 : 0;
    }

    public static boolean a(boolean z, boolean z2) {
        return (z && !z2) || (!z && z2);
    }

    public static Boolean[] a() {
        return a;
    }
}
