package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ep, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0965Ep extends AbstractC1658b1 {
    final Comparator a = new C0933Dp(this);
    final /* synthetic */ C1430Up b;

    C0965Ep(C1430Up c1430Up) {
        this.b = c1430Up;
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
        return ((C1329Rp) this.b.c(((InterfaceC2806pp) obj).a())).b();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C1051Hp(this.b);
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
        C1020Gp c1020GpD = this.b.d(((Integer) entry.getKey()).intValue());
        if (c1020GpD != null) {
            this.b.remove(c1020GpD.a);
        }
        return c1020GpD != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b.c;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return ((C1329Rp) this.b.a(((InterfaceC2806pp) obj).a(), ((InterfaceC2806pp) obj2).a())).b();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return ((C1329Rp) this.b.b(((InterfaceC2806pp) obj).a())).b();
    }
}
