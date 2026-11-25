package com.android.tools.r8.utils;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class J {
    static final /* synthetic */ boolean a = true;

    public static boolean a(int i, int i2) {
        if (a || ((i - 1) & i) == 0) {
            return ((i - 1) & i2) == 0;
        }
        throw new AssertionError();
    }

    public static boolean b(int i, int i2) {
        return (i & i2) != 0;
    }

    public static boolean c(int i, int i2) {
        return b(i, 1 << (i2 - 1));
    }
}
