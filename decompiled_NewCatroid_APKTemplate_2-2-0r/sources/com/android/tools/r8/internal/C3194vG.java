package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.synthesis.C3851i;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3194vG implements C7 {
    static final /* synthetic */ boolean d = true;
    protected final C0702m a;
    protected final C0722p1 b;
    private final ConcurrentHashMap c = new ConcurrentHashMap();

    C3194vG(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0754u0 c0754u0) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.synthesis.P p) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3851i c3851i) {
    }

    private C0759u2 c(AbstractC0748t0 abstractC0748t0) {
        return this.b.c((abstractC0748t0.getAccessFlags().o() ? "-$$Nest$sm" : "-$$Nest$m") + abstractC0748t0.u().toString());
    }

    final C0684j2 b(AbstractC0735r0 abstractC0735r0, boolean z) {
        int iA = com.android.tools.r8.utils.M.a(!z) + com.android.tools.r8.utils.M.a(!abstractC0735r0.getAccessFlags().o());
        C0767v2[] c0767v2Arr = new C0767v2[iA];
        if (!z) {
            c0767v2Arr[iA - 1] = abstractC0735r0.getType();
        }
        if (!abstractC0735r0.getAccessFlags().o()) {
            c0767v2Arr[0] = abstractC0735r0.t();
        }
        return this.b.a(abstractC0735r0.t(), this.b.b(z ? abstractC0735r0.getType() : this.b.D1, c0767v2Arr), a(abstractC0735r0, z));
    }

    final C0684j2 d(AbstractC0748t0 abstractC0748t0) {
        if (d || !abstractC0748t0.e().C1()) {
            return this.b.a(abstractC0748t0.t(), abstractC0748t0.getAccessFlags().o() ? abstractC0748t0.F() : this.b.c(abstractC0748t0.getReference()), c(abstractC0748t0));
        }
        throw new AssertionError();
    }

    public static C3194vG a(C0702m c0702m) {
        if (c0702m.Y().V()) {
            return null;
        }
        if (c0702m.q()) {
            return new C3194vG(c0702m);
        }
        return new C0919Dd(c0702m);
    }

    final void a(Consumer consumer, Consumer consumer2) {
        C3048tG c3048tGA;
        Set setC = OU.c();
        for (C0729q2 c0729q2 : this.a.f().d()) {
            if (c0729q2.D1() && setC.add(c0729q2.g1()) && (c3048tGA = C3048tG.a(this.a, c0729q2, consumer2)) != null) {
                consumer.accept(c3048tGA);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC0727q0 b(AbstractC0748t0 abstractC0748t0) {
        return (AbstractC0727q0) this.c.get(abstractC0748t0.t());
    }

    static void a(Consumer consumer, Consumer consumer2, C0702m c0702m) {
        C3048tG c3048tGA;
        Set setC = OU.c();
        for (C0729q2 c0729q2 : c0702m.f().d()) {
            if (c0729q2.D1() && setC.add(c0729q2.g1()) && (c3048tGA = C3048tG.a(c0702m, c0729q2, consumer2)) != null) {
                consumer.accept(c3048tGA);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.graph.o5 a(C3117uG c3117uG, com.android.tools.r8.graph.m5 m5Var, boolean z) {
        return D1.a(c3117uG.a, m5Var, z);
    }

    @Override // com.android.tools.r8.internal.C7
    public final void a(final G7 g7, final C3124uN c3124uN, final com.android.tools.r8.graph.o5 o5Var) {
        o5Var.e().a1().K().I0().forEach(new Consumer() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, g7, c3124uN, (B7) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var, G7 g7, C3124uN c3124uN, B7 b7) {
        if (b7.E()) {
            com.android.tools.r8.graph.Y0 field = b7.h().getField();
            if (a(field, o5Var)) {
                a(field, b7.h().D(), o5Var, g7, c3124uN);
                return;
            }
            return;
        }
        if (b7.H()) {
            C0684j2 c0684j2T = b7.l().T();
            if (a(c0684j2T, o5Var)) {
                a(c0684j2T, o5Var, g7, c3124uN);
                return;
            }
            return;
        }
        if (b7 instanceof J7) {
            C0721p0 c0721p0T = b7.n().T();
            C0702m c0702m = this.a;
            JA jaB = JA.b(c0721p0T, c0702m, c0702m.g(), o5Var);
            if (jaB != null) {
                AbstractC0642e2 abstractC0642e2 = jaB.f.e;
                if (a(abstractC0642e2, o5Var)) {
                    if (!d && !abstractC0642e2.y0()) {
                        throw new AssertionError();
                    }
                    a(abstractC0642e2.v0(), o5Var, g7, c3124uN);
                }
            }
        }
    }

    private void a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var, InterfaceC3345xG interfaceC3345xG, C3124uN c3124uN) {
        final C3117uG c3117uGA = a(c0684j2, o5Var, new Function() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((AbstractC0748t0) obj);
            }
        });
        if (c3117uGA != null && c3117uGA.b.X() && c3117uGA.b.a().b(c3117uGA.a) == null) {
            final com.android.tools.r8.graph.o5 o5VarG0 = ((AbstractC0748t0) c3117uGA.b).g0();
            com.android.tools.r8.graph.o5 o5VarA = c3124uN.a(new Supplier() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.a(o5VarG0, c3117uGA);
                }
            }, c3117uGA.a);
            if (o5VarG0.e().C1()) {
                interfaceC3345xG.a(o5VarG0, o5VarA, ((AbstractC0727q0) this.c.get(o5VarG0.t())).a0(), o5Var);
            } else {
                interfaceC3345xG.a(o5VarG0, o5VarA, o5Var);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.o5 a(com.android.tools.r8.graph.o5 o5Var, C3117uG c3117uG) {
        if (o5Var.e().C1()) {
            return D1.a(c3117uG.a, o5Var, this.b);
        }
        return D1.b(c3117uG.a, o5Var, this.b);
    }

    private C3117uG a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var, Function function) {
        C0684j2 c0684j2D;
        if (!c0684j2.B0().O0()) {
            return null;
        }
        C0702m c0702m = this.a;
        c0702m.getClass();
        AbstractC0748t0 abstractC0748t0A = c0684j2.a(c0702m.b(o5Var.a(), c0684j2.e));
        if (abstractC0748t0A == null || !a(abstractC0748t0A, o5Var)) {
            return null;
        }
        if (abstractC0748t0A.e().C1()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) function.apply(abstractC0748t0A);
            if (!d && !abstractC0748t0A.e().C1()) {
                throw new AssertionError();
            }
            C0722p1 c0722p1 = this.b;
            C0737r2 c0737r2F = abstractC0748t0A.F();
            C0767v2 type = abstractC0727q0.getType();
            c0722p1.getClass();
            int size = c0737r2F.f.size();
            C0767v2[] c0767v2Arr = new C0767v2[size + 1];
            C0800x2 c0800x2 = c0737r2F.f;
            System.arraycopy(c0800x2.a, 0, c0767v2Arr, 0, c0800x2.size());
            c0767v2Arr[size] = type;
            c0684j2D = abstractC0748t0A.getReference().a(c0722p1.b(c0737r2F.e, c0767v2Arr), this.b);
        } else {
            c0684j2D = d(abstractC0748t0A);
        }
        return new C3117uG(c0684j2D, abstractC0748t0A);
    }

    private void a(com.android.tools.r8.graph.Y0 y0, final boolean z, com.android.tools.r8.graph.o5 o5Var, InterfaceC3345xG interfaceC3345xG, C3124uN c3124uN) {
        C0702m c0702m = this.a;
        c0702m.getClass();
        AbstractC0735r0 abstractC0735r0A = y0.a(c0702m.b(o5Var.a(), y0.e));
        final C3117uG c3117uG = (abstractC0735r0A == null || !a(abstractC0735r0A, o5Var)) ? null : new C3117uG(b(abstractC0735r0A, z), abstractC0735r0A);
        if (c3117uG != null && c3117uG.b.X() && c3117uG.b.a().b(c3117uG.a) == null) {
            final com.android.tools.r8.graph.m5 m5VarQ = ((AbstractC0735r0) c3117uG.b).Q();
            com.android.tools.r8.graph.o5 o5VarA = c3124uN.a(new Supplier() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C3194vG.a(c3117uG, m5VarQ, z);
                }
            }, c3117uG.a);
            if (z) {
                interfaceC3345xG.a(m5VarQ, o5VarA, o5Var);
            } else {
                interfaceC3345xG.b(m5VarQ, o5VarA, o5Var);
            }
        }
    }

    public final boolean a(AbstractC0642e2 abstractC0642e2, com.android.tools.r8.graph.o5 o5Var) {
        if (!o5Var.a().D1() || !abstractC0642e2.B0().O0()) {
            return false;
        }
        C0702m c0702m = this.a;
        c0702m.getClass();
        AbstractC0741s0 abstractC0741s0A = abstractC0642e2.a(c0702m.b(o5Var.a(), abstractC0642e2.e));
        return abstractC0741s0A != null && a(abstractC0741s0A, o5Var);
    }

    public static boolean a(AbstractC0741s0 abstractC0741s0, AbstractC0748t0 abstractC0748t0) {
        return abstractC0741s0.getAccessFlags().j() && abstractC0741s0.t() != abstractC0748t0.t() && abstractC0741s0.a().D1() && abstractC0741s0.a().g1() == abstractC0748t0.a().g1();
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        if (b7.E()) {
            if (a(b7.h().getField(), o5Var)) {
                return a(b7.h());
            }
            return C3224ve.c();
        }
        if (b7.H()) {
            if (a(b7.l().T(), o5Var)) {
                return a(b7.l());
            }
            return C3224ve.c();
        }
        return C3224ve.c();
    }

    private C3224ve a(final AbstractC2206i7 abstractC2206i7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(abstractC2206i7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(AbstractC2206i7 abstractC2206i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        com.android.tools.r8.graph.Y0 field = abstractC2206i7.getField();
        boolean zD = abstractC2206i7.D();
        C0702m c0702m = this.a;
        c0702m.getClass();
        AbstractC0735r0 abstractC0735r0A = field.a(c0702m.b(o5Var.a(), field.e));
        C3117uG c3117uG = (abstractC0735r0A == null || !a(abstractC0735r0A, o5Var)) ? null : new C3117uG(b(abstractC0735r0A, zD), abstractC0735r0A);
        boolean z = d;
        if (!z && c3117uG == null) {
            throw new AssertionError();
        }
        if (!z) {
            AbstractC0735r0 abstractC0735r0 = (AbstractC0735r0) c3117uG.b;
            abstractC0735r0.getClass();
            if ((abstractC0735r0 instanceof com.android.tools.r8.graph.m5) && ((AbstractC0735r0) c3117uG.b).a().b(c3117uG.a) == null) {
                throw new AssertionError();
            }
        }
        return AbstractC1639am.c(new I7(184, c3117uG.a, ((AbstractC0735r0) c3117uG.b).a().isInterface()));
    }

    private C3224ve a(final I7 i7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(i7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        C3117uG c3117uGA = a(i7.T(), o5Var, new Function() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((AbstractC0748t0) obj);
            }
        });
        boolean z = d;
        if (!z && c3117uGA == null) {
            throw new AssertionError();
        }
        if (!z) {
            AbstractC0748t0 abstractC0748t0 = (AbstractC0748t0) c3117uGA.b;
            abstractC0748t0.getClass();
            if ((abstractC0748t0 instanceof com.android.tools.r8.graph.o5) && ((AbstractC0748t0) c3117uGA.b).a().b(c3117uGA.a) == null) {
                throw new AssertionError();
            }
        }
        if (((AbstractC0748t0) c3117uGA.b).e().C1()) {
            if (!z && i7.V()) {
                throw new AssertionError();
            }
            ac.a(1);
            return AbstractC1639am.a(new C1668b7(), new I7(183, c3117uGA.a, false));
        }
        return AbstractC1639am.c(new I7(184, c3117uGA.a, i7.V()));
    }

    private C0759u2 a(AbstractC0735r0 abstractC0735r0, boolean z) {
        String str;
        if (z && !abstractC0735r0.getAccessFlags().o()) {
            str = "-$$Nest$fget";
        } else if (z) {
            str = "-$$Nest$sfget";
        } else if (!abstractC0735r0.getAccessFlags().o()) {
            str = "-$$Nest$fput";
        } else {
            str = "-$$Nest$sfput";
        }
        return this.b.c(str + abstractC0735r0.u().toString());
    }

    final AbstractC0727q0 a(final AbstractC0748t0 abstractC0748t0) {
        if (d || abstractC0748t0.e().C1()) {
            return (AbstractC0727q0) this.c.computeIfAbsent(abstractC0748t0.t(), new Function() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(abstractC0748t0, (C0767v2) obj);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC0727q0 a(AbstractC0748t0 abstractC0748t0, C0767v2 c0767v2) {
        abstractC0748t0.getClass();
        if (abstractC0748t0 instanceof com.android.tools.r8.graph.o5) {
            return this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda10
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.q;
                }
            }, abstractC0748t0.g0().a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3194vG.a((com.android.tools.r8.synthesis.P) obj);
                }
            });
        }
        if (!d && !(abstractC0748t0 instanceof com.android.tools.r8.graph.S)) {
            throw new AssertionError();
        }
        return this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.q;
            }
        }, (com.android.tools.r8.graph.T) abstractC0748t0.m0().a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3194vG.a((C3851i) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.vG$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3194vG.a((C0754u0) obj);
            }
        });
    }
}
