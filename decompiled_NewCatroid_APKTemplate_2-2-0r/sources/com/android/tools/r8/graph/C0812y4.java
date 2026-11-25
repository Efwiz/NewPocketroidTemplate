package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2763pC;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.EJ;
import com.android.tools.r8.internal.OJ;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.y4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0812y4 extends AbstractC0802x4 {
    static final /* synthetic */ boolean c = true;
    private final boolean a;
    private SortedMap b;

    private C0812y4(boolean z, SortedMap sortedMap) {
        this.a = z;
        this.b = sortedMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public W0 b(Function function, W0 w0) {
        return w0.R0() ? (W0) function.apply(w0) : w0;
    }

    public static C0812y4 k() {
        return new C0812y4(true, new EJ());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterator l() {
        return com.android.tools.r8.utils.L1.a(this.b.values().iterator(), new y4$$ExternalSyntheticLambda6(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Iterator m() {
        return com.android.tools.r8.utils.L1.a(this.b.values().iterator(), new y4$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final Iterable c() {
        return new Iterable() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda4
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.l();
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final String d() {
        return "<method-mapbacking/" + (this.a ? "sorted" : "linked") + ">";
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final Iterable e() {
        return this.b.values();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final int f() {
        y4$$ExternalSyntheticLambda6 y4__externalsyntheticlambda6 = new y4$$ExternalSyntheticLambda6(this);
        Iterator it = this.b.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (y4__externalsyntheticlambda6.test((W0) it.next())) {
                i++;
            }
        }
        return i;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    public final int g() {
        y4$$ExternalSyntheticLambda0 y4__externalsyntheticlambda0 = new y4$$ExternalSyntheticLambda0(this);
        Iterator it = this.b.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (y4__externalsyntheticlambda0.test((W0) it.next())) {
                i++;
            }
        }
        return i;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final int h() {
        return this.b.size();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void i() {
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0812y4.a((AbstractC0717o2) obj, (W0) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final Iterable j() {
        return new Iterable() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda3
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return this.f$0.m();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(SortedMap sortedMap, W0 w0) {
        if (w0.R0()) {
            sortedMap.put(w0.o1(), w0);
        }
    }

    private W0 c(final Predicate predicate) {
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        g(new Function() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0812y4.a(predicate, n, (W0) obj);
            }
        });
        return (W0) n.a();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void d(final Function function) {
        e(new Function() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(function, (W0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void e(Function function) {
        for (W0 w0 : new ArrayList(this.b.values())) {
            W0 w1 = (W0) function.apply(w0);
            if (w1 != w0) {
                AbstractC0717o2 abstractC0717o2O1 = w0.o1();
                if (((W0) this.b.get(abstractC0717o2O1)) == w0) {
                }
                this.b.put(w1.o1(), w1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0802x4
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public final C0812y4 a(final Function function) {
        final C0812y4 c0812y4 = new C0812y4(this.a, this.a ? new EJ() : new OJ(this.b.size()));
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0812y4.a(this.f$0, function, (AbstractC0717o2) obj, (W0) obj2);
            }
        });
        return c0812y4;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 b(C0684j2 c0684j2) {
        W0 w0A = a(c0684j2.H0(), c0684j2.C0());
        if (w0A == null || !w0A.R0()) {
            return null;
        }
        return w0A;
    }

    public static C0812y4 a(int i) {
        return new C0812y4(false, new OJ(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f(W0 w0) {
        this.b.remove(w0.o1());
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final com.android.tools.r8.utils.D3 g(Function function) {
        Iterator it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            com.android.tools.r8.utils.D3 d3 = (com.android.tools.r8.utils.D3) function.apply((W0) ((Map.Entry) it.next()).getValue());
            if (d3.f()) {
                return d3;
            }
        }
        return com.android.tools.r8.utils.D3.e();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 b(Predicate predicate) {
        return c(new y4$$ExternalSyntheticLambda0(this).and(predicate));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void f(final Function function) {
        e(new Function() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(function, (W0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b(W0 w0) {
        W0 w1 = (W0) this.b.put(w0.o1(), w0);
        if (!c && w1 != null) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC0717o2 abstractC0717o2, W0 w0) {
        if (c) {
            return;
        }
        C0684j2 reference = w0.getReference();
        reference.getClass();
        C0737r2 c0737r2B = abstractC0717o2.b();
        C0759u2 c0759u2A = abstractC0717o2.a();
        if (reference.h != c0737r2B || reference.f != c0759u2A) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            c((W0) it.next());
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 a(C0737r2 c0737r2, C0759u2 c0759u2) {
        int i = AbstractC0717o2.a;
        return (W0) this.b.get(new C0711n2(c0737r2, c0759u2));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b() {
        this.b.values().removeIf(new y4$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void c(W0 w0) {
        if (!c && !w0.R0()) {
            throw new AssertionError();
        }
        b(w0);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b(W0[] w0Arr) {
        final SortedMap oj;
        if ((w0Arr == null || w0Arr.length == 0) && this.b.isEmpty()) {
            return;
        }
        if (w0Arr == null) {
            w0Arr = W0.s;
        }
        int size = this.b.size() + w0Arr.length;
        if (this.a) {
            oj = new EJ();
        } else {
            oj = new OJ(size);
        }
        a(new Consumer() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(oj, (W0) obj);
            }
        }, CM.b());
        for (W0 w0 : w0Arr) {
            if (!c && !w0.R0()) {
                throw new AssertionError();
            }
            oj.put(w0.o1(), w0);
        }
        this.b = oj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(Predicate predicate, com.android.tools.r8.utils.N n, W0 w0) {
        if (predicate.test(w0)) {
            n.a(w0);
            return com.android.tools.r8.utils.D3.d();
        }
        return com.android.tools.r8.utils.D3.e();
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 c(C0684j2 c0684j2) {
        c0684j2.getClass();
        return (W0) this.b.remove(new C0705m2(c0684j2));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void c(Function function) {
        ArrayList arrayListA = AbstractC2763pC.a(j());
        b();
        ArrayList arrayList = new ArrayList(arrayListA.size());
        Iterator it = arrayListA.iterator();
        while (it.hasNext()) {
            arrayList.add((W0) function.apply((W0) it.next()));
        }
        b(arrayList);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 a(C0684j2 c0684j2) {
        W0 w0A = a(c0684j2.H0(), c0684j2.C0());
        if (w0A == null || !w0A.f.N()) {
            return null;
        }
        return w0A;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 a(Predicate predicate) {
        return c(new y4$$ExternalSyntheticLambda6(this).and(predicate));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(W0 w0) {
        if (!c && !w0.f.N()) {
            throw new AssertionError();
        }
        b(w0);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            a((W0) it.next());
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a() {
        this.b.values().removeIf(new y4$$ExternalSyntheticLambda6(this));
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(Set set) {
        set.forEach(new Consumer() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.f((W0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void b(Function function) {
        ArrayList arrayListA = AbstractC2763pC.a(c());
        a();
        ArrayList arrayList = new ArrayList(arrayListA.size());
        Iterator it = arrayListA.iterator();
        while (it.hasNext()) {
            arrayList.add((W0) function.apply((W0) it.next()));
        }
        a(arrayList);
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(W0[] w0Arr) {
        final SortedMap oj;
        if ((w0Arr == null || w0Arr.length == 0) && this.b.isEmpty()) {
            return;
        }
        if (w0Arr == null) {
            w0Arr = W0.s;
        }
        int size = this.b.size() + w0Arr.length;
        if (this.a) {
            oj = new EJ();
        } else {
            oj = new OJ(size);
        }
        a(new Consumer() { // from class: com.android.tools.r8.graph.y4$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(oj, (W0) obj);
            }
        }, CM.b());
        for (W0 w0 : w0Arr) {
            if (!c && !w0.f.N()) {
                throw new AssertionError();
            }
            oj.put(w0.o1(), w0);
        }
        this.b = oj;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 b(C0684j2 c0684j2, Function function) {
        c0684j2.getClass();
        C0705m2 c0705m2 = new C0705m2(c0684j2);
        W0 w0 = (W0) this.b.get(c0705m2);
        if (w0 == null || w0.R0()) {
            return null;
        }
        W0 w1 = (W0) function.apply(w0);
        if (!c && !w1.R0()) {
            throw new AssertionError();
        }
        C0684j2 reference = w1.getReference();
        reference.getClass();
        C0737r2 c0737r2B = c0705m2.b();
        C0759u2 c0759u2A = c0705m2.a();
        if (reference.h == c0737r2B && reference.f == c0759u2A) {
            this.b.put(c0705m2, w1);
        } else {
            this.b.remove(c0705m2);
            this.b.put(w1.o1(), w1);
        }
        return w1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(SortedMap sortedMap, W0 w0) {
        if (w0.f.N()) {
            sortedMap.put(w0.o1(), w0);
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final W0 a(C0684j2 c0684j2, Function function) {
        y4$$ExternalSyntheticLambda6 y4__externalsyntheticlambda6 = new y4$$ExternalSyntheticLambda6(this);
        c0684j2.getClass();
        C0705m2 c0705m2 = new C0705m2(c0684j2);
        W0 w0 = (W0) this.b.get(c0705m2);
        if (w0 == null || !y4__externalsyntheticlambda6.test(w0)) {
            return null;
        }
        W0 w1 = (W0) function.apply(w0);
        if (!c && !y4__externalsyntheticlambda6.test(w1)) {
            throw new AssertionError();
        }
        C0684j2 reference = w1.getReference();
        reference.getClass();
        C0737r2 c0737r2B = c0705m2.b();
        C0759u2 c0759u2A = c0705m2.a();
        if (reference.h == c0737r2B && reference.f == c0759u2A) {
            this.b.put(c0705m2, w1);
            return w1;
        }
        this.b.remove(c0705m2);
        this.b.put(w1.o1(), w1);
        return w1;
    }

    @Override // com.android.tools.r8.graph.AbstractC0802x4
    final void a(LinkedHashSet linkedHashSet) {
        if (c) {
            return;
        }
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            W0 w0 = (W0) it.next();
            boolean z = c;
            if (!z && !w0.R0()) {
                throw new AssertionError();
            }
            if (!z && this.b.get(w0.o1()) != w0) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0812y4 c0812y4, Function function, AbstractC0717o2 abstractC0717o2, W0 w0) {
        c0812y4.b((W0) function.apply(w0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public W0 a(Function function, W0 w0) {
        return w0.f.N() ? (W0) function.apply(w0) : w0;
    }
}
