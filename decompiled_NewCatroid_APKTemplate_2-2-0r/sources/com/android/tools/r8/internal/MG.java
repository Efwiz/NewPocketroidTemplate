package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class MG extends com.android.tools.r8.graph.J5 {
    final /* synthetic */ NG e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MG(NG ng, com.android.tools.r8.graph.o5 o5Var) {
        super(ng.h.k(), o5Var);
        this.e = ng;
    }

    private void g(C0767v2 c0767v2) {
        C0702m c0702m = this.a;
        c0767v2.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2));
        if (c0729q2A == null || ((C3733i) this.e.h.b.f()).c(((com.android.tools.r8.graph.o5) d()).a(), c0729q2A)) {
            return;
        }
        a(c0729q2A);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0767v2 c0767v2, ListIterator listIterator, boolean z) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0767v2 c0767v2, boolean z) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        g(AbstractC2964s2.a(this.a.E(), c0767v2));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        g(AbstractC2964s2.a(this.a.E(), c0767v2));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
        abstractC1476WjE.getClass();
        com.android.tools.r8.graph.o5 o5VarS = ((C3733i) this.e.h.b.f()).h((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.e, (AbstractC1476Wj) null).b()).s();
        if (o5VarS != null) {
            C0729q2 c0729q2A = o5VarS.a();
            if (!((C3733i) this.e.h.b.f()).c(((com.android.tools.r8.graph.o5) d()).a(), c0729q2A)) {
                a(c0729q2A);
            }
            this.e.a(o5VarS);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
        abstractC1476WjE.getClass();
        AbstractC0748t0 abstractC0748t0C = ((C3733i) this.e.h.b.f()).d((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.g, (AbstractC1476Wj) null).b()).c();
        if (abstractC0748t0C != null) {
            if (abstractC0748t0C.a().c(this.a)) {
                if (abstractC0748t0C instanceof com.android.tools.r8.graph.o5) {
                    this.e.a(abstractC0748t0C.g0());
                }
            } else {
                this.e.b();
                b();
                this.e.a();
            }
        }
    }

    private void a(C0729q2 c0729q2) {
        com.android.tools.r8.utils.O3 o3B = com.android.tools.r8.utils.O3.b(this.e.c);
        o3B.c(c0729q2);
        while (o3B.c()) {
            C0729q2 c0729q3 = (C0729q2) o3B.f();
            if (this.e.b.contains(c0729q3)) {
                NG ng = this.e;
                if (ng.g.size() == 1 && ng.g.contains(c0729q3)) {
                    this.e.b();
                    b();
                    this.e.a();
                } else {
                    this.e.b(c0729q3);
                }
            }
            com.android.tools.r8.graph.o5 o5VarX1 = c0729q3.X1();
            if (o5VarX1 != null && !this.e.a(o5VarX1)) {
                return;
            }
            C0729q2 c0729q2A = C0729q2.a(this.a.c(c0729q3.n1()));
            if (c0729q2A != null) {
                o3B.c(c0729q2A);
            }
            C2688oE c2688oE = (C2688oE) this.e.h.c.get(c0729q3);
            if (c2688oE != null && c2688oE != this.e.a) {
                o3B.a((Iterable) c2688oE);
            }
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
        abstractC1476WjE.getClass();
        C0684j2 c0684j3 = (C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.f, (AbstractC1476Wj) null).b();
        C3733i c3733i = (C3733i) this.e.h.b.f();
        com.android.tools.r8.graph.o5 o5Var2 = (com.android.tools.r8.graph.o5) d();
        C0702m c0702m = this.e.h.b;
        c3733i.getClass();
        com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(c3733i.a(c0684j3, o5Var2.a(), c0702m, (C0680j) c0702m.f()));
        if (o5VarA != null) {
            this.e.a(o5VarA);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        g(this.a.E().c((AbstractC1476Wj) null, y0).B0());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        g(this.a.E().c((AbstractC1476Wj) null, y0).B0());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
        abstractC1476WjE.getClass();
        com.android.tools.r8.graph.I4 i4D = ((C3733i) this.e.h.b.f()).d((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b());
        if (i4D.x() && i4D.d().e0()) {
            this.e.a(i4D.s());
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0721p0 c0721p0) {
        C0664h c0664hF = this.e.h.b.f();
        int i = JA.n;
        if (c0721p0.f.d.g()) {
            C0722p1 c0722p1A = c0664hF.a();
            C0684j2 c0684j2U0 = c0721p0.f.u0();
            if (c0684j2U0 == c0722p1A.I5 || c0684j2U0 == c0722p1A.J5) {
                return;
            }
        }
        this.e.b();
        b();
        this.e.a();
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) d();
        abstractC1476WjE.getClass();
        if (((C3733i) this.e.h.b.f()).f((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.d, (AbstractC1476Wj) null).b()).c() != null) {
            this.e.b();
            b();
            this.e.a();
        }
    }
}
