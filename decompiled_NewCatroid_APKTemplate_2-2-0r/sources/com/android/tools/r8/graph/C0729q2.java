package com.android.tools.r8.graph;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.dex.B$$ExternalSyntheticLambda1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1745cA;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.InterfaceC2743oz;
import com.android.tools.r8.internal.InterfaceC3010sj;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: renamed from: com.android.tools.r8.graph.q2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0729q2 extends AbstractC0727q0 implements InterfaceC0694k5, p5, Supplier<C0729q2>, com.android.tools.r8.utils.structural.x<C0729q2> {
    public static final C0729q2[] A = new C0729q2[0];
    static final /* synthetic */ boolean B = true;
    private final ProgramResource.Kind t;
    private Z8 u;
    private boolean v;
    private InterfaceC2743oz w;
    private AbstractC3938n2 x;
    private final a y;
    private com.android.tools.r8.synthesis.F z;

    /* JADX INFO: renamed from: com.android.tools.r8.graph.q2$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface a {
        long a(C0729q2 c0729q2);
    }

    public C0729q2(C0767v2 c0767v2, ProgramResource.Kind kind, Origin origin, E e, C0767v2 c0767v3, C0800x2 c0800x2, C0759u2 c0759u2, W4 w4, List list, List list2, List list3, O2 o2, List list4, C0706m3.b bVar, C0658g0 c0658g0, T0[] t0Arr, T0[] t0Arr2, C0792w4.a aVar, boolean z, a aVar2, com.android.tools.r8.synthesis.F f) {
        super(c0759u2, c0800x2, e, c0767v3, c0767v2, t0Arr, t0Arr2, aVar, w4, list, list2, list3, o2, list4, bVar, c0658g0, origin, z);
        this.u = null;
        this.v = false;
        this.w = AbstractC1745cA.c();
        this.x = AbstractC3938n2.g();
        boolean z2 = B;
        if (!z2 && aVar2 == null) {
            throw new AssertionError();
        }
        if (!z2 && c0658g0 == null) {
            throw new AssertionError();
        }
        this.t = kind;
        this.y = aVar2;
        this.z = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, com.android.tools.r8.dex.L l, m5 m5Var) {
        m5Var.getReference().a(c0702m, l);
        T0 t0E = m5Var.e();
        for (C0633d0 c0633d0 : t0E.s0().c) {
            c0633d0.a(c0702m, l);
        }
        if (t0E.E0() && t0E.Z0()) {
            t0E.Y0().a(c0702m, l);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean i(C0729q2 c0729q2) {
        return c0729q2.r == C0706m3.b.f();
    }

    @Override // com.android.tools.r8.graph.O0, com.android.tools.r8.graph.InterfaceC0593a0
    public final C0767v2 C() {
        return getType();
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    public final boolean I1() {
        return false;
    }

    public final void Q1() {
        this.w = AbstractC1745cA.c();
    }

    public final Iterable R1() {
        return AbstractC2734ot.a((Iterable) V0(), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda34
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.e((W0) obj);
            }
        });
    }

    public long S1() {
        return this.y.a(this);
    }

    public final a T1() {
        return this.y;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public Z8 U1() {
        return this.u;
    }

    public final InterfaceC2743oz V1() {
        return this.w;
    }

    public final ProgramResource.Kind W1() {
        return this.t;
    }

    public final o5 X1() {
        W0 w0Y0 = Y0();
        if (w0Y0 != null) {
            return new o5(this, w0Y0);
        }
        return null;
    }

    public o5 Y1() {
        W0 w0A = a(C0767v2.g);
        if (w0A != null) {
            return new o5(this, w0A);
        }
        return null;
    }

    public final boolean Z1() {
        return this.u != null;
    }

    @Override // com.android.tools.r8.graph.O0, com.android.tools.r8.graph.InterfaceC0593a0
    public C0729q2 a0() {
        return this;
    }

    public final boolean a2() {
        boolean zG;
        if (s0().isEmpty()) {
            C0792w4 c0792w4 = this.k;
            synchronized (c0792w4) {
                zG = c0792w4.g();
            }
            if (!zG && !a(this.j)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0, com.android.tools.r8.graph.InterfaceC0593a0
    public final AbstractC0727q0 b() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0, com.android.tools.r8.graph.InterfaceC0593a0
    public final C0729q2 b() {
        return this;
    }

    public final boolean b2() {
        return this.j.h() > 0;
    }

    public final boolean c2() {
        return this.k.l() > 0;
    }

    final boolean d2() {
        boolean zG;
        C0792w4 c0792w4 = this.k;
        synchronized (c0792w4) {
            zG = c0792w4.g();
        }
        return (zG || a(this.j)) ? false : true;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final O0 e() {
        return this;
    }

    @Override // com.android.tools.r8.graph.O0, com.android.tools.r8.graph.InterfaceC0593a0
    public final boolean e0() {
        return true;
    }

    public final boolean e2() {
        return this.v;
    }

    public final boolean f(C0702m c0702m) {
        boolean z;
        if (this.x.e()) {
            C0722p1 c0722p1A = c0702m.a();
            Iterator it = L1().iterator();
            loop0: while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                }
                for (C0633d0 c0633d0 : ((U0) it.next()).s0().c) {
                    if (c0633d0.b.a == c0722p1A.l5) {
                        z = true;
                        break loop0;
                    }
                }
            }
            this.x = AbstractC3938n2.a(z);
        }
        return this.x.d();
    }

    public final boolean f2() {
        return this.t == ProgramResource.Kind.CF;
    }

    public final boolean g2() {
        return this.t == ProgramResource.Kind.DEX;
    }

    @Override // java.util.function.Supplier
    public final C0729q2 get() {
        return this;
    }

    @Override // com.android.tools.r8.graph.l5
    public final InterfaceC0593a0 getContext() {
        return this;
    }

    public final Iterable h2() {
        return n(new q2$$ExternalSyntheticLambda9());
    }

    public final void i2() {
        this.v = true;
    }

    public final void j(final Consumer consumer) {
        e(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda35
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(consumer, (T0) obj);
            }
        });
    }

    public final com.android.tools.r8.synthesis.F j2() {
        com.android.tools.r8.synthesis.F f = this.z;
        this.z = null;
        return f;
    }

    public final void k(Consumer consumer) {
        e(consumer, CM.b());
        l((Consumer<? super o5>) consumer);
    }

    public final Iterable k2() {
        return AbstractC2734ot.a((Iterable) P1(), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda27
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.g((W0) obj);
            }
        });
    }

    public void l(Consumer<? super o5> consumer) {
        g(consumer, CM.b());
    }

    public final void m(Consumer consumer) {
        d(consumer, new Predicate() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((W0) obj).E0();
            }
        });
    }

    public final Iterable n(Predicate predicate) {
        return AbstractC2734ot.a(b(predicate), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda32
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.f((W0) obj);
            }
        });
    }

    public final Iterable o(Predicate predicate) {
        return AbstractC2734ot.a((Iterable) m((Predicate<? super W0>) predicate), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.h((W0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0729q2.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public String r0() {
        return this.d.r0();
    }

    public final String toString() {
        return this.d.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Consumer consumer, T0 t0) {
        consumer.accept(new m5(t0, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ o5 h(W0 w0) {
        return new o5(this, w0);
    }

    public final void d(final Consumer consumer, Predicate predicate) {
        this.k.a(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(consumer, (W0) obj);
            }
        }, predicate);
    }

    public final void g(final Consumer consumer, Predicate predicate) {
        this.k.b(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(consumer, (W0) obj);
            }
        }, predicate);
    }

    public final void i(Consumer consumer) {
        if (p1()) {
            W0 w0Y0 = Y0();
            consumer.accept(w0Y0 != null ? new o5(this, w0Y0) : null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        W0 w0E = o5Var.e();
        if (!o5.e && w0E.J1()) {
            throw new AssertionError();
        }
        C0684j2 c0684j2V = o5Var.getReference();
        if (c0684j2V.b(c0702m, l)) {
            C0759u2 c0759u2A = c0702m.z().a(c0684j2V);
            c0759u2A.getClass();
            l.a(c0759u2A);
        }
        if (w0E.q1()) {
            w0E.a1().u0().a(c0702m, l, c2363kB, o5Var);
        }
        for (C0633d0 c0633d0 : w0E.s0().c) {
            c0633d0.a(c0702m, l);
        }
        w0E.h.a(c0702m, l);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Consumer consumer, W0 w0) {
        consumer.accept(new o5(this, w0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Consumer consumer, W0 w0) {
        consumer.accept(new o5(this, w0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ o5 g(W0 w0) {
        return new o5(this, w0);
    }

    public final void e(final Consumer consumer, Predicate predicate) {
        c(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda29
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(consumer, (T0) obj);
            }
        }, predicate);
    }

    public final void h(final Consumer consumer, Predicate predicate) {
        this.k.c(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d(consumer, (W0) obj);
            }
        }, predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ o5 e(W0 w0) {
        return new o5(this, w0);
    }

    public final com.android.tools.r8.utils.D3 c(Function function) {
        return a(function, new q2$$ExternalSyntheticLambda9());
    }

    public final com.android.tools.r8.utils.D3 d(final Function function) {
        return Y().f(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(function, (W0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    public final boolean c(C0702m c0702m) {
        if (this.e.h()) {
            return true;
        }
        if (!c0702m.f().i()) {
            return false;
        }
        if (B || c0702m.q()) {
            return (c0702m.a(this).c((com.android.tools.r8.shaking.K0) c0702m.Y()) || c0702m.i().c(this)) ? false : true;
        }
        throw new AssertionError();
    }

    public final m5 d(Y0 y0) {
        T0 t0A = this.j.a(y0);
        if (t0A != null) {
            return new m5(t0A, this);
        }
        return null;
    }

    public final void d(W0 w0) {
        this.k.b(w0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ o5 f(W0 w0) {
        return new o5(this, w0);
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        consumer.accept(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).d;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).f;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).g;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).e;
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).h;
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).u;
            }
        }).b(new Predicate() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0729q2) obj).v;
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).h1();
            }
        }).f(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).i1();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).s0();
            }
        }).a(new Predicate() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0729q2.i((C0729q2) obj);
            }
        }).f(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).G0();
            }
        }).f(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).I0();
            }
        });
    }

    public final void f(Consumer consumer, final Predicate predicate) {
        d(consumer, new Predicate() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C0729q2.a(predicate, (W0) obj);
            }
        });
    }

    public final void c(List list) {
        if (list.isEmpty()) {
            return;
        }
        C0800x2 c0800x2 = this.g;
        C0767v2[] c0767v2Arr = (C0767v2[]) Arrays.copyOf(c0800x2.a, list.size() + c0800x2.size());
        for (int size = this.g.size(); size < c0767v2Arr.length; size++) {
            c0767v2Arr[size] = ((C0706m3.c) list.get(size - this.g.size())).b;
        }
        this.g = new C0800x2(c0767v2Arr);
        if (this.r.a() || list.isEmpty()) {
            return;
        }
        C1507Xl c1507XlA = AbstractC1639am.g().a((Iterable) this.r.c);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            c1507XlA.a((C0706m3.c) it.next());
        }
        C0706m3.b bVar = this.r;
        this.r = new C0706m3.b(bVar.a, bVar.b, c1507XlA.a());
    }

    public o5 f(C0684j2 c0684j2) {
        W0 w0B = Y().b(c0684j2);
        if (w0B != null) {
            return new o5(this, w0B);
        }
        return null;
    }

    public static C0729q2 b(C0722p1 c0722p1) {
        C0767v2 c0767v2E = c0722p1.e("LMock;");
        Origin originUnknown = Origin.unknown();
        E eK = E.k(0);
        C0767v2 c0767v2 = c0722p1.Z1;
        C0800x2 c0800x2S0 = C0800x2.s0();
        List listEmptyList = Collections.emptyList();
        List listEmptyList2 = Collections.emptyList();
        List listEmptyList3 = Collections.emptyList();
        List listEmptyList4 = Collections.emptyList();
        C0706m3.b bVarF = C0706m3.b.f();
        C0658g0 c0658g0T0 = C0658g0.t0();
        T0[] t0Arr = T0.m;
        return new C0729q2(c0767v2E, null, originUnknown, eK, c0767v2, c0800x2S0, null, null, listEmptyList, listEmptyList2, listEmptyList3, null, listEmptyList4, bVarF, c0658g0T0, t0Arr, t0Arr, C0792w4.a.empty(), false, new B$$ExternalSyntheticLambda1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Predicate predicate, W0 w0) {
        return w0.C1() && predicate.test(w0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.D3 a(Function function, T0 t0) {
        return (com.android.tools.r8.utils.D3) function.apply(new m5(t0, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.D3 a(Function function, W0 w0) {
        return (com.android.tools.r8.utils.D3) function.apply(new o5(this, w0));
    }

    public final com.android.tools.r8.utils.D3 a(final Function function, final Predicate predicate) {
        return Y().f(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(predicate, function, (W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.D3 a(Predicate predicate, Function function, W0 w0) {
        if (predicate.test(w0)) {
            return (com.android.tools.r8.utils.D3) function.apply(new o5(this, w0));
        }
        return com.android.tools.r8.utils.D3.e();
    }

    public final void a(final C0702m c0702m, final com.android.tools.r8.dex.L l, final C2363kB c2363kB) {
        com.android.tools.r8.dex.l0 l0Var = (com.android.tools.r8.dex.l0) l;
        if (l0Var.a(this)) {
            C0767v2 c0767v2 = this.d;
            c0767v2.getClass();
            if (l0Var.a(c0767v2)) {
                C0759u2 c0759u2C = c0702m.z().c(c0767v2);
                c0759u2C.getClass();
                l0Var.a(c0759u2C);
            }
            C0767v2 c0767v3 = this.f;
            if (c0767v3 != null) {
                if (l0Var.a(c0767v3)) {
                    C0759u2 c0759u2C2 = c0702m.z().c(c0767v3);
                    c0759u2C2.getClass();
                    l0Var.a(c0759u2C2);
                }
            } else if (!B && !this.d.b1().equals(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)) {
                throw new AssertionError();
            }
            C0759u2 c0759u2 = this.h;
            if (c0759u2 != null) {
                l0Var.a(c0759u2);
            }
            for (C0633d0 c0633d0 : s0().c) {
                c0633d0.a(c0702m, l);
            }
            C0800x2 c0800x2 = this.g;
            if (c0800x2 != null) {
                for (C0767v2 c0767v4 : c0800x2.a) {
                    c0767v4.getClass();
                    if (l0Var.a(c0767v4)) {
                        C0759u2 c0759u2C3 = c0702m.z().c(c0767v4);
                        c0759u2C3.getClass();
                        l0Var.a(c0759u2C3);
                    }
                }
            }
            if (b1() != null) {
                b1().a(c0702m, l0Var);
            }
            Iterator<J3> it = e1().iterator();
            while (it.hasNext()) {
                it.next().a(c0702m, l0Var);
            }
            e(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda30
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0729q2.a(c0702m, l, (m5) obj);
                }
            }, CM.b());
            l(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda31
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0729q2.b(c0702m, l, c2363kB, (o5) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Consumer consumer, T0 t0) {
        consumer.accept(new m5(t0, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Consumer consumer, W0 w0) {
        consumer.accept(new o5(this, w0));
    }

    public final o5 b(C0767v2[] c0767v2Arr) {
        W0 w0A = a(c0767v2Arr);
        if (w0A != null) {
            return new o5(this, w0A);
        }
        return null;
    }

    public final com.android.tools.r8.utils.D3 b(final Function function) {
        return a(new Function() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(function, (T0) obj);
            }
        });
    }

    public final void b(final com.android.tools.r8.dex.V v) {
        boolean z = B;
        if (!z && b1() != null) {
            throw new AssertionError();
        }
        if (!z && !e1().isEmpty()) {
            throw new AssertionError();
        }
        if (!z && this.r.b()) {
            throw new AssertionError();
        }
        if (c2() || b2()) {
            v.a(this);
            C0792w4 c0792w4 = this.k;
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C0729q2.a(v, (W0) obj);
                }
            };
            c0792w4.getClass();
            c0792w4.b(consumer, CM.b());
            this.j.a(new Consumer() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((T0) obj).a(v);
                }
            });
        }
        C0658g0 c0658g0S0 = s0();
        c0658g0S0.getClass();
        v.a(c0658g0S0);
        AbstractC0594a1.a(v, c0658g0S0.c);
        C0800x2 c0800x2 = this.g;
        if (c0800x2 != null) {
            v.a(c0800x2);
        }
    }

    public C0729q2(C0767v2 c0767v2, ProgramResource.Kind kind, Origin origin, E e, C0767v2 c0767v3, C0800x2 c0800x2, C0759u2 c0759u2, W4 w4, List<X4> list, List<C0670h5> list2, List<v5> list3, O2 o2, List<J3> list4, C0706m3.b bVar, C0658g0 c0658g0, T0[] t0Arr, T0[] t0Arr2, C0792w4.a aVar, boolean z, a aVar2) {
        this(c0767v2, kind, origin, e, c0767v3, c0800x2, c0759u2, w4, list, list2, list3, o2, list4, bVar, c0658g0, t0Arr, t0Arr2, aVar, z, aVar2, null);
    }

    private boolean b(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Predicate predicate, Set set) {
        AbstractC0727q0 abstractC0727q0C;
        boolean z = B;
        if (!z && !isInterface()) {
            throw new AssertionError();
        }
        if (!z && !set.contains(getType())) {
            throw new AssertionError();
        }
        if (!z && predicate.test(getType())) {
            throw new AssertionError();
        }
        if (this == abstractC0727q0) {
            if (!p1() || Y0().i1().f()) {
                return U0();
            }
            return true;
        }
        if (p1() && !Y0().i1().f() && Y().e(new q2$$ExternalSyntheticLambda28())) {
            return true;
        }
        C0767v2 c0767v2 = this.f;
        if (c0767v2 != null && ((abstractC0727q0C = c0702m.c(c0767v2)) == null || abstractC0727q0C.a(c0702m, abstractC0727q0, predicate, set))) {
            return true;
        }
        for (C0767v2 c0767v3 : this.g) {
            c0767v3.getClass();
            AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v3);
            if (abstractC0727q0C2 == null || abstractC0727q0C2.a(c0702m, abstractC0727q0, predicate, set)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0727q0, com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        boolean z = B;
        if (!z && b1() != null) {
            throw new AssertionError();
        }
        if (!z && !e1().isEmpty()) {
            throw new AssertionError();
        }
        if (!z && this.r.b()) {
            throw new AssertionError();
        }
        if (a2()) {
            v.a(this, new C0640e0(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.dex.V v, W0 w0) {
        w0.getClass();
        v.b(w0);
    }

    public static C0729q2 a(AbstractC0727q0 abstractC0727q0) {
        if (abstractC0727q0 != null) {
            return abstractC0727q0.a0();
        }
        return null;
    }

    public final void a(InterfaceC2743oz interfaceC2743oz) {
        boolean z = B;
        if (!z && interfaceC2743oz == null) {
            throw new AssertionError();
        }
        if (!z && this.w != AbstractC1745cA.c()) {
            throw new AssertionError();
        }
        this.w = interfaceC2743oz;
    }

    @Override // com.android.tools.r8.graph.AbstractC0727q0
    final boolean a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Predicate predicate, Set set) {
        C0767v2 c0767v2;
        AbstractC0727q0 abstractC0727q0C;
        if (!set.add(getType()) || predicate.test(getType())) {
            return false;
        }
        if (isInterface()) {
            return b(c0702m, abstractC0727q0, predicate, set);
        }
        boolean z = B;
        if (!z && isInterface()) {
            throw new AssertionError();
        }
        if (!z && !set.contains(getType())) {
            throw new AssertionError();
        }
        if (!z && predicate.test(getType())) {
            throw new AssertionError();
        }
        if ((!p1() || Y0().i1().f()) && !U0() && ((c0767v2 = this.f) == null || ((abstractC0727q0C = c0702m.c(c0767v2)) != null && !abstractC0727q0C.a(c0702m, abstractC0727q0, predicate, set)))) {
            for (C0767v2 c0767v3 : this.g) {
                c0767v3.getClass();
                AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v3);
                if (abstractC0727q0C2 == null || abstractC0727q0C2.a(c0702m, abstractC0727q0, predicate, set)) {
                }
            }
            return false;
        }
        return true;
    }

    public final void b(Z8 z8) {
        boolean z = B;
        if (!z && this.u != null) {
            throw new AssertionError();
        }
        if (!z && z8 == null) {
            throw new AssertionError();
        }
        this.u = z8;
    }

    private static boolean a(W2 w2) {
        boolean zD;
        synchronized (w2) {
            zD = w2.d();
        }
        return zD;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(AbstractC3649o0 abstractC3649o0, T0 t0, T0 t1) {
        return t0.getReference().a((V4) t1.getReference(), abstractC3649o0);
    }

    public final S0 a(final AbstractC3649o0 abstractC3649o0) {
        for (T0 t0 : N1()) {
            B2 b2Y0 = t0.Y0();
            if (b2Y0 != null && b2Y0 != B2.a(t0.getReference().h)) {
                ArrayList arrayList = new ArrayList(N1());
                arrayList.sort(new Comparator() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda5
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return C0729q2.a(abstractC3649o0, (T0) obj, (T0) obj2);
                    }
                });
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                int i = 0;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    T0 t1 = (T0) arrayList.get(i2);
                    B2 b2Y1 = t1.Y0();
                    if (!B && b2Y1 == null) {
                        throw new AssertionError();
                    }
                    arrayList2.add(b2Y1);
                    C0767v2 c0767v2 = t1.getReference().h;
                    b2Y1.getClass();
                    if (b2Y1 != B2.a(c0767v2)) {
                        i = i2 + 1;
                    }
                }
                if (i > 0) {
                    return new S0((B2[]) arrayList2.subList(0, i).toArray(B2.a));
                }
                return null;
            }
        }
        return null;
    }

    public final void a(Z8 z8) {
        if (!B && z8 == null) {
            throw new AssertionError();
        }
        this.u = (Z8) com.android.tools.r8.utils.structural.s.d(this.u, z8);
    }

    public static Iterable a(final Collection collection, final Q0 q0) {
        return new Iterable() { // from class: com.android.tools.r8.graph.q2$$ExternalSyntheticLambda0
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return C0729q2.a(collection, q0);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterator a(Iterable iterable, Q0 q0) {
        return new C0723p2(iterable, q0);
    }

    public static long a(C0729q2 c0729q2) {
        throw new C1367Tb(c0729q2 + " has no checksum information while checksum encoding is requested", c0729q2.c);
    }
}
