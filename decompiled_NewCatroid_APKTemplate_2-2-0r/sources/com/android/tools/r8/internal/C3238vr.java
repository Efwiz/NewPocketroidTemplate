package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3238vr implements InterfaceC3088tr, Serializable {
    protected final InterfaceC3088tr a;

    protected C3238vr(C2879qr c2879qr) {
        this.a = c2879qr;
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr, java.util.Collection
    public final boolean contains(Object obj) {
        return this.a.contains(obj);
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.a.containsAll(collection);
    }

    @Override // java.util.Collection
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return this.a.equals(obj);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        return this.a.f(i);
    }

    @Override // java.util.Collection
    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.a.toArray(objArr);
    }

    public final String toString() {
        return this.a.toString();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final InterfaceC0841Ar iterator() {
        InterfaceC0841Ar it = this.a.iterator();
        C0867Br c0867Br = AbstractC0935Dr.a;
        return new C0907Cr(it);
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        return this.a.toArray();
    }
}
