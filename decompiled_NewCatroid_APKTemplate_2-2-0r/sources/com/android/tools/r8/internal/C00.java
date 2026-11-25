package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C00 {
    private final byte[] a;
    private final int b;

    C00(int i, byte[] bArr) {
        this.a = bArr;
        this.b = i;
    }

    public final int a() {
        return this.a[this.b];
    }

    public final int b(int i) {
        return this.a[(i * 2) + this.b + 2];
    }

    public final String toString() {
        int iA = a();
        StringBuilder sb = new StringBuilder(iA * 2);
        for (int i = 0; i < iA; i++) {
            int iA2 = a(i);
            if (iA2 == 0) {
                sb.append('[');
            } else if (iA2 == 1) {
                sb.append('.');
            } else if (iA2 == 2) {
                sb.append('*');
            } else {
                if (iA2 != 3) {
                    throw new AssertionError();
                }
                sb.append(b(i)).append(';');
            }
        }
        return sb.toString();
    }

    public final int a(int i) {
        return this.a[(i * 2) + this.b + 1];
    }

    static void a(C00 c00, C1667b6 c1667b6) {
        if (c00 == null) {
            c1667b6.b(0);
            return;
        }
        byte[] bArr = c00.a;
        int i = c00.b;
        c1667b6.a(bArr, i, (bArr[i] * 2) + 1);
    }
}
