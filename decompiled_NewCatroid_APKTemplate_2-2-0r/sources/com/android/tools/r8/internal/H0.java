package com.android.tools.r8.internal;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class H0 extends AbstractCollection implements InterfaceC1600aG {
    private transient Set a;
    private transient Set b;

    H0() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public Set I() {
        Set set = this.a;
        if (set != null) {
            return set;
        }
        Set setA = a();
        this.a = setA;
        return setA;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public int a(Object obj, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        a(obj, 1);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean addAll(Collection collection) {
        return AbstractC1987fG.a((InterfaceC1600aG) this, collection);
    }

    Set b() {
        return new G0(this);
    }

    abstract int c();

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        return b(obj) > 0;
    }

    abstract Iterator d();

    abstract Iterator e();

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public Set entrySet() {
        Set set = this.b;
        if (set != null) {
            return set;
        }
        Set setB = b();
        this.b = setB;
        return setB;
    }

    @Override // java.util.Collection, com.android.tools.r8.internal.InterfaceC1600aG
    public final boolean equals(Object obj) {
        return AbstractC1987fG.a(this, obj);
    }

    @Override // java.util.Collection, com.android.tools.r8.internal.InterfaceC1600aG
    public final int hashCode() {
        return entrySet().hashCode();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return entrySet().isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        return b(1, obj) > 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean removeAll(Collection collection) {
        if (collection instanceof InterfaceC1600aG) {
            collection = ((InterfaceC1600aG) collection).I();
        }
        return I().removeAll(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean retainAll(Collection collection) {
        int i = AbstractC3123uM.a;
        collection.getClass();
        if (collection instanceof InterfaceC1600aG) {
            collection = ((InterfaceC1600aG) collection).I();
        }
        return I().retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return entrySet().toString();
    }

    Set a() {
        return new F0(this);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public int a(Object obj) {
        AbstractC2625nb.a(0, "count");
        int iB = b(obj);
        int i = 0 - iB;
        if (i > 0) {
            a(obj, i);
        } else if (i < 0) {
            b(-i, obj);
        }
        return iB;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public boolean a(int i, Object obj) {
        AbstractC2625nb.a(i, "oldCount");
        AbstractC2625nb.a(0, "newCount");
        if (b(obj) != i) {
            return false;
        }
        a(obj);
        return true;
    }
}
