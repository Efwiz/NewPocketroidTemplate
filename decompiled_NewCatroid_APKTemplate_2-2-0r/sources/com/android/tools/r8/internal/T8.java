package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class T8 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[((U8.a[]) U8.a.values().clone()).length];
        a = iArr;
        try {
            iArr[U8.a.LOOKUP.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[U8.a.TABLE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
    }
}
