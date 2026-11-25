package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.o, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC3771o {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[com.android.tools.r8.utils.N3.values().length];
        a = iArr;
        try {
            iArr[com.android.tools.r8.utils.N3.PROTECTED.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[com.android.tools.r8.utils.N3.PACKAGE_PRIVATE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[com.android.tools.r8.utils.N3.PUBLIC.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
