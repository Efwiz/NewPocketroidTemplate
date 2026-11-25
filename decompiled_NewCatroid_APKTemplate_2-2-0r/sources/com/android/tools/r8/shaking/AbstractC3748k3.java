package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.k3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract /* synthetic */ class AbstractC3748k3 {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[EnumC3758l3.values().length];
        a = iArr;
        try {
            iArr[EnumC3758l3.KEEP.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[EnumC3758l3.KEEP_CLASS_MEMBERS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[2] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[3] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
