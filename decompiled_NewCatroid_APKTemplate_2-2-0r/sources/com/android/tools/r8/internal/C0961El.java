package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.El, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0961El implements Set {
    private final IdentityHashMap a = new IdentityHashMap();
    private final Function b;

    public C0961El(Function function) {
        this.b = function;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0744s2 a(com.android.tools.r8.graph.l5 l5Var) {
        return l5Var.getContext().getReference();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        return this.a.put(this.b.apply(obj), obj) == null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean zAdd = false;
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }

    @Override // java.util.Set, java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean contains(Object obj) {
        return this.a.containsKey(this.b.apply(obj));
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.a.values().containsAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.a.values().iterator();
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        return this.a.remove(this.b.apply(obj)) != null;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (Object obj : collection) {
            if (contains(obj)) {
                arrayList.add(obj);
            }
        }
        if (arrayList.size() >= size()) {
            return false;
        }
        clear();
        addAll(arrayList);
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray() {
        return this.a.values().toArray();
    }

    public static C0961El a() {
        return new C0961El(new Function() { // from class: com.android.tools.r8.internal.El$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0961El.a((com.android.tools.r8.graph.l5) obj);
            }
        });
    }

    @Override // java.util.Set, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.a.values().toArray(objArr);
    }
}
