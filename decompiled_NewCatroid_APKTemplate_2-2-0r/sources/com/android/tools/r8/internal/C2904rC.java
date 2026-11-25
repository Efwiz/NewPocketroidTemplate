package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C2904rC extends Y5 {
    protected final byte[] c;
    private int d = 0;

    C2904rC(byte[] bArr) {
        this.c = bArr;
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int a() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final boolean b() {
        return true;
    }

    @Override // com.android.tools.r8.internal.Y5
    public final boolean c() {
        byte[] bArr = this.c;
        return O10.a(bArr, 0, bArr.length) == 0;
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int e() {
        return this.d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Y5) || size() != ((Y5) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (obj instanceof C2904rC) {
            return a((C2904rC) obj, 0, size());
        }
        if (obj instanceof C2615nU) {
            return obj.equals(this);
        }
        String strValueOf = String.valueOf(obj.getClass());
        throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 49).append("Has a new type of ByteString been created? Found ").append(strValueOf).toString());
    }

    protected int g() {
        return 0;
    }

    public final int hashCode() {
        int iA = this.d;
        if (iA == 0) {
            int size = size();
            iA = a(size, 0, size);
            if (iA == 0) {
                iA = 1;
            }
            this.d = iA;
        }
        return iA;
    }

    @Override // java.lang.Iterable
    /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
    public W5 iterator() {
        return new C2834qC(this, 0);
    }

    @Override // com.android.tools.r8.internal.Y5
    public int size() {
        return this.c.length;
    }

    @Override // com.android.tools.r8.internal.Y5
    final void a(OutputStream outputStream, int i, int i2) throws IOException {
        outputStream.write(this.c, g() + i, i2);
    }

    @Override // com.android.tools.r8.internal.Y5
    protected void b(int i, int i2, int i3, byte[] bArr) {
        System.arraycopy(this.c, i, bArr, i2, i3);
    }

    final boolean a(C2904rC c2904rC, int i, int i2) {
        if (i2 <= c2904rC.size()) {
            if (i + i2 <= c2904rC.size()) {
                byte[] bArr = this.c;
                byte[] bArr2 = c2904rC.c;
                int iG = g() + i2;
                int iG2 = g();
                int iG3 = c2904rC.g() + i;
                while (iG2 < iG) {
                    if (bArr[iG2] != bArr2[iG3]) {
                        return false;
                    }
                    iG2++;
                    iG3++;
                }
                return true;
            }
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: ").append(i).append(", ").append(i2).append(", ").append(c2904rC.size()).toString());
        }
        throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(size()).toString());
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int b(int i, int i2, int i3) {
        return O10.a(i, i2, i3 + i2, this.c);
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int a(int i, int i2, int i3) {
        byte[] bArr = this.c;
        int iG = g() + i2;
        for (int i4 = iG; i4 < iG + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    @Override // com.android.tools.r8.internal.Y5
    public final String f() {
        byte[] bArr = this.c;
        return new String(bArr, 0, bArr.length, "UTF-8");
    }
}
