package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Jc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1096Jc {
    static final /* synthetic */ boolean k = true;
    private final C0702m a;
    public final C1177Mc b;
    public final com.android.tools.r8.graph.Y0 c;
    public final com.android.tools.r8.graph.Y0 d;
    private final C0684j2 e;
    private final int f;
    private C0684j2 g;
    private C0684j2 h;
    private boolean i;
    private C0729q2 j = null;

    public C1096Jc(com.android.tools.r8.synthesis.P p, C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, Y6 y6) {
        C0722p1 c0722p1A = c0702m.a();
        this.a = c0702m;
        C1177Mc c1177McV = y6.V();
        this.b = c1177McV;
        this.d = c0722p1A.a(p.e(), y6.getType(), c0722p1A.c("CONST"));
        this.c = c0722p1A.a(p.e(), c0722p1A.v1, c0722p1A.c("INITIALIZED"));
        this.e = c0722p1A.a(p.e(), c0722p1A.b(y6.getType(), new C0767v2[0]), c0722p1A.c("get"));
        C0699l2 c0699l2A = c1177McV.a();
        this.g = c0699l2A.u0();
        com.android.tools.r8.graph.I4 i4B = c0702m.g().b(this.g, c0699l2A.f);
        if (!i4B.x() || !i4B.o().r().E0()) {
            this.f = i4B.b((AbstractC0727q0) o5Var.b(), (C0702m<?>) c0702m, c0702m.g()) ? 2 : 3;
            return;
        }
        com.android.tools.r8.graph.I4.c<?> cVarO = i4B.o();
        if (c0699l2A.f && c0702m.Y().Z0()) {
            C0684j2 c0684j2 = this.g;
            this.g = c0684j2.b(C1566Zr.a(c0722p1A, c0684j2.B0()), c0722p1A);
            this.i = false;
        } else {
            if (!k && this.g.B0() != i4B.d().getType()) {
                throw new AssertionError();
            }
            this.i = c0699l2A.f;
        }
        if (b()) {
            this.h = c0722p1A.a(this.g.B0(), c0722p1A.b(this.g.I0(), c0722p1A.Z1, c0722p1A.X1, c0722p1A.m2), this.g.C0());
        } else {
            this.h = this.g;
            C0725p4 accessFlags = cVarO.r().getAccessFlags();
            accessFlags.I();
            accessFlags.C();
        }
        this.f = 1;
        a(p);
    }

    private boolean b() {
        return !this.a.q() && this.a.Y().B0().e(EnumC3891f.O);
    }

    public final Collection a(Y6 y6, InterfaceC1124Kc interfaceC1124Kc, C1230Ob c1230Ob) {
        if (!k) {
            C1177Mc c1177McV = y6.V();
            C1177Mc c1177Mc = this.b;
            c1177McV.getClass();
            if (!com.android.tools.r8.utils.structural.k.a(c1177McV, c1177Mc)) {
                throw new AssertionError();
            }
        }
        int i = this.f;
        if (i == 1) {
            return AbstractC1639am.c(new I7(184, this.e, false));
        }
        return AbstractC1639am.c(new I7(184, (i == 2 ? new Jc$$ExternalSyntheticLambda2() : new Jc$$ExternalSyntheticLambda3()).a(this.a, interfaceC1124Kc, c1230Ob).b().getReference(), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W0 b(com.android.tools.r8.graph.W0 w0) {
        C0725p4 c0725p4P = w0.f.P();
        c0725p4P.I();
        c0725p4P.C();
        com.android.tools.r8.graph.W0 w0A = com.android.tools.r8.graph.W0.Y1().a(this.h).a(c0725p4P).a(w0.e1()).a(w0.s0()).a(w0.h).a(a(w0)).b(w0.I0()).a(w0.Y0()).a();
        w0A.a(this.a, w0);
        return w0A;
    }

    private void a(com.android.tools.r8.synthesis.P p) {
        p.c(AbstractC1639am.a(com.android.tools.r8.graph.T0.d1().a(this.c).a(com.android.tools.r8.graph.Q2.k(4106)).d().a(), com.android.tools.r8.graph.T0.d1().a(this.d).a(com.android.tools.r8.graph.Q2.k(4106)).d().a()));
        com.android.tools.r8.graph.W0.a aVarA = com.android.tools.r8.graph.W0.Y1().a(this.e).a(C0725p4.Q());
        AbstractC1639am abstractC1639amI = AbstractC1639am.i();
        C1507Xl c1507XlG = AbstractC1639am.g();
        M7 m7 = new M7();
        M7 m8 = new M7();
        M7 m9 = new M7();
        M7 m10 = new M7();
        M7 m11 = new M7();
        M7 m12 = new M7();
        c1507XlG.a(new O8(this.c));
        EnumC1076Il enumC1076Il = EnumC1076Il.NE;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        c1507XlG.a(new C3039t7(enumC1076Il, enumC1812d20, m7));
        c1507XlG.a(new X6(p.e(), false));
        c1507XlG.a(new D8(D8.a.Dup));
        EnumC1812d20 enumC1812d21 = EnumC1812d20.OBJECT;
        c1507XlG.a(new R8(enumC1812d21, 0));
        c1507XlG.a(new R7(EnumC3423yF.ENTER));
        c1507XlG.a(m9);
        c1507XlG.a(new O8(this.c));
        c1507XlG.a(new C3039t7(enumC1076Il, enumC1812d20, m8));
        if (!k && !this.b.a().d.g()) {
            throw new AssertionError();
        }
        c1507XlG.a(new C1668b7());
        c1507XlG.a(new C1891e7(this.b.c()));
        c1507XlG.a(new X6(this.b.d(), false));
        c1507XlG.a(new I7(184, this.h, this.i));
        c1507XlG.a(new M6(this.b.d(), false));
        c1507XlG.a(new P8(this.d));
        c1507XlG.a(new C1818d7(1L, enumC1812d20));
        c1507XlG.a(new P8(this.c));
        c1507XlG.a(m8);
        c1507XlG.a(C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.b(p.c().Z1)).a());
        c1507XlG.a(new O7(enumC1812d21, 0));
        EnumC3423yF enumC3423yF = EnumC3423yF.EXIT;
        c1507XlG.a(new R7(enumC3423yF));
        c1507XlG.a(m10);
        c1507XlG.a(new C2899r7(m7));
        c1507XlG.a(m11);
        c1507XlG.a(C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.b(p.c().Z1)).a((InterfaceC3054tM) InterfaceC1348Si.b(p.c().m3)).a());
        c1507XlG.a(new R8(enumC1812d21, 1));
        c1507XlG.a(new O7(enumC1812d21, 0));
        c1507XlG.a(new R7(enumC3423yF));
        c1507XlG.a(m12);
        c1507XlG.a(new O7(enumC1812d21, 1));
        c1507XlG.a(new V8());
        c1507XlG.a(m7);
        c1507XlG.a(new C2284j7());
        c1507XlG.a(new O8(this.d));
        c1507XlG.a(new C3109u8(enumC1812d21));
        p.a((AbstractCollection) AbstractC1639am.c(aVarA.a(new C0753u(p.e(), 3, 2, c1507XlG.a(), AbstractC1639am.a(new W8(m9, m10, AbstractC1639am.c(p.c().m3), AbstractC1639am.c(m11)), new W8(m11, m12, AbstractC1639am.c(p.c().m3), AbstractC1639am.c(m11))), abstractC1639amI, Position.UNKNOWN, C2049g6.a())).c().a()));
    }

    public final C0729q2 a() {
        if (k || this.j != null) {
            return this.j;
        }
        throw new AssertionError();
    }

    public final void a(C0729q2 c0729q2) {
        if (!k && this.j != null) {
            throw new AssertionError();
        }
        this.j = c0729q2;
    }

    public final void a(G7 g7) {
        com.android.tools.r8.graph.o5 o5VarF;
        if (b() && this.f == 1) {
            C0729q2 c0729q2A0 = this.a.c(this.g.B0()).a0();
            com.android.tools.r8.graph.W0 w0A = c0729q2A0.Y().a(this.g, new Function() { // from class: com.android.tools.r8.internal.Jc$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b((com.android.tools.r8.graph.W0) obj);
                }
            });
            if (w0A != null) {
                boolean z = k;
                if (!z && (this.a.Y().f instanceof ClassFileConsumer) && !w0A.a1().z0()) {
                    throw new AssertionError();
                }
                if (!z && this.a.Y().Y0() && !w0A.a1().B0()) {
                    throw new AssertionError();
                }
                o5VarF = w0A.a(c0729q2A0);
                g7.a(this.g, o5VarF);
            } else {
                o5VarF = c0729q2A0.f(this.h);
            }
            boolean z2 = k;
            if (!z2 && o5VarF == null) {
                throw new AssertionError();
            }
            if (!z2 && !o5VarF.e().O1()) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0767v2 a(C0767v2 c0767v2) {
        return c0767v2 == this.a.a().o5 ? this.a.a().Z1 : c0767v2;
    }

    private com.android.tools.r8.graph.W a(com.android.tools.r8.graph.W0 w0) {
        if (!k && this.f != 1) {
            throw new AssertionError();
        }
        if (w0.a1().B0()) {
            return w0.a1();
        }
        C0753u c0753uK = w0.a1().K();
        List<B7> listI0 = c0753uK.I0();
        List<B7> listA = com.android.tools.r8.utils.V1.a((List) listI0, new Function() { // from class: com.android.tools.r8.internal.Jc$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((B7) obj);
            }
        }, (List) listI0);
        return c0753uK.I0() != listA ? new C0753u(w0.J0(), c0753uK.L0(), c0753uK.K0(), listA, c0753uK.Q0(), c0753uK.J0(), Position.UNKNOWN, C2049g6.a()) : c0753uK;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ B7 a(B7 b7) {
        return b7.G() ? b7.i().a(new Function() { // from class: com.android.tools.r8.internal.Jc$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        }) : b7;
    }
}
