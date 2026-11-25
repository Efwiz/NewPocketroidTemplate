package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1138Kp extends AbstractC1658b1 {
    final /* synthetic */ C1329Rp a;

    C1138Kp(C1329Rp c1329Rp) {
        this.a = c1329Rp;
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
        C1020Gp c1020GpD;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getKey() != null && (entry.getKey() instanceof Integer) && (c1020GpD = this.a.i.d(((Integer) entry.getKey()).intValue())) != null && this.a.d(c1020GpD.a) && entry.equals(c1020GpD);
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.a.k();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return ((C1329Rp) this.a.c(((InterfaceC2806pp) obj).a())).b();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        C1329Rp c1329Rp = this.a;
        C1020Gp c1020Gp = c1329Rp.i.d;
        return !(c1329Rp.k() != null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C1218Np(this.a);
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
        C1020Gp c1020GpD = this.a.i.d(((Integer) entry.getKey()).intValue());
        if (c1020GpD != null && this.a.d(c1020GpD.a)) {
            this.a.remove(c1020GpD.a);
        }
        return c1020GpD != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Object it = iterator();
        int i = 0;
        while (((AbstractC1355Sp) it).hasNext()) {
            i++;
            ((C1218Np) it).a();
        }
        return i;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return ((C1329Rp) this.a.a(((InterfaceC2806pp) obj).a(), ((InterfaceC2806pp) obj2).a())).b();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return ((C1329Rp) this.a.b(((InterfaceC2806pp) obj).a())).b();
    }
}
