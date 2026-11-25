package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1589a6 {
    static final /* synthetic */ boolean a = true;
    public static final /* synthetic */ int b = 0;

    public static int a(int i, int i2) {
        return i | (1 << (i2 - 1));
    }

    private static int a(long j) {
        return ((int) j) & 255;
    }

    public static boolean a(int i) {
        return i >= 0 && i <= 65535;
    }

    public static void a(int i, InterfaceC1741c6 interfaceC1741c6) {
        interfaceC1741c6.a((i >> 24) & 255);
        interfaceC1741c6.a((i >> 16) & 255);
        interfaceC1741c6.a((i >> 8) & 255);
        interfaceC1741c6.a(i & 255);
    }

    public static void a(long j, InterfaceC1741c6 interfaceC1741c6) {
        interfaceC1741c6.a(a(j >> 56));
        interfaceC1741c6.a(a(j >> 48));
        interfaceC1741c6.a(a(j >> 40));
        interfaceC1741c6.a(a(j >> 32));
        interfaceC1741c6.a(a(j >> 24));
        interfaceC1741c6.a(a(j >> 16));
        interfaceC1741c6.a(a(j >> 8));
        interfaceC1741c6.a(a(j));
    }
}
