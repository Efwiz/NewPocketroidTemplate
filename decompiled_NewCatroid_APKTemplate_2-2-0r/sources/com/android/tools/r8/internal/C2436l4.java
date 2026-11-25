package com.android.tools.r8.internal;

import java.math.RoundingMode;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2436l4 {
    private final String a;
    private final char[] b;
    final int c;
    final int d;
    final int e;
    final int f;
    private final byte[] g;

    C2436l4(String str, char[] cArr) {
        this.a = (String) AbstractC3123uM.a(str);
        this.b = (char[]) AbstractC3123uM.a(cArr);
        try {
            int iA = AbstractC1112Jr.a(cArr.length, RoundingMode.UNNECESSARY);
            this.d = iA;
            int iMin = Math.min(8, Integer.lowestOneBit(iA));
            try {
                this.e = 8 / iMin;
                this.f = iA / iMin;
                this.c = cArr.length - 1;
                byte[] bArr = new byte[128];
                Arrays.fill(bArr, (byte) -1);
                for (int i = 0; i < cArr.length; i++) {
                    char c = cArr[i];
                    AbstractC3123uM.a(c < 128, "Non-ASCII character: %s", c);
                    AbstractC3123uM.a(bArr[c] == -1, "Duplicate character: %s", c);
                    bArr[c] = (byte) i;
                }
                this.g = bArr;
                boolean[] zArr = new boolean[this.e];
                for (int i2 = 0; i2 < this.f; i2++) {
                    zArr[AbstractC1112Jr.a(i2 * 8, this.d, RoundingMode.CEILING)] = true;
                }
            } catch (ArithmeticException e) {
                String str2 = new String(cArr);
                throw new IllegalArgumentException(str2.length() != 0 ? "Illegal alphabet ".concat(str2) : new String("Illegal alphabet "), e);
            }
        } catch (ArithmeticException e2) {
            throw new IllegalArgumentException(new StringBuilder(35).append("Illegal alphabet length ").append(cArr.length).toString(), e2);
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C2436l4) {
            return Arrays.equals(this.b, ((C2436l4) obj).b);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.b);
    }

    public final String toString() {
        return this.a;
    }

    final char a(int i) {
        return this.b[i];
    }

    public final boolean a(char c) {
        byte[] bArr = this.g;
        return c < bArr.length && bArr[c] != -1;
    }
}
