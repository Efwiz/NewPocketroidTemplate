package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.AssertionsConfiguration;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.c, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC3545c {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[AssertionsConfiguration.a.values().length];
        a = iArr;
        try {
            iArr[AssertionsConfiguration.a.PACKAGE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[AssertionsConfiguration.a.CLASS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[AssertionsConfiguration.a.ALL.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
