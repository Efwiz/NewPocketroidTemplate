package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ye, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC1527Ye {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[EnumC1076Il.values().length];
        a = iArr;
        try {
            iArr[EnumC1076Il.EQ.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[EnumC1076Il.GE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[EnumC1076Il.GT.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[EnumC1076Il.LE.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            a[EnumC1076Il.LT.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            a[EnumC1076Il.NE.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
