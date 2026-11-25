package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC2167hb {
    static final /* synthetic */ int[] a;
    static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[EnumC2246ib.values().length];
        b = iArr;
        try {
            iArr[EnumC2246ib.NONE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            b[EnumC2246ib.GT.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            b[EnumC2246ib.LT.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        int[] iArr2 = new int[EnumC2527mI.values().length];
        a = iArr2;
        try {
            iArr2[6] = 1;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            a[5] = 2;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            a[EnumC2527mI.LONG.ordinal()] = 3;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
