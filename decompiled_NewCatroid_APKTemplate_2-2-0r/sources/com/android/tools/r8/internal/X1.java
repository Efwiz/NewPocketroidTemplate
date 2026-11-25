package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0513v;
import com.android.tools.r8.dex.code.C0518w;
import com.android.tools.r8.dex.code.C0523x;
import com.android.tools.r8.dex.code.C0528y;
import com.android.tools.r8.dex.code.C0536z;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class X1 extends FC {
    public X1(EnumC2527mI enumC2527mI, S10 s10, S10 s11, S10 s12) {
        super(enumC2527mI, s10, s11, s12);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 4;
    }

    @Override // com.android.tools.r8.internal.T4
    public final boolean K2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.FC
    final P7 O2() {
        return P7.d;
    }

    @Override // com.android.tools.r8.internal.T4
    final int a(int i, int i2) {
        return i & i2;
    }

    @Override // com.android.tools.r8.internal.T4
    final long a(long j, long j2) {
        return j & j2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2) {
        return new C0513v(i, i2);
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2) {
        return new C0536z(i, i2);
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 d(int i, int i2, int i3) {
        return new com.android.tools.r8.dex.code.A(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final X1 p() {
        return this;
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 b(int i, int i2, int i3) {
        return new C0523x(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 c(int i, int i2, int i3) {
        return new C0528y(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof X1) && abstractC2412ko.p().h == this.h;
    }

    @Override // com.android.tools.r8.internal.FC
    public final com.android.tools.r8.dex.code.B1 a(int i, int i2, int i3) {
        return new C0518w(i, i2, i3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        return L2().b(set) && N2().b(set);
    }
}
