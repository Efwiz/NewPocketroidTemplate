package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2428l0 extends PD {
    final /* synthetic */ AbstractC2889r0 b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2428l0(AbstractC2889r0 abstractC2889r0, Map map) {
        super(map);
        this.b = abstractC2889r0;
    }

    @Override // com.android.tools.r8.internal.PD, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        Iterator it = iterator();
        int i = AbstractC3123uM.a;
        while (true) {
            C2348k0 c2348k0 = (C2348k0) it;
            if (!c2348k0.hasNext()) {
                return;
            }
            c2348k0.next();
            c2348k0.remove();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        return this.a.keySet().containsAll(collection);
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        return this == obj || this.a.keySet().equals(obj);
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.a.keySet().hashCode();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new C2348k0(this, this.a.entrySet().iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int size;
        Collection collection = (Collection) this.a.remove(obj);
        if (collection != null) {
            size = collection.size();
            collection.clear();
            AbstractC2889r0.b(this.b, size);
        } else {
            size = 0;
        }
        return size > 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return this.a.keySet().spliterator();
    }
}
