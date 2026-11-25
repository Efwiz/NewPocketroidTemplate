package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0935Dr {
    public static final C0867Br a = new C0867Br();

    public static int a(InterfaceC0841Ar interfaceC0841Ar, int[] iArr) {
        int i;
        int length = iArr.length;
        if (length < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + length + ") is negative");
        }
        if (length > iArr.length) {
            throw new IllegalArgumentException();
        }
        int i2 = 0;
        int i3 = length;
        while (true) {
            i = i3 - 1;
            if (i3 == 0 || !interfaceC0841Ar.hasNext()) {
                break;
            }
            iArr[i2] = interfaceC0841Ar.t();
            i2++;
            i3 = i;
        }
        return (length - i) - 1;
    }
}
