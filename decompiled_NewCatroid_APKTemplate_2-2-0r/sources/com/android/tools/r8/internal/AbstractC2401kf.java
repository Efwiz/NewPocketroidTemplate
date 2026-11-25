package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2401kf<T extends AbstractC0748t0> implements Collection<T> {
    static final /* synthetic */ boolean c = true;
    protected final Map a;
    protected final Supplier b;

    protected AbstractC2401kf(Supplier supplier) {
        this(supplier, (Map) supplier.get());
    }

    @Override // java.util.Collection
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public boolean add(T t) {
        AbstractC0748t0 abstractC0748t0 = (AbstractC0748t0) this.a.put((C0684j2) t.getReference(), t);
        if (c || abstractC0748t0 == null || abstractC0748t0.b(t)) {
            return abstractC0748t0 == null;
        }
        throw new AssertionError();
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean zAdd = false;
        while (it.hasNext()) {
            zAdd |= add((AbstractC0748t0) it.next());
        }
        return zAdd;
    }

    public final boolean b(AbstractC0748t0 abstractC0748t0) {
        return this.a.containsKey(abstractC0748t0.getReference());
    }

    @Override // java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        if (!(obj instanceof AbstractC0748t0)) {
            return false;
        }
        return this.a.containsKey(((AbstractC0748t0) obj).getReference());
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        return AbstractC2734ot.a((Iterable) collection, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.kf$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.contains(obj);
            }
        });
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.a.values().iterator();
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        if (obj instanceof AbstractC0748t0) {
            return ((AbstractC0748t0) this.a.remove(((AbstractC0748t0) obj).getReference())) != null;
        }
        return false;
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.Collection
    public final boolean removeIf(Predicate predicate) {
        return this.a.values().removeIf(predicate);
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        return this.a.values().retainAll(collection);
    }

    @Override // java.util.Collection
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Collection
    public Stream<T> stream() {
        return this.a.values().stream();
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        return this.a.values().toArray();
    }

    protected AbstractC2401kf(Supplier supplier, Map map) {
        this.a = map;
        this.b = supplier;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.a.values().toArray(objArr);
    }

    public boolean a(com.android.tools.r8.graph.W0 w0) {
        return ((AbstractC0748t0) this.a.remove(w0.getReference())) != null;
    }

    public Set<com.android.tools.r8.graph.W0> a() {
        if (c || (this.a instanceof IdentityHashMap)) {
            return a(new ZC$$ExternalSyntheticLambda0());
        }
        throw new AssertionError();
    }

    public final Set a(IntFunction intFunction) {
        final Set set = (Set) intFunction.apply(size());
        forEach(new Consumer() { // from class: com.android.tools.r8.internal.kf$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC2401kf.a(set, (AbstractC0748t0) obj);
            }
        });
        return set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, AbstractC0748t0 abstractC0748t0) {
        set.add(abstractC0748t0.e());
    }
}
