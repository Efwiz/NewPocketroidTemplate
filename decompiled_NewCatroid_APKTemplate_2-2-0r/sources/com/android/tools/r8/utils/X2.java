package com.android.tools.r8.utils;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
abstract /* synthetic */ class X2 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[((Y2.a[]) Y2.a.values().clone()).length];
        a = iArr;
        try {
            iArr[Y2.a.PARENS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[Y2.a.SQUARE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[Y2.a.TUBORG.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[Y2.a.NONE.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
