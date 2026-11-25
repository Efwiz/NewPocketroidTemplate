package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3328x10 extends AbstractC2412ko {
    public AbstractC3328x10(S10 s10, S10 s11) {
        super(s10, s11);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean C2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public int E2() {
        return 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public int F2() {
        return 15;
    }

    public final S10 J2() {
        return this.a;
    }

    public final S10 K2() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
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
    public AbstractC3472z00 a(C0702m c0702m) {
        return K2().w();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
