package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2694oJ extends AbstractC1658b1 {
    final Comparator a = new C2603nJ(this);
    final /* synthetic */ EJ b;

    C2694oJ(EJ ej) {
        this.b = ej;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.b.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.equals(this.b.f(entry.getKey()));
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.b.d;
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return this.b.headMap(((InterfaceC2454lJ) obj).getKey()).k();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C2911rJ(this.b);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        return this.b.e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        C2841qJ c2841qJF = this.b.f(((Map.Entry) obj).getKey());
        if (c2841qJF != null) {
            this.b.remove(c2841qJF.a);
        }
        return c2841qJF != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b.c;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return this.b.subMap(((InterfaceC2454lJ) obj).getKey(), ((InterfaceC2454lJ) obj2).getKey()).k();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return this.b.tailMap(((InterfaceC2454lJ) obj).getKey()).k();
    }
}
