package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.AbstractC3769n2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1182Mh extends AbstractC2412ko {
    static final /* synthetic */ boolean i = true;
    private final com.android.tools.r8.graph.Y0 h;

    protected AbstractC1182Mh(com.android.tools.r8.graph.Y0 y0, S10 s10, S10 s11) {
        this(y0, s10, Collections.singletonList(s11));
    }

    public final EnumC1266Ph J2() {
        return EnumC1266Ph.a(this.h.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean K1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC1182Mh N() {
        return this;
    }

    final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo, AbstractC0677i3 abstractC0677i3) {
        if (!abstractC0677i3.B()) {
            return true;
        }
        AbstractC0677i3.a<?> aVarL = abstractC0677i3.l();
        AbstractC0735r0 abstractC0735r0C = aVarL.c();
        if (abstractC0735r0C.getAccessFlags().o() != v2()) {
            return true;
        }
        if (aVarL.d() != o5Var.a() && aVarL.a(o5Var, c0702m.h0()).b()) {
            return true;
        }
        if (((this instanceof C1189Mn) || S1()) && !c2332jo.c()) {
            S10 s10 = (S10) this.b.get(0);
            if (s10.b(c0702m) || s10.n.F()) {
                return true;
            }
        }
        if (c0702m.q() && v2() && !c2332jo.a()) {
            C3749l c3749lT = c0702m.t();
            c3749lT.getClass();
            if (!c3749lT.a(abstractC0735r0C.getReference()).f() && this.h.e.a(o5Var, c0702m)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return b(c0702m, o5Var, C2332jo.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC2744p f(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.T0 t0A;
        if (d(c0702m, o5Var)) {
            return C2198i10.e();
        }
        if (!J1()) {
            if (i || L1()) {
                return C1501Xf.e();
            }
            throw new AssertionError();
        }
        com.android.tools.r8.graph.Y0 field = getField();
        if (c0702m.q()) {
            t0A = ((C3733i) c0702m.f()).c(field).s();
        } else {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(field.e);
            t0A = abstractC0727q0C != null ? abstractC0727q0C.a(field) : null;
        }
        return t0A != null ? new C2092gc(t0A) : C2198i10.e();
    }

    public com.android.tools.r8.graph.Y0 getField() {
        return this.h;
    }

    public abstract S10 value();

    protected AbstractC1182Mh(com.android.tools.r8.graph.Y0 y0, S10 s10, List list) {
        super(s10, list);
        if (!i && y0 == null) {
            throw new AssertionError();
        }
        this.h = y0;
    }

    public boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        C0664h c0664hF = c0702m.f();
        com.android.tools.r8.graph.Y0 y0 = this.h;
        c0664hF.getClass();
        return a(c0702m, o5Var, c2332jo, c0664hF.a(y0.e, y0, o5Var));
    }

    final boolean a(C0702m c0702m, com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.W0 w0T;
        AbstractC0727q0 abstractC0727q0C;
        if (!i && !L1()) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00W = value().w();
        if (abstractC3472z00W.r()) {
            abstractC3472z00W = abstractC3472z00W.a().R();
        }
        if (!abstractC3472z00W.w()) {
            return false;
        }
        B1 b1F = t0.X0().f();
        if (b1F.g()) {
            if (b1F.A()) {
                return false;
            }
            if (b1F.B()) {
                return b1F.p().b(c0702m);
            }
        }
        C3733i c3733i = (C3733i) c0702m.f();
        S10 s10M = value().m();
        if (!s10M.i() && s10M.b.p2()) {
            AbstractC0727q0 abstractC0727q0C2 = c0702m.c(s10M.b.t0().h);
            if (abstractC0727q0C2 == null) {
                return true;
            }
            return (abstractC0727q0C2.f == null || (w0T = c3733i.e(abstractC0727q0C2, c0702m.a().g4.h).t()) == null || !w0T.getReference().e.O0() || (abstractC0727q0C = c0702m.c(w0T.getReference().e)) == null || !abstractC0727q0C.e0()) ? false : true;
        }
        return AbstractC3769n2.a(c0702m, abstractC3472z00W.b(), ((C3733i) c0702m.f()).z());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!i && !J1()) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.T0 t0S = ((C0680j) c0702m.f()).c(getField()).s();
        if (t0S != null) {
            return t0S.X0().f();
        }
        return C2892r10.F();
    }
}
