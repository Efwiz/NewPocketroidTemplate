package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC2852qU {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[C1651ax.a.values().length];
        a = iArr;
        try {
            iArr[C1651ax.a.SHRINKING.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[C1651ax.a.OPTIMIZING.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[C1651ax.a.OBFUSCATING.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[3] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            a[4] = 5;
        } catch (NoSuchFieldError unused5) {
        }
    }
}
