package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class V0 {
    static final /* synthetic */ int[] a;
    static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[com.android.tools.r8.ir.optimize.U.values().length];
        b = iArr;
        try {
            com.android.tools.r8.ir.optimize.U u = com.android.tools.r8.ir.optimize.U.NEVER;
            iArr[5] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            b[com.android.tools.r8.ir.optimize.U.SUBCLASS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            b[com.android.tools.r8.ir.optimize.U.PACKAGE.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            int[] iArr2 = b;
            com.android.tools.r8.ir.optimize.U u2 = com.android.tools.r8.ir.optimize.U.NEVER;
            iArr2[2] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            b[com.android.tools.r8.ir.optimize.U.SAMECLASS.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            int[] iArr3 = b;
            com.android.tools.r8.ir.optimize.U u3 = com.android.tools.r8.ir.optimize.U.NEVER;
            iArr3[0] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        int[] iArr4 = new int[((X0[]) X0.values().clone()).length];
        a = iArr4;
        try {
            iArr4[2] = 1;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            a[3] = 2;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            a[4] = 3;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            a[5] = 4;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            a[6] = 5;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            a[1] = 6;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            a[0] = 7;
        } catch (NoSuchFieldError unused13) {
        }
    }
}
