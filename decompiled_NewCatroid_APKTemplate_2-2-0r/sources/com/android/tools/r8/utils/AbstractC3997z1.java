package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.z1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
abstract /* synthetic */ class AbstractC3997z1 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[EnumC3904h2.values().length];
        a = iArr;
        try {
            iArr[EnumC3904h2.Off.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[EnumC3904h2.Auto.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
    }
}
