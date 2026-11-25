package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class T2 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[EnumC2527mI.values().length];
        a = iArr;
        try {
            iArr[6] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[5] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[EnumC2527mI.INT.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[EnumC2527mI.LONG.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
