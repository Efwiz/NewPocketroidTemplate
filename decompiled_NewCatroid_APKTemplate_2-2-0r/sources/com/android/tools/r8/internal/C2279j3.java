package com.android.tools.r8.internal;

import java.util.RandomAccess;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2279j3 extends X implements RandomAccess {
    final /* synthetic */ int[] a;

    C2279j3(int[] iArr) {
        this.a = iArr;
    }

    @Override // com.android.tools.r8.internal.AbstractC2578n
    public final int a() {
        return this.a.length;
    }

    @Override // com.android.tools.r8.internal.AbstractC2578n, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Integer) {
            return f(((Number) obj).intValue());
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0017 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:14:? A[RETURN, SYNTHETIC] */
    public final boolean f(int i) {
        int[] iArr = this.a;
        AbstractC0936Ds.c(iArr, "<this>");
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            if (i == iArr[i2]) {
                if (i2 >= 0) {
                    return true;
                }
                return false;
            }
            i2++;
        }
        i2 = -1;
        if (i2 >= 0) {
            return true;
        }
        return false;
    }

    @Override // java.util.List
    public final Object get(int i) {
        return Integer.valueOf(this.a[i]);
    }

    @Override // com.android.tools.r8.internal.X, java.util.List
    public final int indexOf(Object obj) {
        if (!(obj instanceof Integer)) {
            return -1;
        }
        int iIntValue = ((Number) obj).intValue();
        int[] iArr = this.a;
        AbstractC0936Ds.c(iArr, "<this>");
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (iIntValue == iArr[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2578n, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.a.length == 0;
    }

    @Override // com.android.tools.r8.internal.X, java.util.List
    public final int lastIndexOf(Object obj) {
        if (!(obj instanceof Integer)) {
            return -1;
        }
        int iIntValue = ((Number) obj).intValue();
        int[] iArr = this.a;
        AbstractC0936Ds.c(iArr, "<this>");
        int length = iArr.length - 1;
        if (length < 0) {
            return -1;
        }
        while (true) {
            int i = length - 1;
            if (iIntValue == iArr[length]) {
                return length;
            }
            if (i < 0) {
                return -1;
            }
            length = i;
        }
    }
}
