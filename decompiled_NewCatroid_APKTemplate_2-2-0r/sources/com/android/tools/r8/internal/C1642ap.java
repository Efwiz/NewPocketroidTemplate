package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ap, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1642ap extends AbstractC1658b1 {
    final /* synthetic */ C2181hp a;

    C1642ap(C2181hp c2181hp) {
        this.a = c2181hp;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a.i.b().comparator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C1481Wo c1481WoD;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getKey() != null && (entry.getKey() instanceof Integer) && (c1481WoD = this.a.i.d(((Integer) entry.getKey()).intValue())) != null && this.a.d(c1481WoD.a) && entry.equals(c1481WoD);
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.a.k();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return ((C2181hp) this.a.c(((InterfaceC2806pp) obj).a())).b();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        C2181hp c2181hp = this.a;
        C1481Wo c1481Wo = c2181hp.i.d;
        return !(c2181hp.k() != null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C1866dp(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        return this.a.l();
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
        C1481Wo c1481WoD = this.a.i.d(((Integer) entry.getKey()).intValue());
        if (c1481WoD != null && this.a.d(c1481WoD.a)) {
            this.a.remove(c1481WoD.a);
        }
        return c1481WoD != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Object it = iterator();
        int i = 0;
        while (((AbstractC2259ip) it).hasNext()) {
            i++;
            ((C1866dp) it).a();
        }
        return i;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return ((C2181hp) this.a.a(((InterfaceC2806pp) obj).a(), ((InterfaceC2806pp) obj2).a())).b();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return ((C2181hp) this.a.b(((InterfaceC2806pp) obj).a())).b();
    }
}
