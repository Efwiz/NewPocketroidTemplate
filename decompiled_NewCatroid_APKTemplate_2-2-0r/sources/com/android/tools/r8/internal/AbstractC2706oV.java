package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3769n2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3872b1;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2706oV extends AbstractC3292wV {
    static final /* synthetic */ boolean e = true;
    final com.android.tools.r8.graph.Y0 d;

    AbstractC2706oV(com.android.tools.r8.graph.Y0 y0) {
        this.d = y0;
    }

    @Override // com.android.tools.r8.internal.B1
    public final boolean B() {
        return true;
    }

    public final com.android.tools.r8.graph.Y0 F() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0989Fn
    public final InterfaceC0989Fn a(com.android.tools.r8.graph.proto.c cVar) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV, com.android.tools.r8.internal.B1
    /* JADX INFO: renamed from: c */
    public final AbstractC3292wV b(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        C1 c1B = c0702m.b();
        com.android.tools.r8.graph.Y0 y0 = this.d;
        if (y0.e == y0.h) {
            C3226vg c3226vgE0 = c0702m.e0();
            if (c3226vgE0.b(this.d)) {
                return c1B.a(c3226vgE0.a(this.d));
            }
        }
        com.android.tools.r8.graph.Y0 y0C = abstractC1476Wj.c(abstractC1476Wj2, this.d);
        AbstractC3121uK abstractC3121uKA = t().a(c0702m, abstractC1476Wj, abstractC1476Wj2);
        if (y0C == this.d && abstractC3121uKA == t()) {
            return this;
        }
        c1B.getClass();
        return C1.a(y0C, abstractC3121uKA);
    }

    @Override // com.android.tools.r8.internal.B1
    public final AbstractC2706oV p() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B1
    public abstract AbstractC3121uK t();

    public final boolean b(C0702m c0702m) {
        C0767v2 c0767v2 = this.d.h;
        if (c0767v2.O0()) {
            return AbstractC3769n2.a(c0702m, AbstractC3472z00.a(c0767v2, WH.i(), (C0702m<?>) c0702m).b(), ((C3733i) c0702m.f()).z());
        }
        if (e || c0767v2.M0() || c0767v2.W0()) {
            return false;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.graph.T0 a(com.android.tools.r8.graph.Q0 q0) {
        return this.d.c(q0.c(this.d.B0()));
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final AbstractC2412ko a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2145hI c2145hI, InterfaceC3176v00 interfaceC3176v00) {
        AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(this.d.h, WH.i(), (C0702m<?>) c0702m);
        if (!e && !abstractC3472z00A.a(interfaceC3176v00.a(), (C0702m<?>) c0702m) && c0702m.q() && !abstractC3472z00A.a(c0702m.h0())) {
            throw new AssertionError();
        }
        return new QW(this.d, new S10(c2145hI.a(), abstractC3472z00A, interfaceC3176v00.j()));
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    final boolean a(final C0702m c0702m, final com.android.tools.r8.graph.o5 o5Var) {
        final AbstractC0677i3 abstractC0677i3C = ((C0680j) c0702m.f()).c(this.d);
        Objects.requireNonNull(abstractC0677i3C);
        return AbstractC3872b1.a(new Consumer() { // from class: com.android.tools.r8.internal.oV$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                abstractC0677i3C.a((Consumer<AbstractC0677i3>) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.oV$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC2706oV.a(o5Var, c0702m, (AbstractC0677i3) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, AbstractC0677i3 abstractC0677i3) {
        if (abstractC0677i3.A()) {
            return false;
        }
        return abstractC0677i3.a(o5Var, c0702m).d();
    }

    @Override // com.android.tools.r8.internal.AbstractC3292wV
    public final boolean a(C0702m c0702m) {
        com.android.tools.r8.graph.T0 t0S = ((C3733i) c0702m.f()).c(this.d).s();
        if (t0S == null) {
            if (e) {
                return false;
            }
            throw new AssertionError();
        }
        if (!t0S.getAccessFlags().m()) {
            return false;
        }
        AbstractC0727q0 abstractC0727q0C = c0702m.c(t0S.J0());
        if (abstractC0727q0C == null) {
            if (e) {
                return false;
            }
            throw new AssertionError();
        }
        if (!abstractC0727q0C.J1()) {
            return false;
        }
        ((C3733i) c0702m.f()).getClass();
        return (abstractC0727q0C.e0() && C1121Ka.a(c0702m, abstractC0727q0C.a0())) ? false : true;
    }
}
