package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0537z0;
import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ts, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1385Ts extends AbstractC1084Is {
    static final /* synthetic */ boolean j = true;
    private final C0767v2 i;

    public C1385Ts(C0767v2 c0767v2, S10 s10, List list) {
        super(s10, list);
        this.i = c0767v2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 36;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public final C0767v2 L2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    protected final String N2() {
        return "NewArray";
    }

    public final C0767v2 Q2() {
        return this.i;
    }

    public final int R2() {
        return this.b.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.b2() && this.i == abstractC2412ko.e0().i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b2() {
        return true;
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
    public final C1385Ts e0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; type: " + this.i.r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        com.android.tools.r8.dex.code.B1 c0537z0;
        int iP2 = P2();
        c2322jf.a(iP2);
        if (d(c2322jf)) {
            if (!j) {
                c(c2322jf);
            }
            c0537z0 = new com.android.tools.r8.dex.code.A0(b(c2322jf), iP2, this.i);
        } else {
            int[] iArr = new int[5];
            c0537z0 = new C0537z0(a(c2322jf, iArr), this.i, iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
        }
        a(c0537z0, c2322jf);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2B = this.i.M0() ? this.i.b(c0702m.a()) : this.i;
        if (c0767v2B.W0()) {
            if (j || !this.i.Z0()) {
                return false;
            }
            throw new AssertionError("The array's contents must be single-word");
        }
        boolean z = j;
        if (!z && !c0767v2B.X0()) {
            throw new AssertionError();
        }
        if (c0767v2B == o5Var.t()) {
            return false;
        }
        if (!c0702m.q()) {
            return true;
        }
        if (!z && !c0702m.f().h()) {
            throw new AssertionError();
        }
        C0702m c0702mH0 = c0702m.h0();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2B);
        return abstractC0727q0C == null || !abstractC0727q0C.d(c0702m) || AbstractC0639e.a(abstractC0727q0C, o5Var, c0702mH0).b();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.f(this.i, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is, com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(this.i, WH.b(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        throw new C3405y10("InvokeNewArray (non-empty) not supported when compiling to classfiles.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        throw new C3405y10("InvokeNewArray (non-empty) not supported when compiling to classfiles.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10("InvokeNewArray (non-empty) not supported when compiling to classfiles.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        abstractC1855de.a(this);
        return null;
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
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!a(c0702m, o5Var, C2332jo.a)) {
            return GW.a(c0702m.b().a(this.b.size()));
        }
        return C2892r10.F();
    }
}
