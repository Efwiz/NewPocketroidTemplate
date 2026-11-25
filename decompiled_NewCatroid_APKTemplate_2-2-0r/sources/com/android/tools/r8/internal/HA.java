package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0691k2;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.AbstractC3974v0;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class HA {
    private static final com.android.tools.r8.ir.optimize.info.r k = com.android.tools.r8.ir.optimize.info.r.b();
    static final /* synthetic */ boolean l = true;
    final C0702m a;
    final NA b;
    public final C0767v2 c;
    public JA d;
    public final C0684j2 e;
    final C0684j2 f;
    private final C0684j2 g;
    public final com.android.tools.r8.graph.Y0 h;
    public final GA i;
    private C0729q2 j = null;

    public HA(com.android.tools.r8.synthesis.P p, C0702m c0702m, NA na, com.android.tools.r8.graph.o5 o5Var, JA ja, MA ma) {
        boolean z = l;
        if (!z && na == null) {
            throw new AssertionError();
        }
        if (!z && ja == null) {
            throw new AssertionError();
        }
        C0767v2 c0767v2E = p.e();
        this.c = c0767v2E;
        this.a = c0702m;
        this.b = na;
        this.d = ja;
        C0722p1 c0722p1C = p.c();
        C0737r2 c0737r2B = c0722p1C.b(c0722p1C.D1, ja.i.a);
        this.e = c0722p1C.a(c0767v2E, c0737r2B, c0722p1C.Y0);
        this.i = c(o5Var);
        boolean zG = g();
        this.f = zG ? c0722p1C.a(c0767v2E, c0737r2B, c0722p1C.Z0) : null;
        this.h = zG ? c0722p1C.a(c0767v2E, c0767v2E, c0722p1C.d1) : null;
        this.g = c0702m.Y().n1.K0 ? c0722p1C.a(c0767v2E, c0722p1C.b(c0767v2E, ja.i.a), c0722p1C.c(CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME)) : null;
        a(p, ma);
    }

    private GA b(com.android.tools.r8.graph.o5 o5Var) {
        C0699l2 c0699l2 = this.d.f;
        boolean z = l;
        if (!z && c0699l2 == null) {
            throw new AssertionError();
        }
        C0684j2 c0684j2U0 = c0699l2.u0();
        if (!z) {
            JA ja = this.d;
            C0722p1 c0722p1A = this.a.a();
            C0759u2 c0759u2C0 = ja.f.u0().C0();
            C0759u2 c0759u2 = c0722p1A.e1;
            c0759u2C0.getClass();
            if (!c0759u2C0.a(c0759u2.e)) {
                throw new AssertionError();
            }
        }
        if (!z && c0684j2U0.e != o5Var.t()) {
            throw new AssertionError();
        }
        if (!z && !this.d.a(o5Var.t())) {
            throw new AssertionError();
        }
        if (c0699l2.d.g()) {
            com.android.tools.r8.graph.I4 i4B = this.a.g().b(c0684j2U0, c0699l2.f);
            if (i4B.h()) {
                return new DA(c0684j2U0, this.a.a().p3, this.d.f.f);
            }
            com.android.tools.r8.graph.I4.c<?> cVarO = i4B.o();
            if (!z && !cVarO.r().E0()) {
                throw new AssertionError();
            }
            if (!z && !cVarO.d().e0()) {
                throw new AssertionError();
            }
            return new FA(this.d.f.f, new com.android.tools.r8.graph.o5(cVarO.d().a0(), cVarO.r()));
        }
        if (!z && !c0699l2.d.d()) {
            throw new AssertionError();
        }
        if (!this.a.c(c0684j2U0.e).isInterface()) {
            return new BA(this.d.f.u0(), this.d.f.f, this.a.a().a(c0684j2U0.e, c0684j2U0.h, AbstractC3974v0.a(this.a.a(), c0684j2U0)), this.a);
        }
        C0737r2 c0737r2 = c0684j2U0.h;
        C0767v2[] c0767v2Arr = c0737r2.f.a;
        C0767v2[] c0767v2Arr2 = new C0767v2[c0767v2Arr.length + 1];
        c0767v2Arr2[0] = c0684j2U0.e;
        System.arraycopy(c0767v2Arr, 0, c0767v2Arr2, 1, c0767v2Arr.length);
        return new CA(this.d.f.u0(), this.d.f.f, this.a.a().a(c0684j2U0.e, this.a.a().b(c0737r2.e, c0767v2Arr2), c0684j2U0.f), this.a);
    }

    public final void a(C0729q2 c0729q2) {
        boolean z = l;
        if (!z && this.j != null) {
            throw new AssertionError();
        }
        if (!z && this.c != c0729q2.d) {
            throw new AssertionError();
        }
        this.j = c0729q2;
    }

    public final C0684j2 c() {
        if (l || f()) {
            return this.g;
        }
        throw new AssertionError();
    }

    public final C0729q2 d() {
        if (l || this.j != null) {
            return this.j;
        }
        throw new AssertionError();
    }

    public final C0767v2 e() {
        return this.c;
    }

    public final boolean f() {
        return this.g != null;
    }

    public final boolean g() {
        return this.a.Y().f0 && this.d.i.isEmpty();
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:57:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:92:0x018b  */
    /* JADX WARN: Code duplicated, block: B:95:0x0194  */
    private GA c(com.android.tools.r8.graph.o5 o5Var) {
        switch (this.d.f.d.ordinal()) {
            case 4:
                if (a()) {
                    return b(o5Var);
                }
                if (!l && !this.d.f.d.g()) {
                    throw new AssertionError();
                }
                if (!a()) {
                    if (this.a.Y().Z0()) {
                        C0699l2 c0699l2 = this.d.f;
                        if (!c0699l2.f || (!c0699l2.d.d() && !this.d.f.d.g())) {
                            if (this.d.a(o5Var)) {
                                return C3485zA.a(this.d.f, o5Var, this.a.a().a(o5Var.t(), this.d.f.u0().h, b()), this.a);
                            }
                        }
                    } else if (this.d.a(o5Var)) {
                        return C3485zA.a(this.d.f, o5Var, this.a.a().a(o5Var.t(), this.d.f.u0().h, b()), this.a);
                    }
                }
                return EA.a(this.d);
            case 5:
                if (!l && !this.d.f.d.e() && !this.d.f.d.d()) {
                    throw new AssertionError();
                }
                return a(o5Var);
            case 6:
                C0699l2 c0699l3 = this.d.f;
                boolean z = l;
                if (!z && c0699l3 == null) {
                    throw new AssertionError();
                }
                if (!z && !c0699l3.d.c()) {
                    throw new AssertionError();
                }
                if (!a()) {
                    if (this.a.Y().Z0()) {
                        C0699l2 c0699l4 = this.d.f;
                        if (!c0699l4.f || (!c0699l4.d.d() && !this.d.f.d.g())) {
                            if (this.d.a(o5Var)) {
                                C0684j2 c0684j2U0 = c0699l3.u0();
                                return C3485zA.a(this.d.f, o5Var, this.a.a().a(o5Var.t(), this.a.a().b(c0684j2U0.e, c0684j2U0.h.f.a), b()), this.a);
                            }
                        }
                    } else if (this.d.a(o5Var)) {
                        C0684j2 c0684j2U1 = c0699l3.u0();
                        return C3485zA.a(this.d.f, o5Var, this.a.a().a(o5Var.t(), this.a.a().b(c0684j2U1.e, c0684j2U1.h.f.a), b()), this.a);
                    }
                }
                return EA.a(this.d);
            case 7:
                if (a()) {
                    return b(o5Var);
                }
                if (!l && !this.d.f.d.e() && !this.d.f.d.d()) {
                    throw new AssertionError();
                }
                return a(o5Var);
            case 8:
                if (l || this.d.f.d.f()) {
                    return a(o5Var);
                }
                throw new AssertionError();
            case 9:
                throw new Z00("Method references to super methods are not yet supported");
            default:
                throw new C3405y10("Unexpected method handle type in " + this.d.f);
        }
    }

    private void a(com.android.tools.r8.synthesis.P p, MA ma) {
        int i;
        com.android.tools.r8.graph.Q2 q2K;
        p.b(this.d.g);
        if (g()) {
            if (!l && this.h == null) {
                throw new AssertionError();
            }
            p.c(Collections.singletonList(com.android.tools.r8.graph.T0.d1().a(this.h).a(com.android.tools.r8.graph.Q2.k(4121)).a(com.android.tools.r8.graph.I2.b).d().a()));
        }
        int length = this.d.i.a.length;
        ArrayList arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            com.android.tools.r8.graph.T0.a aVarA = com.android.tools.r8.graph.T0.d1().a(a(i2));
            if (this.a.Y().i0().d) {
                q2K = com.android.tools.r8.graph.Q2.k(4113);
            } else {
                q2K = com.android.tools.r8.graph.Q2.k(4097);
            }
            arrayList.add(aVarA.a(q2K).d().a());
        }
        p.a((List) arrayList);
        boolean zG = g();
        ArrayList arrayList2 = new ArrayList(zG ? 2 : 1);
        com.android.tools.r8.graph.W0.a aVarA2 = com.android.tools.r8.graph.W0.Y1().a(this.e).a(C0725p4.b((zG ? 2 : 1) | 4096, true));
        AbstractC1639am abstractC1639amI = AbstractC1639am.i();
        AbstractC1639am abstractC1639amI2 = AbstractC1639am.i();
        C1507Xl c1507XlG = AbstractC1639am.g();
        c1507XlG.a(new O7(EnumC1812d20.OBJECT, 0));
        c1507XlG.a(new I7(183, this.a.a().g4.g, false));
        int i3 = 0;
        int iC = 1;
        int iC2 = 1;
        for (C0767v2[] c0767v2Arr = this.d.i.a; i3 < c0767v2Arr.length; c0767v2Arr = c0767v2Arr) {
            com.android.tools.r8.graph.Y0 y0A = a(i3);
            if (!IA.a && y0A.h != c0767v2Arr[i3]) {
                throw new AssertionError();
            }
            EnumC1812d20 enumC1812d20A = EnumC1812d20.a(y0A.h);
            c1507XlG.a(new O7(EnumC1812d20.OBJECT, 0));
            c1507XlG.a(new O7(enumC1812d20A, iC2));
            c1507XlG.a(new C3482z7(y0A, y0A));
            iC2 += enumC1812d20A.c();
            iC += enumC1812d20A.c();
            i3++;
        }
        c1507XlG.a(new C3186v8());
        arrayList2.add(aVarA2.a(new C0753u(this.e.e, iC, iC2, c1507XlG.a(), abstractC1639amI, abstractC1639amI2, Position.UNKNOWN, C2049g6.a())).c().a());
        if (zG) {
            com.android.tools.r8.graph.W0.a aVarA3 = com.android.tools.r8.graph.W0.Y1().a(this.f).a(C0725p4.b(4104, true));
            C0767v2 c0767v2 = this.c;
            V7 v7 = new V7(null, this.c);
            D8 d8 = new D8(D8.a.Dup);
            I7 i7 = new I7(183, this.e, false);
            com.android.tools.r8.graph.Y0 y0 = this.h;
            AbstractC1639am abstractC1639amA = AbstractC1639am.a(v7, d8, i7, new P8(y0, y0), new C3186v8());
            i = 2;
            arrayList2.add(aVarA3.a(new C0753u(c0767v2, 2, 0, abstractC1639amA)).c().a());
            k.e((com.android.tools.r8.graph.W0) arrayList2.get(1));
        } else {
            i = 2;
        }
        if (f()) {
            com.android.tools.r8.graph.W0.a aVarA4 = com.android.tools.r8.graph.W0.Y1().a(this.g).a(C0725p4.b(4105, false));
            C1507Xl c1507XlG2 = AbstractC1639am.g();
            c1507XlG2.a(new V7(null, this.c)).a(new D8(D8.a.Dup));
            int i4 = 0;
            int i5 = 0;
            int iC3 = i;
            int iC4 = 0;
            while (i4 < this.e.h.u0().size()) {
                EnumC1812d20 enumC1812d20A2 = EnumC1812d20.a(this.e.h.k(i4));
                c1507XlG2.a(new O7(enumC1812d20A2, iC4));
                iC3 += enumC1812d20A2.c();
                iC4 += enumC1812d20A2.c();
                i4++;
                i5 = iC4;
            }
            c1507XlG2.a(new I7(183, this.e, false)).a(new C3109u8(EnumC1812d20.a(this.c)));
            arrayList2.add(aVarA4.a(new C0753u(this.c, iC3, i5, c1507XlG2.a())).c().a());
        }
        p.a((AbstractCollection) arrayList2);
        C0722p1 c0722p1A = this.a.a();
        C0767v2 c0767v3 = this.c;
        JA ja = this.d;
        C0684j2 c0684j2A = c0722p1A.a(c0767v3, ja.d, ja.c);
        ArrayList arrayList3 = new ArrayList(this.d.h.size() + 1);
        arrayList3.add(com.android.tools.r8.graph.W0.Y1().a(c0684j2A).a(C0725p4.b(17, false)).a(PA.a(this, c0684j2A, ma)).c().a());
        Iterator it = this.d.h.iterator();
        while (it.hasNext()) {
            C0684j2 c0684j2A2 = this.a.a().a(this.c, (C0737r2) it.next(), this.d.c);
            arrayList3.add(com.android.tools.r8.graph.W0.Y1().a(c0684j2A2).a(C0725p4.b(4177, false)).a(C1071Ii.a(this.a.a()).b(c0684j2A2).d(c0684j2A, false).a(this.a.g()).c().a()).c().a());
        }
        p.b((AbstractCollection) arrayList3);
    }

    private C0759u2 b() {
        return this.a.a().c("$r8$lambda$" + this.d.a);
    }

    final com.android.tools.r8.graph.Y0 a(int i) {
        return this.a.a().a(this.c, this.d.i.a[i], this.a.a().c("f$" + i));
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002f  */
    /* JADX WARN: Code duplicated, block: B:16:0x0038  */
    private GA a(com.android.tools.r8.graph.o5 o5Var) {
        if (!a()) {
            if (this.a.Y().Z0()) {
                C0699l2 c0699l2 = this.d.f;
                if (!c0699l2.f || (!c0699l2.d.d() && !this.d.f.d.g())) {
                    if (this.d.a(o5Var)) {
                        C0737r2 c0737r2 = this.d.f.u0().h;
                        C0767v2[] c0767v2Arr = c0737r2.f.a;
                        C0767v2[] c0767v2Arr2 = new C0767v2[c0767v2Arr.length + 1];
                        c0767v2Arr2[0] = this.d.a();
                        System.arraycopy(c0767v2Arr, 0, c0767v2Arr2, 1, c0767v2Arr.length);
                        return C3485zA.a(this.d.f, o5Var, this.a.a().a(o5Var.t(), this.a.a().b(c0737r2.e, c0767v2Arr2), b()), this.a);
                    }
                }
            } else if (this.d.a(o5Var)) {
                C0737r2 c0737r3 = this.d.f.u0().h;
                C0767v2[] c0767v2Arr3 = c0737r3.f.a;
                C0767v2[] c0767v2Arr4 = new C0767v2[c0767v2Arr3.length + 1];
                c0767v2Arr4[0] = this.d.a();
                System.arraycopy(c0767v2Arr3, 0, c0767v2Arr4, 1, c0767v2Arr3.length);
                return C3485zA.a(this.d.f, o5Var, this.a.a().a(o5Var.t(), this.a.a().b(c0737r3.e, c0767v2Arr4), b()), this.a);
            }
        }
        return EA.a(this.d);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0040  */
    /* JADX WARN: Code duplicated, block: B:17:0x004c  */
    /* JADX WARN: Code duplicated, block: B:19:0x006b  */
    private boolean a() {
        C0759u2 c0759u2C0;
        C0759u2 c0759u2;
        EnumC0691k2 enumC0691k2 = this.d.f.d;
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        C0702m c0702m = this.a;
        a1Y.getClass();
        if (!c0702m.q()) {
            if (this.a.Y().Z0()) {
                C0699l2 c0699l2 = this.d.f;
                if (!c0699l2.f || (!c0699l2.d.d() && !this.d.f.d.g())) {
                    if (!enumC0691k2.d() || enumC0691k2.g()) {
                        JA ja = this.d;
                        C0722p1 c0722p1A = this.a.a();
                        c0759u2C0 = ja.f.u0().C0();
                        c0759u2 = c0722p1A.e1;
                        c0759u2C0.getClass();
                        if (!c0759u2C0.a(c0759u2.e) && !this.b.a(this.d.f)) {
                            return true;
                        }
                    }
                }
            } else if (!enumC0691k2.d()) {
                JA ja2 = this.d;
                C0722p1 c0722p1A2 = this.a.a();
                c0759u2C0 = ja2.f.u0().C0();
                c0759u2 = c0722p1A2.e1;
                c0759u2C0.getClass();
                if (!c0759u2C0.a(c0759u2.e)) {
                }
            } else {
                JA ja3 = this.d;
                C0722p1 c0722p1A3 = this.a.a();
                c0759u2C0 = ja3.f.u0().C0();
                c0759u2 = c0722p1A3.e1;
                c0759u2C0.getClass();
                if (!c0759u2C0.a(c0759u2.e)) {
                }
            }
        }
        return false;
    }
}
