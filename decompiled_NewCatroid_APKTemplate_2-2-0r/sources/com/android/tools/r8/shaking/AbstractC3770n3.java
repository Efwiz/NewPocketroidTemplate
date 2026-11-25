package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.n3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC3770n3 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[EnumC3791r3.values().length];
        a = iArr;
        try {
            iArr[EnumC3791r3.ALL.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[EnumC3791r3.ALL_FIELDS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[EnumC3791r3.FIELD.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[EnumC3791r3.ALL_METHODS.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            a[4] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            a[5] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            a[6] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            a[EnumC3791r3.METHOD.ordinal()] = 8;
        } catch (NoSuchFieldError unused8) {
        }
    }
}
