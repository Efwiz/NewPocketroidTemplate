package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J2 extends com.android.tools.r8.graph.K5 {
    private final C0702m f;
    private final H2 g;

    J2(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, H2 h2) {
        super(c0702m, o5Var, Boolean.FALSE);
        this.f = c0702m;
        this.g = h2;
    }

    private void i(C0684j2 c0684j2) {
        com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.f.f()).h(c0684j2).o();
        if (cVarO == null || !cVarO.d().e0()) {
            return;
        }
        com.android.tools.r8.graph.o5 o5VarS = cVarO.s();
        C0684j2 c0684j2H = this.g.h(o5VarS.getReference());
        if (c0684j2H != o5VarS.getReference() || this.g.j(c0684j2H)) {
            a(Boolean.TRUE);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
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
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        i(c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    private void i(com.android.tools.r8.graph.Y0 y0) {
        AbstractC0677i3 abstractC0677i3C = ((C3733i) this.f.f()).c(y0);
        if (abstractC0677i3C.u() == null) {
            return;
        }
        com.android.tools.r8.graph.m5 m5VarU = abstractC0677i3C.u();
        if (this.g.d(m5VarU.getReference()) != m5VarU.getReference()) {
            a(Boolean.TRUE);
        }
    }
}
