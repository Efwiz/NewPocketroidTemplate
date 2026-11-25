package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2587n4 extends C2671o4 {
    C2587n4(String str, String str2, Character ch) {
        this(new C2436l4(str, str2.toCharArray()), ch);
    }

    @Override // com.android.tools.r8.internal.C2671o4, com.android.tools.r8.internal.AbstractC2753p4
    final void a(StringBuilder sb, byte[] bArr, int i) {
        int i2 = AbstractC3123uM.a;
        int i3 = 0;
        AbstractC3123uM.a(0, i, bArr.length);
        for (int i4 = i; i4 >= 3; i4 -= 3) {
            int i5 = i3 + 2;
            int i6 = ((bArr[i3 + 1] & 255) << 8) | ((bArr[i3] & 255) << 16);
            i3 += 3;
            int i7 = i6 | (bArr[i5] & 255);
            sb.append(this.b.a(i7 >>> 18));
            sb.append(this.b.a((i7 >>> 12) & 63));
            sb.append(this.b.a((i7 >>> 6) & 63));
            sb.append(this.b.a(i7 & 63));
        }
        if (i3 < i) {
            a(sb, bArr, i3, i - i3);
        }
    }

    private C2587n4(C2436l4 c2436l4, Character ch) {
        super(c2436l4, ch);
        AbstractC3123uM.a(c2436l4.b.length == 64);
    }

    @Override // com.android.tools.r8.internal.C2671o4
    final AbstractC2753p4 a(C2436l4 c2436l4) {
        return new C2587n4(c2436l4, null);
    }
}
