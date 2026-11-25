package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3308wl extends AbstractC1221Ns {
    static final /* synthetic */ boolean l = true;
    private final com.android.tools.r8.graph.T2 k;

    C3308wl(C0702m c0702m, C2768pH c2768pH, Function function, ConcurrentHashMap concurrentHashMap, Predicate predicate) {
        super(c0702m, c2768pH, function, concurrentHashMap, predicate);
        this.k = ((C3733i) c0702m.f()).v();
    }

    private void i(com.android.tools.r8.graph.Y0 y0) {
        com.android.tools.r8.graph.m5 m5VarU;
        com.android.tools.r8.graph.Y0 y0C = this.e.E().c(c(), y0);
        if (!y0C.B0().O0() || (m5VarU = ((C3733i) this.e.f()).c(y0C).u()) == null || ((C3733i) this.e.f()).c(m5VarU)) {
            return;
        }
        if (m5VarU.getAccessFlags().o()) {
            a(m5VarU.a());
        }
        com.android.tools.r8.graph.U2 u2B = this.k.b(m5VarU.getReference());
        if (u2B != null && u2B.j() && u2B.f() == 1) {
            u2B.c(new Consumer() { // from class: com.android.tools.r8.internal.wl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((com.android.tools.r8.graph.o5) obj);
                }
            });
        }
    }

    protected final void a(C0729q2 c0729q2) {
        if (!l && c0729q2 == null) {
            throw new AssertionError();
        }
        if (c0729q2.p1()) {
            a(false, c0729q2.X1());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns, com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        C0767v2 c0767v2A = AbstractC2964s2.a(this.e.E(), c0767v2);
        if (!c0767v2A.R0()) {
            C0729q2 c0729q2A = C0729q2.a(this.e.c(c0767v2A));
            if (c0729q2A != null) {
                a(c0729q2A);
                return;
            } else {
                if (!l) {
                    throw new AssertionError();
                }
                return;
            }
        }
        boolean z = l;
        if (!z && !this.e.K()) {
            throw new AssertionError();
        }
        if (!z && !this.e.e0().b(c0767v2)) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        if (c0767v2.O0()) {
            if (!l && !c0767v2.O0()) {
                throw new AssertionError();
            }
            C0729q2 c0729q2A = C0729q2.a(this.e.c(c0767v2));
            if (c0729q2A != null) {
                a(c0729q2A);
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns, com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns, com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns, com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        com.android.tools.r8.graph.m5 m5VarU;
        com.android.tools.r8.graph.Y0 y0C = this.e.E().c(c(), y0);
        if (y0C.B0().O0() && (m5VarU = ((C3733i) this.e.f()).c(y0C).u()) != null && !((C3733i) this.e.f()).c(m5VarU) && m5VarU.getAccessFlags().o()) {
            a(m5VarU.a());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns, com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        com.android.tools.r8.graph.m5 m5VarU;
        com.android.tools.r8.graph.Y0 y0C = this.e.E().c(c(), y0);
        if (y0C.B0().O0() && (m5VarU = ((C3733i) this.e.f()).c(y0C).u()) != null && !((C3733i) this.e.f()).c(m5VarU) && m5VarU.getAccessFlags().o()) {
            a(m5VarU.a());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        if (!l && o5Var.e().s1()) {
            throw new AssertionError();
        }
        if (this.i.test(o5Var)) {
            ((AbstractC2839qH) this.g.apply(o5Var)).a(this.f);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns
    protected final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        super.a(o5Var, o5Var2);
        if (o5Var.getAccessFlags().o()) {
            a(o5Var.a());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns, com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns, com.android.tools.r8.graph.J5
    public final void a(C0721p0 c0721p0) {
        a(c0721p0.f, com.android.tools.r8.graph.I5.b);
    }
}
