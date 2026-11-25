package com.android.tools.r8.shaking;

import com.android.tools.r8.AbstractC0403b;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class V3 {
    static final /* synthetic */ int[] a;
    static final /* synthetic */ int[] b;
    static final /* synthetic */ int[] c;
    static final /* synthetic */ int[] d;
    static final /* synthetic */ int[] e;

    static {
        int[] iArr = new int[AbstractC0403b.c(2).length];
        e = iArr;
        try {
            iArr[0] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            e[1] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        int[] iArr2 = new int[AbstractC0403b.c(2).length];
        d = iArr2;
        try {
            iArr2[0] = 1;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            d[1] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            new int[Q1.values().length][0] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        int[] iArr3 = new int[r.values().length];
        c = iArr3;
        try {
            iArr3[0] = 1;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            c[1] = 2;
        } catch (NoSuchFieldError unused7) {
        }
        int[] iArr4 = new int[Q0.values().length];
        b = iArr4;
        try {
            iArr4[0] = 1;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            b[1] = 2;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            b[2] = 3;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            b[3] = 4;
        } catch (NoSuchFieldError unused11) {
        }
        int[] iArr5 = new int[EnumC3758l3.values().length];
        a = iArr5;
        try {
            iArr5[EnumC3758l3.KEEP_CLASS_MEMBERS.ordinal()] = 1;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            a[2] = 2;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            a[EnumC3758l3.KEEP.ordinal()] = 3;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            a[3] = 4;
        } catch (NoSuchFieldError unused15) {
        }
    }
}
