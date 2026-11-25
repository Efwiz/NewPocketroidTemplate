package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I5;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.utils.AbstractC3896g;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.u, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3804u extends J5 {
    protected final C0702m e;
    private final C0680j f;
    private final com.android.tools.r8.androidapi.b g;
    private final boolean h;
    private com.android.tools.r8.androidapi.h i;

    public C3804u(C0702m c0702m, o5 o5Var, com.android.tools.r8.androidapi.b bVar) {
        super(c0702m, o5Var);
        this.e = c0702m;
        this.f = c0702m.g();
        this.g = bVar;
        this.h = bVar.a();
        this.i = c0702m.o();
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(C0684j2 c0684j2) {
        a((AbstractC0744s2) c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void b(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public void c(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void d(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void e(C0684j2 c0684j2) {
        a((AbstractC0744s2) c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public void g(C0684j2 c0684j2) {
        a((AbstractC0744s2) c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void h(C0684j2 c0684j2) {
        a((AbstractC0744s2) c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void b(C0684j2 c0684j2) {
        a((AbstractC0744s2) c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void c(C0767v2 c0767v2) {
        a((AbstractC0744s2) c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void d(C0767v2 c0767v2) {
        a((AbstractC0744s2) c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void e(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void f(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void g(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void h(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(C0767v2 c0767v2, ListIterator listIterator, boolean z) {
        a((AbstractC0744s2) c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void b(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0744s2) y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public void e(C0767v2 c0767v2) {
        a((AbstractC0744s2) c0767v2);
    }

    public final com.android.tools.r8.androidapi.h f() {
        return this.i;
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(C0767v2 c0767v2, boolean z) {
        a((AbstractC0744s2) c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(C0767v2 c0767v2) {
        a((AbstractC0744s2) c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(C0699l2 c0699l2, I5 i5) {
        super.a(c0699l2, i5);
    }

    private void a(AbstractC0744s2 abstractC0744s2) {
        if (this.h) {
            abstractC0744s2.getClass();
            if (abstractC0744s2 instanceof C0767v2) {
                this.i = this.i.e(this.g.a(abstractC0744s2));
                return;
            }
            if (!abstractC0744s2.C().O0()) {
                this.i = this.i.e(this.e.o());
                return;
            }
            AbstractC0727q0 abstractC0727q0C = this.e.c(abstractC0744s2.C());
            com.android.tools.r8.androidapi.h hVarF = com.android.tools.r8.androidapi.h.f();
            if (abstractC0727q0C != null) {
                hVarF = (com.android.tools.r8.androidapi.h) AbstractC3896g.a(this.e, this.f, abstractC0727q0C, abstractC0744s2.u0()).b();
            }
            this.i = this.i.e(hVarF);
        }
    }
}
