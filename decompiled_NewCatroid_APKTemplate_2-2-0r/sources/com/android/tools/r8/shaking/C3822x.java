package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.OQ;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.x, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3822x extends C3804u {
    protected final C0702m j;
    protected final L k;

    public C3822x(C0702m c0702m, o5 o5Var, L l, com.android.tools.r8.androidapi.b bVar) {
        super(c0702m, o5Var, bVar);
        this.j = c0702m;
        this.k = l;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0[] y0Arr) {
        super.a(y0Arr);
        this.k.h((o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        this.k.e(c0767v2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        super.c(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.b(y0, o5Var, I.c);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void d(com.android.tools.r8.graph.Y0 y0) {
        super.d(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.b(y0, o5Var, I.d);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        super.e(c0684j2);
        this.k.f(c0684j2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void f(com.android.tools.r8.graph.Y0 y0) {
        super.f(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.c(y0, o5Var, I.d);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        super.g(c0684j2);
        this.k.g(c0684j2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        super.h(c0684j2);
        this.k.h(c0684j2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        super.b(c0684j2);
        this.k.e(c0684j2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        super.a(c0684j2);
        this.k.d(c0684j2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public void e(com.android.tools.r8.graph.Y0 y0) {
        super.e(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.c(y0, o5Var, I.c);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        super.g(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.d(y0, o5Var, I.c);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void h(com.android.tools.r8.graph.Y0 y0) {
        super.h(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.d(y0, o5Var, I.d);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void b(com.android.tools.r8.graph.Y0 y0) {
        super.b(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.a(y0, o5Var, I.d);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
        super.c(c0767v2);
        this.k.f(c0767v2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        super.d(c0767v2);
        this.k.g(c0767v2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
        this.k.i(c0767v2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        super.a(y0);
        L l = this.k;
        o5 o5Var = (o5) d();
        l.getClass();
        l.a(y0, o5Var, I.c);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void e(C0767v2 c0767v2) {
        super.e(c0767v2);
        this.k.h(c0767v2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public void a(C0767v2 c0767v2, ListIterator listIterator, boolean z) {
        super.a(c0767v2, listIterator, z);
        this.k.a(c0767v2, (o5) d(), listIterator, z);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void a(C0767v2 c0767v2, boolean z) {
        super.a(c0767v2, z);
        this.k.b(c0767v2, (o5) d(), z);
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void a(C0767v2 c0767v2) {
        super.a(c0767v2);
        this.k.d(c0767v2, (o5) d());
    }

    @Override // com.android.tools.r8.shaking.C3804u, com.android.tools.r8.graph.J5
    public final void a(C0699l2 c0699l2, I5 i5) {
        super.a(c0699l2, i5);
        this.k.a(c0699l2, i5, (o5) d());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0721p0 c0721p0) {
        b(c0721p0);
        if (OQ.a(c0721p0, this.j, (o5) d()) && this.j.Y().n1.u0) {
            a(2, c0721p0);
            for (int i = 2; i < c0721p0.g.size(); i++) {
                com.android.tools.r8.graph.Y0 y0T0 = ((C0699l2) ((com.android.tools.r8.graph.B2) c0721p0.g.get(i)).G0().b).t0();
                C0680j c0680j = (C0680j) this.j.f();
                o5 o5Var = (o5) d();
                c0680j.getClass();
                if (!c0680j.a(y0T0.e, y0T0, o5Var).s().X0().i()) {
                    super.d(y0T0);
                    L l = this.k;
                    o5 o5Var2 = (o5) d();
                    l.getClass();
                    l.a(y0T0, o5Var2, I.e);
                }
            }
        } else {
            a(c0721p0.g.size(), c0721p0);
        }
        this.k.a(c0721p0, (o5) d());
    }
}
