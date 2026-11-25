package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1207Nf extends AbstractC1154Lf {
    static final C1207Nf d = new C1207Nf(AbstractC3472z00.f());
    static final C1207Nf e = new C1207Nf(AbstractC3472z00.m());
    static final C1207Nf f = new C1207Nf(AbstractC3472z00.p());
    static final /* synthetic */ boolean g = true;
    private final AbstractC3472z00 c;

    C1207Nf(AbstractC3472z00 abstractC3472z00) {
        if (!g && abstractC3472z00 == null) {
            throw new AssertionError();
        }
        this.c = abstractC3472z00;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final C1207Nf a() {
        return this;
    }

    public final boolean b(C0702m c0702m, C1207Nf c1207Nf) {
        if (equals(c1207Nf)) {
            return false;
        }
        if (o().equals(c1207Nf.o())) {
            if (c1207Nf.g()) {
                return g() && c1207Nf.d().c(d(), c0702m);
            }
            return g();
        }
        if (!o().c(c1207Nf.o(), c0702m)) {
            return false;
        }
        if (c1207Nf.g()) {
            return g() && c1207Nf.d().b(o(), (C0702m<?>) c0702m);
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public C1229Oa d() {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public C1229Oa e() {
        if (g() && d().a(o())) {
            return d();
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.c.equals(((C1207Nf) obj).c);
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final WH f() {
        return this.c.N();
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public boolean g() {
        return this instanceof C1180Mf;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean h() {
        AbstractC3472z00 abstractC3472z00 = this.c;
        abstractC3472z00.getClass();
        return abstractC3472z00 instanceof G5;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean i() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public boolean j() {
        return e() != null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean l() {
        AbstractC3472z00 abstractC3472z00 = this.c;
        abstractC3472z00.getClass();
        return abstractC3472z00 instanceof AS;
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean m() {
        AbstractC3472z00 abstractC3472z00 = this.c;
        abstractC3472z00.getClass();
        return abstractC3472z00 instanceof C2163hZ;
    }

    public AbstractC3472z00 o() {
        return this.c;
    }

    public String toString() {
        return "DynamicTypeWithUpperBound(upperBound=" + o() + Constant.AFTER_QUTO;
    }

    public static C1207Nf a(C0702m<C3733i> c0702m, AbstractC3472z00 abstractC3472z00, C1229Oa c1229Oa) {
        abstractC3472z00.getClass();
        if (abstractC3472z00 instanceof G5) {
            return d;
        }
        if (abstractC3472z00 instanceof AS) {
            return e;
        }
        if (abstractC3472z00 instanceof C2163hZ) {
            return f;
        }
        if (c1229Oa != null) {
            boolean z = g;
            if (!z && !abstractC3472z00.w()) {
                throw new AssertionError();
            }
            if (z || abstractC3472z00.N() == c1229Oa.N()) {
                return abstractC3472z00.equals(c1229Oa) ? new C2557mh(c1229Oa) : C1180Mf.a((C0702m) c0702m, abstractC3472z00.b(), c1229Oa);
            }
            throw new AssertionError();
        }
        boolean z2 = g;
        if (!z2 && abstractC3472z00.w()) {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(abstractC3472z00.b().R());
            if (!z2 && abstractC0727q0C != null && abstractC0727q0C.c(c0702m)) {
                throw new AssertionError();
            }
        }
        return new C1207Nf(abstractC3472z00);
    }

    public static C1207Nf a(C0702m<C3733i> c0702m, AbstractC3472z00 abstractC3472z00) {
        AbstractC0727q0 abstractC0727q0C;
        return a(c0702m, abstractC3472z00, (abstractC3472z00.w() && (abstractC0727q0C = c0702m.c(abstractC3472z00.b().R())) != null && abstractC0727q0C.c(c0702m)) ? abstractC3472z00.b() : null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public final AbstractC3472z00 a(AbstractC3472z00 abstractC3472z00) {
        return o();
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0090  */
    public final C1207Nf a(C0702m c0702m, C1207Nf c1207Nf) {
        C1229Oa c1229OaD;
        AbstractC0727q0 abstractC0727q0C;
        AbstractC3472z00 abstractC3472z00A = o().a(c0702m, c1207Nf.o());
        if (abstractC3472z00A.w() && (abstractC0727q0C = c0702m.c(abstractC3472z00A.b().R())) != null && abstractC0727q0C.c(c0702m)) {
            c1229OaD = abstractC3472z00A.b();
        } else if (l()) {
            if (c1207Nf.g()) {
                c1229OaD = c1207Nf.d().b(WH.c());
            } else {
                c1229OaD = null;
            }
        } else if (c1207Nf.l()) {
            if (g()) {
                c1229OaD = d().b(WH.c());
            } else {
                c1229OaD = null;
            }
        } else if (g() && c1207Nf.g()) {
            C1229Oa c1229OaD2 = d();
            C1229Oa c1229OaD3 = c1207Nf.d();
            if (c1229OaD2.b(c1229OaD3, (C0702m<?>) c0702m)) {
                c1229OaD = c1229OaD2.b(c1229OaD3.N());
            } else if (c1229OaD3.b(c1229OaD2, (C0702m<?>) c0702m)) {
                c1229OaD = c1229OaD3.b(c1229OaD2.N());
            } else {
                c1229OaD = null;
            }
        } else {
            c1229OaD = null;
        }
        return (abstractC3472z00A.equals(o()) && Objects.equals(c1229OaD, d())) ? this : a((C0702m<C3733i>) c0702m, abstractC3472z00A, c1229OaD);
    }

    @Override // com.android.tools.r8.internal.AbstractC1154Lf
    public AbstractC1154Lf a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, Set set) {
        if (h() || l() || m()) {
            return this;
        }
        C1229Oa c1229OaB = null;
        AbstractC3472z00 abstractC3472z00A = this.c.a(c0702m, abstractC1476Wj, (AbstractC1476Wj) null, set);
        if (g()) {
            AbstractC3472z00 abstractC3472z00A2 = d().a(c0702m, abstractC1476Wj, (AbstractC1476Wj) null, set);
            if (abstractC3472z00A2.w()) {
                c1229OaB = abstractC3472z00A2.b();
            }
        }
        if (c1229OaB != null) {
            return a((C0702m<C3733i>) c0702m, abstractC3472z00A, c1229OaB);
        }
        return a((C0702m<C3733i>) c0702m, abstractC3472z00A);
    }

    public C1207Nf a(WH wh) {
        if (!g && g()) {
            throw new AssertionError();
        }
        if (!o().I()) {
            return this;
        }
        BS bsD = o().d();
        return bsD.N() == wh ? this : new C1207Nf(bsD.a(wh));
    }
}
