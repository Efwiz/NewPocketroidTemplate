package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C1230Ob;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1818d7;
import com.android.tools.r8.internal.C2049g6;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2284j7;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2561ml;
import com.android.tools.r8.internal.C3109u8;
import com.android.tools.r8.internal.C3446yc;
import com.android.tools.r8.internal.D6;
import com.android.tools.r8.internal.D8;
import com.android.tools.r8.internal.EnumC1812d20;
import com.android.tools.r8.internal.EnumC2523mE;
import com.android.tools.r8.internal.I7;
import com.android.tools.r8.internal.InterfaceC1348Si;
import com.android.tools.r8.internal.InterfaceC3054tM;
import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.O7;
import com.android.tools.r8.internal.R8;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.TE;
import com.android.tools.r8.internal.V7;
import com.android.tools.r8.internal.V8;
import com.android.tools.r8.internal.W7;
import com.android.tools.r8.internal.W8;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.V1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G0 {
    private final C0702m a;
    private final ArrayList b = new ArrayList();
    private final com.android.tools.r8.androidapi.b c;

    public G0(C0702m c0702m, com.android.tools.r8.androidapi.b bVar) {
        this.a = c0702m;
        this.c = bVar;
    }

    public final ArrayList a() {
        return this.b;
    }

    public final void a(final C2409kl c2409kl, final TE te, final C1230Ob c1230Ob) {
        C0722p1 c0722p1A = this.a.a();
        C2561ml c2561ml = new C2561ml(c2409kl);
        IdentityHashMap identityHashMap = new IdentityHashMap();
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            abstractC2412ko.getClass();
            if ((abstractC2412ko instanceof C1721bt) && abstractC2412ko.g0().S2() == c0722p1A.D5.b) {
                C1721bt c1721btG0 = abstractC2412ko.g0();
                S10 s10B = c1721btG0.b();
                if (s10B.Z() == 1 && !s10B.C() && s10B.f0().f2() && s10B.f0().i0().S2() == c0722p1A.D5.d) {
                    boolean z = false;
                    S10 s10M = ((S10) ((ArrayList) c1721btG0.h1()).get(0)).m();
                    if (!s10M.i()) {
                        AbstractC2412ko abstractC2412ko2 = s10M.b;
                        abstractC2412ko2.getClass();
                        if (abstractC2412ko2 instanceof C3446yc) {
                            C3446yc c3446ycA = s10M.n().A();
                            if (!((C3733i) this.a.f()).a((AbstractC0744s2) c3446ycA.K2()) && this.a.j().a().contains(c3446ycA.K2()) && !this.a.j().a(this.a, c3446ycA.K2()) && !((S10) ((ArrayList) c1721btG0.h1()).get(1)).i()) {
                                C2115gt c2115gtI0 = ((S10) ((ArrayList) c1721btG0.h1()).get(1)).b.i0();
                                if (((S10) ((ArrayList) c1721btG0.h1()).get(1)).w().E() || (c2115gtI0 != null && ((ArrayList) c2115gtI0.h1()).size() == 1 && c2115gtI0.U2().m().I() && c2115gtI0.U2().m().n().A().K2() == c3446ycA.K2())) {
                                    AbstractC1639am abstractC1639amA = this.a.j().a(c3446ycA.K2());
                                    final ArrayList arrayList = new ArrayList(abstractC1639amA.size());
                                    Iterator it = abstractC1639amA.iterator();
                                    while (it.hasNext()) {
                                        AbstractC0727q0 abstractC0727q0C = this.a.c((C0767v2) it.next());
                                        if (abstractC0727q0C == null) {
                                            z = true;
                                        }
                                        arrayList.add(abstractC0727q0C);
                                    }
                                    if (!z) {
                                        new F0(c2409kl, c2561ml, c1721btG0).a(c2115gtI0, ((W0) identityHashMap.computeIfAbsent(c3446ycA.K2(), new Function() { // from class: com.android.tools.r8.ir.optimize.G0$$ExternalSyntheticLambda4
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return this.f$0.a(arrayList, te, c1230Ob, c2409kl, (C0767v2) obj);
                                            }
                                        })).getReference());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public W0 a(List list, TE te, C1230Ob c1230Ob, C2409kl c2409kl, C0767v2 c0767v2) {
        W0 w0A = a(c0767v2, list, te, c1230Ob);
        if (this.a.Y().f instanceof ClassFileConsumer) {
            w0A.b(c2409kl.h().e().Z0());
        }
        return w0A;
    }

    private W0 a(final C0767v2 c0767v2, final List list, TE te, C1230Ob c1230Ob) {
        final C0737r2 c0737r2B = this.a.a().b(this.a.a().r5, new C0767v2[0]);
        o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.ir.optimize.G0$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.L;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.ir.optimize.G0$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2B, list, c0767v2, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        synchronized (this.b) {
            this.b.add(o5VarB);
        }
        te.b().c(o5VarB, c1230Ob.b());
        return o5VarB.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0737r2 c0737r2, final List list, final C0767v2 c0767v2, com.android.tools.r8.synthesis.H h) {
        com.android.tools.r8.synthesis.H hB = h.a(C0725p4.Q()).a(c0737r2).b(this.a.o());
        com.android.tools.r8.androidapi.b bVar = this.c;
        List listA = V1.a((Collection) list, new Function() { // from class: com.android.tools.r8.ir.optimize.G0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0727q0) obj).d;
            }
        });
        bVar.getClass();
        hB.a(bVar.a(listA, com.android.tools.r8.androidapi.h.f())).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.ir.optimize.G0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0767v2, list, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0767v2 c0767v2, List list, C0684j2 c0684j2) {
        C0722p1 c0722p1A = this.a.a();
        C1507Xl c1507XlG = AbstractC1639am.g();
        M7 m7 = new M7();
        M7 m8 = new M7();
        char c = 0;
        c1507XlG.a((Object[]) new B7[]{m7, new C1818d7(list.size(), EnumC1812d20.INT), new W7(c0722p1A.a(1, c0767v2))});
        int i = 0;
        while (i < list.size()) {
            B7[] b7Arr = new B7[6];
            D8.a aVar = D8.a.Dup;
            b7Arr[c] = new D8(aVar);
            b7Arr[1] = new C1818d7(i, EnumC1812d20.INT);
            b7Arr[2] = new V7(null, ((AbstractC0727q0) list.get(i)).d);
            b7Arr[3] = new D8(aVar);
            b7Arr[4] = new I7(183, c0722p1A.a(((AbstractC0727q0) list.get(i)).d, c0722p1A.b(c0722p1A.D1, new C0767v2[0]), c0722p1A.Y0), false);
            b7Arr[5] = new D6(EnumC2523mE.OBJECT);
            c1507XlG.a((Object[]) b7Arr);
            i++;
            c = 0;
        }
        EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
        c1507XlG.a((Object[]) new B7[]{new I7(184, c0722p1A.J4.a, false), new I7(185, c0722p1A.a(c0722p1A.K2, c0722p1A.b(c0722p1A.r5, new C0767v2[0]), c0722p1A.c("iterator")), true), m8, new C3109u8(enumC1812d20)});
        M7 m9 = new M7();
        c1507XlG.a((Object[]) new B7[]{m9, C2284j7.T().a((InterfaceC3054tM) InterfaceC1348Si.b(c0722p1A.m3)).a(), new R8(enumC1812d20, 0), new V7(null, c0722p1A.J2), new D8(D8.a.Dup), new O7(enumC1812d20, 0), new I7(182, c0722p1A.n4.b, false), new O7(enumC1812d20, 0), new I7(183, c0722p1A.a(c0722p1A.J2, c0722p1A.b(c0722p1A.D1, c0722p1A.X1, c0722p1A.m3), c0722p1A.Y0), false), new V8()});
        return new C0753u(null, 5, 1, c1507XlG.a(), AbstractC1639am.c(new W8(m7, m8, AbstractC1639am.c(c0722p1A.m3), AbstractC1639am.c(m9))), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }
}
