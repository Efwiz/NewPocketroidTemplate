package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.c7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC1742c7 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[EnumC1812d20.values().length];
        a = iArr;
        try {
            iArr[EnumC1812d20.INT.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[EnumC1812d20.LONG.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            int[] iArr2 = a;
            EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
            iArr2[2] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            int[] iArr3 = a;
            EnumC1812d20 enumC1812d21 = EnumC1812d20.OBJECT;
            iArr3[4] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
