package com.android.tools.r8.internal;

import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Um, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1427Um extends AbstractC3328x10 {
    static final /* synthetic */ boolean i = true;
    private final int h;

    public C1427Um(S10 s10, S10 s11, int i2) {
        super(s10, s11);
        if (!i && s11.d0() != EnumC1812d20.INT) {
            throw new AssertionError();
        }
        this.h = i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC3328x10, com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC3328x10, com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 26;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko instanceof C1427Um;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    protected final void c(S10 s10) {
        if (!i && s10.F()) {
            throw new AssertionError();
        }
        this.b.add(s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + ", " + this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC3328x10, com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        throw new C3405y10("The Inc instruction is not intended for Dex code.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final SA a(C2409kl c2409kl, Function function) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        boolean z = i;
        if (!z) {
            l6.a(this);
        }
        S10 s10 = (S10) this.b.get(0);
        int iA = l6.a(s10);
        int iA2 = l6.a(this.a);
        if (iA == iA2) {
            l6.a(new C3260w7(iA, this.h), this);
        } else {
            if (!z && s10.d0() != EnumC1812d20.INT) {
                throw new AssertionError();
            }
            EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
            l6.a(new O7(enumC1812d20, iA), new C1818d7(this.h, enumC1812d20), new C3481z6(C3481z6.a.Add, EnumC2527mI.INT), new R8(enumC1812d20, iA2));
        }
    }
}
