package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0691k2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2102gl {
    static final /* synthetic */ boolean D = true;
    private final C2145hI k;
    private final C2145hI l;
    private final com.android.tools.r8.graph.o5 m;
    private com.android.tools.r8.graph.o5 n;
    public final C0702m o;
    private final AbstractC1476Wj p;
    private final Origin q;
    private final com.android.tools.r8.graph.proto.k r;
    private S10 s;
    private ArrayList t;
    private KV u;
    private final C2807pq a = new C2807pq();
    private final MR b = new MR();
    private final LinkedList c = new LinkedList();
    private boolean[] d = null;
    private HashSet e = null;
    private final LinkedList f = new LinkedList();
    private final LinkedList g = new LinkedList();
    private C3332x4 h = null;
    private C3332x4 i = null;
    private int j = -1;
    private boolean v = false;
    private S10 w = null;
    private final ArrayList x = new ArrayList();
    private C1303Qq y = null;
    private ArrayList z = null;
    private boolean A = false;
    private boolean B = false;
    private final C3155ul C = new C3155ul();

    private C2102gl(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.proto.k kVar, C2145hI c2145hI, KV kv, Origin origin) {
        boolean z = D;
        if (!z && kv == null) {
            throw new AssertionError();
        }
        if (!z && c2145hI == null) {
            throw new AssertionError();
        }
        this.m = o5Var;
        this.o = c0702m;
        this.u = kv;
        this.q = origin;
        this.p = abstractC1476Wj;
        this.r = kVar;
        this.k = c2145hI;
        this.l = new C2145hI();
    }

    private static AbstractC3472z00 a(EnumC2523mE enumC2523mE) {
        switch (enumC2523mE) {
            case OBJECT:
                return AbstractC3472z00.f();
            case BOOLEAN_OR_BYTE:
            case CHAR:
            case SHORT:
            case INT:
                return AbstractC3472z00.k();
            case FLOAT:
                return AbstractC3472z00.j();
            case LONG:
                return AbstractC3472z00.l();
            case DOUBLE:
                return AbstractC3472z00.i();
            case INT_OR_FLOAT:
                return AbstractC3472z00.o();
            case LONG_OR_DOUBLE:
                return AbstractC3472z00.q();
            default:
                throw new C3405y10("Unexpected member type: " + enumC2523mE);
        }
    }

    public final void b(int i, com.android.tools.r8.graph.X x) {
        boolean z = D;
        if (!z && x == null) {
            throw new AssertionError();
        }
        if (l()) {
            if (!z && !l()) {
                throw new AssertionError();
            }
            EnumC1968f20 enumC1968f20A = EnumC1968f20.a(x.b);
            S10 s10A = a(i, EnumC3182v4.a, this.i, ML.a.b, enumC1968f20A);
            if (s10A.s() == x && !this.i.M() && this.i.p().getLast().b() == s10A) {
                return;
            }
            b(new C1289Qd(a(i, s10A.w(), 1, x), s10A));
        }
    }

    public final void c(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !a(enumC2527mI)) {
            throw new AssertionError();
        }
        X1 x1 = new X1(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!z && x1.g()) {
            throw new AssertionError();
        }
        b(x1);
    }

    public final C0722p1 d() {
        return this.o.a();
    }

    public final ArrayList e() {
        return this.t;
    }

    public final C2807pq f() {
        return this.a;
    }

    public final AbstractC1476Wj g() {
        return this.p;
    }

    public final com.android.tools.r8.graph.W0 h() {
        return this.m.e();
    }

    public final com.android.tools.r8.graph.o5 i() {
        return this.m;
    }

    public final com.android.tools.r8.graph.proto.k j() {
        return this.r;
    }

    public final S10 k() {
        return this.s;
    }

    public final boolean l() {
        if (!this.o.Y().W0) {
            com.android.tools.r8.graph.o5 o5Var = this.m;
            if (!o5Var.a().f(this.o)) {
                return false;
            }
        }
        return true;
    }

    public final void m(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && enumC2527mI == EnumC2527mI.DOUBLE) {
            throw new AssertionError();
        }
        C3218vY c3218vY = new C3218vY(enumC2527mI, a(i, XM.a(enumC2527mI), 1), a(i3), b(i2, EnumC1968f20.a(enumC2527mI)));
        if (!z && c3218vY.g()) {
            throw new AssertionError();
        }
        b(c3218vY);
    }

    public final void n(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !a(enumC2527mI)) {
            throw new AssertionError();
        }
        TU tu = new TU(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.INT));
        if (!z && tu.g()) {
            throw new AssertionError();
        }
        b(tu);
    }

    public final void o(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        TU tu = new TU(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && tu.g()) {
            throw new AssertionError();
        }
        b(tu);
    }

    public final void p(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !a(enumC2527mI)) {
            throw new AssertionError();
        }
        WU wu = new WU(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.INT));
        if (!z && wu.g()) {
            throw new AssertionError();
        }
        b(wu);
    }

    public final void q(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        WU wu = new WU(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && wu.g()) {
            throw new AssertionError();
        }
        b(wu);
    }

    public final void r(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        C3218vY c3218vY = new C3218vY(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!D && c3218vY.g()) {
            throw new AssertionError();
        }
        b(c3218vY);
    }

    public final void s(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !a(enumC2527mI)) {
            throw new AssertionError();
        }
        N10 n10 = new N10(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.INT));
        if (!z && n10.g()) {
            throw new AssertionError();
        }
        b(n10);
    }

    public final void t(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        N10 n10 = new N10(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && n10.g()) {
            throw new AssertionError();
        }
        b(n10);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("blocks:\n");
        Iterator it = this.g.iterator();
        while (it.hasNext()) {
            sb.append(((C3332x4) it.next()).b0());
            sb.append("\n");
        }
        return sb.toString();
    }

    public final void u(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !a(enumC2527mI)) {
            throw new AssertionError();
        }
        S10 s10B = b(i2, EnumC1968f20.a(enumC2527mI));
        S10 s10B2 = b(i3, EnumC1968f20.a(enumC2527mI));
        S10 s10A = a(i, XM.a(enumC2527mI), 1);
        AbstractC2412ko lh = (this.o.Y().W() && s10B2.J() && s10B2.n().E().a(enumC2527mI)) ? new LH(enumC2527mI, s10A, s10B) : new P30(enumC2527mI, s10A, s10B, s10B2);
        if (!z && lh.g()) {
            throw new AssertionError();
        }
        b(lh);
    }

    public final void v(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        AbstractC2412ko p30;
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        S10 s10B = b(i2, EnumC1968f20.a(enumC2527mI));
        if (this.o.Y().W() && i3 == -1) {
            p30 = new LH(enumC2527mI, a(i, XM.a(enumC2527mI), 1), s10B);
        } else {
            p30 = new P30(enumC2527mI, a(i, XM.a(enumC2527mI), 1), s10B, a(i3));
        }
        if (!z && p30.g()) {
            throw new AssertionError();
        }
        b(p30);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(AbstractC2412ko abstractC2412ko) {
        if (D) {
            return;
        }
        abstractC2412ko.getClass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean e(AbstractC2412ko abstractC2412ko) {
        return !abstractC2412ko.m1();
    }

    public final void f(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        boolean z2 = (enumC2527mI == EnumC2527mI.DOUBLE || enumC2527mI == EnumC2527mI.FLOAT) ? false : true;
        C3379xf c3379xf = new C3379xf(enumC2527mI, a(i, XM.a(enumC2527mI), z2 ? 2 : 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && c3379xf.g() != z2) {
            throw new AssertionError();
        }
        a(c3379xf);
    }

    public final void g(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        CF cf = new CF(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!D && cf.g()) {
            throw new AssertionError();
        }
        b(cf);
    }

    public final void h(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        CF cf = new CF(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && cf.g()) {
            throw new AssertionError();
        }
        b(cf);
    }

    public final void i(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !a(enumC2527mI)) {
            throw new AssertionError();
        }
        NK nk = new NK(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!z && nk.g()) {
            throw new AssertionError();
        }
        b(nk);
    }

    public final void j(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        NK nk = new NK(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && nk.g()) {
            throw new AssertionError();
        }
        b(nk);
    }

    public final void k(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = (enumC2527mI == EnumC2527mI.DOUBLE || enumC2527mI == EnumC2527mI.FLOAT) ? false : true;
        US us = new US(enumC2527mI, a(i, XM.a(enumC2527mI), z ? 2 : 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!D && us.g() != z) {
            throw new AssertionError();
        }
        b(us);
    }

    public final void d(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        X1 x1 = new X1(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && x1.g()) {
            throw new AssertionError();
        }
        b(x1);
    }

    public final void e(int i, int i2) {
        this.u.a(i, i2, this);
    }

    public final void e(int i, C0767v2 c0767v2) {
        S10 s10B = b(i, EnumC1968f20.OBJECT);
        a(new C3133uU(a(i, AbstractC3472z00.a(c0767v2, s10B.w().N(), (C0702m<?>) this.o), 2), s10B, c0767v2));
    }

    public final void l(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        boolean z2 = (enumC2527mI == EnumC2527mI.DOUBLE || enumC2527mI == EnumC2527mI.FLOAT) ? false : true;
        US us = new US(enumC2527mI, a(i, XM.a(enumC2527mI), z2 ? 2 : 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && us.g() != z2) {
            throw new AssertionError();
        }
        b(us);
    }

    private void g(int i) {
        if (i >= 0) {
            if (!this.u.f(i)) {
                throw new C1367Tb("Invalid use of register " + i);
            }
            return;
        }
        throw new C3089ts("Invalid register");
    }

    public final void c(int i) {
        boolean z = D;
        if (!z && this.i.y().isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.i.y().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.gl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2102gl.a((C3332x4) obj);
            }
        })) {
            throw new AssertionError();
        }
        S10 s10B = b(i, EnumC1968f20.OBJECT);
        if (z) {
            return;
        }
        if (!s10B.i()) {
            if (z) {
                return;
            }
            AbstractC2412ko abstractC2412ko = s10B.b;
            abstractC2412ko.getClass();
            if (!(abstractC2412ko instanceof AF)) {
                throw new AssertionError();
            }
            return;
        }
        for (S10 s10 : s10B.l().k0()) {
            if (!D) {
                AbstractC2412ko abstractC2412ko2 = s10.b;
                abstractC2412ko2.getClass();
                if (!(abstractC2412ko2 instanceof AF)) {
                    throw new AssertionError();
                }
            }
        }
    }

    private C3332x4 i(int i) {
        return ((C1713bl) this.a.get(i)).a;
    }

    private boolean j(int i) {
        int iG = this.u.g(i);
        boolean[] zArr = this.d;
        if (iG < zArr.length) {
            return zArr[iG];
        }
        if (this.e == null) {
            this.e = new HashSet();
        }
        return this.e.contains(Integer.valueOf(iG));
    }

    final C1713bl h(int i) {
        if (!D && i == -1) {
            throw new AssertionError();
        }
        C1713bl c1713bl = (C1713bl) this.a.get(i);
        if (c1713bl == null) {
            if (i >= 0 && j(i)) {
                int iD = this.a.a(i) ? i : this.a.c(i).d();
                C1713bl c1713bl2 = (C1713bl) this.a.get(iD);
                C2807pq c2807pq = this.a;
                c1713bl2.getClass();
                C1713bl c1713bl3 = new C1713bl();
                c1713bl3.b = new C2879qr(Collections.singleton(Integer.valueOf(iD)));
                c1713bl3.a.L();
                InterfaceC0841Ar it = c1713bl2.c.iterator();
                while (it.hasNext()) {
                    C1713bl c1713bl4 = (C1713bl) c2807pq.get(it.t());
                    c1713bl4.b.k(iD);
                    c1713bl4.b.a(i);
                }
                c1713bl3.c = c1713bl2.c;
                c1713bl2.c = new C2879qr(Collections.singleton(Integer.valueOf(i)));
                InterfaceC0841Ar it2 = c1713bl3.e.iterator();
                while (it2.hasNext()) {
                    ((C1713bl) c2807pq.get(it2.t())).d.a(i);
                }
                c1713bl3.e = new C2879qr((InterfaceC1277Pr) c1713bl2.e);
                c1713bl = c1713bl3;
            } else {
                c1713bl = new C1713bl();
            }
            this.a.a(i, c1713bl);
            this.b.a(i, c1713bl.a);
        }
        return c1713bl;
    }

    public final void d(int i) {
        WH whB;
        AbstractC3472z00 abstractC3472z00A;
        LinkedList<AbstractC2412ko> linkedListP = this.i.p();
        AbstractC1084Is abstractC1084IsX = linkedListP.get(linkedListP.size() - 1).X();
        boolean z = D;
        if (!z && abstractC1084IsX.b() != null) {
            throw new AssertionError();
        }
        if (!z && !abstractC1084IsX.g()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2L2 = abstractC1084IsX.L2();
        if (!abstractC1084IsX.b2() && !(abstractC1084IsX instanceof C1358Ss)) {
            whB = WH.i();
        } else {
            whB = WH.b();
        }
        if (abstractC1084IsX instanceof C1113Js) {
            abstractC3472z00A = abstractC1084IsX.a(this.o);
        } else {
            abstractC3472z00A = AbstractC3472z00.a(c0767v2L2, whB, (C0702m<?>) this.o);
        }
        abstractC1084IsX.f(a(i, abstractC3472z00A, 2));
    }

    public final void f(int i) {
        b(new C1621aZ(b(i, EnumC1968f20.OBJECT)));
        c();
    }

    public final void e(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = (enumC2527mI == EnumC2527mI.DOUBLE || enumC2527mI == EnumC2527mI.FLOAT) ? false : true;
        C3379xf c3379xf = new C3379xf(enumC2527mI, a(i, XM.a(enumC2527mI), z ? 2 : 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!D && c3379xf.g() != z) {
            throw new AssertionError();
        }
        a(c3379xf);
    }

    public final void b(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        boolean z = D;
        if (!z && !b(enumC2527mI)) {
            throw new AssertionError();
        }
        H1 h1 = new H1(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), a(i3));
        if (!z && h1.g()) {
            throw new AssertionError();
        }
        b(h1);
    }

    public final void e(int i) {
        EnumC1968f20 enumC1968f20A;
        C0767v2 c0767v2V1 = this.m.e().V1();
        if (c0767v2V1.Y0()) {
            if (!D && !this.r.d()) {
                throw new AssertionError();
            }
            b();
            return;
        }
        if (this.r.f()) {
            enumC1968f20A = EnumC1968f20.a(this.r.c().h());
        } else {
            enumC1968f20A = EnumC1968f20.a((char) c0767v2V1.e.e[0]);
        }
        AbstractC1954eu c1616aU = new C1616aU(b(i, enumC1968f20A));
        c(c1616aU);
        this.u.a(this);
        a(c1616aU);
    }

    public final void b(EnumC2523mE enumC2523mE, int i, int i2, int i3) {
        C1736c3 c1736c3 = new C1736c3(enumC2523mE, b(i2, EnumC1968f20.OBJECT), b(i3, EnumC1968f20.INT), b(i, EnumC1968f20.a(enumC2523mE)));
        if (!enumC2523mE.a()) {
            if (this.z == null) {
                this.z = new ArrayList();
            }
            this.z.add(c1736c3);
        }
        a(c1736c3);
    }

    public final void c(int i, int i2, C0767v2 c0767v2) {
        if (!D && !c0767v2.M0()) {
            throw new AssertionError();
        }
        b(new AG(a(i, AbstractC3472z00.a(c0767v2, WH.b(), (C0702m<?>) this.o), 2), b(i2, EnumC1968f20.INT), c0767v2));
    }

    public final void d(int i, C0767v2 c0767v2) {
        b(new DG(c0767v2, a(i, AbstractC3472z00.a(c0767v2, WH.b(), (C0702m<?>) this.o), 2)));
    }

    public static C2102gl a(com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, KV kv, Origin origin) {
        return new C2102gl(c0702m, o5Var, o5Var.e().a1().a(c0702m), c0702m.E().d((AbstractC1476Wj) null, o5Var.getReference()), new C2145hI(), kv, origin);
    }

    public final void c(int i, C0767v2 c0767v2) {
        b(new C2179hn(c0767v2, a(i, AbstractC3472z00.k(), 2)));
    }

    public final void d(int i, int i2, C0767v2 c0767v2) {
        b(new FG(c0767v2, i2, a(i, AbstractC3472z00.a(c0767v2, WH.b(), (C0702m<?>) this.o), 2)));
    }

    public final void b(int i, C0767v2 c0767v2) {
        C0702m c0702m = this.o;
        WH whB = WH.b();
        int i2 = AbstractC3472z00.b;
        a(new C3446yc(a(i, AbstractC3472z00.a(c0702m.a().m2, whB, (C0702m<?>) c0702m).b(), 2), c0767v2));
    }

    private void c(AbstractC2412ko abstractC2412ko) {
        if (!l()) {
            boolean z = D;
            if (!z && this.w != null) {
                throw new AssertionError();
            }
            if (!z && !this.x.isEmpty()) {
                throw new AssertionError();
            }
            return;
        }
        S10 s10 = this.w;
        if (s10 != null && s10.s() == abstractC2412ko.j()) {
            if (!D && abstractC2412ko.b() == null) {
                throw new AssertionError();
            }
            this.w.a(abstractC2412ko);
        }
        Iterator it = this.x.iterator();
        while (it.hasNext()) {
            ((S10) it.next()).a(abstractC2412ko);
        }
        this.w = null;
        this.x.clear();
    }

    public final void d(int i, int i2) {
        a(i, i2, true);
    }

    public static C2102gl a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.proto.k kVar, C2145hI c2145hI, KV kv, Origin origin) {
        return new C2102gl(c0702m, o5Var, abstractC1476Wj, kVar, c2145hI, kv, origin);
    }

    private void a(int i, C3332x4 c3332x4) {
        if (c3332x4.N()) {
            return;
        }
        this.f.add(new C2022fl(i, c3332x4));
    }

    public final void b(int i) {
        C3332x4 c3332x4I = i(i);
        if (!D && this.i.c(c3332x4I)) {
            throw new AssertionError();
        }
        this.i.h(c3332x4I);
        a(this.u.g(i), c3332x4I);
        a((AbstractC1954eu) new C1296Qj());
    }

    public final void a(int i, com.android.tools.r8.graph.W0 w0, BiConsumer biConsumer) {
        int i2;
        C0767v2 c0767v2J;
        AbstractC3472z00 abstractC3472z00A;
        com.android.tools.r8.graph.proto.c cVarA = this.r.a();
        if (w0.E0()) {
            i2 = 0;
        } else {
            if (!D && cVarA.b(0) != 0) {
                throw new AssertionError();
            }
            biConsumer.accept(Integer.valueOf(i), w0.J0());
            com.android.tools.r8.graph.o5 o5Var = this.n;
            i2 = 1;
            S10 s10A = a(i, AbstractC3472z00.a(this.m.t(), (o5Var == null || o5Var == this.m) ? WH.b() : WH.i(), (C0702m<?>) this.o), 1, l() ? this.u.a(i) : null);
            b(new C3406y2(s10A, this.i.a0(), false));
            this.s = s10A;
            s10A.W();
            i++;
        }
        int iD1 = (w0.d1() + (cVarA.h() + w0.l1().size())) - this.r.i();
        int i3 = 0;
        while (i2 < iD1) {
            com.android.tools.r8.graph.proto.b bVarA = cVarA.a(i2);
            if (bVarA.c()) {
                com.android.tools.r8.graph.proto.h hVarA = bVarA.a();
                biConsumer.accept(Integer.valueOf(i), hVarA.g());
                abstractC3472z00A = AbstractC3472z00.a(hVarA.g(), WH.i(), (C0702m<?>) this.o);
                a(i, abstractC3472z00A);
                i3++;
            } else {
                int iA = cVarA.a(i2, i3);
                if (bVarA instanceof com.android.tools.r8.graph.proto.l) {
                    com.android.tools.r8.graph.proto.l lVarB = bVarA.b();
                    if (!D && w0.j(iA) != lVarB.g()) {
                        throw new AssertionError();
                    }
                    c0767v2J = lVarB.h();
                } else {
                    c0767v2J = w0.j(iA);
                }
                biConsumer.accept(Integer.valueOf(i), c0767v2J);
                AbstractC3472z00 abstractC3472z00A2 = AbstractC3472z00.a(c0767v2J, WH.i(), (C0702m<?>) this.o);
                if (c0767v2J.N0()) {
                    a(i);
                } else {
                    a(i, abstractC3472z00A2);
                }
                abstractC3472z00A = abstractC3472z00A2;
            }
            i2++;
            i += abstractC3472z00A.O();
        }
        for (AbstractC3078th abstractC3078th : this.r.b()) {
            C0767v2 c0767v2J2 = w0.j(cVarA.a(i2, i3));
            if (abstractC3078th instanceof C3151uh) {
                C3406y2 c3406y2 = new C3406y2(new S10(this.k.a(), c0767v2J2.X0() ? AbstractC3472z00.m() : c0767v2J2.c(this.o), null), this.i.a0(), false);
                if (this.t == null) {
                    this.t = new ArrayList();
                }
                b(c3406y2);
                this.t.add(c3406y2.b());
            } else {
                a(i, abstractC3078th.a(this.o, c0767v2J2));
            }
            i2++;
            i += c0767v2J2.J0();
        }
    }

    public final void b(int i, int i2, com.android.tools.r8.graph.Y0 y0) {
        a(new C1793co(y0, b(i2, EnumC1968f20.OBJECT), b(i, EnumC1968f20.a(y0.h))));
    }

    public final void b(C0767v2 c0767v2, int i, int[] iArr) {
        if (!D && !(this.o.Y().f instanceof ClassFileConsumer)) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i2 : iArr) {
            arrayList.add(b(i2, EnumC1968f20.INT));
        }
        a(EnumC1953et.i, (AbstractC0594a1) c0767v2, (C0737r2) null, arrayList, false);
        d(i);
    }

    final void c(int i, int i2) {
        a(i, i2, false);
    }

    private void c() {
        boolean z = D;
        if (!z && this.i == null) {
            throw new AssertionError();
        }
        this.i.a(this);
        this.i = null;
        this.v = false;
        this.j = -1;
        if (!z && !this.x.isEmpty()) {
            throw new AssertionError();
        }
    }

    public final void b(int i, int i2, C0767v2 c0767v2) {
        int i3;
        String string = c0767v2.e.toString();
        boolean z = D;
        if (!z && string.charAt(0) != '[') {
            throw new AssertionError();
        }
        if (!z && string.length() < 2) {
            throw new AssertionError();
        }
        EnumC1968f20 enumC1968f20A = EnumC1968f20.a(string.charAt(1));
        ArrayList arrayList = new ArrayList(i / enumC1968f20A.c());
        int iC = i2;
        while (true) {
            i3 = i2 + i;
            if (iC >= i3) {
                break;
            }
            arrayList.add(b(iC, enumC1968f20A));
            iC += enumC1968f20A.c();
        }
        b(iC, i3);
        if (!D && !this.o.Y().Y0()) {
            throw new AssertionError();
        }
        a(EnumC1953et.h, (AbstractC0594a1) c0767v2, (C0737r2) null, arrayList, false);
    }

    private static void b(int i, int i2) {
        if (i != i2) {
            throw new C1367Tb("Invalid invoke instruction. Expected use of " + i + " argument registers, found actual use of " + i2);
        }
    }

    public final void b() {
        AbstractC1954eu c1616aU = new C1616aU();
        c(c1616aU);
        this.u.a(this);
        a(c1616aU);
    }

    public final void b(int i, com.android.tools.r8.graph.Y0 y0) {
        a(new RW(y0, b(i, EnumC1968f20.a(y0.h))));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.tools.r8.internal.ML, com.android.tools.r8.internal.S10] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.tools.r8.internal.S10] */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v24, types: [com.android.tools.r8.internal.MR] */
    /* JADX WARN: Type inference failed for: r3v29, types: [com.android.tools.r8.internal.S10] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.tools.r8.internal.S10] */
    /* JADX WARN: Type inference failed for: r3v35 */
    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.tools.r8.internal.x4] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.tools.r8.internal.x4] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.tools.r8.internal.x4] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.tools.r8.internal.MR] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.tools.r8.internal.x4, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private S10 b(int i, EnumC3182v4 enumC3182v4, C3332x4 c3332x4, ML.a aVar, EnumC1968f20 enumC1968f20) {
        EnumC3182v4 enumC3182v4B;
        ?? r9;
        ?? ml;
        ArrayList<C3979v2> arrayList;
        C3332x4 c3332x5;
        S10 s10A;
        NL nl = null;
        if (c3332x4.P() && c3332x4.y().size() == 1) {
            ArrayList arrayList2 = new ArrayList(this.g.size());
            EnumC3182v4 enumC3182v5 = enumC3182v4;
            C3332x4 c3332x6 = c3332x4;
            while (true) {
                if (!D) {
                    c3332x6.g0();
                }
                c3332x5 = c3332x6.y().get(0);
                enumC3182v4B = c3332x5.b(c3332x6);
                g(i);
                s10A = c3332x5.a(i, enumC3182v4B);
                if (s10A != null) {
                    enumC3182v4B = enumC3182v5;
                    c3332x5 = c3332x6;
                    break;
                }
                arrayList2.add(new C3979v2(c3332x6, enumC3182v5));
                if (!c3332x5.P() || c3332x5.y().size() != 1) {
                    break;
                }
                c3332x6 = c3332x5;
                enumC3182v5 = enumC3182v4B;
            }
            arrayList = arrayList2;
            ml = s10A;
            r9 = c3332x5;
        } else {
            enumC3182v4B = enumC3182v4;
            r9 = c3332x4;
            ml = 0;
            arrayList = null;
        }
        if (ml == 0) {
            if (r9 == this.h && aVar == ML.a.b) {
                if (!D && !r9.y().isEmpty()) {
                    throw new AssertionError();
                }
                ml = a(i, enumC1968f20);
            } else {
                com.android.tools.r8.graph.X xA = l() ? this.u.a(i, this.b.a(r9)) : null;
                AbstractC3472z00 abstractC3472z00A = C3403y00.a(enumC1968f20);
                this.A |= !abstractC3472z00A.G();
                if (!this.o.q() && this.u.a() && !this.B) {
                    C0767v2 c0767v2A = this.u.a(i, this.b.a(r9), aVar);
                    if (c0767v2A != null) {
                        nl = new NL(this.k.a(), r9, AbstractC3472z00.a(c0767v2A, WH.i(), (C0702m<?>) this.o), xA, aVar);
                    } else if (aVar != ML.a.b) {
                        if (!D && !this.m.e().Z0().e(Z8.h)) {
                            throw new AssertionError();
                        }
                        this.B = true;
                    } else {
                        throw new C0994Fs("Information in locals-table is invalid with respect to the stack map table. Local refers to non-present stack map type for register: " + i + " with constraint " + enumC1968f20 + ".");
                    }
                }
                ml = nl == null ? new ML(this.k.a(), r9, abstractC3472z00A, xA, aVar) : nl;
                if (!r9.P()) {
                    r9.a(i, ml, enumC3182v4B);
                } else {
                    r9.a(i, ml, enumC3182v4B);
                    ml.a(i, this);
                    ml = r9.a(i, enumC3182v4B);
                }
            }
        }
        if (arrayList != null) {
            for (C3979v2 c3979v2 : arrayList) {
                ((C3332x4) c3979v2.a()).a(i, ml, (EnumC3182v4) c3979v2.b());
            }
        }
        r9.a(i, ml, enumC3182v4B);
        return ml;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var, CE.a aVar) {
        C3332x4 c3332x4;
        boolean zContains;
        boolean zContains2;
        if (!D && this.u == null) {
            throw new AssertionError();
        }
        this.u.b();
        this.n = o5Var;
        C1713bl c1713bl = new C1713bl();
        this.a.a(-1, c1713bl);
        this.b.a(-1, c1713bl.a);
        int iE = this.u.e();
        this.d = new boolean[iE];
        this.c.add(0);
        while (true) {
            if (!this.c.isEmpty()) {
                int iIntValue = ((Integer) this.c.remove()).intValue();
                int iG = this.u.g(iIntValue);
                boolean[] zArr = this.d;
                if (iG < zArr.length) {
                    zContains = zArr[iG];
                } else {
                    if (this.e == null) {
                        this.e = new HashSet();
                    }
                    zContains = this.e.contains(Integer.valueOf(iG));
                }
                if (!zContains) {
                    while (iG < iE) {
                        if (!D) {
                            boolean[] zArr2 = this.d;
                            if (iG < zArr2.length) {
                                zContains2 = zArr2[iG];
                            } else {
                                if (this.e == null) {
                                    this.e = new HashSet();
                                }
                                zContains2 = this.e.contains(Integer.valueOf(iG));
                            }
                            if (zContains2) {
                                throw new AssertionError();
                            }
                        }
                        boolean[] zArr3 = this.d;
                        if (iG < zArr3.length) {
                            zArr3[iG] = true;
                        } else {
                            if (this.e == null) {
                                this.e = new HashSet();
                            }
                            this.e.add(Integer.valueOf(iG));
                        }
                        int iA = this.u.a(iG, this);
                        if (iA != -1) {
                            int i = iA + 1;
                            if (i >= iE) {
                                break;
                            }
                            h(this.u.e(i));
                            break;
                        }
                        iG++;
                        if (iG < iE) {
                            int iE2 = this.u.e(iG);
                            if (this.a.get(iE2) != null) {
                                a(iIntValue, iE2, true);
                                break;
                            }
                        }
                    }
                }
            } else {
                elIA elia = null;
                this.d = null;
                C3332x4 c3332x5 = ((C1713bl) this.a.get(-1)).a;
                this.i = c3332x5;
                this.h = c3332x5;
                this.u.c(this);
                a(0, this.i);
                while (true) {
                    C2022fl c2022fl = (C2022fl) this.f.poll();
                    if (c2022fl != null) {
                        if (!c2022fl.a.N()) {
                            if (!D && !this.x.isEmpty()) {
                                throw new AssertionError();
                            }
                            C3332x4 c3332x6 = c2022fl.a;
                            this.i = c3332x6;
                            this.g.add(c3332x6);
                            this.i.g(this.l.a());
                            if (c2022fl instanceof C1788cl) {
                                C1788cl c1788cl = (C1788cl) c2022fl;
                                int iG2 = this.u.g(c1788cl.f);
                                int iD = this.u.d(iG2);
                                AbstractC1915eM abstractC1915eMB = this.u.b(c1788cl.f);
                                if (iD >= 0) {
                                    AF af = new AF(a(iD, AbstractC3472z00.a(c1788cl.d, WH.b(), (C0702m<?>) this.o), 1, (com.android.tools.r8.graph.X) null), c1788cl.d, this.o.Y());
                                    af.b(abstractC1915eMB);
                                    this.i.a(af, this.C);
                                }
                                this.u.a(this, c1788cl.e, c1788cl.f, true);
                                C3332x4 c3332x4I = i(c1788cl.f);
                                this.i.h(c3332x4I);
                                a(abstractC1915eMB, new C1296Qj());
                                if (!c3332x4I.N()) {
                                    this.f.add(new C2022fl(iG2, c3332x4I));
                                }
                                c();
                            } else {
                                if (c2022fl instanceof C1863dl) {
                                    C1863dl c1863dl = (C1863dl) c2022fl;
                                    this.u.a(this, c1863dl.d, c1863dl.e, false);
                                    if (c2022fl.b == -1) {
                                        a(c1863dl.f, new C1296Qj());
                                        c();
                                    } else if (!this.x.isEmpty()) {
                                        b(new C1232Od());
                                    }
                                }
                                int iE3 = this.u.e();
                                int i2 = c2022fl.b;
                                while (i2 < iE3 && this.i != null) {
                                    int iE4 = this.u.e(i2);
                                    C1713bl c1713bl2 = (C1713bl) this.a.get(iE4);
                                    if (c1713bl2 != null && (c3332x4 = c1713bl2.a) != this.i) {
                                        a(i2, c3332x4);
                                        C3332x4 c3332x7 = c1713bl2.a;
                                        boolean z = D;
                                        if (!z && this.i == null) {
                                            throw new AssertionError();
                                        }
                                        if (!z && this.i.c(c3332x7)) {
                                            throw new AssertionError();
                                        }
                                        this.i.h(c3332x7);
                                        a((AbstractC1954eu) new C1296Qj());
                                        break;
                                    }
                                    this.j = iE4;
                                    this.u.a(this, i2, i2 == c2022fl.b);
                                    i2++;
                                }
                            }
                        }
                    } else {
                        boolean z2 = D;
                        if (!z2 && this.i != null) {
                            throw new AssertionError();
                        }
                        if (!z2) {
                            for (C3332x4 c3332x8 : this.g) {
                                boolean z3 = D;
                                if (!z3) {
                                    if (!z3) {
                                        c3332x8.g0();
                                    }
                                    InterfaceC1912eK it = this.a.values().iterator();
                                    while (it.hasNext()) {
                                        C1713bl c1713bl3 = (C1713bl) it.next();
                                        if (c1713bl3 != null && c1713bl3.a == c3332x8) {
                                            if (!D) {
                                                int size = c1713bl3.d.size() + c1713bl3.b.size();
                                                Set setC = OU.c();
                                                for (C3332x4 c3332x9 : c3332x8.y()) {
                                                    if (this.b.containsKey(c3332x9)) {
                                                        setC.add(c3332x9);
                                                    } else {
                                                        boolean z4 = D;
                                                        if (!z4 && c3332x9.z().size() != 1) {
                                                            throw new AssertionError();
                                                        }
                                                        if (!z4 && c3332x9.y().size() != 1) {
                                                            throw new AssertionError();
                                                        }
                                                        if (!z4) {
                                                            for (AbstractC2412ko abstractC2412ko : c3332x9.p()) {
                                                                if (!D) {
                                                                    abstractC2412ko.getClass();
                                                                    if (!(abstractC2412ko instanceof AF) && !abstractC2412ko.M1() && !abstractC2412ko.F1()) {
                                                                        throw new AssertionError();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (c3332x9.y().get(0).c(c3332x9)) {
                                                            setC.add(c3332x9.y().get(0));
                                                        } else {
                                                            setC.add(c3332x9);
                                                        }
                                                    }
                                                }
                                                if (size != setC.size()) {
                                                    throw new AssertionError();
                                                }
                                            }
                                            boolean z5 = D;
                                            if (!z5 && c1713bl3.c.size() != c3332x8.u().size()) {
                                                throw new AssertionError();
                                            }
                                            if (c3332x8.D() || z5 || !c3332x8.a() || c1713bl3.e.isEmpty() || (c1713bl3.e.size() == 1 && c1713bl3.e.iterator().t() < 0)) {
                                                break;
                                            }
                                            throw new AssertionError();
                                        }
                                    }
                                }
                            }
                        }
                        if (l()) {
                            Iterator it2 = this.g.iterator();
                            while (it2.hasNext()) {
                                A4 a4A = ((C3332x4) it2.next()).a(this.C);
                                AbstractC1915eM abstractC1915eM = null;
                                while (a4A.hasNext()) {
                                    AbstractC2412ko abstractC2412koA = a4A.next();
                                    AbstractC1915eM abstractC1915eMY0 = abstractC2412koA.Y0();
                                    if (abstractC2412koA instanceof AF) {
                                        if (!D && abstractC1915eM != null) {
                                            throw new AssertionError();
                                        }
                                    } else if (abstractC2412koA instanceof C1343Sd) {
                                        if (abstractC1915eMY0.equals(abstractC1915eM)) {
                                            a4A.s();
                                        }
                                    } else if (abstractC1915eMY0.s() && !(abstractC1915eMY0 instanceof AbstractC1915eM.c) && !abstractC1915eMY0.equals(abstractC1915eM)) {
                                        C1343Sd c1343Sd = new C1343Sd();
                                        c1343Sd.b(abstractC1915eMY0);
                                        a4A.previous();
                                        a4A.add(c1343Sd);
                                        a4A.next();
                                    }
                                    abstractC1915eM = abstractC1915eMY0;
                                }
                            }
                        }
                        if (this.y != null) {
                            AbstractC1915eM abstractC1915eMY1 = this.h.k().Y0();
                            A4 a4A2 = this.h.a(this.C);
                            a4A2.a(new Predicate() { // from class: com.android.tools.r8.internal.gl$$ExternalSyntheticLambda2
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return C2102gl.e((AbstractC2412ko) obj);
                                }
                            });
                            a4A2.previous();
                            InterfaceC1912eK it3 = this.y.values().iterator();
                            while (it3.hasNext()) {
                                for (S10 s10 : (List) it3.next()) {
                                    if (s10.S()) {
                                        C1259Pd c1259Pd = new C1259Pd(s10);
                                        c1259Pd.a(this.h);
                                        c1259Pd.b(abstractC1915eMY1);
                                        a4A2.add(c1259Pd);
                                    }
                                }
                            }
                        }
                        Iterator it4 = this.g.iterator();
                        while (it4.hasNext()) {
                            ((C3332x4) it4.next()).d();
                        }
                        ArrayList arrayList = new ArrayList();
                        for (C3332x4 c3332x10 : this.g) {
                            if (!c3332x10.E()) {
                                if (!(c3332x10.k() instanceof AF)) {
                                    ArrayList arrayList2 = new ArrayList();
                                    HashMap map = new HashMap();
                                    HashMap map2 = new HashMap();
                                    if (c3332x10.x().size() > 0) {
                                        ML ml = c3332x10.x().get(0);
                                        int i3 = 0;
                                        while (i3 < ml.k0().size()) {
                                            List<ML> listX = c3332x10.x();
                                            C1945el c1945el = new C1945el();
                                            Iterator<ML> it5 = listX.iterator();
                                            while (it5.hasNext()) {
                                                c1945el.a.add(it5.next().c(i3));
                                            }
                                            C3332x4 c3332x11 = c3332x10.y().get(i3);
                                            if (map.containsKey(c1945el)) {
                                                int iIntValue2 = ((Integer) map.get(c1945el)).intValue();
                                                C3332x4 c3332x4A = (C3332x4) map2.get(Integer.valueOf(iIntValue2));
                                                if (c3332x4A == null) {
                                                    c3332x4A = C3332x4.a(this.l.a(), c3332x10.k().Y0(), this.C, c3332x10);
                                                    map2.put(Integer.valueOf(iIntValue2), c3332x4A);
                                                    arrayList.add(c3332x4A);
                                                    C3332x4 c3332x12 = c3332x10.y().get(iIntValue2);
                                                    c3332x4A.r().add(c3332x12);
                                                    c3332x12.b(c3332x10, c3332x4A);
                                                    c3332x10.r().set(iIntValue2, c3332x4A);
                                                }
                                                c3332x4A.r().add(c3332x11);
                                                c3332x11.b(c3332x10, c3332x4A);
                                                arrayList2.add(Integer.valueOf(i3));
                                            } else {
                                                map.put(c1945el, Integer.valueOf(i3));
                                            }
                                            i3++;
                                            elia = null;
                                        }
                                    }
                                    if (!arrayList2.isEmpty()) {
                                        List<C3332x4> listR = c3332x10.r();
                                        ArrayList arrayList3 = new ArrayList(listR);
                                        listR.clear();
                                        Iterator it6 = arrayList2.iterator();
                                        int i4 = 0;
                                        while (it6.hasNext()) {
                                            int iIntValue3 = ((Integer) it6.next()).intValue();
                                            listR.addAll(arrayList3.subList(i4, iIntValue3));
                                            i4 = iIntValue3 + 1;
                                        }
                                        listR.addAll(arrayList3.subList(i4, arrayList3.size()));
                                    }
                                    c3332x10.b(arrayList2);
                                    elia = null;
                                }
                            } else {
                                throw new C1367Tb("Undefined value encountered during compilation. This is typically caused by invalid dex input that uses a register that is not defined on all control-flow paths leading to the use.");
                            }
                        }
                        this.g.addAll(arrayList);
                        C2409kl c2409kl = new C2409kl(this.o.Y(), this.m, this.u.b(0), this.g, this.k, this.l, this.C, this.q, aVar);
                        if (!D) {
                            c2409kl.I();
                        }
                        Iterator it7 = this.g.iterator();
                        while (it7.hasNext()) {
                            ((C3332x4) it7.next()).h();
                        }
                        c2409kl.a(this, (Set) null);
                        c2409kl.z();
                        if (!this.A && this.z == null) {
                            if ((!this.o.q() && this.u.a()) != false && !this.B) {
                                boolean z6 = D;
                                if (!z6) {
                                    if (!(!this.o.q() && this.u.a()) || this.B) {
                                        throw new AssertionError();
                                    }
                                }
                                if (!z6) {
                                    a(c2409kl);
                                }
                                new C3030t00(this.o, false).a(c2409kl);
                            } else {
                                new C3030t00(this.o, false).b(c2409kl);
                            }
                        } else {
                            if (!D && !(this.u instanceof C3148uf)) {
                                throw new AssertionError();
                            }
                            new C3403y00(this.o, this).a(this.z, c2409kl);
                        }
                        if (aVar.d()) {
                            AbstractC2242iY.a(c2409kl, this.o.a());
                        }
                        if (!D && !c2409kl.b(this.o)) {
                            throw new AssertionError();
                        }
                        this.u.clear();
                        this.u = null;
                        return c2409kl;
                    }
                }
            }
        }
    }

    public final void b(EnumC2527mI enumC2527mI, int i, int i2) {
        S10 s10B;
        AbstractC2412ko p30;
        S10 s10B2 = b(i2, EnumC1968f20.a(enumC2527mI));
        S10 s10A = a(i, XM.a(enumC2527mI), 1);
        if (this.o.Y().W()) {
            p30 = new LH(enumC2527mI, s10A, s10B2);
        } else {
            EnumC1968f20 enumC1968f20A = EnumC1968f20.a(enumC2527mI);
            if (enumC1968f20A == EnumC1968f20.INT) {
                s10B = a(-1L);
            } else {
                if (!D && enumC1968f20A != EnumC1968f20.LONG) {
                    throw new AssertionError();
                }
                C0978Fc c0978Fc = new C0978Fc(new S10(this.k.a(), AbstractC3472z00.l(), null), -1L);
                a(c0978Fc);
                s10B = c0978Fc.b();
            }
            p30 = new P30(enumC2527mI, s10A, s10B2, s10B);
        }
        if (!D && p30.g()) {
            throw new AssertionError();
        }
        b(p30);
    }

    public final S10 b(int i, EnumC1968f20 enumC1968f20) {
        com.android.tools.r8.graph.X xC = l() ? this.u.c(i) : null;
        S10 s10A = a(i, EnumC3182v4.a, this.i, ML.a.a, enumC1968f20);
        if (xC != null && s10A.s() != xC && !s10A.R()) {
            throw new C0994Fs("Attempt to read local " + xC + " but no local information was associated with the value being read.");
        }
        if (!D && s10A.A() && s10A.o() == null) {
            KV kv = this.u;
            s10A.s();
            if (!kv.d()) {
                throw new AssertionError();
            }
        }
        a(s10A, enumC1968f20);
        s10A.X();
        return s10A;
    }

    private void b(AbstractC2412ko abstractC2412ko) {
        a(this.u.f(), abstractC2412ko);
    }

    static boolean b(EnumC2527mI enumC2527mI) {
        return (enumC2527mI == EnumC2527mI.FLOAT || enumC2527mI == EnumC2527mI.DOUBLE || enumC2527mI == EnumC2527mI.LONG) ? false : true;
    }

    private static void a(C2409kl c2409kl) {
        c2409kl.r().forEach(new Consumer() { // from class: com.android.tools.r8.internal.gl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2102gl.d((AbstractC2412ko) obj);
            }
        });
    }

    public final void a(S10 s10, EnumC1968f20 enumC1968f20) {
        s10.a(enumC1968f20, this.m.getReference(), this.q, this.o.Y().e);
    }

    public final void a(int i, int i2, int i3) {
        this.u.a(i, i2, i3, this);
    }

    public final void a(AbstractC2412ko abstractC2412ko) {
        if (!D && abstractC2412ko.g2()) {
            throw new AssertionError();
        }
        b(abstractC2412ko);
    }

    public final void a(int i, com.android.tools.r8.graph.X x) {
        boolean z = D;
        if (!z && x == null) {
            throw new AssertionError();
        }
        if (l()) {
            if (!z && !l()) {
                throw new AssertionError();
            }
            EnumC1968f20 enumC1968f20A = EnumC1968f20.a(x.b);
            S10 s10A = a(i, EnumC3182v4.a, this.i, ML.a.b, enumC1968f20A);
            if (s10A.R() || s10A.s() != x) {
                return;
            }
            this.x.add(s10A);
        }
    }

    public final void a(AbstractC1915eM abstractC1915eM) {
        if (l()) {
            boolean z = D;
            if (!z && this.w != null) {
                throw new AssertionError();
            }
            if (!z && !this.u.f().equals(abstractC1915eM)) {
                throw new AssertionError();
            }
            if (!this.x.isEmpty()) {
                if (this.i.p().isEmpty()) {
                    b(new C1232Od());
                } else {
                    if (!z && this.x.contains(this.i.p().getLast().b())) {
                        throw new AssertionError();
                    }
                    c(this.i.p().getLast());
                }
            }
            b(new C1343Sd());
        }
    }

    public final void a(EnumC2523mE enumC2523mE, int i, int i2, int i3) {
        AbstractC3472z00 abstractC3472z00A;
        S10 s10B = b(i2, EnumC1968f20.OBJECT);
        S10 s10B2 = b(i3, EnumC1968f20.INT);
        if (enumC2523mE == EnumC2523mE.OBJECT && !this.o.q() && this.u.a()) {
            if (s10B.w().E()) {
                abstractC3472z00A = AbstractC3472z00.m();
            } else if (s10B.w().r()) {
                abstractC3472z00A = s10B.w().a().S();
            } else {
                if (!D) {
                    AbstractC3472z00 abstractC3472z00W = s10B.w();
                    abstractC3472z00W.getClass();
                    if (!(abstractC3472z00W instanceof G5) || !this.B) {
                        throw new AssertionError();
                    }
                }
                abstractC3472z00A = a(enumC2523mE);
            }
        } else {
            abstractC3472z00A = a(enumC2523mE);
        }
        Y2 y2 = new Y2(enumC2523mE, a(i, abstractC3472z00A, 2), s10B, s10B2);
        if (!enumC2523mE.a()) {
            if (this.z == null) {
                this.z = new ArrayList();
            }
            this.z.add(y2);
        }
        a(y2);
    }

    public final void a(int i, int i2) {
        a(new Z2(a(i, AbstractC3472z00.k(), 2), b(i2, EnumC1968f20.OBJECT)));
    }

    public final void a(int i, C0767v2 c0767v2) {
        S10 s10B = b(i, EnumC1968f20.OBJECT);
        a(new C2972s9(a(i, AbstractC3472z00.a(c0767v2, s10B.w().N(), (C0702m<?>) this.o), 2), s10B, c0767v2, false));
    }

    public final void a(AbstractC3472z00 abstractC3472z00, int i, long j) {
        C0978Fc c0978Fc = new C0978Fc(a(i, abstractC3472z00, 1), j);
        if (!D && c0978Fc.g()) {
            throw new AssertionError();
        }
        a(c0978Fc);
    }

    public final void a(int i, C0699l2 c0699l2) {
        if (!D && !this.o.Y().K()) {
            throw new AssertionError();
        }
        a(new C0889Cc(a(i, AbstractC3472z00.a(this.o.a().C2, WH.b(), (C0702m<?>) this.o), 2), c0699l2));
    }

    public final void a(int i, C0737r2 c0737r2) {
        if (!D && !this.o.Y().L()) {
            throw new AssertionError();
        }
        a(new C0918Dc(a(i, AbstractC3472z00.a(this.o.a().F2, WH.b(), (C0702m<?>) this.o), 2), c0737r2));
    }

    public final void a(EnumC1968f20 enumC1968f20, int i, int i2) {
        S10 s10B = b(i2, enumC1968f20);
        if (l()) {
            com.android.tools.r8.graph.X xA = l() ? this.u.a(i) : null;
            if (xA != null && xA != s10B.s()) {
                b(new C1289Qd(a(i, s10B.w(), 1), s10B));
                return;
            } else if (!this.x.isEmpty()) {
                b(new C1232Od());
            }
        }
        this.i.a(i, s10B, 1);
    }

    public final void a() {
        if (this.x.isEmpty()) {
            return;
        }
        b(new C1232Od());
    }

    public final void a(EnumC1076Il enumC1076Il, EnumC1968f20 enumC1968f20, int i, int i2, int i3, int i4) {
        if (i3 == i4) {
            if (!D && i3 != i4) {
                throw new AssertionError();
            }
            C3332x4 c3332x4I = i(i3);
            c3332x4I.g();
            this.i.h(c3332x4I);
            a(this.u.g(i3), c3332x4I);
            a((AbstractC1954eu) new C1296Qj());
            return;
        }
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(b(i, enumC1968f20));
        arrayList.add(b(i2, enumC1968f20));
        C1015Gl c1015Gl = new C1015Gl(enumC1076Il, arrayList);
        C3332x4 c3332x4I2 = i(i3);
        C3332x4 c3332x4I3 = i(i4);
        this.i.h(c3332x4I2);
        this.i.h(c3332x4I3);
        int iG = this.u.g(i4);
        if (!c3332x4I3.N()) {
            this.f.add(new C2022fl(iG, c3332x4I3));
        }
        int iG2 = this.u.g(i3);
        if (!c3332x4I2.N()) {
            this.f.add(new C2022fl(iG2, c3332x4I2));
        }
        a((AbstractC1954eu) c1015Gl);
    }

    public final void a(EnumC1076Il enumC1076Il, EnumC1968f20 enumC1968f20, int i, int i2, int i3) {
        if (i2 == i3) {
            if (!D && i2 != i3) {
                throw new AssertionError();
            }
            C3332x4 c3332x4I = i(i2);
            c3332x4I.g();
            this.i.h(c3332x4I);
            a(this.u.g(i2), c3332x4I);
            a((AbstractC1954eu) new C1296Qj());
            return;
        }
        C1015Gl c1015Gl = new C1015Gl(enumC1076Il, b(i, enumC1968f20));
        C3332x4 c3332x4I2 = i(i2);
        C3332x4 c3332x4I3 = i(i3);
        this.i.h(c3332x4I2);
        this.i.h(c3332x4I3);
        int iG = this.u.g(i3);
        if (!c3332x4I3.N()) {
            this.f.add(new C2022fl(iG, c3332x4I3));
        }
        int iG2 = this.u.g(i2);
        if (!c3332x4I2.N()) {
            this.f.add(new C2022fl(iG2, c3332x4I2));
        }
        a((AbstractC1954eu) c1015Gl);
    }

    public final void a(int i, int i2, com.android.tools.r8.graph.Y0 y0) {
        b(new C1189Mn(y0, a(i, AbstractC3472z00.a(y0.h, WH.i(), (C0702m<?>) this.o), 2), b(i2, EnumC1968f20.OBJECT)));
    }

    public final void a(int i, int i2, C0767v2 c0767v2) {
        b(new C1641ao(a(i, AbstractC3472z00.k(), 2), b(i2, EnumC1968f20.OBJECT), c0767v2));
    }

    public final void a(EnumC1953et enumC1953et, AbstractC0594a1 abstractC0594a1, C0737r2 c0737r2, ArrayList arrayList, boolean z) {
        boolean z2 = D;
        if (!z2 && enumC1953et == EnumC1953et.j) {
            if (!z2 && !(abstractC0594a1 instanceof C0684j2)) {
                throw new AssertionError();
            }
            C0684j2 c0684j2 = (C0684j2) abstractC0594a1;
            if (c0684j2.e == this.o.a().C2 && !z2 && !this.o.Y().P()) {
                throw new AssertionError();
            }
            if (c0684j2.e == this.o.a().B2 && !z2 && !this.o.Y().Q()) {
                throw new AssertionError();
            }
        }
        a(AbstractC1084Is.a(enumC1953et, abstractC0594a1, c0737r2, null, arrayList, z));
    }

    public final void a(EnumC1953et enumC1953et, C0684j2 c0684j2, C0737r2 c0737r2, List list, List list2, boolean z) {
        if (!D && list.size() != list2.size()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(b(((Integer) list2.get(i)).intValue(), EnumC1968f20.a((EnumC1812d20) list.get(i))));
        }
        a(enumC1953et, c0684j2, c0737r2, arrayList, z);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0023  */
    public final void a(int i, int i2, C0721p0 c0721p0) {
        int iC;
        C0699l2 c0699l2 = c0721p0.f;
        ArrayList arrayList = new ArrayList(i);
        EnumC0691k2 enumC0691k2 = c0699l2.d;
        enumC0691k2.getClass();
        if (enumC0691k2 == EnumC0691k2.b) {
            iC = i2;
        } else {
            EnumC0691k2 enumC0691k3 = c0699l2.d;
            enumC0691k3.getClass();
            if (enumC0691k3 == EnumC0691k2.c || c0699l2.d.g()) {
                iC = i2;
            } else {
                EnumC1968f20 enumC1968f20 = EnumC1968f20.OBJECT;
                arrayList.add(b(i2, enumC1968f20));
                iC = enumC1968f20.c() + i2;
            }
        }
        String string = c0721p0.e.d.toString();
        for (int i3 = 1; i3 < string.length(); i3++) {
            EnumC1968f20 enumC1968f20A = EnumC1968f20.a(string.charAt(i3));
            arrayList.add(b(iC, enumC1968f20A));
            iC += enumC1968f20A.c();
        }
        b(iC, i2 + i);
        a(new C1113Js(c0721p0, null, arrayList));
    }

    public final void a(C0721p0 c0721p0, ArrayList arrayList, ArrayList arrayList2) {
        if (!D && arrayList.size() != arrayList2.size()) {
            throw new AssertionError();
        }
        ArrayList arrayList3 = new ArrayList(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList3.add(b(((Integer) arrayList2.get(i)).intValue(), EnumC1968f20.a((EnumC1812d20) arrayList.get(i))));
        }
        a(new C1113Js(c0721p0, null, arrayList3));
    }

    public final void a(EnumC1953et enumC1953et, C0684j2 c0684j2, C0737r2 c0737r2, int i, int[] iArr) {
        C0759u2 c0759u2;
        ArrayList arrayList = new ArrayList(i);
        int iC = 0;
        if (enumC1953et != EnumC1953et.e) {
            int i2 = iArr[0];
            EnumC1968f20 enumC1968f20 = EnumC1968f20.OBJECT;
            arrayList.add(b(i2, enumC1968f20));
            iC = enumC1968f20.c();
        }
        if (enumC1953et == EnumC1953et.j) {
            c0759u2 = c0737r2.d;
        } else {
            c0759u2 = c0684j2.h.d;
        }
        String string = c0759u2.toString();
        for (int i3 = 1; i3 < c0759u2.d; i3++) {
            EnumC1968f20 enumC1968f20A = EnumC1968f20.a(string.charAt(i3));
            arrayList.add(b(iArr[iC], enumC1968f20A));
            iC += enumC1968f20A.c();
        }
        b(iC, i);
        if (!D && !this.o.Y().Y0()) {
            throw new AssertionError();
        }
        a(enumC1953et, (AbstractC0594a1) c0684j2, c0737r2, arrayList, false);
    }

    public final void a(C0767v2 c0767v2, int i, int[] iArr) {
        String string = c0767v2.e.toString();
        boolean z = D;
        int iC = 0;
        if (!z && string.charAt(0) != '[') {
            throw new AssertionError();
        }
        if (!z && string.length() < 2) {
            throw new AssertionError();
        }
        EnumC1968f20 enumC1968f20A = EnumC1968f20.a(string.charAt(1));
        ArrayList arrayList = new ArrayList(i / enumC1968f20A.c());
        while (iC < i) {
            arrayList.add(b(iArr[iC], enumC1968f20A));
            if (enumC1968f20A == EnumC1968f20.LONG || enumC1968f20A == EnumC1968f20.DOUBLE || enumC1968f20A == EnumC1968f20.LONG_OR_DOUBLE) {
                boolean z2 = D;
                if (!z2 && iC >= i - 1) {
                    throw new AssertionError();
                }
                if (!z2 && iArr[iC] != iArr[iC + 1] + 1) {
                    throw new AssertionError();
                }
            }
            iC += enumC1968f20A.c();
        }
        b(iC, i);
        a(EnumC1953et.h, (AbstractC0594a1) c0767v2, (C0737r2) null, arrayList, false);
    }

    public final void a(EnumC1953et enumC1953et, C0684j2 c0684j2, C0737r2 c0737r2, int i, int i2) {
        int iC;
        C0759u2 c0759u2;
        ArrayList arrayList = new ArrayList(i);
        if (enumC1953et != EnumC1953et.e) {
            EnumC1968f20 enumC1968f20 = EnumC1968f20.OBJECT;
            arrayList.add(b(i2, enumC1968f20));
            iC = enumC1968f20.c() + i2;
        } else {
            iC = i2;
        }
        if (enumC1953et == EnumC1953et.j) {
            c0759u2 = c0737r2.d;
        } else {
            c0759u2 = c0684j2.h.d;
        }
        String string = c0759u2.toString();
        for (int i3 = 1; i3 < c0759u2.d; i3++) {
            EnumC1968f20 enumC1968f20A = EnumC1968f20.a(string.charAt(i3));
            arrayList.add(b(iC, enumC1968f20A));
            iC += enumC1968f20A.c();
        }
        b(iC, i2 + i);
        if (!D && !this.o.Y().Y0()) {
            throw new AssertionError();
        }
        a(enumC1953et, (AbstractC0594a1) c0684j2, c0737r2, arrayList, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C3332x4 c3332x4) {
        AbstractC2412ko abstractC2412koK = c3332x4.k();
        abstractC2412koK.getClass();
        return abstractC2412koK instanceof AF;
    }

    public final void a(int i, int i2, long j, short[] sArr) {
        b(new BG(b(i, EnumC1968f20.OBJECT), i2, j, sArr));
    }

    public final void a(int i, com.android.tools.r8.graph.Y0 y0) {
        b(new QW(y0, a(i, AbstractC3472z00.a(y0.h, WH.i(), (C0702m<?>) this.o), 2)));
    }

    public final void a(int i, int[] iArr, int i2, int[] iArr2) {
        int i3;
        int length = iArr2.length;
        boolean z = D;
        if (!z && iArr.length != 1 && iArr.length != length) {
            throw new AssertionError();
        }
        if (length == 0) {
            b(i2);
            return;
        }
        S10 s10B = b(i, EnumC1968f20.INT);
        C2732or c2732or = new C2732or(length);
        C2732or c2732or2 = new C2732or(length);
        if (iArr.length == 1) {
            int i4 = iArr[0];
            i3 = 0;
            for (int i5 = 0; i5 < length; i5++) {
                if (iArr2[i5] != i2) {
                    c2732or.a(i4);
                    c2732or2.a(iArr2[i5]);
                } else {
                    i3++;
                }
                i4++;
            }
        } else {
            if (!z && iArr.length != length) {
                throw new AssertionError();
            }
            i3 = 0;
            for (int i6 = 0; i6 < length; i6++) {
                if (iArr2[i6] != i2) {
                    c2732or.a(iArr[i6]);
                    c2732or2.a(iArr2[i6]);
                } else {
                    i3++;
                }
            }
        }
        ((C1713bl) this.a.get(i2)).a.b(i3);
        if (i3 == length) {
            if (!D && c2732or.size() != 0) {
                throw new AssertionError();
            }
            b(i2);
            return;
        }
        int[] iArrA = c2732or.a((int[]) null);
        int[] iArrA2 = c2732or2.a((int[]) null);
        if (!D && iArrA.length != iArrA2.length) {
            throw new AssertionError();
        }
        int[] iArr3 = new int[iArrA2.length];
        HashMap map = new HashMap();
        C3332x4 c3332x4I = i(i2);
        this.i.h(c3332x4I);
        int iG = this.u.g(i2);
        if (!c3332x4I.N()) {
            this.f.add(new C2022fl(iG, c3332x4I));
        }
        int size = this.i.z().size() - 1;
        map.put(Integer.valueOf(i2), Integer.valueOf(size));
        for (int i7 = 0; i7 < iArrA2.length; i7++) {
            int i8 = iArrA2[i7];
            C3332x4 c3332x4I2 = i(i8);
            Integer num = (Integer) map.get(Integer.valueOf(i8));
            if (num == null) {
                this.i.h(c3332x4I2);
                int iG2 = this.u.g(i8);
                if (!c3332x4I2.N()) {
                    this.f.add(new C2022fl(iG2, c3332x4I2));
                }
                int size2 = this.i.z().size() - 1;
                map.put(Integer.valueOf(i8), Integer.valueOf(size2));
                iArr3[i7] = size2;
            } else {
                c3332x4I2.g();
                iArr3[i7] = num.intValue();
            }
        }
        a((AbstractC1954eu) new C1458Vr(size, s10B, iArrA, iArr3));
    }

    public final S10 a(int i, EnumC3182v4 enumC3182v4, C3332x4 c3332x4, ML.a aVar, EnumC1968f20 enumC1968f20) {
        g(i);
        S10 s10A = c3332x4.a(i, enumC3182v4);
        return s10A != null ? s10A : b(i, enumC3182v4, c3332x4, aVar, enumC1968f20);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private S10 a(int i, EnumC1968f20 enumC1968f20) {
        if (!this.o.Y().c0) {
            if (!D && !enumC1968f20.b()) {
                throw new AssertionError();
            }
            AbstractC3472z00 abstractC3472z00M = enumC1968f20.a() ? AbstractC3472z00.m() : enumC1968f20.d();
            if (this.y == null) {
                this.y = new C1303Qq();
            }
            List<S10> arrayList = (List) this.y.get(i);
            if (arrayList != null) {
                for (S10 s10 : arrayList) {
                    if (s10.w() == abstractC3472z00M) {
                        return s10;
                    }
                }
            } else {
                arrayList = new ArrayList(2);
                this.y.a(i, arrayList);
            }
            S10 s11 = new S10(this.k.a(), abstractC3472z00M, null);
            arrayList.add(s11);
            return s11;
        }
        throw new C0994Fs("Information in locals-table is invalid. Local refers to uninitialized register: " + i + " with constraint " + enumC1968f20 + ".");
    }

    public final void a(EnumC2527mI enumC2527mI, int i, int i2, int i3) {
        H1 h1 = new H1(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!D && h1.g()) {
            throw new AssertionError();
        }
        b(h1);
    }

    public final void a(EnumC2527mI enumC2527mI, EnumC2246ib enumC2246ib, int i, int i2, int i3) {
        C2318jb c2318jb = new C2318jb(enumC2527mI, enumC2246ib, a(i, AbstractC3472z00.k(), 1), b(i2, EnumC1968f20.a(enumC2527mI)), b(i3, EnumC1968f20.a(enumC2527mI)));
        if (!D && c2318jb.g()) {
            throw new AssertionError();
        }
        a(c2318jb);
    }

    public final void a(EnumC2527mI enumC2527mI, EnumC2527mI enumC2527mI2, int i, int i2) {
        C1831dI c1831dI = new C1831dI(enumC2527mI2, enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI2)));
        if (!D && c1831dI.g()) {
            throw new AssertionError();
        }
        b(c1831dI);
    }

    public final void a(EnumC2527mI enumC2527mI, int i, int i2) {
        C2979sG c2979sG = new C2979sG(enumC2527mI, a(i, XM.a(enumC2527mI), 1), b(i2, EnumC1968f20.a(enumC2527mI)));
        if (!D && c2979sG.g()) {
            throw new AssertionError();
        }
        b(c2979sG);
    }

    private S10 a(long j) {
        C0978Fc c0978Fc = new C0978Fc(new S10(this.k.a(), AbstractC3472z00.k(), null), j);
        a(c0978Fc);
        return c0978Fc.b();
    }

    private S10 a(int i, AbstractC3472z00 abstractC3472z00, int i2, com.android.tools.r8.graph.X x) {
        S10 s10 = new S10(this.k.a(), abstractC3472z00, x);
        g(i);
        this.i.a(i, s10, i2);
        return s10;
    }

    public final S10 a(int i, AbstractC3472z00 abstractC3472z00, int i2) {
        S10 s10A = null;
        com.android.tools.r8.graph.X xC = l() ? this.u.c(i) : null;
        com.android.tools.r8.graph.X xA = l() ? this.u.a(i) : null;
        if (xC != null && xC == xA) {
            if (!D && !l()) {
                throw new AssertionError();
            }
            s10A = a(i, EnumC3182v4.a, this.i, ML.a.b, EnumC1968f20.a(xC.b));
        }
        this.w = s10A;
        return a(i, abstractC3472z00, i2, xA);
    }

    public final void a(int i) {
        C3406y2 c3406y2 = new C3406y2(a(i, AbstractC3472z00.k(), 1, l() ? this.u.a(i) : null), this.i.a0(), true);
        if (this.t == null) {
            this.t = new ArrayList();
        }
        b(c3406y2);
        this.t.add(c3406y2.b());
    }

    public final void a(int i, AbstractC3472z00 abstractC3472z00) {
        C3406y2 c3406y2 = new C3406y2(a(i, abstractC3472z00, 1, l() ? this.u.a(i) : null), this.i.a0(), false);
        if (this.t == null) {
            this.t = new ArrayList();
        }
        b(c3406y2);
        this.t.add(c3406y2.b());
    }

    private void a(AbstractC1915eM abstractC1915eM, AbstractC2412ko abstractC2412ko) {
        boolean z = D;
        if (!z) {
            if (!z && abstractC2412ko.b() != null && !abstractC2412ko.p1() && abstractC2412ko.a(this.o) != abstractC2412ko.a()) {
                throw new AssertionError();
            }
            if (!z && abstractC2412ko.b() != null && abstractC2412ko.p1() && abstractC2412ko.a(this.o) != abstractC2412ko.a()) {
                AbstractC3472z00 abstractC3472z00A = abstractC2412ko.a();
                abstractC3472z00A.getClass();
                if (!(abstractC3472z00A instanceof G5) || !abstractC2412ko.a(this.o).I()) {
                    throw new AssertionError();
                }
            }
        }
        this.A |= (abstractC2412ko.b() == null || abstractC2412ko.a().G()) ? false : true;
        abstractC2412ko.b(abstractC1915eM);
        c(abstractC2412ko);
        this.i.a(abstractC2412ko, this.C);
        if (abstractC2412ko.g()) {
            if (!z && !this.u.c()) {
                throw new AssertionError();
            }
            C3038t6 c3038t6B = this.u.b(this);
            if (c3038t6B != null) {
                if (!z && this.v) {
                    throw new AssertionError();
                }
                this.v = true;
                final ArrayList arrayList = new ArrayList(c3038t6B.a().size());
                final Set setC = OU.c();
                c3038t6B.a(new BiConsumer() { // from class: com.android.tools.r8.internal.gl$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        this.f$0.a(arrayList, setC, (C0767v2) obj, (Integer) obj2);
                    }
                });
                this.i.a(c3038t6B.b(), arrayList);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, Set set, C0767v2 c0767v2, Integer num) {
        C3332x4 c3332x4 = new C3332x4();
        c3332x4.L();
        this.f.add(new C1788cl(c3332x4, c0767v2, this.j, num.intValue()));
        list.add(c3332x4);
        C3332x4 c3332x4I = i(num.intValue());
        if (set.add(c3332x4I)) {
            return;
        }
        c3332x4I.L();
    }

    private void a(int i, int i2, boolean z) {
        if (i2 >= 0 && !j(i2)) {
            this.c.add(Integer.valueOf(i2));
        }
        C1713bl c1713blH = h(i2);
        if (!this.a.a(i)) {
            i = this.a.c(i).d();
        }
        C1713bl c1713bl = (C1713bl) this.a.get(i);
        if (z) {
            c1713bl.c.a(i2);
            c1713blH.b.a(i);
        } else {
            c1713bl.e.a(i2);
            c1713blH.d.a(i);
        }
        c1713blH.a.L();
    }

    private void a(AbstractC1954eu abstractC1954eu) {
        boolean z = D;
        if (!z && abstractC1954eu.g()) {
            throw new AssertionError();
        }
        if (!z && this.i == null) {
            throw new AssertionError();
        }
        if (!z && !this.i.p().isEmpty() && this.i.p().getLast().g2()) {
            throw new AssertionError();
        }
        if (!z && this.i == null) {
            throw new AssertionError();
        }
        if (!z && !this.i.M() && this.i.p().getLast().g2()) {
            throw new AssertionError();
        }
        C1713bl c1713bl = (C1713bl) this.a.get(this.b.a(this.i));
        AbstractC1915eM abstractC1915eMF = this.u.f();
        if (c1713bl.c.size() > 1 || (c1713bl.c.size() == 1 && !c1713bl.e.isEmpty())) {
            InterfaceC0841Ar it = c1713bl.c.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Integer) it.next()).intValue();
                C1713bl c1713bl2 = (C1713bl) this.a.get(iIntValue);
                if (c1713bl2.d.size() + c1713bl2.b.size() == 1) {
                    C2022fl c2022fl = null;
                    for (C2022fl c2022fl2 : this.f) {
                        if (c2022fl2.a == c1713bl2.a) {
                            c2022fl = c2022fl2;
                        }
                    }
                    if (!D && c2022fl.b != this.u.g(iIntValue)) {
                        throw new AssertionError();
                    }
                    this.f.remove(c2022fl);
                    this.f.add(new C1863dl(c2022fl.b, c2022fl.a, abstractC1915eMF, this.j, iIntValue));
                } else {
                    C3332x4 c3332x4 = this.i;
                    C3332x4 c3332x5 = c1713bl2.a;
                    C3332x4 c3332x6 = new C3332x4();
                    c3332x6.L();
                    c3332x6.r().add(c3332x4);
                    c3332x6.s().add(c3332x5);
                    c3332x4.b(c3332x5, c3332x6);
                    c3332x5.a(c3332x4, c3332x6);
                    this.f.add(new C1863dl(-1, c3332x6, abstractC1915eMF, this.j, iIntValue));
                }
            }
        } else if (c1713bl.c.size() == 1) {
            this.u.a(this, this.j, c1713bl.c.iterator().t(), false);
        } else if (!z) {
            C2879qr c2879qr = new C2879qr(c1713bl.e.size() + c1713bl.c.size());
            c2879qr.a(c1713bl.c);
            c2879qr.a((InterfaceC3088tr) c1713bl.e);
            if (!c2879qr.isEmpty()) {
                throw new AssertionError();
            }
        }
        b(abstractC1954eu);
        c();
    }

    static boolean a(EnumC2527mI enumC2527mI) {
        return (enumC2527mI == EnumC2527mI.FLOAT || enumC2527mI == EnumC2527mI.DOUBLE) ? false : true;
    }
}
