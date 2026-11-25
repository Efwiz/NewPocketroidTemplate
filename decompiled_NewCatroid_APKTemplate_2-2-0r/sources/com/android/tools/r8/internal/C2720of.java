package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.of, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2720of implements Collection {
    private static final C2720of b = new C2720of(Collections.emptySet());
    private final Set a;

    private C2720of(Set set) {
        this.a = set;
    }

    public static C2720of a() {
        return new C2720of(new HashSet());
    }

    public static C2720of b() {
        return new C2720of(OU.a());
    }

    public static C2720of c(C2720of c2720of) {
        return new C2720of(new HashSet(c2720of.a));
    }

    public static C2720of d() {
        return b;
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        return this.a.add((AbstractC0717o2) obj);
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        return this.a.addAll(collection);
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

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.a.iterator();
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

    public static C2720of c() {
        return new C2720of(new LinkedHashSet());
    }

    public final void a(AbstractC0717o2 abstractC0717o2) {
        this.a.add(abstractC0717o2);
    }

    public final boolean b(AbstractC0717o2 abstractC0717o2) {
        return this.a.contains(abstractC0717o2);
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.a.toArray(objArr);
    }

    public final boolean a(C0684j2 c0684j2) {
        return this.a.add(c0684j2.J0());
    }

    public final boolean b(AbstractC0748t0 abstractC0748t0) {
        return this.a.contains(abstractC0748t0.z());
    }

    public final boolean a(com.android.tools.r8.graph.W0 w0) {
        return a(w0.getReference());
    }

    public final boolean b(C2720of c2720of) {
        Iterator it = c2720of.a.iterator();
        while (it.hasNext()) {
            if (this.a.contains((AbstractC0717o2) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final boolean a(AbstractC0748t0 abstractC0748t0) {
        return a(abstractC0748t0.getReference());
    }

    public final void a(Iterable iterable) {
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.of$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    public final void a(C2720of c2720of) {
        this.a.addAll(c2720of.a);
    }

    public final boolean b(com.android.tools.r8.graph.W0 w0) {
        return this.a.remove(w0.o1());
    }

    public final void a(Iterable iterable, Function function) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            AbstractC2734ot.a((Iterable) function.apply(it.next()), (Collection) this);
        }
    }

    public final void b(Iterable iterable) {
        iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.of$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((com.android.tools.r8.graph.W0) obj);
            }
        });
    }
}
