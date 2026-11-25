package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ss, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1358Ss extends AbstractC1084Is {
    static final /* synthetic */ boolean j = true;
    private final C0767v2 i;

    public C1358Ss(C0767v2 c0767v2, S10 s10, List list) {
        super(s10, list);
        this.i = c0767v2;
    }

    private boolean R2() {
        for (S10 s10 : this.b) {
            if (!s10.G() || s10.x().b() < 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 35;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public final C0767v2 L2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    protected final String N2() {
        return "MultiNewArray";
    }

    public final C0767v2 Q2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1358Ss) && this.i == abstractC2412ko.d0().i;
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
    public final C1358Ss d0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.e(this.i, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is, com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(this.i, WH.b(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2B = this.i.M0() ? this.i.b(c0702m.a()) : this.i;
        if (c0767v2B.W0()) {
            if (j || !this.i.Z0()) {
                return R2();
            }
            throw new AssertionError("The array's contents must be single-word");
        }
        boolean z = j;
        if (!z && !c0767v2B.X0()) {
            throw new AssertionError();
        }
        if (c0767v2B == o5Var.t()) {
            return R2();
        }
        if (!c0702m.q()) {
            return true;
        }
        if (!z && !c0702m.f().h()) {
            throw new AssertionError();
        }
        C0702m c0702mH0 = c0702m.h0();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2B);
        if (abstractC0727q0C == null || !abstractC0727q0C.d(c0702m) || AbstractC0639e.a(abstractC0727q0C, o5Var, c0702mH0).b()) {
            return true;
        }
        return R2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new S7(this.b.size(), this.i), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        throw new C3405y10("InvokeNewArray (non-empty) not supported when compiling to dex files.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        if (d1() && b().A()) {
            if (j || c0702m.Y().W0) {
                return true;
            }
            throw new AssertionError();
        }
        return b(c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
    }
}
