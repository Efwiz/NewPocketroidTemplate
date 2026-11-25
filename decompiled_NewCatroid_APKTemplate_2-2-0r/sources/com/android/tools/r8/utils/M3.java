package com.android.tools.r8.utils;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
abstract /* synthetic */ class M3 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[N3.values().length];
        a = iArr;
        try {
            iArr[N3.PUBLIC.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[N3.PROTECTED.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[N3.PRIVATE.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[N3.PACKAGE_PRIVATE.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
