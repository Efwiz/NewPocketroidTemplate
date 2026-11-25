package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3120uJ extends AbstractC1658b1 {
    final /* synthetic */ BJ a;

    C3120uJ(BJ bj) {
        this.a = bj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a.i.k().comparator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        C2841qJ c2841qJF = this.a.i.f(entry.getKey());
        return c2841qJF != null && this.a.f(c2841qJF.a) && entry.equals(c2841qJF);
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.a.l();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return this.a.headMap(((InterfaceC2454lJ) obj).getKey()).k();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        BJ bj = this.a;
        C2841qJ c2841qJ = bj.i.d;
        return !(bj.l() != null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C3348xJ(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        return this.a.m();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        C2841qJ c2841qJF = this.a.i.f(((Map.Entry) obj).getKey());
        if (c2841qJF != null && this.a.f(c2841qJF.a)) {
            this.a.remove(c2841qJF.a);
        }
        return c2841qJF != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Object it = iterator();
        int i = 0;
        while (((CJ) it).hasNext()) {
            i++;
            ((C3348xJ) it).a();
        }
        return i;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return this.a.subMap(((InterfaceC2454lJ) obj).getKey(), ((InterfaceC2454lJ) obj2).getKey()).k();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return this.a.tailMap(((InterfaceC2454lJ) obj).getKey()).k();
    }
}
