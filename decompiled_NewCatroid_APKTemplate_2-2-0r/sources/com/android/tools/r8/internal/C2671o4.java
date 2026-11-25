package com.android.tools.r8.internal;

import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C2671o4 extends AbstractC2753p4 {
    final C2436l4 b;
    final Character c;

    C2671o4(C2436l4 c2436l4, Character ch) {
        this.b = (C2436l4) AbstractC3123uM.a(c2436l4);
        AbstractC3123uM.a(ch == null || !c2436l4.a(ch.charValue()), "Padding character %s was already in alphabet", ch);
        this.c = ch;
    }

    @Override // com.android.tools.r8.internal.AbstractC2753p4
    void a(StringBuilder sb, byte[] bArr, int i) {
        int i2 = AbstractC3123uM.a;
        int i3 = 0;
        AbstractC3123uM.a(0, i, bArr.length);
        while (i3 < i) {
            a(sb, bArr, i3, Math.min(this.b.f, i - i3));
            i3 += this.b.f;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2753p4
    public final AbstractC2753p4 b() {
        return this.c == null ? this : a(this.b);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C2671o4)) {
            return false;
        }
        C2671o4 c2671o4 = (C2671o4) obj;
        return this.b.equals(c2671o4.b) && AbstractC3428yK.a(this.c, c2671o4.c);
    }

    public final int hashCode() {
        return this.b.hashCode() ^ Arrays.hashCode(new Object[]{this.c});
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BaseEncoding.");
        sb.append(this.b.toString());
        if (8 % this.b.d != 0) {
            if (this.c == null) {
                sb.append(".omitPadding()");
            } else {
                sb.append(".withPadChar('").append(this.c).append("')");
            }
        }
        return sb.toString();
    }

    final void a(StringBuilder sb, byte[] bArr, int i, int i2) {
        int i3 = AbstractC3123uM.a;
        AbstractC3123uM.a(i, i + i2, bArr.length);
        int i4 = 0;
        AbstractC3123uM.a(i2 <= this.b.f);
        long j = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            j = (j | ((long) (bArr[i + i5] & 255))) << 8;
        }
        int i6 = ((i2 + 1) * 8) - this.b.d;
        while (i4 < i2 * 8) {
            C2436l4 c2436l4 = this.b;
            sb.append(c2436l4.a(((int) (j >>> (i6 - i4))) & c2436l4.c));
            i4 += this.b.d;
        }
        if (this.c != null) {
            while (i4 < this.b.f * 8) {
                sb.append(this.c.charValue());
                i4 += this.b.d;
            }
        }
    }

    AbstractC2753p4 a(C2436l4 c2436l4) {
        return new C2671o4(c2436l4, null);
    }
}
