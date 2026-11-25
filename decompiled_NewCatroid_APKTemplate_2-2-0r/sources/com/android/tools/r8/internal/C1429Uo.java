package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Uo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1429Uo extends AbstractC1658b1 {
    final Comparator a = new C1381To(this);
    final /* synthetic */ C2413kp b;

    C1429Uo(C2413kp c2413kp) {
        this.b = c2413kp;
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
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
            return false;
        }
        return entry.equals(this.b.d(((Integer) entry.getKey()).intValue()));
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.b.d;
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return ((C2181hp) this.b.c(((InterfaceC2806pp) obj).a())).b();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C1510Xo(this.b);
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
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
            return false;
        }
        C1481Wo c1481WoD = this.b.d(((Integer) entry.getKey()).intValue());
        if (c1481WoD != null) {
            this.b.remove(c1481WoD.a);
        }
        return c1481WoD != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b.c;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return ((C2181hp) this.b.a(((InterfaceC2806pp) obj).a(), ((InterfaceC2806pp) obj2).a())).b();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return ((C2181hp) this.b.b(((InterfaceC2806pp) obj).a())).b();
    }
}
