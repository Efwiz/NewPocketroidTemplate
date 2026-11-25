package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2027fq extends AbstractC1658b1 {
    final /* synthetic */ C2567mq a;

    C2027fq(C2567mq c2567mq) {
        this.a = c2567mq;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a.i.c().comparator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C1718bq c1718bqD;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getKey() != null && (entry.getKey() instanceof Integer) && (c1718bqD = this.a.i.d(((Integer) entry.getKey()).intValue())) != null && this.a.d(c1718bqD.a) && entry.equals(c1718bqD);
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.a.k();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return ((C2567mq) this.a.c(((InterfaceC0934Dq) obj).a())).c();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        C2567mq c2567mq = this.a;
        C1718bq c1718bq = c2567mq.i.d;
        return !(c2567mq.k() != null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C2260iq(this.a);
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
        C1718bq c1718bqD = this.a.i.d(((Integer) entry.getKey()).intValue());
        if (c1718bqD != null && this.a.d(c1718bqD.a)) {
            this.a.remove(c1718bqD.a);
        }
        return c1718bqD != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Object it = iterator();
        int i = 0;
        while (((AbstractC2643nq) it).hasNext()) {
            i++;
            ((C2260iq) it).a();
        }
        return i;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return ((C2567mq) this.a.a(((InterfaceC0934Dq) obj).a(), ((InterfaceC0934Dq) obj2).a())).c();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return ((C2567mq) this.a.b(((InterfaceC0934Dq) obj).a())).c();
    }
}
