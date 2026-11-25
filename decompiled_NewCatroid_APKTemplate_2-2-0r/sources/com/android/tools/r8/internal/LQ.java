package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0423c3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class LQ extends AbstractC2412ko {
    private final com.android.tools.r8.graph.Y0[] h;

    public LQ(com.android.tools.r8.graph.Y0[] y0Arr, S10 s10, ArrayList arrayList) {
        super(s10, arrayList);
        this.h = y0Arr;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 70;
    }

    public final com.android.tools.r8.graph.Y0[] J2() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (abstractC2412ko instanceof LQ) {
            return Arrays.equals(abstractC2412ko.z0().h, this.h);
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final LQ z0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(c0702m.a().c2, WH.b(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new C2830q8(this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        int[] iArr = new int[this.b.size()];
        for (int i = 0; i < this.b.size(); i++) {
            iArr[i] = c2322jf.a(W0(), (S10) this.b.get(i));
        }
        c2322jf.a(this, new C0423c3(c2322jf.a(W0(), b()), iArr, this.h));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
