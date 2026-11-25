package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0637d4;
import com.android.tools.r8.graph.C0644e4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0890Cd extends com.android.tools.r8.graph.J5 {
    static final /* synthetic */ boolean g = true;
    private final InterfaceC3345xG e;
    final /* synthetic */ C0919Dd f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0890Cd(C0919Dd c0919Dd, com.android.tools.r8.graph.S s, InterfaceC3345xG interfaceC3345xG) {
        super(c0919Dd.a, s);
        this.f = c0919Dd;
        this.e = interfaceC3345xG;
    }

    private void a(com.android.tools.r8.graph.Y0 y0, boolean z) {
        C0702m c0702m = this.a;
        c0702m.getClass();
        AbstractC0735r0 abstractC0735r0A = y0.a(c0702m.c(y0.e));
        if (abstractC0735r0A != null) {
            C0919Dd c0919Dd = this.f;
            AbstractC0748t0 abstractC0748t0 = (AbstractC0748t0) d();
            c0919Dd.getClass();
            if (C3194vG.a(abstractC0735r0A, abstractC0748t0)) {
                InterfaceC3345xG interfaceC3345xG = this.e;
                if (abstractC0735r0A instanceof com.android.tools.r8.graph.m5) {
                    com.android.tools.r8.graph.m5 m5VarQ = abstractC0735r0A.Q();
                    C0684j2 c0684j2B = this.f.b(m5VarQ, z);
                    synchronized (m5VarQ.a().Y()) {
                        if (m5VarQ.a().c(c0684j2B) == null) {
                            com.android.tools.r8.graph.o5 o5VarA = D1.a(c0684j2B, m5VarQ, z);
                            o5VarA.a().a(o5VarA.e());
                            if (z) {
                                interfaceC3345xG.a(m5VarQ, o5VarA, (AbstractC0748t0) d());
                            } else {
                                interfaceC3345xG.b(m5VarQ, o5VarA, (AbstractC0748t0) d());
                            }
                        }
                    }
                    return;
                }
                if (abstractC0735r0A instanceof com.android.tools.r8.graph.Q) {
                    return;
                }
                if (!g && !(abstractC0735r0A instanceof C0637d4)) {
                    throw new AssertionError();
                }
                C0919Dd c0919Dd2 = this.f;
                C3048tG c3048tGA = C3048tG.a(c0919Dd2.a, abstractC0735r0A.l().a(), null);
                if (!C3194vG.d && c3048tGA == null) {
                    throw new AssertionError("Should be a compilation error if missing nest host on library class.");
                }
                c0919Dd2.a.Y().a(c3048tGA);
                throw null;
            }
        }
    }

    private void i(C0684j2 c0684j2) {
        if (c0684j2.B0().O0()) {
            C0702m c0702m = this.a;
            c0702m.getClass();
            AbstractC0748t0 abstractC0748t0A = c0684j2.a(c0702m.c(c0684j2.e));
            if (abstractC0748t0A != null) {
                C0919Dd c0919Dd = this.f;
                AbstractC0748t0 abstractC0748t0 = (AbstractC0748t0) d();
                c0919Dd.getClass();
                if (C3194vG.a(abstractC0748t0A, abstractC0748t0)) {
                    InterfaceC3345xG interfaceC3345xG = this.e;
                    if (abstractC0748t0A instanceof com.android.tools.r8.graph.o5) {
                        if (abstractC0748t0A.e().C1()) {
                            a(abstractC0748t0A.g0(), interfaceC3345xG);
                            return;
                        }
                        com.android.tools.r8.graph.o5 o5VarG0 = abstractC0748t0A.g0();
                        if (!g && o5VarG0.e().C1()) {
                            throw new AssertionError();
                        }
                        C0684j2 c0684j2D = this.f.d(o5VarG0);
                        synchronized (o5VarG0.a().Y()) {
                            if (o5VarG0.a().c(c0684j2D) == null) {
                                com.android.tools.r8.graph.o5 o5VarB = D1.b(c0684j2D, o5VarG0, this.f.b);
                                o5VarB.a().a(o5VarB.e());
                                interfaceC3345xG.a(o5VarG0, o5VarB, (AbstractC0748t0) d());
                            }
                        }
                        return;
                    }
                    if (abstractC0748t0A instanceof com.android.tools.r8.graph.S) {
                        if (abstractC0748t0A.e().C1()) {
                            this.f.a(abstractC0748t0A);
                        }
                    } else {
                        if (!g && !(abstractC0748t0A instanceof C0644e4)) {
                            throw new AssertionError();
                        }
                        C0919Dd c0919Dd2 = this.f;
                        C3048tG c3048tGA = C3048tG.a(c0919Dd2.a, abstractC0748t0A.W().a(), null);
                        if (!C3194vG.d && c3048tGA == null) {
                            throw new AssertionError("Should be a compilation error if missing nest host on library class.");
                        }
                        c0919Dd2.a.Y().a(c3048tGA);
                        throw null;
                    }
                }
            }
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        a(y0, false);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        a(y0, true);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        a(y0, false);
    }

    private void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC3345xG interfaceC3345xG) {
        if (!g && !o5Var.e().C1()) {
            throw new AssertionError();
        }
        C0729q2 c0729q2A0 = this.f.a(o5Var).a0();
        C0919Dd c0919Dd = this.f;
        if (!C3194vG.d) {
            c0919Dd.getClass();
            if (!o5Var.e().C1()) {
                throw new AssertionError();
            }
        }
        C0722p1 c0722p1 = c0919Dd.b;
        C0737r2 c0737r2F = o5Var.F();
        C0767v2 type = c0729q2A0.getType();
        c0722p1.getClass();
        int size = c0737r2F.f.size();
        C0767v2[] c0767v2Arr = new C0767v2[size + 1];
        C0800x2 c0800x2 = c0737r2F.f;
        System.arraycopy(c0800x2.a, 0, c0767v2Arr, 0, c0800x2.size());
        c0767v2Arr[size] = type;
        C0684j2 c0684j2A = o5Var.getReference().a(c0722p1.b(c0737r2F.e, c0767v2Arr), c0919Dd.b);
        synchronized (o5Var.a().Y()) {
            if (o5Var.a().c(c0684j2A) == null) {
                com.android.tools.r8.graph.o5 o5VarA = D1.a(c0684j2A, o5Var, this.f.b);
                o5VarA.a().a(o5VarA.e());
                interfaceC3345xG.a(o5Var, o5VarA, c0729q2A0, (AbstractC0748t0) d());
            }
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        a(y0, true);
    }
}
