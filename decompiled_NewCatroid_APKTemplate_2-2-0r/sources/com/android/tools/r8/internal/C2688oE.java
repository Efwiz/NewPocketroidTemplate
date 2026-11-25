package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.AbstractC3735i1;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2688oE implements Collection {
    static final /* synthetic */ boolean e = true;
    private final LinkedList a;
    private com.android.tools.r8.graph.Y0 b;
    private C0729q2 c;
    private L4 d;

    public C2688oE() {
        this.c = null;
        this.a = new LinkedList();
    }

    public final void a(C0729q2 c0729q2) {
        this.a.add(c0729q2);
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        return this.a.add((C0729q2) obj);
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        return this.a.addAll(collection);
    }

    public final LinkedList b() {
        return this.a;
    }

    public final L4 c() {
        if (e || i()) {
            return this.d;
        }
        throw new AssertionError();
    }

    @Override // java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        return this.a.contains(obj);
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.a.containsAll(collection);
    }

    public final Iterable d() {
        if (e || j()) {
            return AbstractC2734ot.c(this.a, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.oE$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return this.f$0.b((C0729q2) obj);
                }
            });
        }
        throw new AssertionError();
    }

    public final C0767v2 e() {
        if (e || com.android.tools.r8.utils.I1.a((AbstractCollection) this.a, new Function() { // from class: com.android.tools.r8.internal.oE$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).n1();
            }
        })) {
            return ((C0729q2) this.a.getFirst()).n1();
        }
        throw new AssertionError();
    }

    public final C0729q2 f() {
        return this.c;
    }

    public final boolean g() {
        return this.b != null;
    }

    public final boolean i() {
        return this.d != null;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.a.iterator();
    }

    public final boolean j() {
        return this.c != null;
    }

    public final boolean k() {
        return !l();
    }

    public final boolean l() {
        boolean z = e;
        if (!z && this.a.isEmpty()) {
            throw new AssertionError();
        }
        if (z || com.android.tools.r8.utils.I1.a((AbstractCollection) this.a, new Function() { // from class: com.android.tools.r8.internal.oE$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((C0729q2) obj).isInterface());
            }
        })) {
            return ((C0729q2) this.a.getFirst()).isInterface();
        }
        throw new AssertionError();
    }

    public final boolean m() {
        return this.a.size() < 2;
    }

    public final C0729q2 n() {
        return (C0729q2) this.a.removeLast();
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        return this.a.remove(obj);
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        return this.a.removeAll(collection);
    }

    @Override // java.util.Collection
    public final boolean removeIf(Predicate predicate) {
        return this.a.removeIf(predicate);
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        return this.a.retainAll(collection);
    }

    @Override // java.util.Collection
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        return this.a.toArray();
    }

    public final void a(C2688oE c2688oE) {
        this.a.addAll(c2688oE.a);
    }

    public final void b(C0702m c0702m) {
        Iterable<C0729q2> iterableC = AbstractC2734ot.c(this.a, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.oE$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((C0729q2) obj).J1();
            }
        });
        if (!((S) iterableC.iterator()).hasNext()) {
            iterableC = this.a;
        }
        C0729q2 next = iterableC.iterator().next();
        AbstractC3735i1 abstractC3735i1V = c0702m.v();
        for (C0729q2 c0729q2 : iterableC) {
            if (abstractC3735i1V != null && abstractC3735i1V.a(c0729q2).a((com.android.tools.r8.shaking.K0) c0702m.Y())) {
                next = c0729q2;
                break;
            } else if (c0729q2.getType().D0().d < next.getType().D0().d) {
                next = c0729q2;
            }
        }
        C0729q2 c0729q2A = c0702m.Y().n1.u.a(c0702m, iterableC, next);
        if (!e && j()) {
            throw new AssertionError();
        }
        this.c = c0729q2A;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.a.toArray(objArr);
    }

    public final void a(Consumer consumer) {
        if (!e && !j()) {
            throw new AssertionError();
        }
        for (C0729q2 c0729q2 : this.a) {
            if (c0729q2 != this.c) {
                consumer.accept(c0729q2);
            }
        }
    }

    public final com.android.tools.r8.graph.Y0 a() {
        if (e || g()) {
            return this.b;
        }
        throw new AssertionError();
    }

    public C2688oE(C0729q2 c0729q2) {
        this();
        a(c0729q2);
    }

    public final void a(com.android.tools.r8.graph.Y0 y0) {
        this.b = y0;
    }

    public final void a(final C0702m c0702m) {
        if (!e && !j()) {
            throw new AssertionError();
        }
        final K4 k4C = K4.c();
        a(new Consumer() { // from class: com.android.tools.r8.internal.oE$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0702m, k4C, (C0729q2) obj);
            }
        });
        a(k4C);
    }

    public C2688oE(Iterable iterable) {
        this();
        AbstractC2734ot.a(iterable, (Collection) this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0702m c0702m, final InterfaceC2220iG interfaceC2220iG, C0729q2 c0729q2) {
        C0729q2 c0729q3 = this.c;
        Objects.requireNonNull(interfaceC2220iG);
        InterfaceC2621na.a(c0702m, c0729q2, c0729q3, new BiConsumer() { // from class: com.android.tools.r8.internal.oE$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                interfaceC2220iG.a((com.android.tools.r8.graph.T0) obj, (com.android.tools.r8.graph.T0) obj2);
            }
        });
    }

    public final void a(L4 l4) {
        if (!e && i()) {
            throw new AssertionError();
        }
        this.d = l4;
    }

    public final com.android.tools.r8.graph.m5 a(com.android.tools.r8.graph.m5 m5Var) {
        boolean z = e;
        if (!z && !j()) {
            throw new AssertionError();
        }
        if (z || i()) {
            return m5Var.a() == this.c ? m5Var : new com.android.tools.r8.graph.m5((com.android.tools.r8.graph.T0) c().get(m5Var.e()), this.c);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(C0729q2 c0729q2) {
        return c0729q2 != this.c;
    }
}
