package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC3533zt;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.InterfaceC3010sj;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.AbstractC3896g;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: renamed from: com.android.tools.r8.graph.q0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0727q0 extends O0 implements F, O {
    static final /* synthetic */ boolean s = true;
    public final Origin c;
    public final C0767v2 d;
    public final E e;
    public C0767v2 f;
    public C0800x2 g;
    public C0759u2 h;
    private AbstractC3938n2 i;
    protected final W2 j;
    protected final C0792w4 k;
    private O2 l;
    private List m;
    private W4 n;
    private List o;
    private List p;
    private List q;
    protected C0706m3.b r;

    public AbstractC0727q0(C0759u2 c0759u2, C0800x2 c0800x2, E e, C0767v2 c0767v2, C0767v2 c0767v3, T0[] t0Arr, T0[] t0Arr2, C0792w4.a aVar, W4 w4, List list, List list2, List list3, O2 o2, List list4, C0706m3.b bVar, C0658g0 c0658g0, Origin origin, boolean z) {
        super(c0658g0);
        this.i = AbstractC3938n2.g();
        boolean z2 = s;
        if (!z2 && origin == null) {
            throw new AssertionError();
        }
        this.c = origin;
        this.h = c0759u2;
        this.g = c0800x2;
        this.e = e;
        this.f = c0767v2;
        this.d = c0767v3;
        this.j = W2.a(this, t0Arr, t0Arr2);
        this.k = aVar.a(this);
        this.n = w4;
        this.o = list;
        if (!z2 && list == null) {
            throw new AssertionError();
        }
        this.p = list2;
        this.q = list3;
        if (!z2 && list2 == null) {
            throw new AssertionError();
        }
        this.l = o2;
        this.m = list4;
        if (!z2 && bVar == null) {
            throw new AssertionError();
        }
        this.r = bVar;
        if (!z2 && !E3.a(bVar, c0658g0)) {
            throw new AssertionError();
        }
        if (c0767v3 == c0767v2) {
            throw new C1367Tb("Class " + c0767v3.toString() + " cannot extend itself");
        }
        for (C0767v2 c0767v4 : c0800x2.a) {
            if (c0767v3 == c0767v4) {
                throw new C1367Tb("Interface " + c0767v3.toString() + " cannot implement itself");
            }
        }
        if (z || c0767v3.e.x0()) {
            return;
        }
        throw new C1367Tb("Class descriptor '" + c0767v3.e.toString() + "' cannot be represented in dex format.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterator a(Iterator it) {
        return it;
    }

    @Override // com.android.tools.r8.graph.O0
    public final boolean A0() {
        return true;
    }

    public boolean A1() {
        return this instanceof C0754u0;
    }

    public boolean B1() {
        return this.e.V();
    }

    public final boolean C1() {
        return this.e.h();
    }

    public boolean D1() {
        return G1() || H1();
    }

    public boolean E1() {
        J3 j3D1 = d1();
        return j3D1 != null && j3D1.d() == null && j3D1.f();
    }

    @Override // com.android.tools.r8.graph.O0
    public final boolean F0() {
        return false;
    }

    public boolean F1() {
        J3 j3D1 = d1();
        boolean z = (j3D1 == null || j3D1.d() == null || !j3D1.f()) ? false : true;
        if (s || !z || b1() == null) {
            return z;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.O
    public final AbstractC0727q0 G() {
        return null;
    }

    public final List G0() {
        return this.j.a();
    }

    public boolean G1() {
        return !this.o.isEmpty();
    }

    public final Iterable H0() {
        C0767v2 c0767v2 = this.f;
        final Iterator itA = c0767v2 != null ? AbstractC3533zt.a(AbstractC3533zt.a(c0767v2), AbstractC3533zt.a((Object[]) this.g.a)) : AbstractC3533zt.a((Object[]) this.g.a);
        return new Iterable() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda2
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return AbstractC0727q0.a(itA);
            }
        };
    }

    public boolean H1() {
        return this.n != null;
    }

    public List<W0> I0() {
        return this.k.a();
    }

    public abstract boolean I1();

    public final Iterable J0() {
        return AbstractC2734ot.a((Iterable) W0(), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c((T0) obj);
            }
        });
    }

    public boolean J1() {
        return this.e.m();
    }

    public final Iterable K0() {
        return AbstractC2734ot.a((Iterable) M1(), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c((W0) obj);
            }
        });
    }

    public boolean K1() {
        return this.e.X();
    }

    public final void L0() {
        this.r = C0706m3.b.f();
    }

    public final Iterable L1() {
        return AbstractC0984Fi.a(W0(), M1());
    }

    public final void M0() {
        this.l = null;
    }

    public Iterable<W0> M1() {
        return this.k.i();
    }

    public final void N0() {
        this.m.clear();
    }

    public List<T0> N1() {
        return this.j.i();
    }

    public final T0[] O0() {
        List<T0> listW1 = w1();
        this.j.b();
        return (T0[]) listW1.toArray(T0.m);
    }

    public final boolean O1() {
        return a1().c.isEmpty() || this.g.a.length == a1().c.size();
    }

    public final void P0() {
        this.n = null;
    }

    public Iterable<W0> P1() {
        return this.k.o();
    }

    public final void Q0() {
        this.o.clear();
    }

    @Override // com.android.tools.r8.graph.O
    public final AbstractC0727q0 R() {
        return this;
    }

    public final void R0() {
        this.p.clear();
    }

    public final void S0() {
        this.q.clear();
    }

    @Override // com.android.tools.r8.graph.O
    public final boolean T() {
        return true;
    }

    public final T0[] T0() {
        List<T0> listN1 = N1();
        this.j.c();
        return (T0[]) listN1.toArray(T0.m);
    }

    public final boolean U0() {
        return N1().stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0727q0.d((T0) obj);
            }
        });
    }

    public Iterable<W0> V0() {
        return this.k.d();
    }

    public Iterable<T0> W0() {
        return this.j.a(CM.b());
    }

    @Override // com.android.tools.r8.graph.O0
    /* JADX INFO: renamed from: X0, reason: merged with bridge method [inline-methods] */
    public E getAccessFlags() {
        return this.e;
    }

    @Override // com.android.tools.r8.graph.F
    public C0792w4 Y() {
        return this.k;
    }

    public final W0 Y0() {
        W0 w0F = this.k.f();
        if (s || w0F != W0.t) {
            return w0F;
        }
        throw new AssertionError();
    }

    public ClassReference Z0() {
        return Reference.classFromDescriptor(getType().b1());
    }

    public abstract void a(Consumer consumer, Consumer consumer2, Consumer consumer3);

    abstract boolean a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Predicate predicate, Set set);

    public C0706m3.b a1() {
        return this.r;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final AbstractC0727q0 asClass() {
        return this;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public AbstractC0727q0 b() {
        return this;
    }

    public O2 b1() {
        return this.l;
    }

    @Override // com.android.tools.r8.graph.F
    public void c(Consumer<? super AbstractC0748t0> consumer) {
        b(consumer, CM.b());
    }

    public final W2 c1() {
        return this.j;
    }

    public final void d(Collection collection) {
        this.k.a((W0[]) collection.toArray(W0.s));
    }

    public C0635d2 d0() {
        return null;
    }

    public J3 d1() {
        for (J3 j3 : e1()) {
            if (this.d == j3.b()) {
                return j3;
            }
        }
        return null;
    }

    public final W0 e(C0684j2 c0684j2) {
        return this.k.d(c0684j2);
    }

    public List<J3> e1() {
        return this.m;
    }

    public void f(Consumer<W0> consumer) {
        C0792w4 c0792w4 = this.k;
        c0792w4.getClass();
        c0792w4.b(consumer, CM.b());
    }

    public boolean f0() {
        return false;
    }

    public C0800x2 f1() {
        return this.g;
    }

    public final Iterable g(Predicate predicate) {
        return AbstractC0984Fi.a(this.j.a(predicate), this.k.f(predicate));
    }

    public C0767v2 g1() {
        if (H1()) {
            return this.n.a();
        }
        if (G1()) {
            return this.d;
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final Origin getOrigin() {
        return this.c;
    }

    @Override // com.android.tools.r8.graph.O0
    public AbstractC0744s2 getReference() {
        return getType();
    }

    @Override // com.android.tools.r8.graph.F
    public C0767v2 getType() {
        return this.d;
    }

    public final Iterable h(Predicate predicate) {
        return this.k.f(predicate);
    }

    public W4 h1() {
        return this.n;
    }

    public final void i(Predicate predicate) {
        O2 o2 = this.l;
        if (o2 == null || !predicate.test(o2)) {
            return;
        }
        this.l = null;
    }

    public List<X4> i1() {
        return this.o;
    }

    @Override // com.android.tools.r8.graph.F
    public boolean isInterface() {
        return this.e.W();
    }

    public final void j(Predicate predicate) {
        this.m.removeIf(predicate);
    }

    public List<C0670h5> j1() {
        return this.p;
    }

    public final void k(Predicate predicate) {
        this.o.removeIf(predicate);
    }

    public List<v5> k1() {
        return this.q;
    }

    public final void l(Predicate predicate) {
        if (this.q.isEmpty()) {
            return;
        }
        this.q.removeIf(predicate);
    }

    public final String l1() {
        return getType().K0();
    }

    @Override // com.android.tools.r8.graph.F
    public C0754u0 m() {
        return null;
    }

    public C0759u2 m1() {
        return this.h;
    }

    public C0767v2 n1() {
        return this.f;
    }

    public final String o1() {
        return getType().L0();
    }

    public final boolean p1() {
        return Y0() != null;
    }

    public final boolean q1() {
        return a(C0767v2.g) != null;
    }

    public final boolean r1() {
        return this.l != null;
    }

    public final boolean s1() {
        return this.j.e();
    }

    @Override // com.android.tools.r8.graph.O0
    public final AbstractC0727q0 t0() {
        return this;
    }

    public final boolean t1() {
        return !this.o.isEmpty();
    }

    public final boolean u1() {
        return !this.p.isEmpty();
    }

    public final boolean v1() {
        return this.j.f();
    }

    public List<T0> w1() {
        return this.j.g();
    }

    @Override // com.android.tools.r8.graph.O
    public final AbstractC0727q0 x() {
        return this;
    }

    public boolean x1() {
        return this.e.R();
    }

    public final boolean y1() {
        return this.e.T();
    }

    public boolean z1() {
        J3 j3D1 = d1();
        return j3D1 != null && j3D1.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC0735r0 c(T0 t0) {
        return AbstractC0735r0.a(this, t0);
    }

    @Override // com.android.tools.r8.graph.F
    public void a(Consumer<? super AbstractC0735r0> consumer) {
        a(consumer, CM.b());
    }

    @Override // com.android.tools.r8.graph.O
    public final void b(Consumer consumer) {
        consumer.accept(this);
    }

    public final void e(Consumer consumer) {
        d(CM.b()).forEach(consumer);
    }

    public final void h(Consumer consumer) {
        com.android.tools.r8.utils.I1.a(N1(), CM.b()).forEach(consumer);
    }

    public Iterable<W0> m(Predicate<? super W0> predicate) {
        Iterable<W0> iterableP1 = P1();
        Objects.requireNonNull(predicate);
        return AbstractC2734ot.c(iterableP1, new q0$$ExternalSyntheticLambda8(predicate));
    }

    public final void a(final Consumer consumer, Predicate predicate) {
        c(new Consumer() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (T0) obj);
            }
        }, predicate);
    }

    public final void b(final Consumer consumer, Predicate predicate) {
        this.k.b(new Consumer() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (W0) obj);
            }
        }, predicate);
    }

    public final Iterable c(Predicate predicate) {
        return this.j.a(predicate);
    }

    public final Iterable d(Predicate predicate) {
        return AbstractC2734ot.c(w1(), new V2$$ExternalSyntheticLambda0(predicate));
    }

    public W0 f(Predicate<W0> predicate) {
        return this.k.b(predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, T0 t0) {
        consumer.accept(AbstractC0735r0.a(this, t0));
    }

    public final Iterable b(Predicate predicate) {
        Iterable<W0> iterableV0 = V0();
        Objects.requireNonNull(predicate);
        return AbstractC2734ot.c(iterableV0, new q0$$ExternalSyntheticLambda8(predicate));
    }

    public final void c(Consumer consumer, Predicate predicate) {
        this.j.a(predicate).forEach(consumer);
    }

    public final W0 d(C0684j2 c0684j2) {
        return this.k.c(c0684j2);
    }

    public final void e(Collection collection) {
        this.j.b((T0[]) collection.toArray(T0.m));
    }

    public final void g(final Consumer consumer) {
        if (!s && !G1()) {
            throw new AssertionError();
        }
        i1().forEach(new Consumer() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC0727q0.a(consumer, (X4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, W0 w0) {
        consumer.accept(AbstractC0748t0.a(this, w0));
    }

    public final void b(W0 w0) {
        this.k.c(w0);
    }

    public final boolean d(C0702m c0702m) {
        boolean zA;
        if (this.i.e()) {
            if (f0()) {
                zA = AbstractC3896g.a(d0(), c0702m);
            } else {
                boolean z = true;
                for (C0767v2 c0767v2 : H0()) {
                    c0767v2.getClass();
                    AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
                    z &= abstractC0727q0C != null && abstractC0727q0C.d(c0702m);
                    if (!z) {
                        break;
                    }
                }
                zA = z;
            }
            this.i = AbstractC3938n2.a(zA);
        }
        if (s || !this.i.e()) {
            return this.i.d();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC0748t0 c(W0 w0) {
        return AbstractC0748t0.a(this, w0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public void a(com.android.tools.r8.dex.V v) {
        throw new C3405y10();
    }

    public final void b(Collection collection) {
        this.k.b(collection);
    }

    public final W0 e(Predicate predicate) {
        return this.k.a(predicate);
    }

    public final void a(W0 w0) {
        this.k.a(w0);
    }

    public final void b(W0[] w0Arr) {
        this.k.b(w0Arr);
    }

    public final void c(Collection collection) {
        this.j.a(collection);
    }

    public final boolean e(C0702m c0702m) {
        C0680j c0680j = (C0680j) c0702m.f();
        return c0680j.c(this.d, c0680j.a().u5);
    }

    public final void a(Collection collection) {
        this.k.a(collection);
    }

    public final void b(T0 t0) {
        this.j.b(t0);
    }

    public final T0 c(Y0 y0) {
        return this.j.c(y0);
    }

    public final void a(W0[] w0Arr) {
        this.k.a(w0Arr);
    }

    public final void b(T0[] t0Arr) {
        this.j.b(t0Arr);
    }

    public final W0 c(C0684j2 c0684j2) {
        return this.k.b(c0684j2);
    }

    public final void a(ArrayList arrayList) {
        this.k.b((W0[]) arrayList.toArray(W0.s));
    }

    public final T0 b(Y0 y0) {
        return this.j.b(y0);
    }

    public boolean c(C0702m c0702m) {
        return this.e.h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0759u2 c0759u2, T0 t0) {
        return t0.K0() == c0759u2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(BiConsumer biConsumer, C0767v2 c0767v2) {
        biConsumer.accept(c0767v2, Boolean.TRUE);
    }

    private void a(Iterable iterable, com.android.tools.r8.utils.A1 a1) {
        if (!a1.j() || x1()) {
            return;
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            W0 w0 = (W0) it.next();
            if (!s && w0.s1()) {
                throw new AssertionError("Non-abstract method on abstract class: `" + w0.getReference().r0() + "`");
            }
        }
    }

    public final T0 b(final C0759u2 c0759u2) {
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0727q0.b(c0759u2, (T0) obj);
            }
        };
        T0 t0 = null;
        for (T0 t1 : N1()) {
            if (predicate.test(t1)) {
                if (t0 != null) {
                    return null;
                }
                t0 = t1;
            }
        }
        return t0;
    }

    public final void c(BiConsumer biConsumer) {
        C0767v2 c0767v2 = this.f;
        if (c0767v2 != null) {
            biConsumer.accept(c0767v2, this.r.b);
        }
        a(biConsumer);
    }

    public final W0 b(C0684j2 c0684j2) {
        return this.k.a(c0684j2);
    }

    public boolean b(C0702m<?> c0702m) {
        return a(c0702m, this, CM.a(), OU.c());
    }

    public final void a(LinkedHashSet linkedHashSet) {
        this.k.a(linkedHashSet);
    }

    public final com.android.tools.r8.utils.D3 a(Function function) {
        Iterator<T0> it = W0().iterator();
        while (it.hasNext()) {
            if (((com.android.tools.r8.utils.D3) function.apply(it.next())).f()) {
                return com.android.tools.r8.utils.D3.d();
            }
        }
        return com.android.tools.r8.utils.D3.e();
    }

    public final void b(final BiConsumer biConsumer) {
        C0767v2 c0767v2 = this.f;
        if (c0767v2 != null) {
            biConsumer.accept(c0767v2, Boolean.FALSE);
        }
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC0727q0.c(biConsumer, (C0767v2) obj);
            }
        };
        for (C0767v2 c0767v3 : this.g.a) {
            consumer.accept(c0767v3);
        }
    }

    public final void d(Consumer consumer) {
        C0767v2 c0767v2 = this.f;
        if (c0767v2 != null) {
            consumer.accept(c0767v2);
        }
        for (C0767v2 c0767v3 : this.g.a) {
            consumer.accept(c0767v3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean d(T0 t0) {
        return t0.Z0() && t0.Y0().h1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(BiConsumer biConsumer, C0767v2 c0767v2) {
        biConsumer.accept(c0767v2, new C0706m3.c(c0767v2));
    }

    public final void a(T0 t0) {
        this.j.a(t0);
    }

    public final void a(T0[] t0Arr) {
        this.j.a(t0Arr);
    }

    public final void b(List list, BiConsumer biConsumer) {
        C0767v2 c0767v2 = this.f;
        if (c0767v2 != null) {
            biConsumer.accept(c0767v2, a(a1().b, list));
        }
        a(list, biConsumer);
    }

    public final T0 a(Y0 y0) {
        return this.j.a(y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0759u2 c0759u2, T0 t0) {
        return t0.K0() == c0759u2;
    }

    public final T0 a(final C0759u2 c0759u2) {
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0727q0.a(c0759u2, (T0) obj);
            }
        };
        T0 t0 = null;
        for (T0 t1 : w1()) {
            if (predicate.test(t1)) {
                if (t0 != null) {
                    return null;
                }
                t0 = t1;
            }
        }
        return t0;
    }

    public final U0 a(AbstractC0642e2 abstractC0642e2) {
        if (abstractC0642e2.x0()) {
            return this.j.a(abstractC0642e2.t0());
        }
        return this.k.b(abstractC0642e2.v0());
    }

    public final void b(List list) {
        this.o = list;
    }

    public final AbstractC0748t0 a(C0684j2 c0684j2) {
        W0 w0B = this.k.b(c0684j2);
        if (w0B != null) {
            return AbstractC0748t0.a(this, w0B);
        }
        return null;
    }

    public final W0 a(C0737r2 c0737r2, C0759u2 c0759u2) {
        return this.k.a(c0737r2, c0759u2);
    }

    public static boolean a(W0 w0, C0722p1 c0722p1) {
        if (s || w0.J0() == c0722p1.C2 || w0.J0() == c0722p1.B2) {
            return w0.f.d0() && w0.f.Z() && w0.getReference().h.f.size() == 1 && w0.getReference().h.f.a[0] == c0722p1.c2;
        }
        throw new AssertionError();
    }

    public final W0 a(C0767v2[] c0767v2Arr) {
        for (W0 w0 : V0()) {
            if (w0.C1() && Arrays.equals(w0.getReference().h.f.a, c0767v2Arr)) {
                return w0;
            }
        }
        return null;
    }

    public final boolean a(C0680j c0680j) {
        AbstractC0727q0 abstractC0727q0C;
        C0767v2 c0767v2 = this.f;
        if (c0767v2 != null && ((abstractC0727q0C = c0680j.c(c0767v2)) == null || abstractC0727q0C.a(c0680j))) {
            return true;
        }
        for (C0767v2 c0767v3 : this.g.a) {
            AbstractC0727q0 abstractC0727q0C2 = c0680j.c(c0767v3);
            if (abstractC0727q0C2 == null || abstractC0727q0C2.a(c0680j)) {
                return true;
            }
        }
        return false;
    }

    public final boolean a(C0702m c0702m, Predicate predicate) {
        return a(c0702m, this, predicate, OU.c());
    }

    public final boolean a(final C0702m c0702m, final InterfaceC0593a0 interfaceC0593a0) {
        return a(c0702m, this, new Predicate() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC0727q0.a(c0702m, interfaceC0593a0, (C0767v2) obj);
            }
        }, OU.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0702m c0702m, InterfaceC0593a0 interfaceC0593a0, C0767v2 c0767v2) {
        return c0702m.a(interfaceC0593a0.C(), c0767v2).d();
    }

    public final void a(final BiConsumer biConsumer) {
        if (!s && !O1()) {
            throw new AssertionError();
        }
        if (a1().c.isEmpty()) {
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AbstractC0727q0.b(biConsumer, (C0767v2) obj);
                }
            };
            for (C0767v2 c0767v2 : this.g.a) {
                consumer.accept(c0767v2);
            }
            return;
        }
        Iterator it = Arrays.asList(this.g.a).iterator();
        Iterator<C0706m3.c> it2 = a1().c.iterator();
        while (it.hasNext()) {
            if (!s && !it2.hasNext()) {
                throw new AssertionError();
            }
            biConsumer.accept((C0767v2) it.next(), it2.next());
        }
    }

    public final void a(List list, final BiConsumer biConsumer) {
        if (!s && !O1()) {
            throw new AssertionError();
        }
        if (a1().c.size() == 0) {
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AbstractC0727q0.a(biConsumer, (C0767v2) obj);
                }
            };
            for (C0767v2 c0767v2 : this.g.a) {
                consumer.accept(c0767v2);
            }
            return;
        }
        Iterator<C0706m3.c> it = a1().c.iterator();
        for (C0767v2 c0767v3 : Arrays.asList(this.g.a)) {
            if (!s && !it.hasNext()) {
                throw new AssertionError();
            }
            C0706m3.c next = it.next();
            if (list.isEmpty()) {
                Iterator<C0706m3.e> it2 = next.c.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (it2.next().o()) {
                            biConsumer.accept(c0767v3, AbstractC1639am.i());
                            break;
                        }
                    }
                }
            }
            biConsumer.accept(c0767v3, a(next, list));
        }
        if (!s && it.hasNext()) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, C0767v2 c0767v2) {
        biConsumer.accept(c0767v2, AbstractC1639am.i());
    }

    private AbstractC1639am a(C0706m3.c cVar, final List list) {
        final C1507Xl c1507XlG = AbstractC1639am.g();
        if (cVar.b.r0().equals(CommonClassNames.JAVA_UTIL_MAP)) {
            System.currentTimeMillis();
        }
        cVar.r().forEach(new Consumer() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(list, c1507XlG, (C0706m3.e) obj);
            }
        });
        return c1507XlG.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list, C1507Xl c1507Xl, C0706m3.e eVar) {
        if (eVar.o()) {
            for (int i = 0; i < a1().a.size(); i++) {
                if (a1().a.get(i).a.equals(eVar.i().b)) {
                    if (i >= list.size()) {
                        if (!s) {
                            throw new AssertionError();
                        }
                    } else {
                        c1507Xl.a((C0706m3.e) list.get(i));
                    }
                }
            }
            return;
        }
        c1507Xl.a(eVar);
    }

    public final boolean a(C0722p1 c0722p1) {
        return this.k.c(c0722p1.g4.h) != null;
    }

    public final void a(List list) {
        this.m = list;
    }

    public final void a(O2 o2) {
        this.l = o2;
    }

    public final void a(C0706m3.b bVar) {
        this.r = bVar;
    }

    public final void a(C0767v2 c0767v2) {
        if (!s && c0767v2 == null) {
            throw new AssertionError();
        }
        this.n = new W4(c0767v2);
    }

    public final void a(W4 w4) {
        this.n = w4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, X4 x4) {
        consumer.accept(x4.a());
    }

    public final void a(com.android.tools.r8.utils.A1 a1) {
        boolean z = s;
        if (!z) {
            a(P1(), a1);
        }
        if (!z && isInterface() && Y().e(new Predicate() { // from class: com.android.tools.r8.graph.q0$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((W0) obj).z1();
            }
        })) {
            throw new AssertionError();
        }
        if (!z) {
            this.j.j();
        }
        if (z) {
            return;
        }
        this.k.n();
    }
}
