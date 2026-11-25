package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.m4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2513m4 extends C2671o4 {
    final char[] d;

    C2513m4() {
        this(new C2436l4("base16()", "0123456789ABCDEF".toCharArray()));
    }

    @Override // com.android.tools.r8.internal.C2671o4, com.android.tools.r8.internal.AbstractC2753p4
    final void a(StringBuilder sb, byte[] bArr, int i) {
        int i2 = AbstractC3123uM.a;
        AbstractC3123uM.a(0, i, bArr.length);
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = bArr[i3] & 255;
            sb.append(this.d[i4]);
            sb.append(this.d[i4 | 256]);
        }
    }

    private C2513m4(C2436l4 c2436l4) {
        super(c2436l4, null);
        this.d = new char[512];
        AbstractC3123uM.a(c2436l4.b.length == 16);
        for (int i = 0; i < 256; i++) {
            this.d[i] = c2436l4.a(i >>> 4);
            this.d[i | 256] = c2436l4.a(i & 15);
        }
    }

    @Override // com.android.tools.r8.internal.C2671o4
    final AbstractC2753p4 a(C2436l4 c2436l4) {
        return new C2513m4(c2436l4);
    }
}
