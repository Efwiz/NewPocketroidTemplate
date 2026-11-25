package com.android.tools.r8.shaking;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class H2 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[((I2[]) I2.f.clone()).length];
        a = iArr;
        try {
            iArr[I2.a.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[I2.b.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[2] = 3;
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
