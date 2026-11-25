package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0719o4;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.utils.AbstractC3896g;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2661o2 implements C7 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final com.android.tools.r8.androidapi.b b;
    private final C0800x2 c;

    public C2661o2(C0702m c0702m, com.android.tools.r8.androidapi.b bVar) {
        this.a = c0702m;
        this.b = bVar;
        this.c = C0800x2.a(new C0767v2[]{c0702m.a().Z1});
    }

    private com.android.tools.r8.androidapi.h b(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        C3979v2 c3979v2A;
        if (o5Var.e().N0()) {
            return this.a.o();
        }
        final AbstractC0744s2 abstractC0744s2A = a(b7);
        if (abstractC0744s2A == null || !abstractC0744s2A.C().O0()) {
            return this.a.o();
        }
        AbstractC0727q0 abstractC0727q0C = this.a.c(abstractC0744s2A.C());
        if (abstractC0727q0C == null) {
            return this.a.o();
        }
        if (abstractC0744s2A instanceof C0767v2) {
            c3979v2A = C3979v2.a(abstractC0727q0C, this.b.a(abstractC0744s2A, com.android.tools.r8.androidapi.h.f()));
        } else {
            C0702m c0702m = this.a;
            c3979v2A = AbstractC3896g.a(c0702m, c0702m.g(), abstractC0727q0C, abstractC0744s2A.u0());
        }
        com.android.tools.r8.androidapi.h hVar = (com.android.tools.r8.androidapi.h) c3979v2A.b();
        if (this.a.o().c(hVar) || ((hVar.y() && hVar.b0().a().b(EnumC3891f.G)) || hVar.H())) {
            return this.a.o();
        }
        boolean z = d;
        if (!z && !hVar.y()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) c3979v2A.a();
        if (abstractC0727q0 == null || !abstractC0727q0.f0()) {
            if (z) {
                return this.a.o();
            }
            throw new AssertionError("When computed a known api level we should always have a library class");
        }
        if (AbstractC3896g.b(o5Var.a(), hVar)) {
            return this.a.o();
        }
        if (abstractC0727q0.isInterface() || b7.B() || (b7 instanceof A7) || (b7 instanceof X6)) {
            return hVar;
        }
        com.android.tools.r8.graph.U0 u0A = a(abstractC0727q0.d0(), abstractC0744s2A.y0() ? new Function() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2661o2.a(abstractC0744s2A, (AbstractC0727q0) obj);
            }
        } : new Function() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2661o2.b(abstractC0744s2A, (AbstractC0727q0) obj);
            }
        });
        return (u0A == null || !u0A.getAccessFlags().m()) ? this.a.o() : hVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W c(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        return C1716bo.a(this.a, c0684j2.B0(), abstractC0727q0.getType()).b();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(final com.android.tools.r8.graph.o5 o5Var, final B7 b7) {
        final com.android.tools.r8.androidapi.h hVarB = b(o5Var, b7);
        return this.a.o().c(hVarB) ? C3224ve.c() : C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(b7, hVarB, o5Var, interfaceC1374Ti, ac, g7, o5Var2, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(B7 b7, com.android.tools.r8.androidapi.h hVar, com.android.tools.r8.graph.o5 o5Var, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var2, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C1314Rb c1314RbA = c1230Ob.a();
        if (!d && !b7.H() && !b7.E() && !b7.B() && !(b7 instanceof A7) && !(b7 instanceof X6)) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.o5 o5VarA = a(c1314RbA, b7, hVar, c0722p1, o5Var);
        g7.c(o5VarA, o5Var);
        return AbstractC1639am.c(new I7(184, o5VarA.getReference(), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.U0 a(AbstractC0744s2 abstractC0744s2, AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0.c(abstractC0744s2.v0());
    }

    private static AbstractC0744s2 a(B7 b7) {
        if (b7.E()) {
            return b7.h().getField();
        }
        if (b7.B()) {
            return b7.b().getType();
        }
        if (b7 instanceof A7) {
            return b7.k().getType();
        }
        if (b7 instanceof X6) {
            return b7.c().getType();
        }
        if (!b7.H() || b7.l().J()) {
            return null;
        }
        return b7.l().T();
    }

    private com.android.tools.r8.graph.U0 a(C0635d2 c0635d2, final Function function) {
        AbstractC0727q0 abstractC0727q0;
        com.android.tools.r8.utils.D3 d3E;
        com.android.tools.r8.graph.U0 u0 = (com.android.tools.r8.graph.U0) function.apply(c0635d2);
        if (u0 != null) {
            return u0;
        }
        C0680j c0680jG = this.a.g();
        com.android.tools.r8.utils.G3 g3 = new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return C2661o2.a(function, (C0767v2) obj, (AbstractC0727q0) obj2, (AbstractC0727q0) obj3);
            }
        };
        c0680jG.getClass();
        while (true) {
            if (abstractC0727q0 != null && abstractC0727q0.n1() != null) {
                AbstractC0727q0 abstractC0727q0C = c0680jG.c(abstractC0727q0.n1());
                d3E = (com.android.tools.r8.utils.D3) g3.a(abstractC0727q0.n1(), abstractC0727q0C, abstractC0727q0);
                if (d3E.f()) {
                    abstractC0727q0 = c0635d2;
                    break;
                }
                abstractC0727q0 = c0635d2;
                abstractC0727q0 = abstractC0727q0C;
            } else {
                abstractC0727q0 = c0635d2;
                d3E = com.android.tools.r8.utils.D3.e();
                break;
            }
        }
        if (d3E.f()) {
            return (com.android.tools.r8.graph.U0) d3E.a().i();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.U0 b(AbstractC0744s2 abstractC0744s2, AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0.a(abstractC0744s2.t0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W b(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        return C3516zc.a(this.a, c0684j2.B0(), abstractC0727q0.getType()).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Function function, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        com.android.tools.r8.graph.U0 u0 = (com.android.tools.r8.graph.U0) function.apply(abstractC0727q0);
        if (u0 != null) {
            return com.android.tools.r8.utils.D3.a(u0);
        }
        return com.android.tools.r8.utils.D3.e();
    }

    private com.android.tools.r8.graph.o5 a(C1314Rb c1314Rb, final B7 b7, final com.android.tools.r8.androidapi.h hVar, final C0722p1 c0722p1, final com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0744s2 abstractC0744s2A = a(b7);
        boolean z = d;
        if (!z && abstractC0744s2A == null) {
            throw new AssertionError();
        }
        final AbstractC0727q0 abstractC0727q0C = this.a.c(abstractC0744s2A.C());
        if (!z && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        return this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return C2661o2.a(abstractC0727q0C, l);
            }
        }, c1314Rb, this.a, new Consumer() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(hVar, b7, c0722p1, o5Var, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.synthesis.L.b a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.synthesis.L l) {
        if (abstractC0727q0.J1()) {
            return l.R;
        }
        return l.S;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.androidapi.h hVar, B7 b7, C0722p1 c0722p1, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.synthesis.H h) {
        h.a((C0725p4) ((C0719o4) ((C0719o4) ((C0719o4) C0725p4.O().c(true)).g()).f()).c().a()).b(hVar).a(hVar);
        if (b7.H()) {
            a(h, b7.l(), c0722p1);
            return;
        }
        if (b7.B()) {
            a(h, b7.b(), c0722p1);
            return;
        }
        if (b7 instanceof A7) {
            a(h, b7.k(), c0722p1);
        } else if (b7 instanceof X6) {
            a(h, b7.c(), c0722p1);
        } else {
            a(h, b7.h(), c0722p1, o5Var);
        }
    }

    private void a(com.android.tools.r8.synthesis.H h, I7 i7, final C0722p1 c0722p1) {
        C0737r2 c0737r2H0;
        com.android.tools.r8.graph.W0 w0C;
        final C0684j2 c0684j2T = i7.T();
        final AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2T.B0());
        boolean z = d;
        if (!z && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        final boolean z2 = i7.L() || i7.I();
        if (!z && (w0C = abstractC0727q0C.c(c0684j2T)) != null && w0C.S1() != z2) {
            throw new AssertionError();
        }
        c0722p1.getClass();
        if (z2) {
            c0737r2H0 = c0722p1.a(c0684j2T.h, c0684j2T.e);
        } else {
            c0737r2H0 = c0684j2T.H0();
        }
        h.a(c0737r2H0).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C2661o2.a(z2, c0722p1, c0684j2T, abstractC0727q0C, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W a(boolean z, C0722p1 c0722p1, C0684j2 c0684j2, AbstractC0727q0 abstractC0727q0, C0684j2 c0684j3) {
        if (z) {
            return C1071Ii.a(c0722p1).d(c0684j2, abstractC0727q0.isInterface()).b(c0684j2).a();
        }
        return C1071Ii.a(c0722p1).b(c0684j2, abstractC0727q0.isInterface()).d(c0684j2).a();
    }

    private void a(com.android.tools.r8.synthesis.H h, final AbstractC2206i7 abstractC2206i7, C0722p1 c0722p1, final com.android.tools.r8.graph.o5 o5Var) {
        final com.android.tools.r8.graph.Y0 field = abstractC2206i7.getField();
        AbstractC0727q0 abstractC0727q0C = this.a.c(field.B0());
        if (!d && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        final boolean z = (abstractC2206i7 instanceof C3482z7) || (abstractC2206i7 instanceof C3413y7);
        C0767v2 type = abstractC2206i7.D() ? field.getType() : c0722p1.D1;
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(abstractC0727q0C.getType());
        }
        if (abstractC2206i7.F()) {
            arrayList.add(field.getType());
        }
        h.a(c0722p1.a(type, arrayList)).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C2661o2.a(z, field, abstractC2206i7, o5Var, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W a(boolean z, final com.android.tools.r8.graph.Y0 y0, AbstractC2206i7 abstractC2206i7, com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2) {
        return C0857Bh.b().a(z, new Consumer() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C0857Bh) obj).a(y0);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C0857Bh) obj).b(y0);
            }
        }).a(abstractC2206i7.D(), new D1$$ExternalSyntheticLambda0(), new D1$$ExternalSyntheticLambda1()).a(o5Var.getReference()).a();
    }

    private void a(com.android.tools.r8.synthesis.H h, M6 m6, C0722p1 c0722p1) {
        final AbstractC0727q0 abstractC0727q0C = this.a.c(m6.getType());
        if (!d && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        h.a(c0722p1.a(abstractC0727q0C.getType(), this.c)).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(abstractC0727q0C, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        return C3110u9.a(this.a, c0684j2.B0(), abstractC0727q0.getType()).b();
    }

    private void a(com.android.tools.r8.synthesis.H h, A7 a7, C0722p1 c0722p1) {
        final AbstractC0727q0 abstractC0727q0C = this.a.c(a7.getType());
        if (!d && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        h.a(c0722p1.a(c0722p1.v1, this.c)).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.c(abstractC0727q0C, c0684j2);
            }
        });
    }

    private void a(com.android.tools.r8.synthesis.H h, X6 x6, C0722p1 c0722p1) {
        final AbstractC0727q0 abstractC0727q0C = this.a.c(x6.getType());
        if (!d && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        h.a(c0722p1.b(c0722p1.m2, new C0767v2[0])).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.o2$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.b(abstractC0727q0C, c0684j2);
            }
        });
    }
}
