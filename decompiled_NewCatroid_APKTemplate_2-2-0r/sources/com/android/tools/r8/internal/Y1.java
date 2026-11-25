package com.android.tools.r8.internal;

import java.util.function.BiPredicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Y1 extends AbstractC1660b2 {
    private final byte[] d;

    private Y1(byte[] bArr) {
        this.d = bArr;
    }

    @Override // com.android.tools.r8.internal.AbstractC1660b2
    final boolean a(byte[] bArr, int i, int i2) {
        if (bArr.length != i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (bArr[i3] != this.d[i3 + i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1660b2
    final byte b(byte[] bArr, int i, int i2) {
        int i3 = i;
        while (i3 < i + i2) {
            byte[] bArr2 = this.d;
            int iA = AbstractC0968Es.a((byte) 0, (byte) 0, bArr2[i3], bArr2[i3 + 1]);
            int i4 = i3 + 2;
            int i5 = i4 + iA;
            if (a(bArr, i4, iA)) {
                return this.d[i5];
            }
            i3 = i5 + 1;
        }
        return (byte) 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1660b2
    final C1580a2 c(int i) {
        byte[] bArr = this.d;
        boolean z = C1580a2.d;
        int iA = AbstractC0968Es.a(bArr[i], bArr[i + 1], bArr[i + 2], bArr[i + 3]);
        int iA2 = AbstractC0968Es.a((byte) 0, (byte) 0, bArr[i + 4], bArr[i + 5]);
        if (iA == 0 && iA2 == 0) {
            return C1580a2.c;
        }
        if ((iA >= 0 || iA2 <= 0) && (iA <= 0 || iA2 != 0)) {
            return new C1580a2(iA, iA2);
        }
        if (C1580a2.d) {
            return C1580a2.c;
        }
        throw new AssertionError("Unexpected position and length");
    }

    @Override // com.android.tools.r8.internal.AbstractC1660b2
    public final int d() {
        byte[] bArr = this.d;
        return AbstractC0968Es.a(bArr[0], bArr[1], bArr[2], bArr[3]);
    }

    @Override // com.android.tools.r8.internal.AbstractC1660b2
    final int a(int i, int i2, byte[] bArr, BiPredicate biPredicate) {
        if (this.d.length < i2) {
            return -1;
        }
        for (int i3 = i; i3 < i + i2; i3 += 2) {
            byte[] bArr2 = this.d;
            int iA = AbstractC0968Es.a((byte) 0, (byte) 0, bArr2[i3], bArr2[i3 + 1]);
            if (biPredicate.test(Integer.valueOf(iA), bArr)) {
                return iA;
            }
        }
        return -1;
    }
}
