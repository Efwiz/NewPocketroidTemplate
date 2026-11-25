package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.K5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2331jn;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C2482li;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.utils.AbstractC3938n2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class r4 extends K5 {
    static final /* synthetic */ boolean g = true;
    private final C0702m f;

    public r4(C0702m c0702m, o5 o5Var) {
        super(c0702m, o5Var, Boolean.FALSE);
        this.f = c0702m;
    }

    private boolean i(com.android.tools.r8.graph.Y0 y0) {
        AbstractC0727q0 abstractC0727q0C;
        boolean z = g;
        if (!z && !y0.B0().O0()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2B0 = y0.B0();
        if (!c0767v2B0.a(((o5) d()).t())) {
            return false;
        }
        AbstractC1476Wj abstractC1476WjE = AbstractC1476Wj.e();
        C0767v2 c0767v2B = c0767v2B0.b(this.f.a());
        abstractC1476WjE.getClass();
        C0767v2 c0767v2A = abstractC1476WjE.a(AbstractC1476Wj.e(), c0767v2B);
        if (c0767v2A.O0() && c0767v2A.a(((o5) d()).t()) && ((abstractC0727q0C = this.f.c(c0767v2A)) == null || !abstractC0727q0C.J1())) {
            a(Boolean.TRUE);
            if (z || ((Boolean) f()).booleanValue()) {
                return true;
            }
            throw new AssertionError();
        }
        AbstractC0735r0 abstractC0735r0C = ((C0680j) this.f.f()).c(y0).c();
        if (abstractC0735r0C == null) {
            a(Boolean.TRUE);
            return true;
        }
        if (abstractC0735r0C.a() != ((o5) d()).a() && !abstractC0735r0C.getAccessFlags().m()) {
            a(Boolean.TRUE);
            return true;
        }
        if (!a(abstractC0735r0C)) {
            return false;
        }
        if (z || ((Boolean) f()).booleanValue()) {
            return true;
        }
        throw new AssertionError();
    }

    protected boolean a(AbstractC0735r0 abstractC0735r0) {
        return false;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        AbstractC2331jn abstractC2331jnN = this.f.N();
        abstractC2331jnN.getClass();
        if (!(abstractC2331jnN instanceof C2482li)) {
            g(c0767v2);
        } else {
            i(this.f.N().a(AbstractC2964s2.a(this.f.E(), c0767v2)));
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        i(this.f.E().c((AbstractC1476Wj) null, y0));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        g(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        i(this.f.E().c((AbstractC1476Wj) null, y0));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
        g(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        i(this.f.E().c((AbstractC1476Wj) null, y0));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        a((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.g, (AbstractC1476Wj) null).b(), AbstractC3938n2.b);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        i(this.f.E().c((AbstractC1476Wj) null, y0));
    }

    private boolean g(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C;
        AbstractC1476Wj abstractC1476WjE = this.f.E();
        C0767v2 c0767v2B = c0767v2.b(this.f.a());
        abstractC1476WjE.getClass();
        C0767v2 c0767v2A = abstractC1476WjE.a(AbstractC1476Wj.e(), c0767v2B);
        if (!c0767v2A.O0() || !c0767v2A.a(((o5) d()).t()) || ((abstractC0727q0C = this.f.c(c0767v2A)) != null && abstractC0727q0C.J1())) {
            return false;
        }
        a(Boolean.TRUE);
        return true;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
        g(c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        a((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.e, (AbstractC1476Wj) null).b(), AbstractC3938n2.c);
    }

    private void a(C0684j2 c0684j2, AbstractC3938n2 abstractC3938n2) {
        I4 i4A;
        C0767v2 c0767v2B = c0684j2.B0().b(this.f.a());
        if (c0767v2B.O0() && c0767v2B.a(((o5) d()).t())) {
            if (g(c0684j2.B0())) {
                if (!g && !((Boolean) f()).booleanValue()) {
                    throw new AssertionError();
                }
                return;
            }
            if (abstractC3938n2.e()) {
                i4A = ((C0680j) this.f.f()).g(c0684j2);
            } else {
                i4A = ((C0680j) this.f.f()).a(c0684j2, abstractC3938n2.d());
            }
            if (!i4A.x()) {
                a(Boolean.TRUE);
                return;
            }
            AbstractC0748t0 abstractC0748t0C = i4A.o().c();
            if (abstractC0748t0C.a() == ((o5) d()).a() || abstractC0748t0C.getAccessFlags().m()) {
                return;
            }
            a(Boolean.TRUE);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        a((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.f, (AbstractC1476Wj) null).b(), AbstractC3938n2.c);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        a((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b(), AbstractC3938n2.c);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        a((C0684j2) abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.d, (AbstractC1476Wj) null).b(), AbstractC3938n2.a);
    }
}
