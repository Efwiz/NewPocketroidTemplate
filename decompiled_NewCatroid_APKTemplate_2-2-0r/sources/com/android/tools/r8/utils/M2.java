package com.android.tools.r8.utils;

import com.android.tools.r8.DiagnosticsLevel;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
abstract /* synthetic */ class M2 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[DiagnosticsLevel.values().length];
        a = iArr;
        try {
            iArr[DiagnosticsLevel.INFO.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[DiagnosticsLevel.WARNING.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[DiagnosticsLevel.ERROR.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[DiagnosticsLevel.NONE.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
