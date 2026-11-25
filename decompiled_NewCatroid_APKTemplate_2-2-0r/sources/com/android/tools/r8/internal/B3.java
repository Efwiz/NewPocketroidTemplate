package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B3 {
    public static int a(int i, boolean z) {
        return z ? i | 1 : i & (-2);
    }

    public static boolean a(int i) {
        return (i & 1) != 0;
    }

    public static int b(int i, boolean z) {
        return z ? i | 4 : i & (-5);
    }

    public static boolean b(int i) {
        return (i & 4) != 0;
    }

    public static int c(int i, boolean z) {
        return z ? i | 2 : i & (-3);
    }

    public static boolean c(int i) {
        return (i & 2) != 0;
    }
}
